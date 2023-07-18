package com.fw.bo.system.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.dto.CommonDTO;
import com.fw.core.mapper.db1.bo.BoCommonMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoCommonService {

	private final BoCommonMapper boCommonMapper;

	public List<CommonDTO> selectCommonCd(CommonDTO commonDTO) {
		return boCommonMapper.selectCommonCd(commonDTO);
	}

	public List<CommonDTO> selectOrgList(CommonDTO commonDTO) {
		return boCommonMapper.selectOrgList(commonDTO);
	}

	public List<CommonDTO> selectBoardList(CommonDTO commonDTO) {
		return boCommonMapper.selectBoardList(commonDTO);
	}

}
