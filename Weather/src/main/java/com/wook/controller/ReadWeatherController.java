package com.wook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReadWeatherController {

	//이제 여기다가 API를 만들면됨
	@GetMapping("/todayMaxMin")
	public String readWeather() {
		return "this is weather info";
	}
	
}
