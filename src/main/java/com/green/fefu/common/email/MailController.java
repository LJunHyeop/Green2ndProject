package com.green.fefu.common.email;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mail-send")
public class MailController {
    private final MailService service ;
    private String number ;

    @PostMapping
    public HashMap<String, Object> mailSend(String mail) {
        HashMap<String, Object> map = new HashMap<>() ;

        try{
            number = service.sendMail(mail) ;
            String num = String.valueOf(number) ;

            map.put("success", Boolean.TRUE) ;
            map.put("number", num) ;
        } catch (Exception e){
            map.put("success",Boolean.FALSE) ;
            map.put("error", e.getMessage()) ;
        }
        return map ;
    }
    @GetMapping
    public ResponseEntity<?> mailCheck(@RequestParam String userNumber) {
        boolean isMatch = userNumber.equals(String.valueOf(number)) ;
        return ResponseEntity.ok(isMatch) ;
    }
}
