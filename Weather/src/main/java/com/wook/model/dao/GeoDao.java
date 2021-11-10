package com.wook.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wook.model.dto.GeoInfo;

@Mapper
public interface GeoDao {
	List<GeoInfo> getGeoInfo();
}
