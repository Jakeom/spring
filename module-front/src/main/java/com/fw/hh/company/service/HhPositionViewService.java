package com.fw.hh.company.service;

import org.springframework.stereotype.Service;

import com.fw.core.dto.hh.HhCompanyDTO;
import com.fw.core.mapper.db1.hh.HhPositionViewMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhPositionViewService {

	private final HhPositionViewMapper hhPositionViewMapper;

	public HhCompanyDTO selectCompanyDetail(HhCompanyDTO hhCompanyDTO) {
		return hhPositionViewMapper.selectCompanyDetail(hhCompanyDTO);
	}

	public HhCompanyDTO selectPositionDetail(HhCompanyDTO hhCompanyDTO) {
		return hhPositionViewMapper.selectPositionDetail(hhCompanyDTO);
	}

	public HhCompanyDTO selectPositionDetailByPositionId(HhCompanyDTO hhCompanyDTO) {
		return hhPositionViewMapper.selectPositionDetailByPositionId(hhCompanyDTO);
	}

	public HhCompanyDTO selectCompanyDetailByPositionId(HhCompanyDTO hhCompanyDTO) {
		return hhPositionViewMapper.selectCompanyDetailByPositionId(hhCompanyDTO);
	}

}