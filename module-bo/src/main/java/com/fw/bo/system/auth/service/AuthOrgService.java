package com.fw.bo.system.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.dto.bo.BoAdminMenuProgramDTO;
import com.fw.core.dto.bo.BoAuthOrgDTO;
import com.fw.core.dto.bo.BoOrgDTO;
import com.fw.core.mapper.db1.bo.BoAuthOrgMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthOrgService {

	private final BoAuthOrgMapper boAuthOrgMapper;

	public List<BoOrgDTO> selectMenuProgramList(BoAdminMenuProgramDTO boAdminMenuProgramDTO) {
		return boAuthOrgMapper.selectMenuProgramList(boAdminMenuProgramDTO);
	}

	public List<BoOrgDTO> selectOrgList(BoOrgDTO boOrgDTO) {
		return boAuthOrgMapper.selectOrgList(boOrgDTO);
	}

	public List<BoAuthOrgDTO> selectAuthList(BoAuthOrgDTO boAuthOrgDTO) {
		return boAuthOrgMapper.selectAuthList(boAuthOrgDTO);
	}

	public void insertAuthOrg(BoAuthOrgDTO boAuthOrgDTO) {
		String orgId = boAuthOrgDTO.getOrgId();
		boAuthOrgMapper.deleteOrgAuthAll(boAuthOrgDTO);
		for (BoAuthOrgDTO b : boAuthOrgDTO.getAuthList()) {
			b.setOrgId(orgId);
			boAuthOrgMapper.insertAuthOrg(b);
		}
	}

}