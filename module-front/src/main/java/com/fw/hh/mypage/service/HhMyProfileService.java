package com.fw.hh.mypage.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.hh.HhMyProfileDTO;
import com.fw.core.mapper.db1.hh.HhMyProfileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhMyProfileService {
	private final HhMyProfileMapper hhMyProfileMapper;
	private final CommonFileService commonFileService;

	public HhMyProfileDTO selectMyProfileInfo(HhMyProfileDTO hhMyProfileDTO) {
		HhMyProfileDTO h = hhMyProfileMapper.selectMyProfileInfo(hhMyProfileDTO);
		String careerDesc = h.getCareerDesc(); // 주요이력 줄바꿈
		if(StringUtils.isNotBlank(careerDesc)) {
			h.setFormatDesc(careerDesc.replaceAll("\n", "<br/>"));
		}
		String fileSeq = h.getProfilePictureFileId();
		if (StringUtils.isNotBlank(fileSeq)) {
			h.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
		}

		return h;
	}

	public List<HhMyProfileDTO> selectHhPositionFieldList(HhMyProfileDTO hhMyProfileDTO) {
		return hhMyProfileMapper.selectHhPositionFieldList(hhMyProfileDTO);
	}

	public void updateProfileSetting(HhMyProfileDTO hhMyProfileDTO) throws Exception {

		if (StringUtils.equals("Y", hhMyProfileDTO.getPhotoChangeFlag())) { // 사진 변경시에만 update
			Integer fileSeq = commonFileService.fileUpload(hhMyProfileDTO.getFiles(), "HH-PROFILE");
			hhMyProfileDTO.setProfilePictureFileId(String.valueOf(fileSeq));
			hhMyProfileMapper.updateMember(hhMyProfileDTO);
		}
		hhMyProfileMapper.updateProfileSetting(hhMyProfileDTO); // 프로필 기본정보 update
		hhMyProfileMapper.deletePositionField(hhMyProfileDTO); // 전문분야 삭제
		if (hhMyProfileDTO.getCheckFieldArr() != null && hhMyProfileDTO.getCheckFieldArr().length > 0) { // 전문분야 재등록
			hhMyProfileMapper.insertPositionField(hhMyProfileDTO);
		}
	}

	public void updateSignature(HhMyProfileDTO hhMyProfileDTO) {
		hhMyProfileMapper.updateSignature(hhMyProfileDTO);
	}

	public void updateMember(HhMyProfileDTO hhMyProfileDTO) {
		hhMyProfileMapper.updateMember(hhMyProfileDTO);
	}

	public List<HhMyProfileDTO> selectMyLogList(HhMyProfileDTO hhMyProfileDTO) {
		String startDt = hhMyProfileDTO.getStartDt();
		String endDt = hhMyProfileDTO.getEndDt();
		if (StringUtils.isBlank(startDt) && StringUtils.isNotBlank(endDt)) { // 이전날짜 ~ endDt 검색조건
			hhMyProfileDTO.setDateSearchType("endDt");
		}
		if (StringUtils.isBlank(endDt) && StringUtils.isNotBlank(startDt)) { // startDt ~ 이후날짜 검색조건
			hhMyProfileDTO.setDateSearchType("startDt");
		}
		return hhMyProfileMapper.selectMyLogList(hhMyProfileDTO);
	}

	public int selectMyLogListCnt(HhMyProfileDTO hhMyProfileDTO) {
		return hhMyProfileMapper.selectMyLogListCnt(hhMyProfileDTO);
	}
}