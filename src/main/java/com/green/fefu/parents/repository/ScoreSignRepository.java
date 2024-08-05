package com.green.fefu.parents.repository;

import com.green.fefu.entity.ScoreSign;
import com.green.fefu.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreSignRepository extends JpaRepository<ScoreSign, Long> {
    ScoreSign getAllByStuIdAndExamSignAndSemesterAndYear(Student student, int examSign, int semester, int year) ;
}
