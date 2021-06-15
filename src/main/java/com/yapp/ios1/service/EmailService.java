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
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

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
    private final SpringTemplateEngine templateEngine;

    public void sendEmailMessage(String email) {
        try {
            String code = createCode();
            redisUtil.setDataExpire(code, email, emailProperties.getValidTime());
            MimeMessage message = createMessage(email, code);
            emailSender.send(message);
        } catch (Exception e) {
            throw new EmailSendException();
        }
    }

    private MimeMessage createMessage(String email, String code) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("[buok] 비밀번호를 잊으셨나요? " + code);
        message.setText(setContext(code), "utf-8", "html");
        message.setFrom(new InternetAddress(emailProperties.getLink(), emailProperties.getName()));
        return message;
    }

    private String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("email", context);
    }

    private String createCode() {
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 7; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    code.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1:
                    code.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    code.append((rnd.nextInt(10)));
                    break;
            }
        }
        return code.toString();
    }

    public Long getUserIdByCode(String code) {
        String email = redisUtil.getData(code);
        if (email == null) {
            // TODO 에러 변경
            throw new EmailNotExistException();
        }

        User user = userValidator.checkEmailPresent(email);
        return user.getId();
    }
}