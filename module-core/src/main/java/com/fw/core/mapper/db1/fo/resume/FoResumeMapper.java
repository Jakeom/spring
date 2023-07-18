package com.fw.core.mapper.db1.fo.resume;

import com.fw.core.dto.bo.BoResumeDTO;
import com.fw.core.dto.fo.FoApplicantDTO;
import com.fw.core.dto.fo.FoPointDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoResumeMapper {

    // 이력서 리스트 카운트 취득
    public int selectResumeListCnt(FoResumeDTO foResumeDTO);

    // 이력서 리스트 취득
    public List<FoResumeDTO> selectResumeList(FoResumeDTO foResumeDTO);

    //이력서 상세보기
    public FoResumeDTO selectResumeInfo(FoResumeDTO foResumeDTO);

    // 학력,전공 정보 취득
    public List<FoResumeDTO> selectAcademicInfo(FoResumeDTO foResumeDTO);

    // 경력 정보 취득
    public List<FoResumeDTO> selectCareerInfo(FoResumeDTO foResumeDTO);

    // 최종 경력 정보 취득
    public FoResumeDTO selectFinalCareerInfo(FoResumeDTO foResumeDTO);

    // 핵심역량 정보 취득
    public List<FoResumeDTO> selectSpecInfo(FoResumeDTO foResumeDTO);

    // 희망근무지 정보 취득
    public FoResumeDTO selectDesiredLocationInfo(FoResumeDTO foResumeDTO);

    // 어학 정보 취득
    public List<FoResumeDTO> selectLanguageInfo(FoResumeDTO foResumeDTO);

    // 자격증 정보 취득
    public List<FoResumeDTO> selectLicenseInfo(FoResumeDTO foResumeDTO);

    // 수상 정보 취득
    public List<FoResumeDTO> selectAwardInfo(FoResumeDTO foResumeDTO);

    // 대외활동 정보 취득
    public List<FoResumeDTO> selectActivityInfo(FoResumeDTO foResumeDTO);

    // 포트폴리오 정보 취득
    public List<FoResumeDTO> selectPortfolioInfo(FoResumeDTO foResumeDTO);

    // 영문이력서 정보 취득
    public FoResumeDTO selectEnglishResumeInfo(FoResumeDTO foResumeDTO);

    // 이력서 저장
    public void insertResume(FoResumeDTO foResumeDTO);

    // 핵심역량 저장
    public void insertResumeSpec(FoResumeDTO foResumeDTO);

    // 경력 저장
    public void insertResumeCareer(FoResumeDTO foResumeDTO);

    // 학력 저장
    public void insertResumeAcademicBackground(FoResumeDTO foResumeDTO);

    // 전공 저장
    public void insertResumeAcademicBackgroundMajor(FoResumeDTO foResumeDTO);

    // 어학 저장
    public void insertResumeLanguage(FoResumeDTO foResumeDTO);

    // 자격증 저장
    public void insertResumeLicense(FoResumeDTO foResumeDTO);

    // 수상 저장
    public void insertResumeAward(FoResumeDTO foResumeDTO);

    // 외부활동 저장
    public void insertResumeExternalActivity(FoResumeDTO foResumeDTO);

    // 포트폴리오 저장
    public void insertResumePortfolio(FoResumeDTO foResumeDTO);

    // 영어 이력서 저장
    public void insertResumeEnglish(FoResumeDTO foResumeDTO);
    void insertResumeFiltering(FoResumeDTO foResumeDTO);
    void deleteResumeFiltering(FoResumeDTO foResumeDTO);
    void insertResumeLocation(FoResumeDTO foResumeDTO);

    // 이력서 수정
    public void updateResume(FoResumeDTO foResumeDTO);

    public int selectResumeSpecCnt(FoResumeDTO foResumeDTO);
    public void deleteResumeAcademicBackgroundMajor(FoResumeDTO foResumeDTO);
    // 핵심역량 수정
    public void updateResumeSpec(FoResumeDTO foResumeDTO);

    //  학력수정
    public void updateResumeAcademicBackground(FoResumeDTO foResumeDTO);

    //  전공수정
    public void updateResumeAcademicBackgroundMajor(FoResumeDTO foResumeDTO);

    // 학력수전 전에 부전공 입렸했었는지 여부
    public int selectMinorCnt(FoResumeDTO foResumeDTO);

    // 경력 수정
    public void updateResumeCareer(FoResumeDTO foResumeDTO);

    // 어학 수정
    public void updateResumeLanguage(FoResumeDTO foResumeDTO);

    // 자격증 수정
    public void updateResumeLicense(FoResumeDTO foResumeDTO);

    // 수상 수정
    public void updateResumeAward(FoResumeDTO foResumeDTO);

    // 외부활동 수정
    public void updateResumeExternalActivity(FoResumeDTO foResumeDTO);

    // 포트폴리오 수정
    public void updateResumePortfolio(FoResumeDTO foResumeDTO);

    // 영문이력서 수정
    public void updateResumeEnglish(FoResumeDTO foResumeDTO);

    // 지역 수정
    public void updateResumeLocation(FoResumeDTO foResumeDTO);

    // 이력서 공개여부, 구직의사여부
    public FoApplicantDTO selectResumeOpened(FoApplicantDTO foApplicantDTO);

    //  이력서 공개여부 수정
    public void updateResumeRestricted(FoApplicantDTO foApplicantDTO);

    //  구직의사여부 수정
    public void updateFindingJob(FoApplicantDTO foApplicantDTO);

    // 맞춤 채용 공고 리스트 취득
    public List<FoResumeDTO> selectPositionAlert(FoResumeDTO foResumeDTO);

    //  맞춤 채용 공고 직무 저장
    public void insertPositionAlert(FoResumeDTO foResumeDTO);

    //  맞춤 채용 공고 직무 삭제
    public void deletePositionAlert(FoResumeDTO foResumeDTO);

    // 포트폴리오 삭제
    public void updateDeleteResumeResumePortfolio(FoResumeDTO foResumeDTO);

    // 경력 삭제
    public void updateDeleteResumeCareer(FoResumeDTO foResumeDTO);

    // 학력 삭제
    public void updateDeleteResumeAcademicBackground(FoResumeDTO foResumeDTO);

    //복사

    // 이력서 저장
    public void insertResumeClone(FoResumeDTO foResumeDTO);

    // 핵심역량 저장
    public void insertResumeSpecClone(FoResumeDTO foResumeDTO);

    // 경력 저장
    public void insertResumeCareerClone(FoResumeDTO foResumeDTO);

    // 학력 저장
    public void insertResumeAcademicBackgroundClone(FoResumeDTO foResumeDTO);

    // 전공 저장
    public void insertResumeAcademicBackgroundMajorClone(FoResumeDTO foResumeDTO);

    // 어학 저장
    public void insertResumeLanguageClone(FoResumeDTO foResumeDTO);

    // 자격증 저장
    public void insertResumeLicenseClone(FoResumeDTO foResumeDTO);

    // 수상 저장
    public void insertResumeAwardClone(FoResumeDTO foResumeDTO);

    // 외부활동 저장
    public void insertResumeExternalActivityClone(FoResumeDTO foResumeDTO);

    // 포트폴리오 저장
    public void insertResumePortfolioClone(FoResumeDTO foResumeDTO);

    // 영어 이력서 저장
    public void insertResumeEnglishClone(FoResumeDTO foResumeDTO);
    void insertResumeFilteringClone(FoResumeDTO foResumeDTO);
    void insertResumeLocationClone(FoResumeDTO foResumeDTO);

    // 기본이력서 등록여부
    public int selectResumeRepresentationCnt(FoResumeDTO foResumeDTO);

    // 기본이력서 변경
    public void updateResumeRepresentation(FoResumeDTO foResumeDTO);

    // 기본이력서 변경시 원래 기본이력서 변경
    public void updateResumeOriginRepresentation(FoResumeDTO foResumeDTO);

    // 이력서 삭제
    public void updateDeleteResume(FoResumeDTO foResumeDTO);

    // 가장 최근 insert 이력서
    public FoResumeDTO selectLastResume(FoResumeDTO foResumeDTO);

    // 가입시 hh추천코드입력했는지 확인
    public FoResumeDTO selectHhReferralCode(FoResumeDTO foResumeDTO);

    // 지원서 이력서 업데이트
    public int updateApplicantForResumeId(FoResumeDTO foResumeDTO);

    //재직중 확인
    public boolean selectHoldOffice(FoResumeDTO foResumeDTO);

}
