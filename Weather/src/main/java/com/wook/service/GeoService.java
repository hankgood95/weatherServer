package com.wook.service;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import com.wook.model.dao.GeoDao;
import com.wook.model.dto.GeoInfo;

@Service
@MapperScan("com.wook.model.dao")
public class GeoService {
	private GeoDao geodao;
	
	public GeoService(GeoDao geodao) {
		this.geodao = geodao;
	}
	
	public List<GeoInfo> getGeoXY() {
		return geodao.getGeoInfo();
	}
}
