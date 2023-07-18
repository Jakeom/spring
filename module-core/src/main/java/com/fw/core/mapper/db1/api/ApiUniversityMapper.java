package com.fw.core.mapper.db1.api;

import java.util.List;

import com.fw.core.dto.api.ApiResumeAcademicBackgroundDTO;

/***
 * @author wsh
 */
public interface ApiUniversityMapper {

	/** 학력 셀렉터 리스트 */
	List<ApiResumeAcademicBackgroundDTO> selectUniversityList(ApiResumeAcademicBackgroundDTO apiResumeAcademicBackgroundDTO);

}
