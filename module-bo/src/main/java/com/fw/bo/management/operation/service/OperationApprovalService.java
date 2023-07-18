package com.fw.bo.management.operation.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.bo.BoHhApprovalRequestDTO;
import com.fw.core.dto.bo.BoSfChangeRequestDTO;
import com.fw.core.dto.bo.BoWefirmRequestHistoryDTO;
import com.fw.core.mapper.db1.bo.BoApprovalMapper;
import com.fw.core.mapper.db1.common.CommonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OperationApprovalService {

	private final BoApprovalMapper boApprovalMapper;
	private final CommonMapper commonMapper;
	private final CommonFileService commonFileService;
	private final CommonService commonService;

	/**
	 * 헤드한터 검색
	 */
	public List<BoHhApprovalRequestDTO> saerchHeadhunterRequestList(BoHhApprovalRequestDTO boHhApprovalRequestDTO) {
		return boApprovalMapper.saerchHeadhunterRequestList(boHhApprovalRequestDTO);
	}
	/**
	 * 헤드한터 리스트
	 */
	public List<BoHhApprovalRequestDTO> selectHeadhunterRequestList(BoHhApprovalRequestDTO boHhApprovalRequestDTO) {
		return boApprovalMapper.selectHeadhunterRequestList(boHhApprovalRequestDTO);
	}
	/**
	 * 헤드한터 상태 확인
	 * */
	public List<BoHhApprovalRequestDTO> saerchHeadhunterStatus(BoHhApprovalRequestDTO boHhApprovalRequestDTO) {
		return boApprovalMapper.searchHeadhunterStatus(boHhApprovalRequestDTO);
	}
	/**
	 * 헤드한터 상태 거절
	 */
	public void updateRejectRequest(BoHhApprovalRequestDTO boHhApprovalRequestDTO) {
		boApprovalMapper.updateRejectRequest(boHhApprovalRequestDTO);

		BoHhApprovalRequestDTO hhInfo = boApprovalMapper.searchHeadhunter(boHhApprovalRequestDTO);
		boHhApprovalRequestDTO.setMemberId(hhInfo.getMemberId());

		String dispType = "HH_JOIN_DENY";
		String content = "HH 가입이 거절되었습니다.";
		String title = "HH 가입신청";

		PushHistDTO push = PushHistDTO.builder()
				.dispType(dispType)
				.memberId(boHhApprovalRequestDTO.getMemberId())
				.title(title)
				.content(content)
				.createId(boHhApprovalRequestDTO.getAdminSession().getAdminSeq()).build();
		commonService.insertPushHist(push);
	}
	/**
	 * 헤드한터 상태 승인
	 */
	public void updateAcceptRequest(BoHhApprovalRequestDTO boHhApprovalRequestDTO) {
		boApprovalMapper.updateAcceptRequest(boHhApprovalRequestDTO);
		boApprovalMapper.updateHhApproved(boHhApprovalRequestDTO);

		BoHhApprovalRequestDTO hhInfo = boApprovalMapper.searchHeadhunter(boHhApprovalRequestDTO);
		boHhApprovalRequestDTO.setMemberId(hhInfo.getMemberId());

		String dispType = "HH_JOIN_APPROVAL";
		String content = "HH 가입이 승인되었습니다.";
		String title = "HH 가입신청";

		PushHistDTO push = PushHistDTO.builder()
				.dispType(dispType)
				.memberId(boHhApprovalRequestDTO.getMemberId())
				.title(title)
				.content(content)
				.createId(boHhApprovalRequestDTO.getAdminSession().getAdminSeq()).build();
		commonService.insertPushHist(push);
	}

	/**
	 * 서치펌 리스트
	 */
	public List<BoSfChangeRequestDTO> selectSFRequestList(BoSfChangeRequestDTO boSfChangeRequestDTO) {
		return boApprovalMapper.selectSFRequestList(boSfChangeRequestDTO);
	}
	/**
	 * 서치펌 검색
	 */
	public List<BoSfChangeRequestDTO> saerchSFRequestList(BoSfChangeRequestDTO boSfChangeRequestDTO) {
		return boApprovalMapper.saerchSFRequestList(boSfChangeRequestDTO);
	}
	/**
	 * 서치펌 상태 확인
	 * */
	public List<BoSfChangeRequestDTO> saerchSFStatus(BoSfChangeRequestDTO boSfChangeRequestDTO) {
		return boApprovalMapper.searchSFStatus(boSfChangeRequestDTO);
	}
	/**
	 * 서치펌 상태 거절
	 */
	public void updateSFRejectRequest(BoSfChangeRequestDTO boSfChangeRequestDTO) {
		boApprovalMapper.updateSFRejectRequest(boSfChangeRequestDTO);

		BoSfChangeRequestDTO sfInfo = boApprovalMapper.selectSFRequest(boSfChangeRequestDTO);
		boSfChangeRequestDTO.setMemberId(sfInfo.getMemberId());

		String dispType = "SF_CHANGE_DENY";
		String content = "SF 변경신청이 거절되었습니다.";
		String title = "SF 변경신청";

		PushHistDTO push = PushHistDTO.builder()
				.dispType(dispType)
				.memberId(boSfChangeRequestDTO.getMemberId())
				.title(title)
				.content(content)
				.createId(boSfChangeRequestDTO.getAdminSession().getAdminSeq()).build();
		commonService.insertPushHist(push);
	}
	/**
	 * 서치펌 상태 승인
	 */
	public void updateSFAcceptRequest(BoSfChangeRequestDTO boSfChangeRequestDTO) {
		boApprovalMapper.updateSFAcceptRequest(boSfChangeRequestDTO);

		BoSfChangeRequestDTO sfInfo = boApprovalMapper.selectSFRequest(boSfChangeRequestDTO);
		boSfChangeRequestDTO.setMemberId(sfInfo.getMemberId());

		String dispType = "SF_CHANGE_APPROVAL";
		String content = "SF 변경신청이 승인되었습니다.";
		String title = "SF 변경신청";

		PushHistDTO push = PushHistDTO.builder()
				.dispType(dispType)
				.memberId(boSfChangeRequestDTO.getMemberId())
				.title(title)
				.content(content)
				.createId(boSfChangeRequestDTO.getAdminSession().getAdminSeq()).build();
		commonService.insertPushHist(push);
	}

	/**
	 * 위펌  리스트
	 */
	public List<BoWefirmRequestHistoryDTO> selectWefirmRequestList(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO) {
		return boApprovalMapper.selectWefirmRequestList(boWefirmRequestHistoryDTO);
	}
	/**
	 * 위펌 검색
	 */
	public List<BoWefirmRequestHistoryDTO> saerchWefirmRequestList(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO) {
		return boApprovalMapper.saerchWefirmRequestList(boWefirmRequestHistoryDTO);
	}
	/**
	 * 위펌 상태 거절
	 */
	public List<BoWefirmRequestHistoryDTO> saerchWefirmStatus(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO) {
		return boApprovalMapper.searchWefirmStatus(boWefirmRequestHistoryDTO);
	}
	/**
	 * 위펌 상태 거절
	 */
	public void updateWefirmRejectRequest(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO) {
		boApprovalMapper.updateWefirmRejectRequest(boWefirmRequestHistoryDTO);

		// wefirm_request_history id로 wefirm_id 조회 -> wefirm_id로 해당 위펌 운영자들을 조회
		BoWefirmRequestHistoryDTO wefirmInfo = boApprovalMapper.selectWefirmRequestHistory(boWefirmRequestHistoryDTO);
		boWefirmRequestHistoryDTO.setWefirmId(wefirmInfo.getWefirmId());

		List<BoWefirmRequestHistoryDTO> wefirmAdmin = boApprovalMapper.selectWefirmAdminList(boWefirmRequestHistoryDTO);
		for (BoWefirmRequestHistoryDTO b : wefirmAdmin) { // 위펌 승인 거절 push 알림 전송
			String dispType = "";
			String content = "";
			String title = "";
			if (StringUtils.equals(wefirmInfo.getRequestType(), "OPEN")) {
				dispType = "WE_OPEN_RESULT";
				title = "WE펌 개설신청 결과";
				content = "WE펌 개설신청이 거부 되었습니다.";
			} else if (StringUtils.equals(wefirmInfo.getRequestType(), "CLOSE")) {
				dispType = "WE_CLOSE_RESULT";
				title = "WE펌 폐쇄신청 결과";
				content = "WE펌 폐쇄신청이 거부 되었습니다.";
			}

			PushHistDTO push = PushHistDTO.builder()
					.dispType(dispType)
					.memberId(b.getMemberId())
					.title(title)
					.content(content)
					.createId(boWefirmRequestHistoryDTO.getAdminSession().getAdminSeq()).build();
			commonService.insertPushHist(push);
		}
	}
	/**
	 * 위펌 상태 승인
	 */
	@Transactional
	public void updateWefirmAcceptRequest(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO) {
		boApprovalMapper.updateWefirmAcceptRequest(boWefirmRequestHistoryDTO);

		// wefirm_request_history id로 wefirm_id 조회 -> wefirm_id로 해당 위펌 운영자들을 조회
		BoWefirmRequestHistoryDTO wefirmInfo = boApprovalMapper.selectWefirmRequestHistory(boWefirmRequestHistoryDTO);
		boWefirmRequestHistoryDTO.setWefirmId(wefirmInfo.getWefirmId());
		if (StringUtils.equals(wefirmInfo.getRequestType(), "OPEN")) {
			boApprovalMapper.updateWefirmApprovalHh(boWefirmRequestHistoryDTO);
			boApprovalMapper.updateWefirmApproval(boWefirmRequestHistoryDTO);
		} else if (StringUtils.equals(wefirmInfo.getRequestType(), "CLOSE")) {
			boApprovalMapper.updateWefirmRejectHh(boWefirmRequestHistoryDTO);
			boApprovalMapper.updateWefirmReject(boWefirmRequestHistoryDTO);
		}
		List<BoWefirmRequestHistoryDTO> wefirmAdmin = boApprovalMapper.selectWefirmAdminList(boWefirmRequestHistoryDTO);
		for (BoWefirmRequestHistoryDTO b : wefirmAdmin) { // 위펌 승인 수락 push 알림 전송
			String dispType = "";
			String content = "";
			String title = "";
			if (StringUtils.equals(wefirmInfo.getRequestType(), "OPEN")) {
				dispType = "WE_OPEN_RESULT";
				title = "WE펌 개설신청";
				content = "WE펌 개설신청이 승인되었습니다.";
			} else if (StringUtils.equals(wefirmInfo.getRequestType(), "CLOSE")) {
				dispType = "WE_CLOSE_RESULT";
				title = "WE펌 폐쇄신청";
				content = "WE펌 폐쇄신청이 승인되었습니다.";
			}

			PushHistDTO push = PushHistDTO.builder()
					.dispType(dispType)
					.memberId(b.getMemberId())
					.title(title)
					.content(content)
					.createId(boWefirmRequestHistoryDTO.getAdminSession().getAdminSeq()).build();
			commonService.insertPushHist(push);
		}
	}


	public BoHhApprovalRequestDTO selecthhRequestDetail(BoHhApprovalRequestDTO boHhApprovalRequestDTO) {
		BoHhApprovalRequestDTO bo =	boApprovalMapper.selecthhRequestDetail(boHhApprovalRequestDTO);
		List<FileDTO> list = commonFileService.selectFileDetailList(bo.getSfWorkerListFileId());
		if(!list.isEmpty()) {
			bo.setOriginName(list.get(0).getOriginName());
			bo.setUrl(list.get(0).getUrl());
		}
		return bo;
	}

	public BoSfChangeRequestDTO selectSearchFirmDetail(BoSfChangeRequestDTO boSfChangeRequestDTO) {
		BoSfChangeRequestDTO bo = boApprovalMapper.selectSearchFirmDetail(boSfChangeRequestDTO);
		List<FileDTO> list = commonFileService.selectFileDetailList(bo.getSfWorkerListFileId());
		if(!list.isEmpty()) {
			bo.setOriginName(list.get(0).getOriginName());
			bo.setUrl(list.get(0).getUrl());
		}
		return bo;
	}

	public Object selectWefirmDetail(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO) {
		BoWefirmRequestHistoryDTO bo = boApprovalMapper.selectWefirmDetail(boWefirmRequestHistoryDTO);
		List<FileDTO> list = commonFileService.selectFileDetailList(bo.getFileId());
		if(!list.isEmpty()) {
			bo.setOriginName(list.get(0).getOriginName());
			bo.setUrl(list.get(0).getUrl());
		}
		return bo;
	}
}
