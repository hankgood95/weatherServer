package com.wook.service;

import java.util.ArrayList;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	private JavaMailSender jms;

	public MailService(JavaMailSender jms) {
		super();
		this.jms = jms;
	}
	
	public void sendErrorMail(String message) {
		ArrayList<String> toUserList  = new ArrayList<>();
		
		toUserList.add("hankgood958@gmail.com");
		toUserList.add("hankgood95@naver.com");
		
		int listSize = toUserList.size();
		
		SimpleMailMessage smm = new SimpleMailMessage();
		
		//수신자 설정
		smm.setTo((String[]) toUserList.toArray(new String[listSize]));
		
		//메일 제목 설정
		smm.setSubject("API connection error Occur");
		
		//메일 내용 설정
		smm.setText(message);
		
		jms.send(smm);
	}
	
}