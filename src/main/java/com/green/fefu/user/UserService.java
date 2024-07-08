package com.green.fefu.user;


import com.green.fefu.user.model.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    int signUpPostReq(MultipartFile pic, SignUpPostReq p);
    SignInPostRes signInPost(HttpServletResponse res, SignInPostReq p);
    UserInfoGetRes getUserInfo(UserInfoGetReq p);
    String patchProfilePic(UserProfilePatchReq p);
}
