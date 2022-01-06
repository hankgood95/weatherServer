package com.wook.model.dto;

public class RegionCode {

	private String address;
	private String regionTempCode;
	private int type;
	
	public RegionCode() {}
	
	
	public RegionCode(String address, String regionTempCode, int type) {
		super();
		this.address = address;
		this.regionTempCode = regionTempCode;
		this.type = type;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRegionTempCode() {
		return regionTempCode;
	}
	public void setRegionTempCode(String regionTempCode) {
		this.regionTempCode = regionTempCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "RegionCode [address=" + address + ", regionTempCode=" + regionTempCode + ", type=" + type + "]";
	}
	
	
}
