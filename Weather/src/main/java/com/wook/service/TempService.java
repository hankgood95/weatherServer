package com.wook.service;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wook.controller.ShortWeatherController;
import com.wook.model.dao.TempDao;
import com.wook.model.dto.TempRequest;
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
	
	public int saveTemp(Temperature temp) {
		// TODO Auto-generated method stub
		return td.insertTemp(temp);
	}
	
	public Temperature getTemp(TempRequest tr) {
		logger.info(tr.toString());
		Temperature temp = td.getTemp(tr);
		logger.info(temp.toString());
		return temp;
	}

}
