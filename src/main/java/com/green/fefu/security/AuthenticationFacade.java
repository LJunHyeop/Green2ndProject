package com.green.fefu.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public MyUser getLogInUser() {
        MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return myUserDetails == null ? null : myUserDetails.getMyUser();
    }

    public long getLogInUserId() {
        MyUser myUser = getLogInUser();
        return myUser == null ? 0 : myUser.getUserId();
    }
}
