package com.green.fefu.student.repository;

import com.green.fefu.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class,Long> {
    Class getReferenceByTeaId(Long teaId);
}
