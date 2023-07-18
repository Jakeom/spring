package com.fw.bo.system.org.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.dto.bo.BoGroupDTO;
import com.fw.core.mapper.db1.bo.BoGroupMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrgGroupService {

	private final BoGroupMapper boGroupMapper;

	public List<BoGroupDTO> selectGroupList(BoGroupDTO boGroupDTO) {
		return boGroupMapper.selectGroupList(boGroupDTO);
	}

	public BoGroupDTO selectGroupInfo(BoGroupDTO boGroupDTO) {
		return boGroupMapper.selectGroupInfo(boGroupDTO);
	}

	public BoGroupDTO selectGroupModify(BoGroupDTO boGroupDTO) {
		return boGroupMapper.selectGroupModify(boGroupDTO);
	}

	public int selectGroupIdCheck(String groupId) {
		return boGroupMapper.selectGroupIdCheck(groupId);
	}

	public void insertGroupInfo(BoGroupDTO boGroupDTO) {
		boGroupMapper.insertGroupInfo(boGroupDTO);
	}

	public void updateGroupInfo(BoGroupDTO boGroupDTO) {
		boGroupMapper.updateGroupInfo(boGroupDTO);
	}

	public void deleteGroupInfo(BoGroupDTO boGroupDTO) {
		boGroupMapper.deleteGroupInfo(boGroupDTO);
	}
}