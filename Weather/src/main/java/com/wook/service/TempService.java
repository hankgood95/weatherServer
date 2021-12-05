package com.wook.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import com.wook.model.dao.TempDao;
import com.wook.model.dto.Temperature;

@Service
@MapperScan("com.wook.model.dao")
public class TempService {

	private TempDao td;
	
	public TempService(TempDao td) {
		super();
		this.td = td;
	}
	
	public int saveTemp(Temperature temp) {
		// TODO Auto-generated method stub
		return td.insertTemp(temp);
	}

}
