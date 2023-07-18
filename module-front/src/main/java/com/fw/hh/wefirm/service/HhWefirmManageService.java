package com.fw.hh.wefirm.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.headhunter.FoSearchHhDTO;
import com.fw.core.dto.hh.HhWefirmDTO;
import com.fw.core.mapper.db1.common.CommonMapper;
import com.fw.core.mapper.db1.fo.searchHh.FoSearchHhMapper;
import com.fw.core.mapper.db1.hh.HhWefirmManageMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HhWefirmManageService {

	private final HhWefirmManageMapper hhWefirmManageMapper;
	private final CommonFileService commonFileService;
	private final CommonMapper commonMapper;
	private final CommonService commonService;
	private final FoSearchHhMapper foSearchHhMapper;

	public List<HhWefirmDTO> selectHeadhunterList(HhWefirmDTO hhWefirmDTO) {
		List<HhWefirmDTO> list = hhWefirmManageMapper.selectHeadhunterList(hhWefirmDTO);
		for (HhWefirmDTO h : list) {
			if (!Objects.isNull(h) && StringUtils.isNotBlank(h.getProfilePictureFileId())) {
				h.setCommonFileList(commonFileService.selectFileDetailList(h.getProfilePictureFileId()));
			}
		}
		return list;
	}

	public int selectHeadhunterListCnt(HhWefirmDTO hhWefirmDTO) {
		return hhWefirmManageMapper.selectHeadhunterListCnt(hhWefirmDTO);
	}

	public HhWefirmDTO selectWefirmDetail(HhWefirmDTO hhWefirmDTO) {
		HhWefirmDTO resDTO = hhWefirmManageMapper.selectWefirmDetail(hhWefirmDTO);
		if (!Objects.isNull(resDTO) && StringUtils.isNotBlank(resDTO.getLogoFileId())) {
			resDTO.setCommonFileList(commonFileService.selectFileDetailList(resDTO.getLogoFileId()));
		}
		return resDTO;
	}

	public void insertWefirmAdmin(HhWefirmDTO hhWefirmDTO) {
		this.deleteWefirmAdmin(hhWefirmDTO);
		hhWefirmManageMapper.insertWefirmAdmin(hhWefirmDTO);
	}

	public void deleteWefirmAdmin(HhWefirmDTO hhWefirmDTO) {
		hhWefirmManageMapper.deleteWefirmAdmin(hhWefirmDTO);
	}

	public void updateWefirm(HhWefirmDTO hhWefirmDTO) throws Exception {
		if (hhWefirmDTO.getLogoFiles() != null) {
			Integer logoFileId = commonFileService.fileUpload(hhWefirmDTO.getLogoFiles(), "WEFIRM_LOGO");
			hhWefirmDTO.setLogoFileId(String.valueOf(logoFileId));
		}
		hhWefirmManageMapper.updateWefirm(hhWefirmDTO);
	}

	public void insertWefirmRequestHistory(HhWefirmDTO hhWefirmDTO) {
		hhWefirmDTO.setRequestType("CLOSE");
		hhWefirmDTO.setStatus("RECEIPT");
		hhWefirmManageMapper.insertWefirmRequestHistory(hhWefirmDTO);

		// 위펌 폐쇄신청 push알림 (관리자)
		List<FoMemberDTO> adminList = commonService.selectAdminList(new FoMemberDTO());
		for(FoMemberDTO admin : adminList) {
			PushHistDTO push = PushHistDTO.builder()
					.memberId(admin.getApprovalSeq())
					.dispType("WE_CLOSE_APPLY")
					.title("WE펌 폐쇄신청")
					.content("WE펌 폐쇄신청이 있습니다.")
					.createId(hhWefirmDTO.getFrontSession().getId())
					.build();
			commonService.insertPushHist(push);
		}
	}

	public boolean selectWefirmRequestDuplicateCheck(HhWefirmDTO hhWefirmDTO) {
		hhWefirmDTO.setRequestType("CLOSE");
		hhWefirmDTO.setStatus("RECEIPT");
		return hhWefirmManageMapper.selectWefirmRequestDuplicateCheck(hhWefirmDTO);
	}

	/** 위펌 헤드헌터 강제탈퇴 */
	public void deleteHeadhunter(HhWefirmDTO hhWefirmDTO) {
		for (String id : hhWefirmDTO.getIdArray()) {
			HhWefirmDTO h = new HhWefirmDTO();
			h.setId(id);
			// 위펌 헤드헌터 강제탈퇴
			hhWefirmManageMapper.deleteHeadhunter(h);

			// 강제탈퇴 push 알림 (관리자)
			FoSearchHhDTO foSearchHhDTO = new FoSearchHhDTO();
			foSearchHhDTO.setMemberId(id);
			FoSearchHhDTO headhunter = foSearchHhMapper.selectHhInfo(foSearchHhDTO);

			List<FoMemberDTO> adminList = commonService.selectAdminList(new FoMemberDTO());
			for(FoMemberDTO admin : adminList) {
				PushHistDTO push = PushHistDTO.builder()
						.dispType("HH_WITHDRAW")
						.memberId(admin.getApprovalSeq())
						.title("헤드헌터 강제탈퇴")
						.content(headhunter.getSfName() + " " + headhunter.getName() + "(" + headhunter.getPhone() + "," + headhunter.getEmail() + ") 회원이 강제탈퇴 되었습니다.")
						.createId(hhWefirmDTO.getFrontSession().getId())
						.build();
				commonService.insertPushHist(push);
			}
		}
	}

	public List<HhWefirmDTO> selectNoWefirmHeadhunterList(HhWefirmDTO hhWefirmDTO) {
		List<HhWefirmDTO> list = hhWefirmManageMapper.selectNoWefirmHeadhunterList(hhWefirmDTO);
		for (HhWefirmDTO h : list) {
			if (!Objects.isNull(h) && StringUtils.isNotBlank(h.getProfilePictureFileId())) {
				h.setCommonFileList(commonFileService.selectFileDetailList(h.getProfilePictureFileId()));
			}
		}
		return list;
	}

	public int selectNoWefirmHeadhunterListCnt(HhWefirmDTO hhWefirmDTO) {
		return hhWefirmManageMapper.selectNoWefirmHeadhunterListCnt(hhWefirmDTO);
	}

	/** 위펌 가입신청 승인 */
	public void approvalHeadhunter(HhWefirmDTO hhWefirmDTO) {
		for (String id : hhWefirmDTO.getIdArray()) {
			HhWefirmDTO h = new HhWefirmDTO();
			h.setMemberId(hhWefirmDTO.getFrontSession().getId());
			h.setId(id);
			// 위펌 가입신청 승인
			hhWefirmManageMapper.updateHeadhunter(h);
			hhWefirmManageMapper.approvalHeadhunter(h);

			PushHistDTO push = PushHistDTO.builder()
					.dispType("WE_JOIN_RESULT")
					.memberId(id)
					.title("가입신청결과")
					.content("WE펌 가입신청 결과가 통보 되었습니다.")
					.createId(hhWefirmDTO.getFrontSession().getId()).build();
			commonService.insertPushHist(push);
		}
	}
}
