package com.fw.core.mapper.db1.api;

import com.fw.core.dto.api.ApiMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApiHeadHunterMapper {
	/** 내 인재 리스트 */
	List<ApiMemberDTO> selectApList(ApiMemberDTO apiMemberDTO);
	int selectApListCnt(ApiMemberDTO apiMemberDTO);
}