package com.yapp.ios1.service;

import com.yapp.ios1.model.user.User;
import com.yapp.ios1.error.exception.email.EmailSendException;
import com.yapp.ios1.error.exception.user.EmailNotExistException;
import com.yapp.ios1.properties.EmailProperties;
import com.yapp.ios1.utils.RedisUtil;
import com.yapp.ios1.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

/**
 * created by ayoung 2021/05/30
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final RedisUtil redisUtil;
    private final EmailProperties emailProperties;
    private final UserValidator userValidator;
    private static final StringBuilder sb = new StringBuilder();   // TODO 다시 고민

    private MimeMessage createMessage(String email, String code) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("[buok] 비밀번호를 잊으셨나요? " + code);
        message.setText(createHtml(code), "utf-8", "html");
        message.setFrom(new InternetAddress(emailProperties.getLink(), emailProperties.getName()));
        return message;
    }

    private String createHtml(String code) {
        sb.append("<div align=\"center\" style=\"font-size: 15px\">");
        sb.append("<br/><img src=\"" + emailProperties.getLogoUrl() + "\"/>");
        sb.append("<br/><br/><br/>비밀번호를 잊으셨나요?<br/>너무 걱정 마세요. 저희도 가끔 잊어버린답니다.<br/><br/>");
        sb.append("<span style=\"border: 0.5px; padding: 8px;font-size: 20px;\">" + code + "</span>");
        sb.append("<br/><br/>buok으로 돌아가 위 인증번호를 입력해 주세요.<br/><br/>");
        sb.append("혹시 비밀번호 재설정을 요청하지 않으셨거나,<br/>비밀번호를 찾으셨다면 이 이메일을 무시해 주세요.<br/><br/>");
        sb.append("그럼, 계속 저희와 함께 미래 계획을 세워나가 볼까요?");
        return sb.toString();
    }

    private String createKey() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 7; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    key.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }

    public void sendEmailMessage(String email) {
        try {
            String code = createKey();
            redisUtil.setDataExpire(code, email, emailProperties.getValidTime());
            MimeMessage message = createMessage(email, code);
            emailSender.send(message);
        } catch (Exception e) {
            throw new EmailSendException();
        }
    }

    // TODO 책임론 생각해보기
    public Long verifyCode(String code) {
        String email = redisUtil.getData(code);
        if (email == null) {
            throw new EmailNotExistException();
        }

        User user = userValidator.checkEmailPresent(email);
        return user.getId();
    }
}