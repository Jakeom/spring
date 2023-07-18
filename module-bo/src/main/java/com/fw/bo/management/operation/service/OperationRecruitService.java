package com.fw.bo.management.operation.service;

import java.util.List;

import com.fw.core.dto.bo.BoPositionBanWordDTO;
import com.fw.core.dto.bo.BoRecruitDTO;
import org.springframework.stereotype.Service;
import com.fw.core.mapper.db1.bo.BoRecruitMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OperationRecruitService {

	private final BoRecruitMapper boRecruitMapper;
	/**
	 * 채용 리스트
	 */
	public List<BoRecruitDTO> selectRecruitList(BoRecruitDTO boRecruitDTO) {
		return boRecruitMapper.selectRecruitList(boRecruitDTO);
	}

	/**
	 * selectParticipantName 리스트
	 */
	public List<BoRecruitDTO> selectParticipantName(BoRecruitDTO boRecruitDTO) {
		return boRecruitMapper.selectParticipantName(boRecruitDTO);
	}
	/**
	 * 채용 상세 취득
	 */
	public BoRecruitDTO selectRecruitDetail(BoRecruitDTO boRecruitDTO) {
		return boRecruitMapper.selectRecruitDetail(boRecruitDTO);
	}
	/**
	 * 채용 검색
	 */
	public List<BoRecruitDTO> saerchRecruitList(BoRecruitDTO boRecruitDTO) {
		return boRecruitMapper.searchRecruitList(boRecruitDTO);
	}

	public int searchRecruitListCnt(BoRecruitDTO boRecruitDTO) {
		return boRecruitMapper.searchRecruitListCnt(boRecruitDTO);
	}

	/**
	 * 채용 상태 확인
	 */
	public List<BoRecruitDTO> saerchCoworkRecruitList(BoRecruitDTO boRecruitDTO) {
		return boRecruitMapper.selectCoworkRecruitList(boRecruitDTO);
	}

	public List<BoPositionBanWordDTO> selectPositionBanWord(BoPositionBanWordDTO boPositionBanWordDTO) {
		return boRecruitMapper.selectPositionBanWord(boPositionBanWordDTO);
	}

	public BoPositionBanWordDTO insertPositionBanWord(BoPositionBanWordDTO boPositionBanWordDTO) {
		boRecruitMapper.insertPositionBanWord(boPositionBanWordDTO);
		return boPositionBanWordDTO;
	}

	public void updatePositionBanWord(BoPositionBanWordDTO boPositionBanWordDTO) {
		boRecruitMapper.updatePositionBanWord(boPositionBanWordDTO);
	}

	/**
	 * 채용 수정
	 */
	public void updateRecruit(BoRecruitDTO boRecruitDTO) {
		boRecruitMapper.updateRecruit(boRecruitDTO);
	}
}
