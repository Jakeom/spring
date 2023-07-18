package com.fw.bo.system.setting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.dto.bo.BoCommonCdDTO;
import com.fw.core.mapper.db1.bo.BoCommonCdMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoCommonCdService {

	private final BoCommonCdMapper boCommonCdMapper;

	public List<BoCommonCdDTO> selectCommonCodeList(BoCommonCdDTO boCommonCdDTO) {
		return boCommonCdMapper.selectCommonCodeList(boCommonCdDTO);
	}

	public BoCommonCdDTO selectCommonCodeInfo(BoCommonCdDTO boCommonCdDTO) {
		return boCommonCdMapper.selectCommonCodeInfo(boCommonCdDTO);
	}

	public BoCommonCdDTO selectCommonCodeDetailInfo(BoCommonCdDTO boCommonCdDTO) {
		return boCommonCdMapper.selectCommonCodeDetailInfo(boCommonCdDTO);
	}

	public int selectGroupCdCheck(String groupCd) {
		return boCommonCdMapper.selectGroupCdCheck(groupCd);
	}

	public int selectCdCheck(String cd) {
		return boCommonCdMapper.selectCdCheck(cd);
	}

	public void insertCommonCd(BoCommonCdDTO boCommonCdDTO) {
		boCommonCdMapper.insertCommonCd(boCommonCdDTO);
	}

	public void insertDetailCommonCd(BoCommonCdDTO boCommonCdDTO) {
		boCommonCdMapper.insertDetailCommonCd(boCommonCdDTO);
	}

	public void updateCommonCd(BoCommonCdDTO boCommonCdDTO) {
		boCommonCdMapper.updateCommonCd(boCommonCdDTO);
	}

	public void updateDetailCommonCd(BoCommonCdDTO boCommonCdDTO) {
		boCommonCdMapper.updateDetailCommonCd(boCommonCdDTO);
	}

	public void deleteCommonCd(BoCommonCdDTO boCommonCdDTO) {
		boCommonCdMapper.deleteCommonCd(boCommonCdDTO);
	}

	public void deleteDetailCommonCd(BoCommonCdDTO boCommonCdDTO) {
		boCommonCdMapper.deleteDetailCommonCd(boCommonCdDTO);
	}
}
