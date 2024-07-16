package com.green.fefu.parents;

import com.green.fefu.chcommon.Parser;
import com.green.fefu.chcommon.SmsSender;
import com.green.fefu.security.MyUser;
import com.green.fefu.parents.model.*;
import com.green.fefu.security.MyUserDetails;
import com.green.fefu.sms.SmsService;
import com.green.fefu.common.AppProperties;
import com.green.fefu.common.CookieUtils;
import com.green.fefu.common.CustomFileUtils;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.security.jwt.JwtTokenProviderV2;
import com.green.fefu.student.service.StudentMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Pattern;

import static com.green.fefu.teacher.model.dataset.ExceptionMsgDataSet.DUPLICATE_DATA_ERROR;
import static com.green.fefu.teacher.model.dataset.ExceptionMsgDataSet.SMS_SEND_ERROR;


@Service
@Slf4j
@RequiredArgsConstructor
public class ParentsUserServiceImpl implements ParentsUserService {
    private final ParentsUserMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProviderV2 jwtTokenProvider;
    private final CookieUtils cookieUtils;
    private final AuthenticationFacade authenticationFacade;
    private final AppProperties appProperties;
    private final String ID_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,12}$";
    private final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d|.*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,20}$";
    private final Pattern idPattern = Pattern.compile(ID_PATTERN);
    private final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    private final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private final SmsService smsService;
    private final StudentMapper studentMapper;
    private final HttpServletRequest httpServletRequest;
    @Value("${coolsms.api.caller}") private String coolsmsApiCaller;

    @Override @Transactional // 회원가입
    public int postParentsUser(PostParentsUserReq p) {
        if(p.getUid() == null || p.getUpw() == null){
            throw new IllegalArgumentException("아이디와 비밀번호는 필수 입력사항입니다.");
        } else if (!idPattern.matcher(p.getUid()).matches()) {
            throw new IllegalArgumentException("아이디 형식이 잘못되었습니다.");
        } else if (!passwordPattern.matcher(p.getUpw()).matches()) {
            throw new IllegalArgumentException("비밀번호 형식이 잘못되었습니다.");
        } else if (p.getEmail() != null && !p.getEmail().isEmpty()) {
            if(!emailPattern.matcher(p.getEmail()).matches()) {
                throw new IllegalArgumentException("이메일 형식이 잘못되었습니다.");
            } else {
                p.setEmail(null);
            }
        }
        if ( p.getAddr() != null && p.getZoneCode() != null ){
            p.setAddrs(p.getZoneCode(), p.getAddr(), p.getDetail()) ;
        }
        String password = passwordEncoder.encode(p.getUpw());
        p.setUpw(password);
        int result = mapper.postParentsUser(p);
        if(result != 1){
            throw new IllegalArgumentException("회원가입에 실패했습니다.");
        }
        int stuResult = studentMapper.updStudentParent( p.getStudentPk(),p.getParentsId());
        if(stuResult != 1){
            throw new IllegalArgumentException("학생 정보를 등록 해 주세요");
        }
        return result;
    }
    @Override // 아이디, 이메일 중복조회
    public String checkEmailOrUid(CheckEmailOrUidReq req) {
        if(req.getEmail() != null && req.getUid() != null){
            return "아이디 및 이메일을 하나만 입력해주세요" ;
        }
        CheckEmailOrUidRes res = mapper.checkEmailOrUid(req) ;
        if(res == null){
            return "사용할 수 있는 값입니다." ;
        }
        if(res.getUid() != null){
            return DUPLICATE_DATA_ERROR ;
        } else if (res.getEmail() != null) {
            return DUPLICATE_DATA_ERROR ;
        }
        return "사용할 수 있는 값입니다." ;
    }
    @Override // 회원정보 조회
    public ParentsUserEntity getParentsUser(String token) {
        Authentication auth = jwtTokenProvider.getAuthentication(token) ;
        SecurityContextHolder.getContext().setAuthentication(auth) ;
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        long parentsId = userDetails.getMyUser().getUserId() ;
        GetParentsUserReq req = new GetParentsUserReq();
        req.setSignedUserId(parentsId);
        return mapper.getParentsUser(req);
    }
    @Override @Transactional // 회원정보 수정
    public int patchParentsUser(PatchParentsUserReq p) {
        p.setParentsId(authenticationFacade.getLoginUserId());
        p.setAddrs(p.getZoneCode(), p.getAddr(), p.getDetail());
        return mapper.patchParentsUser(p);
    }
    @Override // 아이디 찾기
    public GetFindIdRes getFindId(GetFindIdReq req) {
        GetFindIdRes result = mapper.getFindId(req);
        if (result == null) {
            throw new RuntimeException("해당 요청에 대한 정보가 존재하지 않습니다.");
        }
        return result;
    }
    @Override @Transactional // 비밀번호 수정
    public int patchPassword(PatchPasswordReq p) {
        GetParentsUserReq req = new GetParentsUserReq();
        req.setSignedUserId(authenticationFacade.getLoginUserId());
        ParentsUserEntity entity = mapper.getParentsUser(req);
        if(Objects.isNull(entity)){
            throw new IllegalArgumentException("아이디를 확인해 주세요");
        }
        String password = passwordEncoder.encode(p.getNewUpw());
        p.setParentsId(entity.getParentsId());
        p.setNewUpw(password);
        return mapper.patchPassword(p);
    }
    @Override // 로그인
    public SignInPostRes signInPost(SignInPostReq p, HttpServletResponse res) {
        ParentsUser user = mapper.signInPost(p.getUid());
        if(Objects.isNull(user)){
            throw new IllegalArgumentException("아이디를 확인해 주세요");
        } else if (!BCrypt.checkpw(p.getUpw(), user.getUpw())) {
            throw new IllegalArgumentException("비밀번호를 확인해 주세요");
        }
        if(user.getAcept() != 1){
            throw new IllegalArgumentException("아직 승인되지않은 아이디 입니다.");
        }

        MyUser myUser = MyUser.builder().
                userId(user.getParentsId()).
                role("ROLE_PARENTS").
                build();

        String accessToken = jwtTokenProvider.generateAccessToken(myUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(myUser);

        int refreshTokenMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
        cookieUtils.deleteCookie(res, "refresh-token");
        cookieUtils.setCookie(res,"refresh-token", refreshToken, refreshTokenMaxAge);

        return SignInPostRes.builder().
                parentsId(user.getParentsId()).
                nm(user.getNm()).
                accessToken(accessToken).
                build();
    }
    @Override // access token
    public Map<String, Object> getAccessToken(HttpServletRequest req) {
        log.info("req: {}", req);
        System.out.println("Checking for refresh token cookie");

        if (req == null) {
            throw new RuntimeException("HttpServletRequest is null");
        }

        // 요청 헤더 출력
        Enumeration<String> headerNames = req.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = req.getHeader(headerName);
                System.out.println("Header: " + headerName + " = " + headerValue);
            }
        } else {
            System.out.println("No headers found in the request");
        }

        // 요청에서 모든 쿠키 출력
        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                System.out.println("Cookie Name: " + cookie.getName() + ", Cookie Value: " + cookie.getValue());
            }
        } else {
            System.out.println("No cookies found in the request");
        }

        Cookie cookie = cookieUtils.getCookie(req, "refresh-token");
        if (cookie == null) {
            System.out.println("Refresh token cookie is missing");
            throw new RuntimeException("Refresh token cookie is missing");
        }

        String refreshToken = cookie.getValue();
        System.out.println("Refresh token found: " + refreshToken);
        if (!jwtTokenProvider.isValidateToken(refreshToken)) {
            throw new RuntimeException("Refresh token is invalid");
        }

        UserDetails auth = jwtTokenProvider.getUserDetailsFromToken(refreshToken);
        if (auth == null) {
            throw new RuntimeException("Failed to get user details from token");
        }

        MyUser myUser = ((MyUserDetails) auth).getMyUser();
        if (myUser == null) {
            throw new RuntimeException("Failed to get MyUser from UserDetails");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(myUser);

        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        return map;
    }
    @Override // 문자발송 비밀번호 찾기
    public void getFindPassword(GetFindPasswordReq req, Map map) {
        // 랜덤코드 6자리 생성
        String code = SmsSender.makeRandomCode();
        List<ParentsUserEntity> list = mapper.getParentUserList(req);
        // 회원정보 확인
        if(list == null || list.isEmpty()){
            throw new IllegalArgumentException("회원정보가 존재하지 않습니다.");
        }
        try{
            // 문자보내기
            smsService.sendPasswordSms(req.getPhone(), coolsmsApiCaller, code);
        } catch (Exception e){
            throw new RuntimeException(SMS_SEND_ERROR);
        }

        map.put("RANDOM_CODE", code) ;
    }
    @Override @Transactional // 전자서명
    public SignatureRes signature(MultipartFile pic, SignatureReq req){
        if (pic == null || pic.isEmpty()) {
            throw new RuntimeException("서명 파일이 없습니다.");
        }
        try{
            String path = String.format("sign/%d", req.getSignId()) ;
            String fullPath = customFileUtils.makeFolders(path) ;
            String saveFileName = customFileUtils.makeRandomFileName(pic) ;
            String target = String.format("%s/%s", path, saveFileName) ;

            customFileUtils.transferTo(pic, target);
            req.setPic(saveFileName) ;

            int result = mapper.signature(req) ;
            if(result != 1){
                throw new RuntimeException("서명 등록 오류가 발생했습니다: 저장에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("File upload error", e);
            throw new RuntimeException("서명 등록 오류가 발생했습니다: " + e.getMessage());
        }
        return SignatureRes
                .builder()
                .signId(req.getSignId())
                .pics(req.getPic())
                .build() ;
    }
    @Override // 자녀 조회
    public List<GetStudentParentsRes> getStudentParents(String token){
        Authentication auth = jwtTokenProvider.getAuthentication(token) ;
        SecurityContextHolder.getContext().setAuthentication(auth) ;
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        long parentsId = userDetails.getMyUser().getUserId() ;
        GetParentsUserReq req = new GetParentsUserReq();
        req.setSignedUserId(parentsId);
        List<GetStudentParentsRes> list = mapper.getStudentParents(req.getSignedUserId());
        for(GetStudentParentsRes res : list){
            res.setClassId(Parser.classParser(res.getClassId()));
        }
        return list ;
    }
}
