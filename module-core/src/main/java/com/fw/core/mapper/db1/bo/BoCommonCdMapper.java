package com.fw.core.mapper.db1.bo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.bo.BoCommonCdDTO;

@Mapper
public interface BoCommonCdMapper {

	/**
	 * 공통코드 리스트(cd_level=1) 조회
	 */
	List<BoCommonCdDTO> selectCommonCodeList(BoCommonCdDTO boCommonCdDTO);

	BoCommonCdDTO selectCommonCodeInfo(BoCommonCdDTO boCommonCdDTO);

	BoCommonCdDTO selectCommonCodeDetailInfo(BoCommonCdDTO boCommonCdDTO);

	/** 그룹코드 중복체크 */
    int selectGroupCdCheck(String groupCd);

	/** 코드 중복체크 */
    int selectCdCheck(String cd);

	/** 공통 코드(cd_level=1) 등록 */
	void insertCommonCd(BoCommonCdDTO boCommonCdDTO);

	/** 공통 코드(cd_level=2) 등록 */
	void insertDetailCommonCd(BoCommonCdDTO boCommonCdDTO);

	/** 공통 코드(cd_level=1) 수정 */
	void updateCommonCd(BoCommonCdDTO boCommonCdDTO);

	/** 공통 코드(cd_level=2) 수정 */
	void updateDetailCommonCd(BoCommonCdDTO boCommonCdDTO);

	void deleteCommonCd(BoCommonCdDTO boCommonCdDTO);

	void deleteDetailCommonCd(BoCommonCdDTO boCommonCdDTO);
}
