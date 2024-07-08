package com.green.fefu.user;


import com.green.fefu.user.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
     List<User> selTest(long userId);

     int signUpPostReq(SignUpPostReq p);
     User signInPost(SignInPostReq p);
     UserInfoGetRes selProfileUserInfo(UserInfoGetReq p);
     int updProfilePic(UserProfilePatchReq p);
}
