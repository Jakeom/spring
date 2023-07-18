package com.fw.core.mapper.db1.bo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.bo.BoOrgDTO;

/**
 * 조직 관리 Mapper
 * @author 문종현
 */
@Mapper
public interface BoOrgMapper {
	
	/** 조직 리스트 조회 */
    List<BoOrgDTO> selectOrgList(BoOrgDTO boOrgDTO);
    
    /** 조직 관리 수정 */
	void updateOrgInfo(BoOrgDTO boOrgDTO);

	/** 조직 아이디 중복 체크 */
	int selectOrgOverLap(String orgId);
	
	/** 조직 정보 조회 */
	BoOrgDTO selectOrg(BoOrgDTO boOrgDTO);

	/** 조직 정보 등록 */
	void insertOrgInfo(BoOrgDTO boOrgDTO);

	/** 조직 정보 삭제 */
	void deleteOrgInfo(BoOrgDTO boOrgDTO);

}
