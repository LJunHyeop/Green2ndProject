package com.green.fefu.parents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.fefu.chcommon.SmsSender;
import com.green.fefu.common.AppProperties;
import com.green.fefu.common.CookieUtils;
import com.green.fefu.common.CustomFileUtils;
import com.green.fefu.parents.model.*;
import com.green.fefu.parents.model.ParentsUserEntity;
import com.green.fefu.parents.model.PostParentsUserReq;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.security.MyUser;
import com.green.fefu.security.MyUserDetails;
import com.green.fefu.security.jwt.JwtTokenProviderV2;
import com.green.fefu.sms.SmsService;
import com.green.fefu.student.model.req.createStudentReq;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import({ParentsUserServiceImpl.class})
class ParentsUserServiceTest {
    @Value("${file.directory}") String uploadPath;
    @MockBean private ParentsUserMapper mapper;
    @Autowired private ParentsUserService service;
    @MockBean private CustomFileUtils customFileUtils;
    @MockBean private ObjectMapper om ;
    @MockBean private PasswordEncoder passwordEncoder;
    @MockBean private JwtTokenProviderV2 jwtTokenProvider;
    @MockBean private CookieUtils cookieUtils;
    @MockBean private AuthenticationFacade authenticationFacade;
    @MockBean private Authentication authentication ;
    @MockBean private AppProperties appProperties;
    @MockBean private SmsService smsService ;
    private final String ID_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,12}$";
    private final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d|.*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,20}$";
    private final Pattern idPattern = Pattern.compile(ID_PATTERN);
    private final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    private final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    @Value("${coolsms.api.caller}") private String coolsmsApiCaller;

    private ParentsUserEntity p1;
    private PatchPasswordReq req1;
    private GetParentsUserReq q;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        Authentication authentication = mock(Authentication.class);
//        SecurityContext securityContext = mock(SecurityContext.class);
//        SecurityContextHolder.setContext(securityContext);
//        given(securityContext.getAuthentication()).willReturn(authentication);
//
//        MyUserDetails myUserDetails = mock(MyUserDetails.class);
//        MyUser myUser = new MyUser();
//        myUser.setUserId(1L);
//        given(authentication.getPrincipal()).willReturn(myUserDetails);
//        given(myUserDetails.getMyUser()).willReturn(myUser);
        String encodedOldPassword = "encodedOldPassword";
        String encodedNewPassword = "encodedNewPassword";

        p1 = new ParentsUserEntity();
        p1.setParentsId(1L);
        p1.setUid("pG123456");
        p1.setUpw(encodedOldPassword);
        p1.setNm("홍길동");
        p1.setEmail("12345@naver.com");
        p1.setPhone("010-1234-1234");
        p1.setConnet("부");
        p1.setAuth("ROLE_USER");
        p1.setAcept(2);

        req1 = new PatchPasswordReq();
        req1.setParentsId(p1.getParentsId());
        req1.setUid(p1.getUid());
        req1.setNewUpw(encodedNewPassword);

        q = new GetParentsUserReq();
        q.setSignedUserId(p1.getParentsId());

        // Mock 설정
        given(authenticationFacade.getLoginUserId()).willReturn(p1.getParentsId());
        given(mapper.getParentsUser(q)).willReturn(p1);
        given(passwordEncoder.encode(req1.getNewUpw())).willReturn(encodedNewPassword);
        given(mapper.patchPassword(any(PatchPasswordReq.class))).willReturn(1);
    }
    @Test @DisplayName("post 1") // 회원가입
    void postParentsUser() {
        PostParentsUserReq p1 = new PostParentsUserReq();
        p1.setUid("pG123456");
        p1.setUpw("aAbB!@1212");
        p1.setNm("홍길동");
        p1.setEmail("12345@naver.com");
        p1.setPhone("010-1234-1234");
        p1.setConnet("부");

        PostParentsUserReq p2 = new PostParentsUserReq();
        p2.setUid("pG234567");
        p2.setUpw("aAbB!@1212");
        p2.setNm("김길동");
        p1.setEmail("12345678@naver.com");
        p2.setPhone("010-2345-2345");
        p2.setConnet("모");

        given(mapper.postParentsUser(p1)).willReturn(1);
        given(mapper.postParentsUser(p2)).willReturn(1);

        assertEquals(1, service.postParentsUser(p1),"1. 이상");
        assertEquals(1, service.postParentsUser(p2),"2. 이상");

        verify(mapper).postParentsUser(p1);
        verify(mapper).postParentsUser(p2);
    }
    @Test @DisplayName("회원정보 확인") // 회원정보 확인
    void getParentsUser() {
        HttpServletResponse res = null ;
        long parentsId = 100 ;
        PostParentsUserReq p1 = new PostParentsUserReq();
        p1.setUid("pG123456");
        p1.setUpw("aAbB!@1212");
        p1.setNm("홍길동");
        p1.setEmail("12345@naver.com");
        p1.setPhone("010-1234-1234");
        p1.setConnet("부");
        p1.setParentsId(parentsId) ;

        String token = "accessToken" ;
        Authentication auth = mock(Authentication.class) ;
        MyUserDetails myUserDetails = mock(MyUserDetails.class) ;
        MyUser myUser = new MyUser() ;
        myUser.setUserId(p1.getParentsId());

        given(jwtTokenProvider.getAuthentication(token)).willReturn(auth) ;
        given(auth.getPrincipal()).willReturn(myUserDetails) ;
        given(myUserDetails.getMyUser()).willReturn(myUser) ;

        GetParentsUserReq req = new GetParentsUserReq() ;
        req.setSignedUserId(p1.getParentsId()) ;
        ParentsUserEntity entity = new ParentsUserEntity() ;
        entity.setParentsId(p1.getParentsId()) ;
        entity.setUid(p1.getUid());
        entity.setUpw(p1.getUpw());
        entity.setNm(p1.getNm());
        entity.setEmail(p1.getEmail());
        entity.setPhone(p1.getPhone());
        entity.setConnet(p1.getConnet());

        System.out.println("Request: " + req) ;
        System.out.println("Entity: " + entity);

        given(mapper.getParentsUser(eq(req))).willReturn(entity) ;

        ParentsUserEntity res0 = service.getParentsUser(token) ;

        System.out.println("ResultEntity: " + res0) ;

        assertEquals(entity, res0) ;
        verify(jwtTokenProvider).getAuthentication(token) ;
        verify(mapper).getParentsUser(eq(req)) ;

        long parentsId2 = 200L;
        String token2 = "accessToken2";

        PostParentsUserReq p2 = new PostParentsUserReq();
        p2.setUid("pG234567");
        p2.setUpw("aAbB!@1212");
        p2.setNm("김길동");
        p2.setEmail("12345678@naver.com");
        p2.setPhone("010-2345-2345");
        p2.setConnet("모");
        p2.setParentsId(parentsId2);

        Authentication auth2 = mock(Authentication.class);
        MyUserDetails myUserDetails2 = mock(MyUserDetails.class);
        MyUser myUser2 = new MyUser();
        myUser2.setUserId(parentsId2);

        given(jwtTokenProvider.getAuthentication(token2)).willReturn(auth2);
        given(auth2.getPrincipal()).willReturn(myUserDetails2);
        given(myUserDetails2.getMyUser()).willReturn(myUser2);

        GetParentsUserReq req2 = new GetParentsUserReq();
        req2.setSignedUserId(parentsId2);

        given(mapper.getParentsUser(eq(req2))).willReturn(null);

        ParentsUserEntity res2 = service.getParentsUser(token2);

        assertNull(res2);
        verify(jwtTokenProvider).getAuthentication(token2);
        verify(mapper).getParentsUser(eq(req2));

    }
    @Test @DisplayName("정보수정") // 회원정보 수정
    void patchParentsUser() {
        PostParentsUserReq p1 = new PostParentsUserReq();
        p1.setUid("p1");
        p1.setNm("홍길동");
        p1.setPhone("010-1234-1234");
        p1.setConnet("부");
        p1.setParentsId(1L);
        ParentsUserEntity entity = new ParentsUserEntity();
        entity.setParentsId(p1.getParentsId());
        PatchParentsUserReq req = new PatchParentsUserReq();
        req.setParentsId(entity.getParentsId());
        req.setAddr("대구");
        given(mapper.patchParentsUser(req)).willReturn(1);
        assertNotEquals(p1.getAddr(), req.getAddr());
        int result = service.patchParentsUser(req);
        assertEquals(1, result);
        verify(mapper).patchParentsUser(req);

        ParentsUserEntity entity1 = new ParentsUserEntity();
        entity1.setParentsId(p1.getParentsId());
        PatchParentsUserReq req1 = new PatchParentsUserReq();
        req1.setParentsId(entity1.getParentsId());
        req1.setConnet("모");
        req1.setNm("유관순");
        given(mapper.patchParentsUser(req1)).willReturn(2);
        int result1 = service.patchParentsUser(req1);
        assertEquals(2, result1);

        ParentsUserEntity entity2 = new ParentsUserEntity();
        entity2.setParentsId(2L);
        PatchParentsUserReq req2 = new PatchParentsUserReq();
        req2.setParentsId(entity2.getParentsId());
        req2.setConnet("기타");
        given(mapper.patchParentsUser(req2)).willReturn(0);
        int result2 = service.patchParentsUser(req2);
        assertEquals(0, result2);
    }
    @Test @DisplayName("아이디 찾기") // 아이디 찾기
    void getFindId() {
        PostParentsUserReq p1 = new PostParentsUserReq();
        p1.setUid("p1");
        p1.setNm("홍길동");
        p1.setPhone("010-1234-1234");
        p1.setConnet("부");
        p1.setParentsId(1L);

        ParentsUserEntity entity = new ParentsUserEntity();
        entity.setParentsId(p1.getParentsId());

        GetFindIdReq req = new GetFindIdReq();
        req.setPhone("010-1234-1234");
        req.setNm("홍길동");

        GetFindIdRes beforeRes = new GetFindIdRes();
        beforeRes.setUid(entity.getUid());

        given(mapper.getFindId(req)).willReturn(beforeRes);
        GetFindIdRes afterRes = service.getFindId(req);

        assertEquals(beforeRes.getUid(), afterRes.getUid());
        verify(mapper).getFindId(req);
    }
    @Test @DisplayName("아이디 찾기2") // 아이디 찾기
    void getFindId2() {
        GetFindIdReq req = new GetFindIdReq();
        req.setPhone("010-1234-3456");
        req.setNm("홍길동");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.getFindId(req) ;
        }) ;

        assertEquals("해당 요청에 대한 정보가 존재하지 않습니다.", exception.getMessage());
        verify(mapper).getFindId(req);
    }
    @Test @DisplayName("비밀번호 수정") // 비밀번호 수정 성공
    void patchPassword() {
//        String encodedOldPassword = "encodedOldPassword";
//        String encodedNewPassword = "encodedNewPassword";
//        ParentsUserEntity p1 = new ParentsUserEntity();
//        p1.setParentsId(1L);
//        p1.setUid("pG123456");
//        p1.setUpw(encodedOldPassword);
//        p1.setNm("홍길동");
//        p1.setEmail("12345@naver.com");
//        p1.setPhone("010-1234-1234");
//        p1.setConnet("부");
//        p1.setAuth("ROLE_USER");
//        p1.setAcept(2);
//
//        PatchPasswordReq req1 = new PatchPasswordReq();
//        req1.setParentsId(p1.getParentsId());
//        req1.setUid(p1.getUid());
//        req1.setNewUpw(encodedNewPassword);
//
//        GetParentsUserReq q = new GetParentsUserReq() ;
//        q.setSignedUserId(authenticationFacade.getLoginUserId()) ;
//        given(authenticationFacade.getLoginUserId()).willReturn(p1.getParentsId()) ;
//        given(mapper.getParentsUser(q)).willReturn(p1) ;
//        given(passwordEncoder.encode(req1.getNewUpw())).willReturn(encodedNewPassword);
//        given(mapper.patchPassword(any(PatchPasswordReq.class))).willReturn(1);

//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            mapper.getParentsUser(q) ;
//        }) ;
//        assertEquals("아이디를 확인해 주세요", exception.
        given(mapper.getParentsUser(q)).willReturn(p1) ;
        int result = service.patchPassword(req1) ;

        assertEquals(1, result);
        verify(mapper).patchPassword(argThat(req ->
                req.getParentsId() == p1.getParentsId() &&
                req.getNewUpw().equals("encodedNewPassword")
        ));
    }
    @Test @DisplayName("비밀번호 수정2") // 비밀번호 수정 실패
    void patchPassword2() {
        given(mapper.getParentsUser(q)).willReturn(null) ;
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.patchPassword(req1) ;
        }) ;
        assertEquals("아이디를 확인해 주세요", exception.getMessage()) ;
    }
    @Test @DisplayName("로그인") // 로그인
    void signInPost() {
        HttpServletResponse res = null;
        SignInPostReq req1 = new SignInPostReq();
        req1.setUid("p1");
        req1.setUpw("1212");
        String password = passwordEncoder.encode(req1.getUpw());

        ParentsUser user1 = new ParentsUser();
        user1.setParentsId(1L);
        user1.setUid(req1.getUid());
        user1.setUpw(password);
        user1.setNm("길동");
        user1.setPhone("010-1234-1234");
        user1.setConnet("부");
        user1.setAuth("ROLE_USER");
        user1.setAcept(2);
        user1.setCreatedAt("2024-07-01 15:49:23");

        given(mapper.signInPost(req1.getUid())).willReturn(user1);

        String accessToken = "access-token";
        String refreshToken = "refresh-token";
        given(jwtTokenProvider.generateAccessToken(any(MyUser.class))).willReturn(accessToken);
        given(jwtTokenProvider.generateRefreshToken(any(MyUser.class))).willReturn(refreshToken);

        AppProperties.Jwt jwtProperties = mock(AppProperties.Jwt.class);
        given(appProperties.getJwt()).willReturn(jwtProperties);
        given(jwtProperties.getRefreshTokenCookieMaxAge()).willReturn(3600);

        try (MockedStatic<BCrypt> mockedStatic = mockStatic(BCrypt.class)) {
            mockedStatic.when(() -> BCrypt.checkpw(req1.getUpw(), password)).thenReturn(true);

            SignInPostRes res1 = service.signInPost(req1, res);

            assertEquals(user1.getParentsId(), res1.getParentsId(), "1. 이상");
            assertEquals(user1.getNm(), res1.getNm(),"2. 이상");
            assertEquals(accessToken, res1.getAccessToken(), "3. 이상");

            mockedStatic.verify(() -> BCrypt.checkpw(req1.getUpw(), password));
        }
        verify(mapper).signInPost(req1.getUid());
        verify(jwtTokenProvider).generateAccessToken(any(MyUser.class));
        verify(jwtTokenProvider).generateRefreshToken(any(MyUser.class));
        verify(cookieUtils).deleteCookie(res, "refresh-token");
        verify(cookieUtils).setCookie(res, "refresh-token",refreshToken,3600);
    }
    @Test @DisplayName("비밀번호 찾기 문자발송") // 비밀번호 찾기 문자발송
    void getFindPassword() throws JsonProcessingException {
        String code = "123456" ;
        GetFindPasswordReq req = new GetFindPasswordReq() ;
        req.setUid(p1.getUid()) ;
        req.setPhone(p1.getPhone()) ;
        List<ParentsUserEntity> list = List.of(p1) ;

        given(mapper.getParentUserList(req)).willReturn(list) ;
        doNothing().when(smsService).sendPasswordSms(req.getPhone(), coolsmsApiCaller, code) ;

        mockStatic(SmsSender.class) ;
        when(SmsSender.makeRandomCode()).thenReturn(code) ;

        Map<String, Object> map = new HashMap<>() ;
        service.getFindPassword(req, map) ;
        assertEquals(code, map.get("RANDOM_CODE"));

        verify(smsService).sendPasswordSms(req.getPhone(), coolsmsApiCaller, code) ;
    }
    @Test @DisplayName("비밀번호 찾기 문자발송2") // 비밀번호 찾기 문자발송 회원정보 X
    void getFindPassword2() throws IOException {
        String phone = "010-1234-5678";
        String code = "123456";
        GetFindPasswordReq req = new GetFindPasswordReq();
        req.setPhone(phone);
        List<ParentsUserEntity> list = List.of(); // 빈 리스트를 반환하도록 설정

        // Mock 설정
        given(mapper.getParentUserList(req)).willReturn(list);
        doNothing().when(smsService).sendPasswordSms(phone, coolsmsApiCaller, code);

        // map 생성
        Map<String, String> map = new HashMap<>();

        // 예외가 발생하는지 확인
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.getFindPassword(req, map);
        });
        assertEquals("회원정보가 존재하지 않습니다.", exception.getMessage());
    }
    @Test @DisplayName("전자서명") // 전자서명
    void signature() throws Exception {
        String filePath = "D:\\download\\2nd" ;
        MultipartFile file = new MockMultipartFile("pic", "a.png","image/png",
                new FileInputStream(String.format("%s/sign/a.png", filePath)));
        createStudentReq req = new createStudentReq() ;
        req.setPk(1);
        SignatureReq signatureReq = SignatureReq
                .builder()
                .stuId(req.getPk())
                .year(2024)
                .pic(String.valueOf(file))
                .semester(1)
                .build() ;
        String randomFileName = "aaa.jpg" ;

        given(customFileUtils.makeRandomFileName(file)).willReturn(randomFileName) ;
        SignatureRes res = service.signature(file, signatureReq) ;

        String path = String.format("sign/%d", signatureReq.getSignId()) ;
        verify(customFileUtils).makeFolders(path) ;
        verify(customFileUtils).makeRandomFileName(file) ;
        String filePicPath = String.format("%s/%s", path, randomFileName) ;
        verify(customFileUtils).transferTo(file, filePicPath) ;
        verify(mapper).signature(signatureReq) ;

    }
    @Test @DisplayName("전자서명 없음") // 전자서명 없음
    void signature2() throws Exception {
        MultipartFile file = null ;
        given(customFileUtils.makeRandomFileName(file)).willReturn(null);

        createStudentReq req = new createStudentReq();
        req.setPk(1);
        SignatureReq signatureReq = SignatureReq.builder()
                .stuId(req.getPk())
                .year(2024)
                .pic(null)
                .semester(1)
                .build();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.signature(file, signatureReq);
        });
        assertTrue(exception.getMessage().contains("서명 파일이 없습니다."));
    }
    @Test @DisplayName("accessToken 가져오기 - 성공")
    void getAccessToken() {
        HttpServletRequest req = mock(HttpServletRequest.class) ;

        Cookie cookie = new Cookie("refresh-token", "valid-refresh-token") ;
        given(cookieUtils.getCookie(req, "refresh-token")).willReturn(cookie) ;

        given(jwtTokenProvider.isValidateToken("valid-refresh-token")).willReturn(true);

        MyUser myUser = MyUser.builder()
                .userId(1)
                .role("ROLE_PARENTS")
                .build() ;

        MyUserDetails userDetails = new MyUserDetails(); // MyUserDetails 객체를 올바르게 생성
        userDetails.setMyUser(myUser) ;

        given(jwtTokenProvider.getUserDetailsFromToken("valid-refresh-token")).willReturn(userDetails) ;
        given(jwtTokenProvider.generateAccessToken(myUser)).willReturn("new-access-token") ;

        Map<String, Object> result = service.getAccessToken(req) ;
        assertEquals("new-access-token", result.get("accessToken")) ;

        verify(cookieUtils).getCookie(req, "refresh-token") ;
        verify(jwtTokenProvider).isValidateToken("valid-refresh-token") ;
        verify(jwtTokenProvider).getUserDetailsFromToken("valid-refresh-token") ;
        verify(jwtTokenProvider).generateAccessToken(myUser) ;
    }
}



























