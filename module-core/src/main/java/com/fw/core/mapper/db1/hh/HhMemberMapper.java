package com.fw.core.mapper.db1.hh;

import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.hh.HhSfChangeRequestDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HhMemberMapper {

	FoMemberDTO selectExistMember(FoMemberDTO foMemberDTO);
	void insertHhResumeReadingHistory(FoMemberDTO foMemberDTO);
	boolean selectSfChangeHistory(FoMemberDTO foMemberDTO);
	void insertSfChange(HhSfChangeRequestDTO hhSfChangeRequestDTO);
}
