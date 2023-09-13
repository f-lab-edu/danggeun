package com.danggeun.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danggeun.user.dto.User;
import com.danggeun.user.service.UserService;
import com.google.gson.JsonObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/user")
public class UserController {

	private final UserService userService;

	@PostMapping("/insert")
	@ResponseBody
	public ResponseEntity<String> userInsert(@Valid User user){
		JsonObject jsonObject = new JsonObject();

		try{
			userService.join(user);
			jsonObject.addProperty("result", "회원가입 완료");
		} catch (Exception e){
			jsonObject.addProperty("result","회원가입 실패");
			jsonObject.addProperty("error", e.getMessage());

			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}
}
