package com.green.fefu.parents;

import com.green.fefu.entity.ParentOAuth2;
import com.green.fefu.entity.Parents;
import com.green.fefu.security.SignInProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentOAuth2Repository extends JpaRepository<ParentOAuth2, Long> {
    ParentOAuth2 getParentsByProviderTypeAndId(SignInProviderType providerType, String uid) ;
}
