package com.wook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wook.model.dto.TempRequest;
import com.wook.model.dto.Temperature;
import com.wook.service.TempService;

@RestController
public class ReadWeatherController {


	private TempService ts;
	
	public ReadWeatherController(TempService ts) {
		this.ts = ts;
	}



	//이제 여기다가 API를 만들면됨
	@GetMapping("/todayMaxMin")
	public String readWeather(
			@RequestParam(value="x")int x,
			@RequestParam(value="y")int y,
			@RequestParam(value="baseDate")String baseDate) {
		
		String tempKey = baseDate+String.valueOf(x)+String.valueOf(y);
		
		TempRequest tr = new TempRequest(x,y,baseDate,tempKey);
		
		Temperature temp = ts.getTemp(tr);
		
		return temp.toString();
	}
	
}
