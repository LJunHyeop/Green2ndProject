package com.green.fefu.student.repository;

import com.green.fefu.entity.Student;
import com.green.fefu.parents.model.StudentRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByRandCode(String randCode);

    Student findStudentByGrade(int grade);
    Student findStudentByUid(String uid);
}
