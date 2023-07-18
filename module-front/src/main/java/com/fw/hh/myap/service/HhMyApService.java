package com.fw.hh.myap.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.hh.*;
import com.fw.core.mapper.db1.hh.HhMyApMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhMyApService {

	private final CommonFileService commonFileService;
	private final HhMyApMapper hhMyApMapper;

	public int selectHhMyapListCount(HhMyapListDTO hhMyapListDTO) {
		return hhMyApMapper.selectHhMyapListCount(hhMyapListDTO);
	}

	public int insertMyapgroup(HhMyapHistoryGroupDTO hhMyapHistoryGroupDTO) {
		return hhMyApMapper.insertMyapgroup(hhMyapHistoryGroupDTO);
	}

	public List<HhMyapListDTO> selectHhMyapList(HhMyapListDTO hhMyapListDTO) {
		List<HhMyapListDTO> list = hhMyApMapper.selectHhMyapList(hhMyapListDTO);
		for (HhMyapListDTO h : list) {
			h.setCommonFileList(commonFileService.selectFileDetailList(h.getProfilePictureFileId()));
		}
		return list;
	}

	public List<HhMyapHistoryGroupDTO> selectHhMyapHistoryGroupList(HhMyapHistoryGroupDTO hhMyapHistoryGroupDTO) {
		return hhMyApMapper.selectHhMyapHistoryGroupList(hhMyapHistoryGroupDTO);
	}

	public int updateHhMyapListGorup(HhMyapListDTO hhMyapListDTO) {
		return hhMyApMapper.updateHhMyapListGorup(hhMyapListDTO);
	}

	public int updateHhMyapListRegistPath(HhMyapListDTO hhMyapListDTO) {
		return hhMyApMapper.updateHhMyapListRegistPath(hhMyapListDTO);
	}

	public int updateHhMyapListRegisterPathCd(HhMyapListDTO hhMyapListDTO) {
		return hhMyApMapper.updateHhMyapListRegisterPathCd(hhMyapListDTO);
	}

	public int updateHhMyapListResumeOpend(HhMyapListDTO hhMyapListDTO) {
		return hhMyApMapper.updateHhMyapListResumeOpend(hhMyapListDTO);
	}

	public HhRewardDTO selectReward(HhRewardDTO hhRewardDTO) {
		return hhMyApMapper.selectReward(hhRewardDTO);
	}

	public int insertReward(HhRewardDTO hhRewardDTO) {
		return hhMyApMapper.insertReward(hhRewardDTO);
	}

	public int updateReward(HhRewardDTO hhRewardDTO) {
		return hhMyApMapper.updateReward(hhRewardDTO);
	}

	public int insertRewardHistory(HhRewardHistoryDTO hhRewardHistoryDTO) {
		return hhMyApMapper.insertRewardHistory(hhRewardHistoryDTO);
	}

	public HhPointDTO selectPoint(HhPointDTO hhPointDTO) {
		return hhMyApMapper.selectPoint(hhPointDTO);
	}

	public int insertPoint(HhPointDTO hhPointDTO) {
		return hhMyApMapper.insertPoint(hhPointDTO);
	}

	public int updatePoint(HhPointDTO hhRewardDTO) {
		return hhMyApMapper.updatePoint(hhRewardDTO);
	}

	public int insertPointUseHistory(HhPointUseHistoryDTO hhPointUseHistoryDTO) {
		return hhMyApMapper.insertPointUseHistory(hhPointUseHistoryDTO);
	}

	public void addExpiredPoint(HhMyapListDTO hhMyapListDTO) {
		HhPointDTO hhPointDTO = new HhPointDTO();
		hhPointDTO.setFrontSession(hhMyapListDTO.getFrontSession());

		HhPointDTO tt = selectPoint(hhPointDTO);
		hhPointDTO.setBalance("20000");
		hhPointDTO.setFreePoint("20000");
		hhPointDTO.setPaidPoint("0");
		hhPointDTO.setMemberId(hhMyapListDTO.getFrontSession().getId());
		// 등록된 리워드가 없을경우 신규등록
		if (tt == null) {

			insertPoint(hhPointDTO);
		} else {
			// 이미 존재하는 포인트인 경우
			hhPointDTO.setId(tt.getId());
			updatePoint(hhPointDTO);
		}

		// 포인트 히스토리 등록
		HhPointUseHistoryDTO hhPointUseHistoryDTO = new HhPointUseHistoryDTO();
		hhPointUseHistoryDTO.setReasonCd("USE_RESUME");
		hhPointUseHistoryDTO.setMemberId(hhMyapListDTO.getFrontSession().getId());
		hhPointUseHistoryDTO.setPointId(hhPointDTO.getId());
		hhPointUseHistoryDTO.setFreeIncrease("20000");
		hhPointUseHistoryDTO.setPaidIncrease("0");
		insertPointUseHistory(hhPointUseHistoryDTO);
	}

	// 메인 내 인재 리스트 취득
	public List<HhMyapListDTO> selectMyApListPaging(HhMyapListDTO hhMyapListDTO) {
		return hhMyApMapper.selectMyApListPaging(hhMyapListDTO);
	}
}