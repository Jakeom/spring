package com.fw.bo.system.screen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.dto.bo.BoHelpDTO;
import com.fw.core.mapper.db1.bo.BoHelpMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ScreenHelpService {
	private final BoHelpMapper boHelpMapper;
	
	/** 리스트  */
	public List<BoHelpDTO> selectHelpList(BoHelpDTO boHelpDTO) {
		return boHelpMapper.selectHelpList(boHelpDTO);
		
	}
	
	/** 글 등록 */
	public void insertHelpInfo(BoHelpDTO boHelpDTO) {
		boHelpMapper.insertHelpInfo(boHelpDTO);
	}

}
