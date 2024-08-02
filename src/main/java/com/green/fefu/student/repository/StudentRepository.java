package com.green.fefu.student.repository;

import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByRandCode(String randCode);
    @Query(value = "select ff from Student ff where ff.parent.parentsId = :parentId")
    List<Student> findByParent(Long parentId);
}
