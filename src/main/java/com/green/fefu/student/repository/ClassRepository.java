package com.green.fefu.student.repository;

import com.green.fefu.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClassRepository extends JpaRepository<Class,Long> {
    @Query("select cl from Class cl where cl.teaId.teaId = :teaId")
    Class findByTeaId(Long teaId);
}
