package com.wook.model.dto;

public class MemberResponse {
	private int reponseCode;
	private String message;
	private TempMember tm;
	
	
	public MemberResponse() {}
	
	public MemberResponse(int reponseCode, String message, TempMember tm) {
		super();
		this.reponseCode = reponseCode;
		this.message = message;
		this.tm = tm;
	}

	public int getReponseCode() {
		return reponseCode;
	}
	public void setReponseCode(int reponseCode) {
		this.reponseCode = reponseCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TempMember getTm() {
		return tm;
	}
	public void setTm(TempMember tm) {
		this.tm = tm;
	}

	@Override
	public String toString() {
		return "MemberResponse [reponseCode=" + reponseCode + ", message=" + message + ", tm=" + tm + "]";
	}
	
}
