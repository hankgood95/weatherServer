package com.wook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.wook.model.dto.ApiResponse;
import com.wook.model.dto.RegionCode;
import com.wook.service.MemberService;
import com.wook.service.RegionCodeService;

@RestController
public class RegionCodeController {
	
	private Logger logger = LoggerFactory.getLogger(ShortWeatherController.class); // 로그를 찍기 위해서 사용하는 Class
	private MemberService ms;
	private RegionCodeService rcs;
	
	@Autowired
	public RegionCodeController(MemberService ms, RegionCodeService rcs) {
		super();
		this.ms = ms;
		this.rcs = rcs;
	}
	
	@GetMapping("/regioncode")
	public ApiResponse getRegionCode(@RequestHeader String serviceKey ,@RequestBody RegionCode rc) {
		if(ms.checkServiceKey(serviceKey) > 0) { //서비스키가 유효한 서비스키라면 진입
			//여기서 이제 해당 주소로 regioncode를 받아야 된다.
			RegionCode result = rcs.getRegionCode(rc); //주어진 request로 DB에서 찾아본다.
			
			if(result != null) {
				result.setType(rc.getType());//type이 정해져 있지 않으니 요청으로 받은걸로 타입을 지정한다.
				logger.info("Success");
				return new ApiResponse(200,"Success",result);
			}else {
				logger.error("No Data");
				return new ApiResponse(204,"No Data",null);
			}
			
		}else {
			logger.error("Failed : Authorization Failed");
			return new ApiResponse(401,"Authorization Failed : Wrong ServiceKey",null);
		}
	}

}
