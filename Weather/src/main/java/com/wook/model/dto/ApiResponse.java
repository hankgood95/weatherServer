package com.wook.model.dto;

public class ApiResponse {
	private int statusCode;
	private String message;
	private Object ob;
	
	public ApiResponse() {}

	public ApiResponse(int statusCode, String message, Object ob) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.ob = ob;
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

	public Object getOb() {
		return ob;
	}

	public void setOb(Object ob) {
		this.ob = ob;
	}

	@Override
	public String toString() {
		return "ApiResponse [statusCode=" + statusCode + ", message=" + message + ", ob=" + ob + "]";
	}
}
