package com.fw.bo.management.operation.service;

import com.fw.core.dto.bo.BoInfoDTO;
import com.fw.core.mapper.db1.bo.BoInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationInfoService {

	private final BoInfoMapper boInfoMapper;
	/**
	 * 정보 리스트 취득
	 */
	public List<BoInfoDTO> selectInfoList(BoInfoDTO boInfoDTO) {
		return boInfoMapper.selectInfoList(boInfoDTO);
	}
	/**
	 * 	 정보 상세 취득
	 */
	public BoInfoDTO selectInfoModify(BoInfoDTO boInfoDTO) {
		return boInfoMapper.selectInfoModify(boInfoDTO);
	}
	/**
	 * 	 정보 상세 조회 취득
	 */
	public BoInfoDTO selectInfoDetail(BoInfoDTO boInfoDTO) {
		return boInfoMapper.selectInfoDetail(boInfoDTO);
	}
	/**
	 * 정보  검색
	 */
	public List<BoInfoDTO> saerchInfoList(BoInfoDTO boInfoDTO) {
		return boInfoMapper.searchInfoList(boInfoDTO);
	}
	/**
	 * 정보 등록
	 */
	public void insertInfo(BoInfoDTO boInfoDTO) {
		boInfoMapper.insertInfo(boInfoDTO);
	}
	/**
	 * 정보 수정
	 */
	public void updateInfo(BoInfoDTO boInfoDTO) {
		boInfoMapper.updateInfo(boInfoDTO);
	}
	/**
	 * 정보 삭제
	 */
	public void deleteInfo(BoInfoDTO boInfoDTO) {

		boInfoMapper.deleteInfo(boInfoDTO);
	}

}
