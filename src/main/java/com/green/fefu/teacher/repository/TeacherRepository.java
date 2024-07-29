package com.green.fefu.teacher.repository;

import com.green.fefu.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findByUid(String uid);
    Teacher findByEmail(String email);
    Teacher findByNameAndPhone(String name, String phone);

}
