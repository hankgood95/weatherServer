package com.wook.service;

import org.springframework.stereotype.Service;

import com.wook.model.dao.RegionCodeDao;
import com.wook.model.dto.RegionCode;

@Service
public class RegionCodeService {
	
	private RegionCodeDao rcd;

	public RegionCodeService(RegionCodeDao rcd) {
		super();
		this.rcd = rcd;
	}
	
	public RegionCode getRegionCode(RegionCode rc) {
		return rcd.getRegionCode(rc);
	}
	
}
