package com.wook.model.dto;

public class RegionCode {

	private String address;
	private String regionCode;
	private int type;
	
	public RegionCode() {}
	
	
	public RegionCode(String address, String regionCode, int type) {
		super();
		this.address = address;
		this.regionCode = regionCode;
		this.type = type;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "RegionCode [address=" + address + ", regionCode=" + regionCode + ", type=" + type + "]";
	}
	
	
}
