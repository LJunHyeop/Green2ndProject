package com.green.fefu.student.repository;

import com.green.fefu.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByRandCode(String randCode);
}
