package com.fw.bo.system.auth.service;

import com.fw.core.dto.bo.BoAdminMenuProgramDTO;
import com.fw.core.dto.bo.BoAuthGroupDTO;
import com.fw.core.dto.bo.BoGroupDTO;
import com.fw.core.mapper.db1.bo.BoAuthGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthGroupService {

	private final BoAuthGroupMapper boAuthGroupMapper;

	/**
	 * 메뉴프로그램 리스트
	 */
	public List<BoAdminMenuProgramDTO> selectMenuProgram(BoAdminMenuProgramDTO boAdminMenuProgramDTO) {
		return boAuthGroupMapper.selectMenuProgram(boAdminMenuProgramDTO);
	}

	/**
	 * 그룹 리스트
	 */
	public List<BoGroupDTO> selectGroupList(BoGroupDTO boGroupDTO) {
		return boAuthGroupMapper.selectGroupList(boGroupDTO);
	}

	/**
	 * 그룹별 권한 정보
	 */
	public List<BoAuthGroupDTO> selectGroupAuth(BoAuthGroupDTO boAuthGroupDTO) {
		return boAuthGroupMapper.selectGroupAuth(boAuthGroupDTO);
	}

	/**
	 * 그룹별 권한 부여
	 */
	public void insertGroupAuth(BoAuthGroupDTO boAuthGroupDTO) {
		/* 권한 적용 전 모든 권한 초기화 */
		boAuthGroupMapper.deleteGroupAuth(boAuthGroupDTO);
		/* 권한적용 */
		boAuthGroupMapper.insertGroupAuth(boAuthGroupDTO);
	}
}
