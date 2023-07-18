package com.fw.bo.system.org.service;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminIpDTO;
import com.fw.core.mapper.db1.bo.BoAdminIpMapper;
import com.fw.core.mapper.db1.bo.BoAdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Admin Service
 */
@Service
@RequiredArgsConstructor
public class OrgAdminService {

	private final BoAdminMapper boAdminMapper;
	private final BoAdminIpMapper boAdminIpMapper;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<BoAdminDTO> selectAdminList(BoAdminDTO boAdminDTO) {
		return boAdminMapper.selectAdminList(boAdminDTO);
	}

	public List<BoAdminIpDTO> selectIpForAdminSeq(BoAdminIpDTO boAdminIpDTO) { return boAdminMapper.selectIpForAdminSeq(boAdminIpDTO); }

	public int selectIdCheck(String adminId) {
		return boAdminMapper.selectIdCheck(adminId);
	}

	public BoAdminDTO selectAdmin(BoAdminDTO boAdminDTO) {
		return boAdminMapper.selectAdmin(boAdminDTO);
	}

	public void insertAdmin(BoAdminDTO boAdminDTO) {
		boAdminDTO.setAdminPassword(bCryptPasswordEncoder.encode(boAdminDTO.getAdminPassword()));
		boAdminMapper.insertAdmin(boAdminDTO);

		String adminSeq = boAdminDTO.getAdminSeq();
		for(BoAdminIpDTO b : boAdminDTO.getIpList()){
			b.setAdminSeq(adminSeq);
			boAdminIpMapper.insertAdminIp(b);
		}
	}

	public void updateAdmin(BoAdminDTO boAdminDTO) {
		boAdminMapper.updateAdmin(boAdminDTO);

		BoAdminIpDTO boAdminIpDTO = new BoAdminIpDTO();
		boAdminIpDTO.setAdminSeq(boAdminDTO.getAdminSeq());
		boAdminIpMapper.deleteAdminIp(boAdminIpDTO);
		for(BoAdminIpDTO b : boAdminDTO.getIpList()){
			b.setAdminSeq(boAdminDTO.getAdminSeq());
			boAdminIpMapper.insertAdminIp(b);
		}

	}

	public void deleteAdmin(BoAdminDTO boAdminDTO) {
		boAdminMapper.deleteAdmin(boAdminDTO);
	}


}
