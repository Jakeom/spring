package com.fw.m.ubi.service;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.m.UbiDataDTO;
import com.fw.core.mapper.db1.m.UbiResumeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UbiService {

	private final UbiResumeMapper ubiResumeMapper;
	private final CommonFileService commonFileService;

	// 이력서 상세보기
	public UbiDataDTO selectResumeInfo(UbiDataDTO ubiDataDTO) {
		UbiDataDTO f = ubiResumeMapper.selectResumeInfo(ubiDataDTO);
		if (StringUtils.isNotBlank(f.getPictureFileId())) {
			f.setPictureFile(commonFileService.selectFileDetailList(f.getPictureFileId()).get(0));
		}
		f.setAcademicBackgrounds(this.selectAcademicInfo(ubiDataDTO));
		f.setCareers(this.selectCareerInfo(ubiDataDTO));
		for (UbiDataDTO c : f.getCareers()) {
			String positionCd = c.getPositionCd();
			String dutyCd = c.getDutyCd();
			String salary = c.getSalary().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
			if (StringUtils.isBlank(c.getPositionCd())) {
				positionCd = "-";
			}
			if (StringUtils.isBlank(c.getDutyCd())) {
				dutyCd = "-";
			}
			f.setCareerDescription(f.getTotalCareerNm() + "       " + c.getCompanyName() + "       부서명  |  " + c.getDepartmentName()
					+ "       직급  |  " + c.getPositionInput() + "      직책  |  " + c.getDutyInput() + "       최종연봉  |  " + salary + " 만원" + "\n"+"\n"
					  + c.getTask());
		}
		f.setSpecs(this.selectSpecInfo(ubiDataDTO));
		f.setLanguages(this.selectLanguageInfo(ubiDataDTO));
		f.setLicenses(this.selectLicenseInfo(ubiDataDTO));
		f.setAwards(this.selectAwardInfo(ubiDataDTO));
		f.setExternalActivities(this.selectActivityInfo(ubiDataDTO));
		f.setPortfolios(this.selectPortfolioInfo(ubiDataDTO));
		f.setEnglishResume(this.selectEnglishResumeInfo(ubiDataDTO));

		return f;
	}

	// 학력,전공 정보 취득
	public List<UbiDataDTO> selectAcademicInfo(UbiDataDTO ubiDataDTO) {
		return ubiResumeMapper.selectAcademicInfo(ubiDataDTO);
	}

	// 경력 정보 취득
	public List<UbiDataDTO> selectCareerInfo(UbiDataDTO ubiDataDTO) {
		return ubiResumeMapper.selectCareerInfo(ubiDataDTO);
	}

	// 최종 경력 정보 취득
	public UbiDataDTO selectFinalCareerInfo(UbiDataDTO ubiDataDTO) {
		return ubiResumeMapper.selectFinalCareerInfo(ubiDataDTO);
	}

	// 핵심역량 정보 취득
	public List<UbiDataDTO> selectSpecInfo(UbiDataDTO ubiDataDTO) {
		return ubiResumeMapper.selectSpecInfo(ubiDataDTO);
	}

	// 희망근무지 정보 취득
	public UbiDataDTO selectDesiredLocationInfo(UbiDataDTO ubiDataDTO) {
		return ubiResumeMapper.selectDesiredLocationInfo(ubiDataDTO);
	}

	// 어학 정보 취득
	public List<UbiDataDTO> selectLanguageInfo(UbiDataDTO ubiDataDTO) {
		return ubiResumeMapper.selectLanguageInfo(ubiDataDTO);
	}

	// 자격증 정보 취득
	public List<UbiDataDTO> selectLicenseInfo(UbiDataDTO ubiDataDTO) {
		return ubiResumeMapper.selectLicenseInfo(ubiDataDTO);
	}

	// 수상 정보 취득
	public List<UbiDataDTO> selectAwardInfo(UbiDataDTO ubiDataDTO) {
		return ubiResumeMapper.selectAwardInfo(ubiDataDTO);
	}

	// 대외활동 정보 취득
	public List<UbiDataDTO> selectActivityInfo(UbiDataDTO ubiDataDTO) {
		return ubiResumeMapper.selectActivityInfo(ubiDataDTO);
	}

	// 포트폴리오 정보 취득
	public List<UbiDataDTO> selectPortfolioInfo(UbiDataDTO ubiDataDTO) {
		List<UbiDataDTO> list = ubiResumeMapper.selectPortfolioInfo(ubiDataDTO);
		for (int i = 0; i < list.size(); i++) {
			String fileSeq = list.get(i).getAttachFileId();
			if (!Objects.isNull(fileSeq)) {
				if (StringUtils.isNotBlank(fileSeq)) {
					list.get(i).setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
				}
			}
		}
		return list;
	}

	// 영문이력서 정보 취득
	public UbiDataDTO selectEnglishResumeInfo(UbiDataDTO ubiDataDTO) {
		UbiDataDTO f = ubiResumeMapper.selectEnglishResumeInfo(ubiDataDTO);
		if (!Objects.isNull(f)) {
			f.setCommonFileList(commonFileService.selectFileDetailList(f.getEnAttachFileId()));
		}
		return f;
	}

	// 취업활동 증명서
	public List<UbiDataDTO> selectEmploymentCertificate(UbiDataDTO ubiDataDTO) {
		return ubiResumeMapper.selectEmploymentCertificate(ubiDataDTO);
	}

}