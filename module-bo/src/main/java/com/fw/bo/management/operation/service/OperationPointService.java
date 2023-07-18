package com.fw.bo.management.operation.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.bo.*;
import com.fw.core.dto.fo.MsgDTO;
import com.fw.core.mapper.db1.bo.BoPointMapper;
import com.fw.core.util.SmsUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OperationPointService {

	private final BoPointMapper boPointMapper;
	private final CommonFileService commonFileService;
	/**
	 * 포인트 리스트
	 */
	public List<BoPointUseHistoryDTO> selectPointList(BoPointUseHistoryDTO boPointUseHistoryDTO) {
		return boPointMapper.selectPointList(boPointUseHistoryDTO);
	}
	/**
	 * 포인트 검색
	 */
	public List<BoPointUseHistoryDTO> saerchPointList(BoPointUseHistoryDTO boPointUseHistoryDTO) {
		return boPointMapper.searchPointList(boPointUseHistoryDTO);
	}
	/**
	 * 포인트 상태 확인
	 * */
	public List<BoPointUseHistoryDTO> saerchStatus(BoPointUseHistoryDTO boPointUseHistoryDTO) {
		return boPointMapper.searchStatus(boPointUseHistoryDTO);
	}

	public BoPointUseHistoryDTO selectPointMemberDetail(BoPointUseHistoryDTO boPointUseHistoryDTO) {
		return boPointMapper.selectPointMemberDetail(boPointUseHistoryDTO);
	}


	/**
	 * 상품리스트
	 */
	public List<BoRewardMallDTO> selectRewardMallList(BoRewardMallDTO boRewardMallDTO) {
		List<BoRewardMallDTO> list = boPointMapper.selectRewardMallList(boRewardMallDTO);
		for(int i=0; i< list.size(); i++) {
			String fileSeq = list.get(i).getFileSeq();
			if(!Objects.isNull(fileSeq)){
				if(StringUtils.isNotBlank(fileSeq)){
					list.get(i).setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
				}
			}
		}
		return list;
	}
	/**
	 * 상품 등록
	 */
	public void insertRewardMall(BoRewardMallDTO boRewardMallDTO) throws Exception {

		Integer fileId = commonFileService.fileUpload(boRewardMallDTO.getImageFile(), "REWARD_IMG");
		if (fileId != null) {
			boRewardMallDTO.setFileSeq(String.valueOf(fileId));
		}
		boPointMapper.insertRewardMall(boRewardMallDTO);
	}
	/**
	 * 상품 상세
	 */
    public BoRewardMallDTO selectRewardDetail(BoRewardMallDTO boRewardMallDTO) {
		BoRewardMallDTO bo =  boPointMapper.selectRewardDetail(boRewardMallDTO);
		bo.setCommonFileList(commonFileService.selectFileDetailList(bo.getFileSeq()));


				for(FileDTO file : bo.getCommonFileList()){
					System.out.println(file.getName());
				}

		return bo;
    }
	/**
	 * 상품 정보 수정
	 */
	public void updateReward(BoRewardMallDTO boRewardMallDTO) throws Exception {
		if(boRewardMallDTO.getFileCheck().equals("Y")) {
			if (boRewardMallDTO.getFileSeq()!=null && !boRewardMallDTO.getFileSeq().equals("") && !boRewardMallDTO.getFileSeq().equals("0")) {
				commonFileService.updateFileDetail(boRewardMallDTO.getFileSeq());
				commonFileService.fileUpdate(boRewardMallDTO.getImageFile(), "REWARD_IMG" , boRewardMallDTO.getFileSeq());
			} else{
				Integer fileId = commonFileService.fileUpload(boRewardMallDTO.getImageFile(), "REWARD_IMG");
				if (fileId != null) {
					boRewardMallDTO.setFileSeq(String.valueOf(fileId));
				}
			}
		}
		boPointMapper.updateReward(boRewardMallDTO);
	}

	/**
	 * 상품 정보 삭제
	 */
	public void deleteReward(BoRewardMallDTO boRewardMallDTO) {
		boPointMapper.deleteReward(boRewardMallDTO);
	}
	/**
	 * 상품 검색
	 */
	public List<BoRewardHistoryDTO> saerchRewardMall(BoRewardMallDTO boRewardMallDTO) {
		return boPointMapper.searchRewardMall(boRewardMallDTO);
	}
	/**
	 * 리워드 상세 (신청현황)
	 */
	public List<BoRewardHistoryDTO> selectOrderDetail(BoRewardHistoryDTO boRewardHistoryDTO) {
		return boPointMapper.selectOrderDetail(boRewardHistoryDTO);
	}

	public List<BoPointUseHistoryDTO> selectPointDetail(BoPointUseHistoryDTO boPointHistoryDTO) {
		return boPointMapper.selectPointDetail(boPointHistoryDTO);
	}

	public void sendSms(BoRewardHistoryDTO boRewardHistoryDTO, MsgDTO msgDTO) {
		SmsUtil.sendMessage(MsgDTO.builder()
				.subject("")
				.type("SMS")
				.to(boRewardHistoryDTO.getMemberPhone())
				.message(boRewardHistoryDTO.getContent())
				.build());
		boPointMapper.rewardSendUpdate(boRewardHistoryDTO);
	}

	public void insertPointUseHistory(BoPointUseHistoryDTO boPointUseHistoryDTO) {
		if(boPointMapper.selectHavePointCnt(boPointUseHistoryDTO) == 0) {
			boPointMapper.insertPoint(boPointUseHistoryDTO);
		}
		boPointMapper.insertPointUseHistory(boPointUseHistoryDTO);
		boPointMapper.updatePointBalance(boPointUseHistoryDTO);
	}
}
