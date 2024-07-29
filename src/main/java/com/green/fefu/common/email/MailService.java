package com.green.fefu.common.email;

import com.green.fefu.chcommon.SmsSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}") private String senderEmail  ;
    private static String number ;

    public static void createNumber(){
        number = SmsSender.makeRandomCode() ;
    }

    public MimeMessage CreateMail(String mail) {
        createNumber() ;
        MimeMessage mimeMessage = mailSender.createMimeMessage() ;

        try{
            mimeMessage.setFrom(senderEmail) ;
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, mail) ;
            mimeMessage.setSubject("이메일 인증") ;
            String body = "" ;
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>" ;
            body += "<h1>" + number + "</h1>" ;
            body += "<h3>" + "감사합니다." + "</h3>" ;
            mimeMessage.setText(body, "UTF-8", "html") ;
        } catch (MessagingException e) {
            e.printStackTrace() ;
        }
        return mimeMessage ;
    }

    public String sendMail(String mail) {
        MimeMessage message = CreateMail(mail) ;
        mailSender.send(message) ;

        return number ;
    }
}
