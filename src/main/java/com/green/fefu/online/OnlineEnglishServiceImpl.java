package com.green.fefu.online;

import com.green.fefu.common.model.ResultDto;
import com.green.fefu.online.model.PostOnlineQuestionEnglishListeningReq;
import com.green.fefu.online.model.PostOnlineQuestionEnglishWordReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class OnlineEnglishServiceImpl {
    public int PostEnglishWordQuestion(PostOnlineQuestionEnglishWordReq p, MultipartFile pic /*, @RequestPart MultipartFile v*/){
        return 1;
    }
    public int PostEnglishListeningQuestion(PostOnlineQuestionEnglishListeningReq p, MultipartFile pic /*, @RequestPart MultipartFile v*/) {
        return 1;
    }
}

