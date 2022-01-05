package com.wook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.wook.model.dto.MemberResponse;
import com.wook.model.dto.TempMember;
import com.wook.security.Encryption;
import com.wook.service.MemberService;

@RestController
public class MemberController {

	private Logger logger = LoggerFactory.getLogger(ShortWeatherController.class); // 로그를 찍기 위해서 사용하는 Class
	private MemberService ms;

	public MemberController(MemberService ms) {
		super();
		this.ms = ms;
	}

	// 내가 MemberController에서 할것은
	// 1. 회원의 이메일을 받아오는 PostReqeust를 만들면 된다.
	// 2.

	// 1. 관리자가 관리자 서비스키는 Header에 담고 회원의 이메일은 body에 담아 매니저의 키가 맞는지 확인한 후에 맞다면
	// insert한다.
	@PostMapping("maxmintemp/member")
	public MemberResponse insertMember(@RequestHeader String managerKey, @RequestBody TempMember tm) {

		// 관리자의 키가 맞는지 확인하기
		TempMember manager = ms.checkManager(managerKey);

		if (manager == null) { // 해당 키로 관리자가 없다면 진입
			logger.info("인증 실패");
			return new MemberResponse(401, "Authorization Failed : Wrong ServiceKey", null);
		} else {
			// 여기서부터는 이제 입력받은 이메일로 서비스키를 생성해줘야 하는데 그보다 더 우선인것은 이메일 중복체크를 해줘야 함
			if (ms.checkEmail(tm.getEmail()) > 0) { // 이미 존재하는 이메일이라면 진입
				return new MemberResponse(409, "Member Insert Fail : Email already exist", null);
			} else {
				Encryption sha = new Encryption(); // 보안 클래스 인스턴스 생성
				String memberServiceKey = sha.encryption(tm.getEmail()); // sha256으로 변환
				tm.setServiceKey(memberServiceKey);

				if (ms.insertMember(tm) == 1) {
					//회원 입력 성공했을떄
					return new MemberResponse(200, "Member Insert Success", null);
				} else {
					//회원 입력 실패했을떄
					return new MemberResponse(500, "Member Insert Failed : Server Error", null);
				}
			}
		}
	}
}
