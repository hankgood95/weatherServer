package com.wook.model.dto;

public class TempMember {
	
	private String email;
	private String serviceKey;
	
	public TempMember() {}
	
	public TempMember(String email, String managerKey, String serviceKey) {
		super();
		this.email = email;
		this.serviceKey = serviceKey;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getServiceKey() {
		return serviceKey;
	}
	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	@Override
	public String toString() {
		return "TempMember [email=" + email +  ", serviceKey=" + serviceKey + "]";
	}
}
