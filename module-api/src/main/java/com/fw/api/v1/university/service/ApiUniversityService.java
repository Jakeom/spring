package com.fw.api.v1.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.dto.api.ApiResumeAcademicBackgroundDTO;
import com.fw.core.mapper.db1.api.ApiUniversityMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wsh
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiUniversityService {

	private final ApiUniversityMapper universityMapper;

	/** 학력 셀렉터 리스트 조회 */
	public List<ApiResumeAcademicBackgroundDTO> selectUniversityList(ApiResumeAcademicBackgroundDTO apiResumeAcademicBackgroundDTO) {
		return universityMapper.selectUniversityList(apiResumeAcademicBackgroundDTO);
	}
}
