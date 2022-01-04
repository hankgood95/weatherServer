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
	public TempMember checkManager(String managerKey) {
		return md.checkManager(managerKey);
	}
	
}
