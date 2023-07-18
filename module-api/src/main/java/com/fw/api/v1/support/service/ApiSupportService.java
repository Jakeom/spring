package com.fw.api.v1.support.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.dto.api.ApiBannerDTO;
import com.fw.core.dto.api.ApiCompanyDTO;
import com.fw.core.dto.api.ApiFaqDTO;
import com.fw.core.dto.api.ApiNoticeDTO;
import com.fw.core.mapper.db1.api.ApiBannerMapper;
import com.fw.core.mapper.db1.api.ApiCompanyMapper;
import com.fw.core.mapper.db1.api.ApiFaqMapper;
import com.fw.core.mapper.db1.api.ApiNoticeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wsh
 * @since 2022.11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiSupportService {

	private final ApiNoticeMapper apiNoticeMapper;
	private final ApiFaqMapper apiFaqMapper;
	private final ApiBannerMapper apiBannerMapper;
	private final ApiCompanyMapper apiCompanyMapper;

	/** 공지사항 리스트 조회 */
	public List<ApiNoticeDTO> selectNoticeList(ApiNoticeDTO apiNoticeDTO) {
		return apiNoticeMapper.selectNoticeList(apiNoticeDTO);
	}

	/** FAQ 리스트 조회 */
	public List<ApiFaqDTO> selectFaqList(ApiFaqDTO apiFaqDTO) {
		return apiFaqMapper.selectFaqList(apiFaqDTO);
	}

	/** 배너 리스트 조회 */
	public List<ApiBannerDTO> selectBannerList(ApiBannerDTO apiBannerDTO) {
		return apiBannerMapper.selectBannerList(apiBannerDTO);
	}

	/** 기업정보 조회 */
	public ApiCompanyDTO selectCompanyDetail(ApiCompanyDTO apiCompanyDTO) {
		return apiCompanyMapper.selectCompanyDetail(apiCompanyDTO);
	}

}
