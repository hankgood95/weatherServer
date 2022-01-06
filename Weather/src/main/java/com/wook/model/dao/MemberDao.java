package com.wook.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wook.model.dto.MemberResponse;
import com.wook.model.dto.TempMember;

@Mapper
public interface MemberDao {
	//관리자 서비스키를 확인하는 부분
	int checkServiceKey(String serviceKey);
	
	//이메일 중복 체크하는 부분
	int checkEmail(String email);
	
	//새로운 멤버를 insert 하는 부분
	int insertMember(TempMember tm);
	
	//이메일로 서비스키 조회
	TempMember getServiceKey(String email);
}
