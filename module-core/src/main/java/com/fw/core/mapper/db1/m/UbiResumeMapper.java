package com.fw.core.mapper.db1.m;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.m.UbiDataDTO;

@Mapper
public interface UbiResumeMapper {

	// 이력서 상세보기
	UbiDataDTO selectResumeInfo(UbiDataDTO ubiDataDTO);

	// 학력,전공 정보 취득
	List<UbiDataDTO> selectAcademicInfo(UbiDataDTO ubiDataDTO);

	// 경력 정보 취득
	List<UbiDataDTO> selectCareerInfo(UbiDataDTO ubiDataDTO);

	// 최종 경력 정보 취득
	UbiDataDTO selectFinalCareerInfo(UbiDataDTO ubiDataDTO);

	// 핵심역량 정보 취득
	List<UbiDataDTO> selectSpecInfo(UbiDataDTO ubiDataDTO);

	// 희망근무지 정보 취득
	UbiDataDTO selectDesiredLocationInfo(UbiDataDTO ubiDataDTO);

	// 어학 정보 취득
	List<UbiDataDTO> selectLanguageInfo(UbiDataDTO ubiDataDTO);

	// 자격증 정보 취득
	List<UbiDataDTO> selectLicenseInfo(UbiDataDTO ubiDataDTO);

	// 수상 정보 취득
	List<UbiDataDTO> selectAwardInfo(UbiDataDTO ubiDataDTO);

	// 대외활동 정보 취득
	List<UbiDataDTO> selectActivityInfo(UbiDataDTO ubiDataDTO);

	// 포트폴리오 정보 취득
	List<UbiDataDTO> selectPortfolioInfo(UbiDataDTO ubiDataDTO);

	List<UbiDataDTO> selectCareerDescriptionsInfo(UbiDataDTO ubiDataDTO);

	// 영문이력서 정보 취득
	UbiDataDTO selectEnglishResumeInfo(UbiDataDTO ubiDataDTO);

	// 취업활동증명서
	List<UbiDataDTO> selectEmploymentCertificate(UbiDataDTO ubiDataDTO);

}
