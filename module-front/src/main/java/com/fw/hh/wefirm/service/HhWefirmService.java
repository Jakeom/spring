package com.fw.hh.wefirm.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.hh.HhWefirmDTO;
import com.fw.core.mapper.db1.common.CommonMapper;
import com.fw.core.mapper.db1.hh.HhWefirmMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class HhWefirmService {

	private final HhWefirmMapper hhWefirmMapper;
	private final CommonFileService commonFileService;
	private final CommonMapper commonMapper;
	private final CommonService commonService;

	public HhWefirmDTO selectHeadhunter(HhWefirmDTO hhWefirmDTO) {
		return hhWefirmMapper.selectHeadhunter(hhWefirmDTO);
	}

	public int selectWefirmRegisterCnt(HhWefirmDTO hhWefirmDTO) {
		return hhWefirmMapper.selectWefirmRegisterCnt(hhWefirmDTO);
	}

	@Transactional
	public String insertWefirm(HhWefirmDTO hhWefirmDTO) throws Exception {
		String result = "SUCCESS";
		if (hhWefirmMapper.selectWefirmRegisterCnt(hhWefirmDTO) > 0) {
			return "REGISTER";
		}

		if (hhWefirmMapper.selectWefirmJoinStatusCnt(hhWefirmDTO) > 0) {
			return "WAIT";
		}

		if (hhWefirmMapper.selectWefirmUrlDuplicateCnt(hhWefirmDTO) > 0) {
			return "DUPLICATE";
		}

		Integer fileId = commonFileService.fileUpload(hhWefirmDTO.getFiles(), "WEFIRM");
		Integer logoFileId = commonFileService.fileUpload(hhWefirmDTO.getLogoFiles(), "WEFIRM_LOGO");
		hhWefirmDTO.setFileId(String.valueOf(fileId));
		hhWefirmDTO.setLogoFileId(String.valueOf(logoFileId));
		hhWefirmMapper.insertWefirm(hhWefirmDTO);

		hhWefirmDTO.setRequestType("OPEN");
		hhWefirmDTO.setStatus("RECEIPT");
		hhWefirmDTO.setWefirmId(hhWefirmDTO.getId());
		hhWefirmMapper.insertWefirmRequestHistory(hhWefirmDTO);

		List<FoMemberDTO> adminList = commonService.selectAdminList(new FoMemberDTO());
		for (FoMemberDTO admin : adminList) {
			// 위펌 개설신청 push알림 (관리자)
			PushHistDTO push = PushHistDTO.builder().dispType("WE_OPEN_APPLY")
					.memberId(admin.getApprovalSeq())
					.title("WE펌 개설신청")
					.content("WE펌 개설신청이 있습니다.")
					.createId(hhWefirmDTO.getFrontSession().getId())
					.build();
			commonService.insertPushHist(push);
		}
		return "SUCCESS";
	}

	public List<HhWefirmDTO> selectWefirmJoinRequestList(HhWefirmDTO hhWefirmDTO) {
		return hhWefirmMapper.selectWefirmJoinRequestList(hhWefirmDTO);
	}

	@Transactional
	public void deleteCancelWefirmJoin(HhWefirmDTO hhWefirmDTO) {
		hhWefirmMapper.deleteCancelWefirmJoin(hhWefirmDTO);
	}

	@Transactional
	public String insertWefirmJoinRequest(HhWefirmDTO hhWefirmDTO) {
		if (hhWefirmMapper.selectWefirmRegisterCnt(hhWefirmDTO) > 0) {
			return "REGISTER";
		}

		if (hhWefirmMapper.selectWefirmJoinStatusCnt(hhWefirmDTO) > 0) {
			return "WAIT";
		}

		hhWefirmMapper.insertWefirmJoinRequest(hhWefirmDTO);

		// 위펌 대표/관리자 헤드헌터 가입신청 알림 push 전송
		hhWefirmDTO.setWefirmId(hhWefirmDTO.getId());
		List<HhWefirmDTO> adminList = hhWefirmMapper.selectWefirmAdminList(hhWefirmDTO);

		for (HhWefirmDTO wefirmDTO : adminList) {
			PushHistDTO push = PushHistDTO.builder().dispType("HH_JOIN_RESULT").memberId(wefirmDTO.getMemberId()).content("헤드헌터 가입신청이 있습니다.")
					.title("헤드헌터 가입신청").createId(hhWefirmDTO.getFrontSession().getId()).build();
			commonService.insertPushHist(push);
		}
		return "SUCCESS";
	}

	public int selectWefirmListCnt(HhWefirmDTO hhWefirmDTO) {
		return hhWefirmMapper.selectWefirmListCnt(hhWefirmDTO);
	}

	public List<HhWefirmDTO> selectWefirmList(HhWefirmDTO hhWefirmDTO) {
		List<HhWefirmDTO> list = hhWefirmMapper.selectWefirmList(hhWefirmDTO);
		for (HhWefirmDTO h : list) {
			if (!Objects.isNull(h) && StringUtils.isNotBlank(h.getLogoFileId())) {
				h.setCommonFileList(commonFileService.selectFileDetailList(h.getLogoFileId()));
			}
		}
		return list;
	}

	public HhWefirmDTO selectWefirmDetail(HhWefirmDTO hhWefirmDTO) {
		HhWefirmDTO resDTO = hhWefirmMapper.selectWefirmDetail(hhWefirmDTO);
		if (!Objects.isNull(resDTO) && StringUtils.isNotBlank(resDTO.getLogoFileId())) {
			resDTO.setCommonFileList(commonFileService.selectFileDetailList(resDTO.getLogoFileId()));
		}
		return resDTO;
	}

	public int selectWefirmHhCnt(HhWefirmDTO hhWefirmDTO) {
		return hhWefirmMapper.selectWefirmHhCnt(hhWefirmDTO);
	}

}
