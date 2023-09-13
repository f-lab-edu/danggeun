package com.danggeun.mail.service;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.danggeun.commons.util.RedisUtil;
import com.danggeun.mail.domain.Mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailService {
	public static final long DURATION = 180L;

	private final JavaMailSender javaMailSender;
	private final RedisUtil redisUtil;

	public void send(Mail mail) throws MessagingException, UnsupportedEncodingException  {
		if(redisUtil.existData(mail.getTo())){
			redisUtil.deleteData(mail.getTo());
		}
		createCertificationNumber(mail);
		MimeMessage message = createMailForm(mail);
		redisUtil.setDataExpire(mail.getTo(), mail.getCertificationNumber(), DURATION);
		javaMailSender.send(message);
	}

	public void createCertificationNumber(Mail mail){
		// 이메일 인증번호 5자리 생성
		String certificationNumber = String.valueOf((int)(Math.random() * (99999 - 10000 + 1)) + 10000);
		mail.setCertificationNumber(certificationNumber);
	}

	public MimeMessage createMailForm(Mail mail) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

		StringBuffer text = new StringBuffer();
		text.append("<div style='margin:100px;'>")
			.append("<h1> 안녕하세요 Dangguen입니다. </h1><br>")
			.append("<p>아래 인증번호를 이메일 인증 창으로 돌아가 입력해주세요<p><br>")
			.append("<p>감사합니다!<p><br>")
			.append("<div align='center' style='border:1px solid black; font-family:verdana';>")
			.append("<h3 style='color:blue;'>이메일 인증번호입니다.</h3>")
			.append("인증번호 : ")
			.append(mail.getCertificationNumber())
			.append("<strong>")
			.append("</strong><div><br/> </div>");

		mail.setFrom("dangguen");
		mail.setSubject("[ Dangguen 이메일 인증 ]");
		mail.setText(text.toString());
		messageHelper.setTo(mail.getTo());
		messageHelper.setFrom(mail.getFrom(), mail.getFrom());
		messageHelper.setSubject(mail.getSubject());
		messageHelper.setText(mail.getText(), true);

		return message;
	}

	public boolean mailCertification(Mail mail){
			String generationCode = redisUtil.getData(mail.getTo());
			if(generationCode == null){
				return false;
			}
			return generationCode.equals(mail.getCertificationNumber());
	}
}