package com.green.fefu.parents.model;

import com.green.fefu.security.SignInProviderType;
import lombok.Data;

@Data
public class SocialLoginRequest {
    private SignInProviderType providerType ;
    private String token ;
}
