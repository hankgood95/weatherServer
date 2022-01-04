package com.wook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.wook.model.dto.MemberResponse;
import com.wook.model.dto.TempMember;
import com.wook.service.MemberService;

@RestController
public class MemberController {

	private Logger logger = LoggerFactory.getLogger(ShortWeatherController.class); //로그를 찍기 위해서 사용하는 Class
	private MemberService ms;
	
	public MemberController(MemberService ms) {
		super();
		this.ms = ms;
	}

	//내가 MemberController에서 할것은
	//1. 회원의 이메일을 받아오는 PostReqeust를 만들면 된다.
	//2. 
	
	//1. 관리자가 관리자 서비스키는 Header에 담고 회원의 이메일은 body에 담아 매니저의 키가 맞는지 확인한 후에 맞다면 insert한다.
	@PostMapping("maxmintemp/member")
	public MemberResponse insertMember(@RequestHeader String managerKey, @RequestBody TempMember tm) {
		
		MemberResponse mr = null;
		//관리자의 키가 맞는지 확인하기
		TempMember manager = ms.checkManager(managerKey);
		
		if(manager == null) { //해당 키로 관리자가 없다면 진입
			logger.info("인증 실패");
			mr = new MemberResponse(401,"Authorization Failed",null);
		}else {
			logger.info("확인 완료");
		}

		return mr;
	}
}
