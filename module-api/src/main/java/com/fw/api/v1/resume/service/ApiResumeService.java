package com.fw.api.v1.resume.service;

import com.fw.core.dto.api.ApiResumeDTO;
import com.fw.core.mapper.db1.api.ApiResumeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wsh
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiResumeService {

	private final ApiResumeMapper resumeMapper;

	/** 이력서 리스트 조회 */
	public List<ApiResumeDTO> selectResumeList(ApiResumeDTO apiResumeDTO) {
		List<ApiResumeDTO> list = resumeMapper.selectResumeList(apiResumeDTO);
		for(ApiResumeDTO r : list) { // 이력서 네이밍
			r.setResumeName(r.getName()+"_"+r.getTotalCareer()+"_"+r.getBirthYear()+"("+r.getAge()+")"+"_"+r.getFinalCompany()+"_"+r.getFinalSchool());
		}
		return list;
	}

	/** 이력서 존재여부 확인 */
	public boolean selectResumeExists(ApiResumeDTO apiResumeDTO) {
		return resumeMapper.selectResumeExists(apiResumeDTO);
	}

	/** 이력서 상세 조회 */
	public ApiResumeDTO selectResumeInfo(ApiResumeDTO apiResumeDTO) {
		return resumeMapper.selectResumeInfo(apiResumeDTO);
	}

	/** 이력서 복사 */
	public void insertResumeClone(ApiResumeDTO apiResumeDTO) {
		resumeMapper.insertResumeClone(apiResumeDTO);
	}

	/** 최근 등록 이력서 조회 */
	public ApiResumeDTO selectLastResume(ApiResumeDTO apiResumeDTO) {
		return resumeMapper.selectLastResume(apiResumeDTO);
	}
}
