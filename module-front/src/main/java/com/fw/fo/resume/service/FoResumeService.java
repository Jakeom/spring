package com.fw.fo.resume.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.fo.FoApplicantDTO;
import com.fw.core.dto.fo.FoPointDTO;
import com.fw.core.dto.fo.FoPositionApplicantDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import com.fw.core.mapper.db1.fo.resume.FoResumeMapper;
import com.fw.core.mapper.db1.hh.HhPointMapper;
import com.fw.core.mapper.db1.m.MResumeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoResumeService {

    private final FoResumeMapper foResumeMapper;
    private final FoFilterService foFilterService;
    private final CommonFileService commonFileService;
    private final HhPointMapper hhPointMapper;


    //이력서 리스트 카운트 취득
    public int selectResumeListCnt(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectResumeListCnt(foResumeDTO);
    }

    //이력서 리스트
    public List<FoResumeDTO> selectResumeList(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectResumeList(foResumeDTO);
    }

   //이력서 상세보기
    public FoResumeDTO selectResumeInfo(FoResumeDTO foResumeDTO){
        FoResumeDTO f = foResumeMapper.selectResumeInfo(foResumeDTO);
        f.setCommonFileList(commonFileService.selectFileDetailList(f.getPictureFileId()));
        return f;
    }

    // 학력,전공 정보 취득
    public List<FoResumeDTO> selectAcademicInfo(FoResumeDTO foResumeDTO) {
        return foResumeMapper.selectAcademicInfo(foResumeDTO);
    }

    // 경력 정보 취득
    public List<FoResumeDTO> selectCareerInfo(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectCareerInfo(foResumeDTO);
    }

    // 최종 경력 정보 취득
    public FoResumeDTO selectFinalCareerInfo(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectFinalCareerInfo(foResumeDTO);
    }

    // 핵심역량 정보 취득
    public List<FoResumeDTO> selectSpecInfo(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectSpecInfo(foResumeDTO);
    }

    // 희망근무지 정보 취득
    public FoResumeDTO selectDesiredLocationInfo(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectDesiredLocationInfo(foResumeDTO);
    }

    // 어학 정보 취득
    public List<FoResumeDTO> selectLanguageInfo(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectLanguageInfo(foResumeDTO);
    }

    // 자격증 정보 취득
    public List<FoResumeDTO> selectLicenseInfo(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectLicenseInfo(foResumeDTO);
    }

    // 수상 정보 취득
    public List<FoResumeDTO> selectAwardInfo(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectAwardInfo(foResumeDTO);
    }

    // 대외활동 정보 취득
    public List<FoResumeDTO> selectActivityInfo(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectActivityInfo(foResumeDTO);
    }

    // 포트폴리오 정보 취득
    public List<FoResumeDTO> selectPortfolioInfo(FoResumeDTO foResumeDTO){
        List<FoResumeDTO> list = foResumeMapper.selectPortfolioInfo(foResumeDTO);
        for(int i=0; i<list.size();i++){
            String fileSeq = list.get(i).getAttachFileId();
            if(!Objects.isNull(fileSeq)){
                if(StringUtils.isNotBlank(fileSeq)){
                    list.get(i).setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
                }
            }
        }
        return list;
    }

    // 영문이력서 정보 취득
    public FoResumeDTO selectEnglishResumeInfo(FoResumeDTO foResumeDTO){
        FoResumeDTO f = foResumeMapper.selectEnglishResumeInfo(foResumeDTO);
        if(!Objects.isNull(f)){
            f.setCommonFileList(commonFileService.selectFileDetailList(f.getEnAttachFileId()));
        }
        return f;
    }

    // 이력서 등록
    @Transactional
    public void insertResume(FoResumeDTO foResumeDTO) throws Exception {
        // 이력서 프로필 사진 업로드
        Integer profileFileId = commonFileService.fileUpload(foResumeDTO.getResumeImageFiles(), "RESUME_PROFILE");
        if(profileFileId != null){
            foResumeDTO.setPictureFileId(String.valueOf(profileFileId));
        }

        // 이력서 등록
        foResumeMapper.insertResume(foResumeDTO);
        String resumeId = foResumeDTO.getId();

        // 핵심역량 등록
        for (FoResumeDTO f : foResumeDTO.getSpecList()) {
            f.setResumeId(resumeId);
            foResumeMapper.insertResumeSpec(f);
        }

        // 경력 저장
        for (FoResumeDTO f : foResumeDTO.getCareerList()) {
            f.setResumeId(resumeId);
            foResumeMapper.insertResumeCareer(f);
        }

        // 학력 저장
        String filterCd = "";
        for (FoResumeDTO f : foResumeDTO.getAcademicBackgroundList()) {
            f.setResumeId(resumeId);
            foResumeMapper.insertResumeAcademicBackground(f);

            // 전공 저장
            String academicBackgroundId = f.getId();
            for (FoResumeDTO fm : f.getAcademicBackgroundMajorList()) {
            	if(StringUtils.isNotBlank(fm.getMajorName())) {
	                fm.setAcademicBackgroundId(academicBackgroundId);
	                foResumeMapper.insertResumeAcademicBackgroundMajor(fm);
            	}
            }

            String resultFilterCd = foFilterService.getUnivFilter(f);
            if(StringUtils.isNotBlank(resultFilterCd)){
                filterCd = resultFilterCd;
            }
        }

        // 필터링 저장
        foResumeMapper.insertResumeFiltering(FoResumeDTO.builder()
                .filterCd(filterCd)
                .resumeId(resumeId)
                .build());

        // 어학 저장
        if(StringUtils.isNotBlank(foResumeDTO.getLanguageInput())) {
            foResumeMapper.insertResumeLanguage(FoResumeDTO.builder()
                    .conversationCd(foResumeDTO.getConversationCd())
                    .languageCd(foResumeDTO.getLanguageCd())
                    .languageCertificationCd(foResumeDTO.getLanguageCertificationCd())
                    .languageInput(foResumeDTO.getLanguageInput())
                    .obtainYm(foResumeDTO.getObtainYm())
                    .testScore(foResumeDTO.getTestScore())
                    .testTypeCd(foResumeDTO.getTestTypeCd())
                    .resumeId(resumeId)
                    .build());
        }

        // 자격증 저장
        if(StringUtils.isNotBlank(foResumeDTO.getLicenseName())) {
            foResumeMapper.insertResumeLicense(FoResumeDTO.builder()
                    .agency(foResumeDTO.getAgency())
                    .name(foResumeDTO.getLicenseName())
                    .obtainYmLicense(foResumeDTO.getObtainYmLicense())
                    .resumeId(resumeId)
                    .build());
        }

        // 수상 저장
        if(StringUtils.isNotBlank(foResumeDTO.getAwardName())) {
            foResumeMapper.insertResumeAward(FoResumeDTO.builder()
                    .agency(foResumeDTO.getAgency())
                    .awardYm(foResumeDTO.getAwardYm())
                    .name(foResumeDTO.getAwardName())
                    .resumeId(resumeId)
                    .build());
        }

        // 외부활동 저장
        if(StringUtils.isNotBlank(foResumeDTO.getContent())) {
            foResumeMapper.insertResumeExternalActivity(FoResumeDTO.builder()
                    .content(foResumeDTO.getContent())
                    .resumeId(resumeId)
                    .build());
        }

        // 포트폴리오 저장
        int i = 0;
        for (FoResumeDTO f : foResumeDTO.getPortfolioList()) {
            f.setResumeId(resumeId);
            if(StringUtils.equals("FILE", f.getAttachType())) {
                Integer portFolioId = commonFileService.fileUpload(new MultipartFile[]{ foResumeDTO.getPortFolioFiles()[i] }, "PORTFOLIO");
                f.setAttachFileId(String.valueOf(portFolioId));
                i++;
            }
            foResumeMapper.insertResumePortfolio(f);
        }

        // 영문이력서 저장
        Integer enResumeId = commonFileService.fileUpload(foResumeDTO.getEnResumeFile(), "EN_RESUME");
        if(enResumeId != null){
            foResumeMapper.insertResumeEnglish(FoResumeDTO.builder()
                    .attachType("FILE")
                    .url(null)
                    .attachFileId(String.valueOf(enResumeId))
                    .resumeId(resumeId)
                    .build());
        }

        // 지역 저장
        foResumeMapper.insertResumeLocation(FoResumeDTO.builder()
                .desiredLocationCd(foResumeDTO.getDesiredLocationCd())
                .resumeId(resumeId)
                .build());
    }

    // 이력서 수정
    @Transactional
    public void updateResume(FoResumeDTO foResumeDTO) throws Exception {
        //이력서 프로필사진 수정시
        if(foResumeDTO.getPhotoChangeFlag().equals("Y")){
            // 이력서 프로필 사진 업로드
            Integer profileFileId = commonFileService.fileUpload(foResumeDTO.getResumeImageFiles(), "RESUME_PROFILE");
            if(profileFileId != null){
                foResumeDTO.setPictureFileId(String.valueOf(profileFileId));
            }
        }

        // 이력서 수정
        foResumeMapper.updateResume(foResumeDTO);
        String resumeId = foResumeDTO.getId();

        // 핵심역량 수정
        for (FoResumeDTO f : foResumeDTO.getSpecList()) {
            f.setResumeId(resumeId);
            if(foResumeMapper.selectResumeSpecCnt(f) > 0) {
                foResumeMapper.updateResumeSpec(f);
            } else {
                foResumeMapper.insertResumeSpec(f);
            }
        }

        //경력 삭제 업데이트
        if(foResumeDTO.getDeleteCareerList().size()>0){
            foResumeMapper.updateDeleteResumeCareer(foResumeDTO);
        }

        // 경력 수정
        for (FoResumeDTO f : foResumeDTO.getCareerList()) {
            if(StringUtils.isNotBlank(f.getId())){
                foResumeMapper.updateResumeCareer(f);
            }else{
                f.setResumeId(resumeId);
                foResumeMapper.insertResumeCareer(f);
            }
        }

        //학력 삭제 업데이트
        if(foResumeDTO.getDeleteAcademicList().size()>0){
            foResumeMapper.updateDeleteResumeAcademicBackground(foResumeDTO);
        }


        // 학력 수정 or 저장
        String filterCd = "";
        for (FoResumeDTO f : foResumeDTO.getAcademicBackgroundList()) {
            if(StringUtils.isNotBlank(f.getId())){
                //전공수정
                foResumeMapper.updateResumeAcademicBackground(f);
                f.setAcademicBackgroundId(f.getId());
                foResumeMapper.deleteResumeAcademicBackgroundMajor(f);
                for (FoResumeDTO fm : f.getAcademicBackgroundMajorList()) {
                    if(StringUtils.isNotBlank(fm.getMajorName())) {
                        fm.setAcademicBackgroundId(f.getId());
                        foResumeMapper.insertResumeAcademicBackgroundMajor(fm);
                    }
                }
            }else{
                f.setResumeId(resumeId);
                foResumeMapper.insertResumeAcademicBackground(f);
                // 전공 저장
                String academicBackgroundId = f.getId();
                for (FoResumeDTO fm : f.getAcademicBackgroundMajorList()) {
                    if(StringUtils.isNotBlank(fm.getMajorName())) {
                        fm.setAcademicBackgroundId(academicBackgroundId);
                        foResumeMapper.insertResumeAcademicBackgroundMajor(fm);
                    }
                }
            }

            String resultFilterCd = foFilterService.getUnivFilter(f);
            if(StringUtils.isNotBlank(resultFilterCd)){
                filterCd = resultFilterCd;
            }
        }

        // 필터링 저장
        foResumeMapper.deleteResumeFiltering(FoResumeDTO.builder()
                .filterCd(filterCd)
                .resumeId(resumeId)
                .build());
        foResumeMapper.insertResumeFiltering(FoResumeDTO.builder()
                .filterCd(filterCd)
                .resumeId(resumeId)
                .build());

        // 어학 저장
        if(StringUtils.isNotBlank(foResumeDTO.getLanguageInput())) {
            if(StringUtils.isNotBlank(foResumeDTO.getLanguageId())){
                foResumeMapper.updateResumeLanguage(FoResumeDTO.builder()
                        .conversationCd(foResumeDTO.getConversationCd())
                        .languageCd(foResumeDTO.getLanguageCd())
                        .languageCertificationCd(foResumeDTO.getLanguageCertificationCd())
                        .languageInput(foResumeDTO.getLanguageInput())
                        .obtainYm(foResumeDTO.getObtainYm())
                        .testScore(foResumeDTO.getTestScore())
                        .testTypeCd(foResumeDTO.getTestTypeCd())
                        .resumeId(resumeId)
                        .build());
            }else{//수정시 등록하면
                foResumeMapper.insertResumeLanguage(FoResumeDTO.builder()
                        .conversationCd(foResumeDTO.getConversationCd())
                        .languageCd(foResumeDTO.getLanguageCd())
                        .languageCertificationCd(foResumeDTO.getLanguageCertificationCd())
                        .languageInput(foResumeDTO.getLanguageInput())
                        .obtainYm(foResumeDTO.getObtainYm())
                        .testScore(foResumeDTO.getTestScore())
                        .testTypeCd(foResumeDTO.getTestTypeCd())
                        .resumeId(resumeId)
                        .build());
            }

        }

        // 자격증 저장
        if(StringUtils.isNotBlank(foResumeDTO.getLicenseName())) {
            if(StringUtils.isNotBlank(foResumeDTO.getLicenseId())){
                foResumeMapper.updateResumeLicense(FoResumeDTO.builder()
                        .agency(foResumeDTO.getAgency())
                        .name(foResumeDTO.getLicenseName())
                        .obtainYmLicense(foResumeDTO.getObtainYmLicense())
                        .resumeId(resumeId)
                        .build());
            }else {//수정시 등록하면
                foResumeMapper.insertResumeLicense(FoResumeDTO.builder()
                        .agency(foResumeDTO.getAgency())
                        .name(foResumeDTO.getLicenseName())
                        .obtainYmLicense(foResumeDTO.getObtainYmLicense())
                        .resumeId(resumeId)
                        .build());
            }

        }

        // 수상 저장
        if(StringUtils.isNotBlank(foResumeDTO.getAwardName())) {
            if(StringUtils.isNotBlank(foResumeDTO.getAwardId())){
                foResumeMapper.updateResumeAward(FoResumeDTO.builder()
                        .agency(foResumeDTO.getAgency())
                        .awardYm(foResumeDTO.getAwardYm())
                        .name(foResumeDTO.getAwardName())
                        .resumeId(resumeId)
                        .build());
            }else {//수정시 등록하면
                foResumeMapper.insertResumeAward(FoResumeDTO.builder()
                        .agency(foResumeDTO.getAgency())
                        .awardYm(foResumeDTO.getAwardYm())
                        .name(foResumeDTO.getAwardName())
                        .resumeId(resumeId)
                        .build());
            }

        }

        // 외부활동 저장
        if(StringUtils.isNotBlank(foResumeDTO.getContent())) {
            if(StringUtils.isNotBlank(foResumeDTO.getContentId())){
                foResumeMapper.updateResumeExternalActivity(FoResumeDTO.builder()
                        .content(foResumeDTO.getContent())
                        .resumeId(resumeId)
                        .build());
            }else {//수정시 등록하면
                foResumeMapper.insertResumeExternalActivity(FoResumeDTO.builder()
                        .content(foResumeDTO.getContent())
                        .resumeId(resumeId)
                        .build());
            }

        }

        //포트폴리오 삭제
        if(foResumeDTO.getDeletePortfolioList().size()>0){
            foResumeMapper.updateDeleteResumeResumePortfolio(foResumeDTO);
        }

        // 포트폴리오 저장
        int i = 0;
        for (FoResumeDTO f : foResumeDTO.getPortfolioList()) {
            f.setResumeId(resumeId);
            if(StringUtils.isNotBlank(f.getId())) {
                if(StringUtils.equals("FILE", f.getAttachType())&&f.getFileChangeFlag().equals("Y")) {
                    Integer portFolioId = commonFileService.fileUpload(new MultipartFile[]{ foResumeDTO.getPortFolioFiles()[i] }, "PORTFOLIO");
                    f.setAttachFileId(String.valueOf(portFolioId));
                    i++;
                    foResumeMapper.updateResumePortfolio(f);
                } else if(StringUtils.equals("URL", f.getAttachType())){
                    foResumeMapper.updateResumePortfolio(f);
                }
            }else {
                if(StringUtils.equals("FILE", f.getAttachType())) {
                    Integer portFolioId = commonFileService.fileUpload(new MultipartFile[]{ foResumeDTO.getPortFolioFiles()[i] }, "PORTFOLIO");
                    f.setAttachFileId(String.valueOf(portFolioId));
                    i++;
                }
                foResumeMapper.insertResumePortfolio(f);
            }
        }

        // 영문이력서 수정
        if(StringUtils.equals("Y",foResumeDTO.getEnFileChangeFlag())){
            Integer enResumeId = commonFileService.fileUpload(foResumeDTO.getEnResumeFile(), "EN_RESUME");
            if(enResumeId != null){
                foResumeMapper.updateResumeEnglish(FoResumeDTO.builder()
                        .attachType("FILE")
                        .url(null)
                        .attachFileId(String.valueOf(enResumeId))
                        .resumeId(resumeId)
                        .build());
            }
        }


        // 지역 저장
        foResumeMapper.updateResumeLocation(FoResumeDTO.builder()
                .desiredLocationCd(foResumeDTO.getDesiredLocationCd())
                .resumeId(resumeId)
                .build());
    }

    // 이력서 복사
    @Transactional
    public void insertResumeClone(FoResumeDTO foResumeDTO) throws Exception {
        //복사할 이력서 id
        String id = foResumeDTO.getId();

        //복사할 학력
        foResumeDTO.setResumeId(id);
        List<FoResumeDTO> list = this.selectAcademicInfo(foResumeDTO);

        // 이력서 등록
        foResumeMapper.insertResumeClone(foResumeDTO);
        String resumeId = foResumeDTO.getId();
        foResumeDTO.setResumeId(resumeId);
        foResumeDTO.setId(id);

        // 핵심역량 등록
        foResumeMapper.insertResumeSpecClone(foResumeDTO);

        // 경력 저장
        foResumeMapper.insertResumeCareerClone(foResumeDTO);


        // 학력 저장
        for(FoResumeDTO f : list){
            f.setResumeId(resumeId);
            String cloneId =f.getId();
            foResumeDTO.setId(id);
            foResumeMapper.insertResumeAcademicBackgroundClone(f);
            f.setAcademicBackgroundId(f.getId());
            f.setId(cloneId);
            foResumeMapper.insertResumeAcademicBackgroundMajorClone(f);
        }

        //다시 세팅
        foResumeDTO.setId(id);
        foResumeDTO.setResumeId(resumeId);

        // 필터링 저장
        foResumeMapper.insertResumeFilteringClone(foResumeDTO);

        // 어학 저장
        if(foResumeMapper.selectLanguageInfo(FoResumeDTO.builder().resumeId(id).build()) !=null) {
            foResumeMapper.insertResumeLanguageClone(foResumeDTO);
        }

        // 자격증 저장
        if(foResumeMapper.selectLicenseInfo(FoResumeDTO.builder().resumeId(id).build()) !=null) {
            foResumeMapper.insertResumeLicenseClone(foResumeDTO);
        }

        // 수상 저장
        if(foResumeMapper.selectAwardInfo(FoResumeDTO.builder().resumeId(id).build()) !=null) {
            foResumeMapper.insertResumeAwardClone(foResumeDTO);
        }

        // 외부활동 저장
        if(foResumeMapper.selectActivityInfo(FoResumeDTO.builder().resumeId(id).build()) !=null) {
            foResumeMapper.insertResumeExternalActivityClone(foResumeDTO);
        }

        // 포트폴리오 저장
        if(foResumeMapper.selectPortfolioInfo(FoResumeDTO.builder().resumeId(id).build()) !=null) {
            foResumeMapper.insertResumePortfolioClone(foResumeDTO);
        }

        // 영문이력서 저장
        if(foResumeMapper.selectEnglishResumeInfo(FoResumeDTO.builder().resumeId(id).build()) !=null) {
            foResumeMapper.insertResumeEnglishClone(foResumeDTO);
        }
        // 지역 저장
        if(foResumeMapper.selectDesiredLocationInfo(FoResumeDTO.builder().resumeId(id).build()) !=null) {
            foResumeMapper.insertResumeLocationClone(foResumeDTO);
        }
    }

    // 이력서 공개여부, 구직의사여부
    public FoApplicantDTO selectResumeOpened(FoApplicantDTO foApplicantDTO){

        return foResumeMapper.selectResumeOpened(foApplicantDTO);
    }

    // 이력서 공개여부 수정
    public void updateResumeRestricted(FoApplicantDTO foApplicantDTO){
        foResumeMapper.updateResumeRestricted(foApplicantDTO);
    }

    //구직의사여부 수정
    public void updateFindingJob(FoApplicantDTO foApplicantDTO){
        foResumeMapper.updateFindingJob(foApplicantDTO);
    }

    //맞춤 채용 공고 리스트 취득
    public List<FoResumeDTO> selectPositionAlert(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectPositionAlert(foResumeDTO);
    }

    //구직의사여부 수정
    public void insertPositionAlert(FoResumeDTO foResumeDTO){
        foResumeMapper.insertPositionAlert(foResumeDTO);
    }

    //맞춤 채용 공고 직무 삭제
    public void deletePositionAlert(FoResumeDTO foResumeDTO){
        foResumeMapper.deletePositionAlert(foResumeDTO);
    }

    //기본이력서 등록여부
    public int selectResumeRepresentationCnt(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectResumeRepresentationCnt(foResumeDTO);
    }

    //기본이력서 변경
    @Transactional
    public void updateResumeRepresentation(FoResumeDTO foResumeDTO){
        foResumeMapper.updateResumeOriginRepresentation(foResumeDTO);
        foResumeMapper.updateResumeRepresentation(foResumeDTO);
    }

    //이력서 삭제
    public void updateDeleteResume(FoResumeDTO foResumeDTO){
        foResumeMapper.updateDeleteResume(foResumeDTO);
    }

    // 가장 최근 insert 이력서
    public FoResumeDTO selectLastResume(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectLastResume(foResumeDTO);
    }

    // 가입시 hh 추천코드 입력했는지 확인
    public FoResumeDTO selectHhReferralCode(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectHhReferralCode(foResumeDTO);
    }

    //hh 추천코드 있을시 이력서 최초등록 hh에게 포인트 지급
    @Transactional
    public void updateHhPoint(FoPointDTO foPointDTO){
        FoPointDTO hhPoint = hhPointMapper.selectHhPoint(foPointDTO);
        if(hhPoint.getId() == null){
            hhPointMapper.insertPointHh(foPointDTO); // 포인트 생성
        }
        hhPointMapper.updatePoint(foPointDTO); // 포인트 업데이트
        hhPointMapper.insertPointUseHistory(foPointDTO); // 포인트 사용내역 추가
    }

    public boolean selectHoldOffice(FoResumeDTO foResumeDTO){
        return foResumeMapper.selectHoldOffice(foResumeDTO);
    }
}