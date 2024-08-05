package com.green.fefu.score.repository;


import com.green.fefu.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
   Score  findScoreBy(int mark);

   Score  findNameBy(String name);

   Score findExamBy(int exam);

   Score findSemesterBy(int semester);

   Score findYearBy(int year);

   Score findStudentBy(int scId);
}
