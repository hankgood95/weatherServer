package com.wook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
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

	// Member 입력하는 부분
	@PostMapping("maxmintemp/member")
	public MemberResponse insertMember(@RequestHeader String managerKey, @RequestBody TempMember tm) {

		if (ms.checkManager(managerKey) < 1) { // 해당 키로 관리자가 없다면 진입 즉 관리자 서비스키 틀렸다면 진입
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
					return new MemberResponse(200, "Member Insert Success", tm);
				} else {
					//회원 입력 실패했을떄
					return new MemberResponse(500, "Member Insert Failed : Server Error", null);
				}
			}
		}
	}
	
	@GetMapping("maxmintemp/member")
	public MemberResponse getMember(@RequestHeader String managerKey, @RequestBody TempMember tm) {
		
		if (ms.checkManager(managerKey) < 1) { // 해당 키로 관리자가 없다면 진입 즉 관리자 서비스키 틀렸다면 진입
			logger.info("인증 실패");
			return new MemberResponse(401, "Authorization Failed : Wrong ServiceKey", null);
		} else {
			//관리자키가 맞으니까 진입
			//여기서 이제 이메일로 serviceKey 조회하면 돼
			logger.info(tm.getEmail());
			return new MemberResponse(200,"Success",ms.getServiceKey(tm.getEmail()));
		}
	}
}
