package com.fw.core.mapper.db1.bo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.bo.BoAdminMenuProgramDTO;
import com.fw.core.dto.bo.BoAuthOrgDTO;
import com.fw.core.dto.bo.BoOrgDTO;

@Mapper
public interface BoAuthOrgMapper {

	/** 메뉴 리스트 조회 */
	List<BoOrgDTO> selectMenuProgramList(BoAdminMenuProgramDTO boAdminMenuProgramDTO);

	/** 조직 리스트 조회 */
	List<BoOrgDTO> selectOrgList(BoOrgDTO boOrgDTO);

	/** 조직 권한 리스트 취득 */
	List<BoAuthOrgDTO> selectAuthList(BoAuthOrgDTO boAuthOrgDTO);

	/** 조직 권한 적용 */
	void insertAuthOrg(BoAuthOrgDTO boAuthOrgDTO);

	/** 조직 권한 삭제 */
	void deleteOrgAuthAll(BoAuthOrgDTO boAuthOrgDTO);

}
