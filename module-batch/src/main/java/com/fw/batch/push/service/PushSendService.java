package com.fw.batch.push.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fw.core.dto.api.ApiCompanyDTO;
import com.fw.core.mapper.db1.api.ApiCompanyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class PushSendService {
    
    @Autowired
    private ApiCompanyMapper companyMapper;

    /** 기업정보 조회 */
	public ApiCompanyDTO selectCompanyDetail(ApiCompanyDTO apiCompanyDTO) {
		return companyMapper.selectCompanyDetail(apiCompanyDTO);
	}
}
