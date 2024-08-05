package com.green.fefu.student.repository;

import com.green.fefu.entity.Class;
import com.green.fefu.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClassRepository extends JpaRepository<Class,Long> {
    @Query("select cl from Class cl where cl.teaId = :teaId")
    Class getReferenceByTeaId(Long teaId);
}