package com.green.fefu.online.repository;

import com.green.fefu.entity.dummy.TypeTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeTagRepository extends JpaRepository<TypeTag, Long> {
    TypeTag findByTypeNum(Long typeNum);
}
