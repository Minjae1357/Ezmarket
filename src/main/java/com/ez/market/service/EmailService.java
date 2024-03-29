package com.ez.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService 
{
	//스프링 프레임워크에서 제공하는 이메일 전송을 위한 인터페이스
	@Autowired
	private JavaMailSender sender;
	@Autowired
	private HttpSession session; 
	
	
	public void sendSimpleMessage(String emailAddress,String subject,String Content){
		try {
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setTo(emailAddress);
			helper.setSubject(subject);
			helper.setText(Content,true);
			sender.send(message);
		}catch(MessagingException e) { //예외처리
			e.printStackTrace();
		}
	}
	public String createRandomStr() {
		UUID randomUUID = UUID.randomUUID();
		return randomUUID.toString().replaceAll("-", ""); // "-" 문자를 제거
	}
}
