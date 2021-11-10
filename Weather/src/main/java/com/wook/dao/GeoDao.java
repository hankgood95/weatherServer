package com.wook.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wook.model.GeoInfo;
@Repository
public interface GeoDao {
	List<GeoInfo> getGeoData();	
}
