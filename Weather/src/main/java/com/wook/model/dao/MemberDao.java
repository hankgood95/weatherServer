package com.wook.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wook.model.dto.TempMember;

@Mapper
public interface MemberDao {
	//관리자 서비스키를 확인하는 부분
	TempMember checkManager(String managerKey);
	
	//새로운 멤버를 insert 하는 부분
	int insertMember(TempMember tm);
}
