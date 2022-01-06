package com.wook.service;

import org.springframework.stereotype.Service;

import com.wook.model.dao.MemberDao;
import com.wook.model.dto.TempMember;

@Service
public class MemberService {

	private MemberDao md;
	
	public MemberService(MemberDao md) {
		super();
		this.md = md;
	}
	
	//매니저의 키를 확인하는 부분
	public int checkServiceKey(String serviceKey) {
		return md.checkServiceKey(serviceKey);
	}
	
	//이메일 중복 체크하는 부분
	public int checkEmail(String email) {
		return md.checkEmail(email);
	}
	
	//이메일과 서비스키 입력하는 부분
	public int insertMember(TempMember tm) {
		return md.insertMember(tm);
	}
	
	//이메일로 서비스키 조회하는 부분
	public TempMember getServiceKey(String email) {
		return md.getServiceKey(email);
	}
}
