package com.fw.core.mapper.db1.api;

import com.fw.core.dto.api.ApiResumeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApiResumeMapper {

	// 이력서 리스트 조회
	List<ApiResumeDTO> selectResumeList(ApiResumeDTO apiResumeDTO);

	// 이력서 존재여부 확인
	boolean selectResumeExists(ApiResumeDTO apiResumeDTO);

	// 이력서 상세 조회
	ApiResumeDTO selectResumeInfo(ApiResumeDTO apiResumeDTO);

	// 이력서 복사
	void insertResumeClone(ApiResumeDTO apiResumeDTO);

	// 최근 등록 이력서 조회
	ApiResumeDTO selectLastResume(ApiResumeDTO apiResumeDTO);
}
