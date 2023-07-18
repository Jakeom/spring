package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoHhResumeFormDTO;
import com.fw.core.dto.bo.BoParsingErrorAcceptDTO;
import com.fw.core.dto.bo.BoResumeDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dongbeom
 */
@Mapper
public interface BoResumeMapper {

    /** 이력서 리스트 조회 */
    List<BoResumeDTO> selectResumeList(BoResumeDTO boResumeDTO);

    /** 이력서 리스트 옵션 조회 */
    List<BoResumeDTO> selectResumeOptionList(BoResumeDTO boResumeDTO);

    /** 이력서 리스트 옵션 검색 */
    List<BoResumeDTO> selectResumeSearch(BoResumeDTO boResumeDTO);

    /** 이력서 오류신고 리스트 조회 */
    List<BoParsingErrorAcceptDTO> selectParsingErrorList(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO);

    /** 이력서 오류신고 리스트 옵션 검색 */
    List<BoParsingErrorAcceptDTO> selectParsingErrorOptionList(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO);

    /** 이력서 등록신청(HH) 리스트 검색 */
    int selectParsingErrorAcceptListCnt(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO);

    int selectResumeListCnt(BoResumeDTO boResumeDTO);
    List<BoParsingErrorAcceptDTO> selectParsingErrorAcceptList(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO);

    /** 이력서 전용 이력서 리스트 조회 */
    List<BoHhResumeFormDTO> selectHhResumeFormList(BoHhResumeFormDTO boHhResumeFormDTO);

    /** 이력서 전용 이력서 옵션 검색 */
    List<BoHhResumeFormDTO> selectHhResumeFormOptionList(BoHhResumeFormDTO boHhResumeFormDTO);

    /** 이력서 전용 이력서 전용이력서 등록처리 승인 */
    void updateResumeFormAcceptStatus(BoHhResumeFormDTO boHhResumeFormDTO);

    /** 이력서 전용 이력서 전용이력서 등록처리 거절 및 사유 업데이트 */
    void updateResumeFormRegistImpossibleStatus(BoHhResumeFormDTO boHhResumeFormDTO);

    void updateRefuseRequestRegistResume(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO);
    void updateParsingErrorComplete(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO);


    //이력서 상세보기
    public BoResumeDTO selectResumeInfo(BoResumeDTO boResumeDTO);

    // 학력,전공 정보 취득
    public List<BoResumeDTO> selectAcademicInfo(BoResumeDTO boResumeDTO);

    // 경력 정보 취득
    public List<BoResumeDTO> selectCareerInfo(BoResumeDTO boResumeDTO);

    // 최종 경력 정보 취득
    public BoResumeDTO selectFinalCareerInfo(BoResumeDTO boResumeDTO);

    // 핵심역량 정보 취득
    public List<BoResumeDTO> selectSpecInfo(BoResumeDTO boResumeDTO);

    // 희망근무지 정보 취득
    public BoResumeDTO selectDesiredLocationInfo(BoResumeDTO boResumeDTO);

    // 어학 정보 취득
    public List<BoResumeDTO> selectLanguageInfo(BoResumeDTO boResumeDTO);

    // 자격증 정보 취득
    public List<BoResumeDTO> selectLicenseInfo(BoResumeDTO boResumeDTO);

    // 수상 정보 취득
    public List<BoResumeDTO> selectAwardInfo(BoResumeDTO boResumeDTO);

    // 대외활동 정보 취득
    public List<BoResumeDTO> selectActivityInfo(BoResumeDTO boResumeDTO);

    // 포트폴리오 정보 취득
    public List<BoResumeDTO> selectPortfolioInfo(BoResumeDTO boResumeDTO);

    // 영문이력서 정보 취득
    public BoResumeDTO selectEnglishResumeInfo(BoResumeDTO boResumeDTO);


    /** 이력서 저장 */
    // 이력서 저장
    public void insertResume(BoResumeDTO boResumeDTO);

    // 핵심역량 저장
    public void insertResumeSpec(BoResumeDTO boResumeDTO);

    // 경력 저장
    public void insertResumeCareer(BoResumeDTO boResumeDTO);

    // 학력 저장
    public void insertResumeAcademicBackground(BoResumeDTO boResumeDTO);

    // 전공 저장
    public void insertResumeAcademicBackgroundMajor(BoResumeDTO boResumeDTO);

    // 어학 저장
    public void insertResumeLanguage(BoResumeDTO boResumeDTO);

    // 자격증 저장
    public void insertResumeLicense(BoResumeDTO boResumeDTO);

    // 수상 저장
    public void insertResumeAward(BoResumeDTO boResumeDTO);

    // 외부활동 저장
    public void insertResumeExternalActivity(BoResumeDTO boResumeDTO);

    // 포트폴리오 저장
    public void insertResumePortfolio(BoResumeDTO boResumeDTO);

    // 영어 이력서 저장
    public void insertResumeEnglish(BoResumeDTO boResumeDTO);
    void insertResumeFiltering(BoResumeDTO boResumeDTO);
    void deleteResumeFiltering(BoResumeDTO foResumeDTO);
    void updateParsingErrorAccept(BoResumeDTO boResumeDTO);
    void insertResumeLocation(BoResumeDTO boResumeDTO);

    // 이력서 수정
    public void updateResume(BoResumeDTO boResumeDTO);

    public int selectResumeSpecCnt(BoResumeDTO boResumeDTO);

    // 핵심역량 수정
    public void updateResumeSpec(BoResumeDTO boResumeDTO);
    public void deleteResumeAcademicBackgroundMajor(BoResumeDTO boResumeDTO);

    //  학력수정
    public void updateResumeAcademicBackground(BoResumeDTO boResumeDTO);

    //  전공수정
    public void updateResumeAcademicBackgroundMajor(BoResumeDTO boResumeDTO);

    // 학력수전 전에 부전공 입렸했었는지 여부
    public int selectEnResumeCnt(BoResumeDTO boResumeDTO);
    public int selectResumeLanguageCnt(BoResumeDTO boResumeDTO);
    public int selectResumeLicenseCnt(BoResumeDTO boResumeDTO);
    public int selectResumeAwardCnt(BoResumeDTO boResumeDTO);
    public int selectResumeExternalActivityCnt(BoResumeDTO boResumeDTO);

    public int selectMinorCnt(BoResumeDTO boResumeDTO);

    // 경력 수정
    public void updateResumeCareer(BoResumeDTO boResumeDTO);

    // 어학 수정
    public void updateResumeLanguage(BoResumeDTO boResumeDTO);

    // 자격증 수정
    public void updateResumeLicense(BoResumeDTO boResumeDTO);

    // 수상 수정
    public void updateResumeAward(BoResumeDTO boResumeDTO);

    // 외부활동 수정
    public void updateResumeExternalActivity(BoResumeDTO boResumeDTO);

    // 포트폴리오 수정
    public void updateResumePortfolio(BoResumeDTO boResumeDTO);

    // 영문이력서 수정
    public void updateResumeEnglish(BoResumeDTO boResumeDTO);

    // 포트폴리오 삭제
    public void updateDeleteResumeResumePortfolio(BoResumeDTO boResumeDTO);

    // 지역 수정
    public void updateResumeLocation(BoResumeDTO boResumeDTO);

    // 경력 삭제
    public void updateDeleteResumeCareer(BoResumeDTO boResumeDTO);

    // 학력 삭제
    public void updateDeleteResumeAcademicBackground(BoResumeDTO boResumeDTO);
    
    // 회원 가입
    public BoResumeDTO selectExistMember(BoResumeDTO boResumeDTO);

    public void insertMember(BoResumeDTO boResumeDTO);
    public void insertMemberRole(BoResumeDTO boResumeDTO);
    public void insertApplicant(BoResumeDTO boResumeDTO);

    public String selectHeadhunterByMemberId(BoResumeDTO boResumeDTO);


}
