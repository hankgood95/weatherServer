package com.wook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wook.model.dto.Temperature;
import com.wook.service.TempService;

@RestController
public class ReadWeatherController {


	private TempService ts;
	
	public ReadWeatherController(TempService ts) {
		this.ts = ts;
	}



	//이제 여기다가 API를 만들면됨
	@GetMapping("/maxmintemp/{tempKey}")
	public Temperature readWeather(@PathVariable("tempKey")String tempKey) {
		
		Temperature temp = ts.getTemp(tempKey);
		return temp; //temp를 json 형식으로 반환
	}
	
}
