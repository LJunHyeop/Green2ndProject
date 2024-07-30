package com.green.fefu.security.oauth2;

import com.green.fefu.parents.ParentsUserMapper;
import com.green.fefu.parents.model.*;
import com.green.fefu.security.MyUserDetails;
import com.green.fefu.security.MyUserOAuth2Vo;
import com.green.fefu.security.SignInProviderType;
import com.green.fefu.security.oauth2.userinfo.OAuth2UserInfo;
import com.green.fefu.security.oauth2.userinfo.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/*
    MyOAuth2UserService:
    OAuth2 제공자(구글, 페이스북, 카카오, 네이버 등)로부터 Access-Token 받은 후 loadUser메소드가 호출이 됩니다.
    ( 스프링 시큐리티에 구현되어 있음 )
    OAuth2 제공자로부터 사용자 정보를 가져온다. (이미 구현되어 있음. super.loadUser(userRequest);)
    OAuth2User 인터페이스를 구현한 객체(인증 객체)를 리턴

    FE > 플랫폼 소셜로그인 아이콘 클릭(리다이렉트 정보 전달 -- 이 리다이렉트는 로그인 완료 후 다시 돌아올 FE 주소값)
       > 백엔드에 요청('나 무슨 소셜로그인 하고 싶어'에 대한 정보가 전달)
       > 백엔드는 리다이렉트 (OAuth2 제공자 로그인 화면)
       > 해당 제공자의 아이디/비번을 작성 후 로그인 처리
       > 제공자는 인가 코드를 백엔드에게 보내준다.
       > (해당) 백엔드는 인가 코드를 가지고 access-token을 발급받는다.
       > (해당) access-token으로 사용자 정보(scope에 작성한 내용)를 받는다.
       > 이후는 자체 로그인 처리
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MyOAuth2UserService extends DefaultOAuth2UserService {
    private final ParentsUserMapper mapper ;
    private final OAuth2UserInfoFactory oAuth2UserInfoFactory ;
    private ClientRegistrationRepository clientRegistrationRepository ;
    private final OAuth2UserInfoFactory factory ;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            return this.process(userRequest);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest); //제공자로부터 사용자정보를 얻음
        //각 소셜플랫폼에 맞는 enum타입을 얻는다.
        SignInProviderType signInProviderType = SignInProviderType.valueOf(userRequest.getClientRegistration()
                                                                                      .getRegistrationId()
                                                                                      .toUpperCase()
        );

        //규격화된 UserInfo객체로 변환
        // oAuth2User.getAttributes() > Data가 HashMap 객체로 변환
        OAuth2UserInfo oAuth2UserInfo = oAuth2UserInfoFactory.getOAuth2UserInfo(signInProviderType, oAuth2User.getAttributes());

        //기존에 회원가입이 되어있는가 체크
        SignInPostReq signInParam = new SignInPostReq();
        signInParam.setUid(oAuth2UserInfo.getId()); //플랫폼에서 넘어오는 유니크값(항상 같은 값이며 다른 사용자와 구별되는 유니크 값)
        signInParam.setProviderType(signInProviderType.name());
        List<ParentsUser> list = mapper.getParentUser(signInParam.getUid()) ;

        // 연동된 아이디가 없을 시
        if(list == null) {
            throw new IllegalArgumentException("연동된 아이디가 없습니다.") ;
        }

        // 로컬 사용자 정보를 가져오는 로직
        SocialUserEntity localUser = getLocalUser(list.get(0).getParentsId()) ;
        if(localUser == null) {
            throw new IllegalArgumentException("로컬 사용자가 존재하지 않습니다.") ;
        }

        SocialLoginRequest socialLoginRequest = new SocialLoginRequest() ;
        socialLoginRequest.setProviderType(signInProviderType) ;
        socialLoginRequest.setToken(oAuth2UserInfo.getId()) ;

        SocialUserInfo socialUserInfo = verifyAndGetUserInfo(socialLoginRequest) ;

        // 로컬 사용자와 소셜 사용자를 연동
        linkSocialAccountToLocalUser(localUser.getUserId(), socialUserInfo, signInProviderType.name()) ;

        MyUserOAuth2Vo myUserOAuth2Vo
                = new MyUserOAuth2Vo(list.get(0).getParentsId(), list.get(0).getAuth(), list.get(0).getNm(), null) ;

        MyUserDetails signInUser = new MyUserDetails();
        signInUser.setMyUser(myUserOAuth2Vo);
        return signInUser ;
    }
     // 사용자정보 검증 후 가져옴
    public SocialUserInfo verifyAndGetUserInfo(SocialLoginRequest socialToken) {
        SignInProviderType providerType = socialToken.getProviderType() ;
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(providerType.name().toLowerCase()) ;

        OAuth2UserRequest oAuth2UserRequest = new OAuth2UserRequest(
                clientRegistration,
                new OAuth2AccessToken(
                        OAuth2AccessToken.TokenType.BEARER,
                        socialToken.getToken(),
                        Instant.now(),
                        Instant.now().plusSeconds(3600)
                )
        ) ;

        // 소셜로그인 API 호출
        OAuth2User oAuth2User = loadUser(oAuth2UserRequest) ;

        // 사용자 정보 변환
        OAuth2UserInfo oAuth2UserInfo = factory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes()) ;
        SocialUserInfo userInfo = new SocialUserInfo() ;
        userInfo.setId(oAuth2UserInfo.getId()) ;
        userInfo.setEmail(oAuth2UserInfo.getEmail()) ;
        userInfo.setName(oAuth2UserInfo.getName()) ;
        return userInfo ;
    }
     // 연동
    public void linkSocialAccountToLocalUser(long localUserId, SocialUserInfo socialUserInfo, String provider){
        SocialUserEntity entity = new SocialUserEntity() ;
        entity.setUserId(localUserId) ;
        entity.setProvider(provider) ;
        entity.setSocialId(socialUserInfo.getId()) ;
        entity.setSocialEmail(socialUserInfo.getEmail()) ;
        entity.setSocialName(socialUserInfo.getName()) ;

        SocialUserEntity existingSocialUser = mapper.findUser(localUserId, provider) ;
        if(existingSocialUser == null){
            mapper.insUserEntity(socialUserInfo) ;
        }
    }

    public SocialUserEntity getLocalUser(long userId){
        List<SocialUserEntity> users = mapper.findAllGet(userId);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
