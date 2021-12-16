package com.wook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.wook.model.dto.MaxMinResponse;
import com.wook.model.dto.Temperature;
import com.wook.service.TempService;

@RestController
public class ReadWeatherController {


	private TempService ts;
	private Logger logger = LoggerFactory.getLogger(ShortWeatherController.class); //로그를 찍기 위해서 사용하는 Class
	private MaxMinResponse mmr;
	
	public ReadWeatherController(TempService ts) {
		this.ts = ts;
	}



	//이제 여기다가 API를 만들면됨
	@GetMapping("/maxmintemp/{tempKey}")
	public MaxMinResponse readWeather(@PathVariable("tempKey")String tempKey, @RequestHeader String serviceKey) {
		
		logger.info(serviceKey);
		Temperature temp = ts.getTemp(tempKey);
		
		//내가 만들 응답코드
		//1 . 401 응답코드 - 서비스키 오류
		//2. 204 응답코드 - 데이터 없는 오류
		//3. 200 응답코드 - 성공
		
		if(temp== null) { //해당 데이터로 받은 내용이 없다면
			mmr = new MaxMinResponse(204,"No Data"); //204 응답코드를 보내준다.
		}else {
			mmr = new MaxMinResponse(200,"Success",temp);
		}
		
		return mmr; //temp를 json 형식으로 반환
	}
	
}
