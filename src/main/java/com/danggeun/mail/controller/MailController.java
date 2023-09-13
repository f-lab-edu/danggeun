package com.danggeun.mail.controller;

import java.io.UnsupportedEncodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.danggeun.mail.domain.Mail;
import com.danggeun.mail.service.MailService;
import com.google.gson.JsonObject;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {
	private final MailService mailService;

	@PostMapping("/send")
	public ResponseEntity<String> mailSend(@Valid Mail mail){

		JsonObject jsonObject = new JsonObject();
		try{
			mailService.send(mail);
		} catch (MessagingException | UnsupportedEncodingException e) {
			log.info("메일 발송 실패"+ e.getMessage());
			jsonObject.addProperty("result", "이메일 발송 실패");
			jsonObject.addProperty("error", e.getMessage());
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("이메일 발송", HttpStatus.OK);
	}

	@PostMapping("/certification")
	public ResponseEntity<String> certification(@Valid Mail mail){
		JsonObject jsonObject = new JsonObject();
		if(mailService.mailCertification(mail)){
			jsonObject.addProperty("result", "이메일 인증번호가 일치");
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		} else{
			jsonObject.addProperty("result", "이메일 인증번호 불일치");
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		}
	}
}