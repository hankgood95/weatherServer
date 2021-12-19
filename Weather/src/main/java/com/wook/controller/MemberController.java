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
	
	//body에 있는 값을 받아옴
	@PostMapping("/maxmintemp/user")
	public String newMember(@RequestHeader String managerKey,@RequestBody TempMember tm) {
		logger.info("managerKey : " + managerKey);
		
		//1. managerKey를 받고 받아온 값이 managerKey인지 확인해본다
		//2. managerKey가 맞다면 받아온 이메일의 중복확인을 해본다.
		//3. 중복 이메일이 아니라면 랜덤 난수를 만들어서 serviceKey로 등록시킨다.
		//4. 중복 이메일이라면 중복 이메일이 있다고 하고 알려주는 에러코드를 보내준다.
		
		return tm.toString();
	}
}
