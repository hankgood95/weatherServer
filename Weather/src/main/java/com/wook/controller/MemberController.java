package com.wook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.wook.model.dto.MemberResponse;
import com.wook.model.dto.TempMember;

@RestController
public class MemberController {

	private Logger logger = LoggerFactory.getLogger(ShortWeatherController.class); //로그를 찍기 위해서 사용하는 Class
	
	//내가 MemberController에서 할것은
	//1. 회원의 이메일을 받아오는 PostReqeust를 만들면 된다.
	public MemberResponse insertMember() {
		return null;
	}
}
