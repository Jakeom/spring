package com.fw.core.mapper.db1.bo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.CommonDTO;

@Mapper
public interface BoCommonMapper {

	/**
	 * 공통코드 리스트 조회
	 */
	List<CommonDTO> selectCommonCd(CommonDTO commonDTO);

	/**
	 * 조직 리스트 조회
	 */
	List<CommonDTO> selectOrgList(CommonDTO commonDTO);

	/**
	 * 공지 종류 리스트 조회
	 */
	List<CommonDTO> selectBoardList(CommonDTO commonDTO);

}
