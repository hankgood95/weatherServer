package com.wook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.wook.model.dto.ApiResponse;
import com.wook.model.dto.RegionCode;
import com.wook.service.MemberService;

@RestController
public class RegionCodeController {
	
	private Logger logger = LoggerFactory.getLogger(ShortWeatherController.class); // 로그를 찍기 위해서 사용하는 Class
	private MemberService ms;
	
	@GetMapping("/regioncode/tempcode")
	public ApiResponse getRegionCode(@RequestHeader String serviceKey ,@RequestBody RegionCode rc) {
		if(ms.checkServiceKey(serviceKey) > 0) { //서비스키가 유효한 서비스키라면 진입
			//여기서 이제 해당 주소로 regioncode를 받아야 된다.
			switch(rc.getType()) {
			case 1:
				break;
			case 2:
				break;
			}
		}else {
			return new ApiResponse(401,"Authorization Failed : Wrong ServiceKey",null);
		}
		return null;
	}
	

}
