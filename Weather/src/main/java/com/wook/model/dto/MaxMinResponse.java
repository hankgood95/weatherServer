package com.wook.model.dto;

public class MaxMinResponse {
	private int statusCode;
	private String message;
	private Temperature tmp;
	
	
	
	public MaxMinResponse() {}
	
	
	public MaxMinResponse(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}


	public MaxMinResponse(int statusCode, String message, Temperature tmp) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.tmp = tmp;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Temperature getTmp() {
		return tmp;
	}
	public void setTmp(Temperature tmp) {
		this.tmp = tmp;
	}
	
	
}
