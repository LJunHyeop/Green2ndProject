package com.green.fefu.online.repository;


import com.green.fefu.entity.HaesolOnline;
import com.green.fefu.online.model.GetKoreanAndMathQuestionRes;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HaesolOnlineRepository extends JpaRepository<HaesolOnline,Long> {
    @Query("SELECT ha FROM HaesolOnline ha WHERE ha.subjectCode.subjectId=:subjectCode AND ha.classId=:classId")
    List<HaesolOnline> findBySubjectCodeAndClassId(@Param("subjectCode")Long subjectCode, @Param("classId")Long classId);
}
