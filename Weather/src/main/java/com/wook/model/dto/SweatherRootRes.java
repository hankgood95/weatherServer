package com.wook.model.dto;

import org.springframework.stereotype.Component;

@Component
public class SweatherRootRes {
	private Response response;
	private boolean error;
	
	public SweatherRootRes(boolean error) {
		super();
		this.error = error;
	}


	public boolean isError() {
		return error;
	}


	public void setError(boolean error) {
		this.error = error;
	}


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
