package com.fw.api.v1.common.service;

import com.fw.core.dto.api.ApiCommonCdDTO;
import com.fw.core.dto.api.ApiInfoDTO;
import com.fw.core.mapper.db1.api.ApiCommonInfoMapper;
import com.fw.core.mapper.db1.api.ApiCommonMapper;
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
public class ApiCommonService {

	private final ApiCommonInfoMapper apiCommonInfoMapper;
	private final ApiCommonMapper apiCommonMapper;

	/** 약관 조회 */
	public List<ApiInfoDTO> selectCommonInfo(ApiInfoDTO apiInfoDTO) {
		return apiCommonInfoMapper.selectCommonInfo(apiInfoDTO);
	}

	/** 공통코드 리스트 조회 */
	public List<ApiCommonCdDTO> selectCommonCd(ApiCommonCdDTO apiCommonCdDTO) {
		return apiCommonMapper.selectCommonCd(apiCommonCdDTO);
	}

}
