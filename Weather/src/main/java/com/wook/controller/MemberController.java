package com.wook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.wook.model.dto.TempMember;

@RestController
public class MemberController {

	private Logger logger = LoggerFactory.getLogger(ShortWeatherController.class); //로그를 찍기 위해서 사용하는 Class
	
	//내가 MemberController에서 할것은
	//1. 관리자의 서비스키와 회원정보를 받으면 회원의 서비스키를 발급해주고 DB에 저장하는 일을 할것이다.
	//이건 그램에서 올린것임
}
