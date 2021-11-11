package com.wook.service;

import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import com.wook.model.dao.GeoDao;
import com.wook.model.dto.GeoInfo;

@Service
public class GeoService {
	private GeoDao geodao;
	
	public GeoService(GeoDao geodao) {
		this.geodao = geodao;
	}
	
	public List<GeoInfo> getGeoXY() {
		return geodao.getGeoInfo();
	}
}
