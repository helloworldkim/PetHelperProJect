package com.help.HelloPet.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.help.HelloPet.model.User;

import lombok.RequiredArgsConstructor;

@ Service
@RequiredArgsConstructor
public class UserService {

	private final JavaMailSender javaMailSender;
	//이메일 발송
	public void signUpEmailSender(User user) {
		String email = user.getEmail();
		MimeMessage mail = javaMailSender.createMimeMessage();
		String mailContent = "<h1>[이메일 인증]</h1><br><p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>"
		                    + "<a href='http://localhost:8080/check_email?email=" 
		                    + email + "&token=" + user.getEmailCheckToken() + "' target='_blenk'>이메일 인증 확인</a>";

		try {
		    mail.setSubject("회원가입 이메일 인증 ", "utf-8");
		    mail.setText(mailContent, "utf-8", "html");
		    mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		    javaMailSender.send(mail);
		} catch (MessagingException e) {
		    e.printStackTrace();
		}

		
	}
}
