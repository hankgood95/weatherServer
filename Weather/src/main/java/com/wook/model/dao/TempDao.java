package com.wook.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wook.model.dto.Temperature;

@Mapper
public interface TempDao {
	int insertTemp(Temperature temp); //온도 DB 입력

	Temperature getTemp(String tempKey); //온도 조회
}
