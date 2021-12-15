package com.wook.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wook.model.dto.Temperature;

@Mapper
public interface TempDao {
	int insertTemp(Temperature temp);

	Temperature getTemp(String tempKey);
}
