package com.green.fefu.parents;

import com.green.fefu.entity.Parents;
import com.green.fefu.security.SignInProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParentRepository extends JpaRepository<Parents, Long> {
    @Query(value = "select pp from parents pp " +
            "inner join parent_oath2 po2 " +
            "on pp.parents_id = po2.parent_id " +
            "where po2.provider_type = :providerType" +
            " and po2.id = :id" , nativeQuery = true)
    Parents findAllByProviderTypeAndId(SignInProviderType providerType, String id) ;
}
