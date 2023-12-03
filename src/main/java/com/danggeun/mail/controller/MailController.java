package com.danggeun.mail.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danggeun.mail.dto.MailDTO;
import com.danggeun.mail.service.MailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class MailController {
	private final MailService mailService;

	/**
	 * 이메일 발송
	 * @param mailDTO
	 * @return MailDTO
	 */
	@PostMapping("/send")
	public ResponseEntity<MailDTO> mailSend(@RequestBody MailDTO mailDTO) {
		// 이메일 null 값, 형식 체크
		mailDTO.validate();
		return new ResponseEntity<>(mailService.send(mailDTO), HttpStatus.OK);
	}

	/**
	 * 이메일 인증번호 확인
	 * @param mailDTO
	 * @return MailDTO
	 */
	@PostMapping("/certification")
	public ResponseEntity<MailDTO> certification(@RequestBody MailDTO mailDTO) {
		mailService.mailCertification(mailDTO);
		return new ResponseEntity<>(mailDTO, HttpStatus.OK);
	}
}