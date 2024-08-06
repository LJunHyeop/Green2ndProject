package com.green.fefu.online;

import com.green.fefu.common.CustomFileUtils;
import com.green.fefu.common.model.ResultDto;
import com.green.fefu.entity.OnlineEnglishListening;
import com.green.fefu.entity.OnlineEnglishWord;
import com.green.fefu.entity.Teacher;
import com.green.fefu.online.model.PostOnlineQuestionEnglishListeningReq;
import com.green.fefu.online.model.PostOnlineQuestionEnglishWordReq;
import com.green.fefu.online.repository.OnlineEnglishListeningRepository;
import com.green.fefu.online.repository.OnlineEnglishWordRepository;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class OnlineEnglishServiceImpl {
    private final OnlineEnglishWordRepository wordRepository;
    private final OnlineEnglishListeningRepository listeningRepository;

    private final TeacherRepository teacherRepository;
    private final AuthenticationFacade authenticationFacade;
    private final CustomFileUtils customFileUtils;

    @Transactional // word DB 업로드
    public int PostEnglishWordQuestion(PostOnlineQuestionEnglishWordReq p, MultipartFile pic) {
        String picName = null;
        if (pic != null || pic.isEmpty()) {
            picName = customFileUtils.makeRandomFileName(pic);
        }


        // 파일 이외의 정보 처리
        OnlineEnglishWord entOnlineEnglishWord = new OnlineEnglishWord();
        entOnlineEnglishWord.setWord(p.getWord());
        entOnlineEnglishWord.setAnswer(p.getAnswer());
        entOnlineEnglishWord.setPic(picName);
        Teacher teacher = teacherRepository.getReferenceById(authenticationFacade.getLoginUserId());
        entOnlineEnglishWord.setTeaId(teacher);
        wordRepository.save(entOnlineEnglishWord);

        String path = null;
        try {
            customFileUtils.transferTo(pic, path);

        } catch (Exception e) {

        }
        return 1;
    }
        public int PostEnglishListeningQuestion(PostOnlineQuestionEnglishListeningReq p, MultipartFile pic){
            return 1;
        }
}

