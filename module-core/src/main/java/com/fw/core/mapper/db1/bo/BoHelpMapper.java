package com.fw.core.mapper.db1.bo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.bo.BoHelpDTO;



@Mapper
public interface BoHelpMapper {
	
	/** 리스트 조회 */
	List<BoHelpDTO> selectHelpList(BoHelpDTO boHelpDTO);

	/** 글 등록 */
	void insertHelpInfo (BoHelpDTO boHelpDTO);
	
}

