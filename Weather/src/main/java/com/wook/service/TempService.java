package com.wook.service;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wook.controller.ShortWeatherController;
import com.wook.model.dao.TempDao;
import com.wook.model.dto.Temperature;

@Service
@MapperScan("com.wook.model.dao")
public class TempService {

	private TempDao td;
	private Logger logger = LoggerFactory.getLogger(ShortWeatherController.class); //로그를 찍기 위해서 사용하는 Class
	
	public TempService(TempDao td) {
		super();
		this.td = td;
	}
	
	//API로부터 받아온 온도를 저장하는 부분
	public int saveTemp(Temperature temp) {
		// TODO Auto-generated method stub
		return td.insertTemp(temp);
	}
	
	//API를 통해서 온도를 조회하는 부분
	public Temperature getTemp(String tempKey) {
		Temperature temp = td.getTemp(tempKey);
		return temp;
	}

}
