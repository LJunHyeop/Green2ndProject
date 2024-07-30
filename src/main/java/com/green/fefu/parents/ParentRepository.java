package com.green.fefu.parents;

import com.green.fefu.entity.Parents;
import com.green.fefu.security.SignInProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParentRepository extends JpaRepository<Parents, Long> {
    @Query(value = "select ff from Parents ff " +
            "inner join ParentOAuth2 po2 " +
            "on ff.parentsId = po2.parentsId.parentsId " +
            "where po2.providerType = :providerType" +
            " and po2.id = :id")
    Parents findAllByProviderTypeAndId(SignInProviderType providerType, String id) ;
}
