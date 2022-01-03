package com.wook.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wook.model.dto.TempMember;

@Mapper
public interface MemberDao {
	TempMember checkManager(String managerKey);
	int insertMember(TempMember tm);
}
