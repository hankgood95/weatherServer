package com.wook.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wook.model.dto.RegionCode;

@Mapper
public interface RegionCodeDao {

	RegionCode getRegionCode(RegionCode rc); 
	
}
