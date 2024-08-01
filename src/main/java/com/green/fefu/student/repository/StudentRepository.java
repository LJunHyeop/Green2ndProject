package com.green.fefu.student.repository;

import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByRandCode(String randCode);
    List<Student> findByParent(Parents parentId);
}
