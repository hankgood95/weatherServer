package com.wook.model.dto;

import org.springframework.stereotype.Component;

@Component
public class SweatherRootRes {
	private Response response;

	
	
	public SweatherRootRes() {
		super();
	}


	public Response getResponse() {
		return response;
	}

	
	public void setResponse(Response response) {
		this.response = response;
	}


	@Override
	public String toString() {
		return "SweatherRootRes [response=" + response + "]";
	}
}
