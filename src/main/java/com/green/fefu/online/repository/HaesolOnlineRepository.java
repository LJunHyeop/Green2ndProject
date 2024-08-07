package com.green.fefu.online.repository;


import com.green.fefu.entity.HaesolOnline;
import com.green.fefu.online.model.GetKoreanAndMathQuestionRes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HaesolOnlineRepository extends JpaRepository<HaesolOnline,Long> {
    List<GetKoreanAndMathQuestionRes> findQueIdQuestionContentsAnswerLevelPicTypeTagQueTagBySubjectCodeAndClassId(Long SubjectCode, Long ClassId);
}
