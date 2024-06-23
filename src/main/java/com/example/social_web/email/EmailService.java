package com.example.social_web.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EmailService {

    private final JavaMailSender mailSender;


    @Async
    public void sendVerificationEmail(String url, String username) throws MessagingException, UnsupportedEncodingException, jakarta.mail.MessagingException {

        String subject = "Email Verification";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, " + username + ", </p>" + "<p>Thank you for registering with us," + "Please, follow the link below to complete your registration.</p>" + "<a href=\"" + url + "\">Verify your email to activate your account</a>" + "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("bubakush20099@gmail.com", senderName);
        messageHelper.setTo(username);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
