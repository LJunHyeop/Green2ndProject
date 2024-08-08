package com.green.fefu.online.repository;


import com.green.fefu.entity.HaesolOnline;
import com.green.fefu.online.model.GetKoreanAndMathQuestionRes;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HaesolOnlineRepository extends JpaRepository<HaesolOnline,Long> {
    @Query("SELECT question, contents, answer, level, pic FROM HaesolOnline WHERE subjectCode=:subjectCode AND classId=:classId")
    List<GetKoreanAndMathQuestionRes> findBySubjectCodeAndClassId(@Param("subjectCode")Long subjectCode, @Param("classId")Long classId);
}
