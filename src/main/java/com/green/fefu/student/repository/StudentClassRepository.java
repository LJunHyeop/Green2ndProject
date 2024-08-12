package com.green.fefu.student.repository;

import com.green.fefu.entity.Class;
import com.green.fefu.entity.Student;
import com.green.fefu.entity.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentClassRepository extends JpaRepository<StudentClass, Integer> {
    StudentClass findByStuId(Student stuId);
    Long countByClassId(Class classId);
}
