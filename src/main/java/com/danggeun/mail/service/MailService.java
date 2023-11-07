package com.danggeun.mail.service;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.danggeun.commons.util.EmailRedisService;
import com.danggeun.commons.util.RandomNumber;
import com.danggeun.mail.dto.MailDTO;
import com.danggeun.mail.exception.CertificationNumberMismatchException;
import com.danggeun.mail.exception.EmailCertificationNumberSendException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailService {
	private static final long DURATION = 180L;

	JavaMailSender javaMailSender;
	EmailRedisService emailRedisService;

	/**
	 * 이메일 인증번호 발송
	 * @param mailDTO
	 * @return MailDTO
	 */
	public MailDTO send(MailDTO mailDTO) {
		// 이메일 null 값, 형식 체크
		mailDTO.validate();

		if (emailRedisService.exist(mailDTO.getTo())) {
			emailRedisService.delete(mailDTO.getTo());
		}

		try {
			createCertificationNumber(mailDTO);
			MimeMessage message = createMailForm(mailDTO);
			emailRedisService.set(mailDTO.getTo(), mailDTO.getCertificationNumber(), DURATION);
			javaMailSender.send(message);
		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		return mailDTO;
	}

	/**
	 * 이메일 인증번호 생성
	 * @param mailDTO
	 */
	public void createCertificationNumber(MailDTO mailDTO) {
		RandomNumber certificationNumber = new RandomNumber();
		// 이메일 인증번호 5자리 생성
		mailDTO.setCertificationNumber(certificationNumber.getNumber());
	}

	/**
	 * 이메일 발송 폼 생성
	 * @param mailDTO
	 * @return MimeMessage
	 */
	public MimeMessage createMailForm(MailDTO mailDTO) throws MessagingException, UnsupportedEncodingException {
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
			.append(mailDTO.getCertificationNumber())
			.append("<strong>")
			.append("</strong><div><br/> </div>");

		mailDTO.setFrom("dangguen");
		mailDTO.setSubject("[ Dangguen 이메일 인증 ]");
		mailDTO.setText(text.toString());
		messageHelper.setTo(mailDTO.getTo());
		messageHelper.setFrom(mailDTO.getFrom(), mailDTO.getFrom());
		messageHelper.setSubject(mailDTO.getSubject());
		messageHelper.setText(mailDTO.getText(), true);

		return message;
	}

	/**
	 * 이메일 인증번호 확인
	 * @param mailDTO
	 * @return boolean
	 */
	public boolean mailCertification(MailDTO mailDTO) {
		String generationCode = emailRedisService.get(mailDTO.getTo());
		if (generationCode == null) {
			return false;
		}
		return generationCode.equals(mailDTO.getCertificationNumber());
	}
}