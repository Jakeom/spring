package com.fw.bo.management.operation.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.MailDTO;
import com.fw.core.dto.bo.BoHhResumeFormDTO;
import com.fw.core.dto.bo.BoMemberDTO;
import com.fw.core.dto.bo.BoParsingErrorAcceptDTO;
import com.fw.core.dto.bo.BoResumeDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import com.fw.core.mapper.db1.bo.BoResumeMapper;
import com.fw.core.util.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationResumeService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final BoResumeMapper boResumeMapper;
    private final CommonFileService commonFileService;

    private final BoFilterService boFilterService;

    public List<BoResumeDTO> selectResumeList(BoResumeDTO boResumeDTO) {
        return boResumeMapper.selectResumeList(boResumeDTO);
    }

    public List<BoResumeDTO> selectResumeOptionList(BoResumeDTO boResumeDTO) {
        return boResumeMapper.selectResumeOptionList(boResumeDTO);
    }

    public List<BoResumeDTO> selectResumeSearch(BoResumeDTO boResumeDTO) {
        return boResumeMapper.selectResumeSearch(boResumeDTO);
    }

    public List<BoParsingErrorAcceptDTO> selectParsingErrorList(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO) {
        List<BoParsingErrorAcceptDTO> list = boResumeMapper.selectParsingErrorList(boParsingErrorAcceptDTO);
        for(int i=0; i<list.size();i++){
            String fileSeq = list.get(i).getResumeFileId();
            if(!Objects.isNull(fileSeq)){
                if(StringUtils.isNotBlank(fileSeq)){
                    list.get(i).setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
                }
            }
        }
        return list;
    }

    public List<BoParsingErrorAcceptDTO> selectParsingErrorOptionList(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO) {
        List<BoParsingErrorAcceptDTO> list = boResumeMapper.selectParsingErrorOptionList(boParsingErrorAcceptDTO);
        for(int i=0; i<list.size();i++){
            String fileSeq = list.get(i).getResumeFileId();
            if(!Objects.isNull(fileSeq)){
                if(StringUtils.isNotBlank(fileSeq)){
                    list.get(i).setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
                }
            }
        }
        return list;
    }

    public int selectParsingErrorAcceptListCnt(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO) {
        return boResumeMapper.selectParsingErrorAcceptListCnt(boParsingErrorAcceptDTO);
    }

    public int selectResumeListCnt(BoResumeDTO boResumeDTO) {
        return boResumeMapper.selectResumeListCnt(boResumeDTO);
    }

    public List<BoParsingErrorAcceptDTO> selectParsingErrorAcceptList(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO) {
        List<BoParsingErrorAcceptDTO> list = boResumeMapper.selectParsingErrorAcceptList(boParsingErrorAcceptDTO);
        for(int i=0; i<list.size();i++){
            String fileSeq = list.get(i).getResumeFileId();
            if(!Objects.isNull(fileSeq)){
                if(StringUtils.isNotBlank(fileSeq)){
                    list.get(i).setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
                }
            }
            String fileSeq2 = list.get(i).getAgreeFileid();
            if(!Objects.isNull(fileSeq2)){
                if(StringUtils.isNotBlank(fileSeq2)){
                    list.get(i).setAgreeFileList(commonFileService.selectFileDetailList(fileSeq2));
                }
            }
        }
        return list;
    }

    public List<BoHhResumeFormDTO> selectHhResumeFormList(BoHhResumeFormDTO boHhResumeFormDTO) {
        List<BoHhResumeFormDTO> list = boResumeMapper.selectHhResumeFormList(boHhResumeFormDTO);
        for(int i=0; i<list.size();i++){
            String fileSeq = list.get(i).getFormFileId();
            if(!Objects.isNull(fileSeq)){
                if(StringUtils.isNotBlank(fileSeq)){
                    list.get(i).setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
                }
            }
        }
        return list;
    }

    public List<BoHhResumeFormDTO> selectHhResumeFormOptionList(BoHhResumeFormDTO boHhResumeFormDTO) {
        List<BoHhResumeFormDTO> list = boResumeMapper.selectHhResumeFormOptionList(boHhResumeFormDTO);
        for(int i=0; i<list.size();i++){
            String fileSeq = list.get(i).getFormFileId();
            if(!Objects.isNull(fileSeq)){
                if(StringUtils.isNotBlank(fileSeq)){
                    list.get(i).setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
                }
            }
        }
        return list;
    }

    public void updateResumeFormAcceptStatus(BoHhResumeFormDTO boHhResumeFormDTO) {
        boResumeMapper.updateResumeFormAcceptStatus(boHhResumeFormDTO);
    }

    public void updateResumeFormRegistImpossibleStatus(BoHhResumeFormDTO boHhResumeFormDTO) {
        boResumeMapper.updateResumeFormRegistImpossibleStatus(boHhResumeFormDTO);
    }

    public void updateRefuseRequestRegistResume(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO) {
        boResumeMapper.updateRefuseRequestRegistResume(boParsingErrorAcceptDTO);
    }

    @Transactional
    public void sendEmail(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO) {
        MailUtil.sendEmail(MailDTO.builder()
                .fromEmail("resume9@resume9.co.kr")
                .receiveEmail(Collections.singletonList((boParsingErrorAcceptDTO.getEmail())))
                .subject("Resume9 오류신고")
                .content("신고하신 오류 내역이 처리되었습니다.")
                .template("certCode")
                .build());
        boResumeMapper.updateParsingErrorComplete(boParsingErrorAcceptDTO);
    }



    //이력서 상세보기
    public BoResumeDTO selectResumeInfo(BoResumeDTO boResumeDTO){
        BoResumeDTO f = boResumeMapper.selectResumeInfo(boResumeDTO);
        if(f != null) {
            f.setCommonFileList(commonFileService.selectFileDetailList(f.getPictureFileId()));
        }
        return f;
    }

    // 학력,전공 정보 취득
    public List<BoResumeDTO> selectAcademicInfo(BoResumeDTO boResumeDTO) {
        return boResumeMapper.selectAcademicInfo(boResumeDTO);
    }

    // 경력 정보 취득
    public List<BoResumeDTO> selectCareerInfo(BoResumeDTO boResumeDTO){
        return boResumeMapper.selectCareerInfo(boResumeDTO);
    }

    // 최종 경력 정보 취득
    public BoResumeDTO selectFinalCareerInfo(BoResumeDTO boResumeDTO){
        return boResumeMapper.selectFinalCareerInfo(boResumeDTO);
    }

    // 핵심역량 정보 취득
    public List<BoResumeDTO> selectSpecInfo(BoResumeDTO boResumeDTO){
        return boResumeMapper.selectSpecInfo(boResumeDTO);
    }

    // 희망근무지 정보 취득
    public BoResumeDTO selectDesiredLocationInfo(BoResumeDTO boResumeDTO){
        return boResumeMapper.selectDesiredLocationInfo(boResumeDTO);
    }

    // 어학 정보 취득
    public List<BoResumeDTO> selectLanguageInfo(BoResumeDTO boResumeDTO){
        return boResumeMapper.selectLanguageInfo(boResumeDTO);
    }

    // 자격증 정보 취득
    public List<BoResumeDTO> selectLicenseInfo(BoResumeDTO boResumeDTO){
        return boResumeMapper.selectLicenseInfo(boResumeDTO);
    }

    // 수상 정보 취득
    public List<BoResumeDTO> selectAwardInfo(BoResumeDTO boResumeDTO){
        return boResumeMapper.selectAwardInfo(boResumeDTO);
    }

    // 대외활동 정보 취득
    public List<BoResumeDTO> selectActivityInfo(BoResumeDTO boResumeDTO){
        return boResumeMapper.selectActivityInfo(boResumeDTO);
    }

    // 포트폴리오 정보 취득
    public List<BoResumeDTO> selectPortfolioInfo(BoResumeDTO boResumeDTO){
        List<BoResumeDTO> list = boResumeMapper.selectPortfolioInfo(boResumeDTO);
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
    public BoResumeDTO selectEnglishResumeInfo(BoResumeDTO boResumeDTO){
        BoResumeDTO f = boResumeMapper.selectEnglishResumeInfo(boResumeDTO);
        if(!Objects.isNull(f)){
            f.setCommonFileList(commonFileService.selectFileDetailList(f.getEnAttachFileId()));
        }
        return f;
    }





    // 이력서 등록
    @Transactional
    public void insertResume(BoResumeDTO boResumeDTO) throws Exception {

        // 이력서 프로필 사진 업로드
        Integer profileFileId = commonFileService.fileUpload(boResumeDTO.getResumeImageFiles(), "RESUME_PROFILE");
        if(profileFileId != null){
            boResumeDTO.setPictureFileId(String.valueOf(profileFileId));
        }

        // 이력서 등록
        boResumeMapper.insertResume(boResumeDTO);
        String resumeId = boResumeDTO.getId();

        // 핵심역량 등록
        for (BoResumeDTO f : boResumeDTO.getSpecList()) {
            f.setResumeId(resumeId);
            boResumeMapper.insertResumeSpec(f);
        }

        // 경력 저장
        for (BoResumeDTO f : boResumeDTO.getCareerList()) {
            f.setResumeId(resumeId);
            boResumeMapper.insertResumeCareer(f);
        }

        // 학력 저장
        String filterCd = "";
        for (BoResumeDTO f : boResumeDTO.getAcademicBackgroundList()) {
            f.setResumeId(resumeId);
            boResumeMapper.insertResumeAcademicBackground(f);

            // 전공 저장
            String academicBackgroundId = f.getId();
            for (BoResumeDTO fm : f.getAcademicBackgroundMajorList()) {
                if(StringUtils.isNotBlank(fm.getMajorName())) {
                    fm.setAcademicBackgroundId(academicBackgroundId);
                    boResumeMapper.insertResumeAcademicBackgroundMajor(fm);
                }
            }

            String resultFilterCd = boFilterService.getUnivFilter(f);
            if(StringUtils.isNotBlank(resultFilterCd)){
                filterCd = resultFilterCd;
            }
        }

        // 필터링 저장
        boResumeMapper.insertResumeFiltering(BoResumeDTO.builder()
                .filterCd(filterCd)
                .resumeId(resumeId)
                .build());

        // 어학 저장
        if(StringUtils.isNotBlank(boResumeDTO.getLanguageInput())) {
            boResumeMapper.insertResumeLanguage(BoResumeDTO.builder()
                    .conversationCd(boResumeDTO.getConversationCd())
                    .languageCd(boResumeDTO.getLanguageCd())
                    .languageCertificationCd(boResumeDTO.getLanguageCertificationCd())
                    .languageInput(boResumeDTO.getLanguageInput())
                    .obtainYm(boResumeDTO.getObtainYm())
                    .testScore(boResumeDTO.getTestScore())
                    .testTypeCd(boResumeDTO.getTestTypeCd())
                    .resumeId(resumeId)
                    .build());
        }

        // 자격증 저장
        if(StringUtils.isNotBlank(boResumeDTO.getLicenseName())) {
            boResumeMapper.insertResumeLicense(BoResumeDTO.builder()
                    .agency(boResumeDTO.getAgency())
                    .name(boResumeDTO.getLicenseName())
                    .obtainYmLicense(boResumeDTO.getObtainYmLicense())
                    .resumeId(resumeId)
                    .build());
        }

        // 수상 저장
        if(StringUtils.isNotBlank(boResumeDTO.getAwardName())) {
            boResumeMapper.insertResumeAward(BoResumeDTO.builder()
                    .agency(boResumeDTO.getAgency())
                    .awardYm(boResumeDTO.getAwardYm())
                    .name(boResumeDTO.getAwardName())
                    .resumeId(resumeId)
                    .build());
        }

        // 외부활동 저장
        if(StringUtils.isNotBlank(boResumeDTO.getContent())) {
            boResumeMapper.insertResumeExternalActivity(BoResumeDTO.builder()
                    .content(boResumeDTO.getContent())
                    .resumeId(resumeId)
                    .build());
        }

        // 포트폴리오 저장
        int i = 0;
        for (BoResumeDTO f : boResumeDTO.getPortfolioList()) {
            f.setResumeId(resumeId);
            if(StringUtils.equals("FILE", f.getAttachType())) {
                Integer portFolioId = commonFileService.fileUpload(new MultipartFile[]{ boResumeDTO.getPortFolioFiles()[i] }, "PORTFOLIO");
                f.setAttachFileId(String.valueOf(portFolioId));
                i++;
            }
            boResumeMapper.insertResumePortfolio(f);
        }

        // 영문이력서 저장
        Integer enResumeId = commonFileService.fileUpload(boResumeDTO.getEnResumeFile(), "EN_RESUME");
        if(enResumeId != null){
            boResumeMapper.insertResumeEnglish(BoResumeDTO.builder()
                    .attachType("FILE")
                    .url(null)
                    .attachFileId(String.valueOf(enResumeId))
                    .resumeId(resumeId)
                    .build());
        }

        // 지역 저장
        boResumeMapper.insertResumeLocation(BoResumeDTO.builder()
                .desiredLocationCd(boResumeDTO.getDesiredLocationCd())
                .resumeId(resumeId)
                .build());
        boResumeMapper.updateParsingErrorAccept(boResumeDTO.builder().parsingErrorId(boResumeDTO.getParsingErrorId()).build());
    }

    // 이력서 수정
    @Transactional
    public void updateResume(BoResumeDTO boResumeDTO) throws Exception {
        //이력서 프로필사진 수정시
        if(boResumeDTO.getPhotoChangeFlag().equals("Y")){
            // 이력서 프로필 사진 업로드
            Integer profileFileId = commonFileService.fileUpload(boResumeDTO.getResumeImageFiles(), "RESUME_PROFILE");
            if(profileFileId != null){
                boResumeDTO.setPictureFileId(String.valueOf(profileFileId));
            }
        }

        // 이력서 수정
        boResumeMapper.updateResume(boResumeDTO);
        String resumeId = boResumeDTO.getId();

        // 핵심역량 수정
        for (BoResumeDTO f : boResumeDTO.getSpecList()) {
            f.setResumeId(resumeId);
            if(boResumeMapper.selectResumeSpecCnt(f) > 0) {
                boResumeMapper.updateResumeSpec(f);
            } else {
                boResumeMapper.insertResumeSpec(f);
            }
        }

        //경력 삭제 업데이트
        if(boResumeDTO.getDeleteCareerList().size()>0){
            boResumeMapper.updateDeleteResumeCareer(boResumeDTO);
        }



        // 경력 수정
        for (BoResumeDTO f : boResumeDTO.getCareerList()) {
            if(StringUtils.isNotBlank(f.getId())){
                boResumeMapper.updateResumeCareer(f);
            }else{
                f.setResumeId(resumeId);
                boResumeMapper.insertResumeCareer(f);
            }
        }

        //학력 삭제 업데이트
        if(boResumeDTO.getDeleteAcademicList().size()>0){
            boResumeMapper.updateDeleteResumeAcademicBackground(boResumeDTO);
        }


        // 학력 수정 or 저장
        String filterCd = "";
        for (BoResumeDTO f : boResumeDTO.getAcademicBackgroundList()) {
            if(StringUtils.isNotBlank(f.getId())){
                //전공수정
                boResumeMapper.updateResumeAcademicBackground(f);
                for (BoResumeDTO fm : f.getAcademicBackgroundMajorList()) {
                    if(StringUtils.isNotBlank(fm.getMajorName())) {
                        fm.setAcademicBackgroundId(f.getId());
                        boResumeMapper.updateResumeAcademicBackgroundMajor(fm);
                    }
                }
            }else if(!StringUtils.isNotBlank(f.getId()) || f.getId().equals("")){
                f.setResumeId(resumeId);
                boResumeMapper.insertResumeAcademicBackground(f);
                f.setAcademicBackgroundId(f.getId());
                boResumeMapper.deleteResumeAcademicBackgroundMajor(f);
                // 전공 저장
                String academicBackgroundId = f.getId();
                for (BoResumeDTO fm : f.getAcademicBackgroundMajorList()) {
                    if(StringUtils.isNotBlank(fm.getMajorName())) {
                        fm.setAcademicBackgroundId(academicBackgroundId);
                        boResumeMapper.insertResumeAcademicBackgroundMajor(fm);
                    }
                }
            }

            String resultFilterCd = boFilterService.getUnivFilter(f);
            if(StringUtils.isNotBlank(resultFilterCd)){
                filterCd = resultFilterCd;
            }
        }

        // 필터링 저장
        boResumeMapper.deleteResumeFiltering(BoResumeDTO.builder()
                .filterCd(filterCd)
                .resumeId(resumeId)
                .build());
        boResumeMapper.insertResumeFiltering(BoResumeDTO.builder()
                .filterCd(filterCd)
                .resumeId(resumeId)
                .build());

        // 어학 저장
        if(StringUtils.isNotBlank(boResumeDTO.getLanguageInput())) {
            if(boResumeMapper.selectResumeLanguageCnt(BoResumeDTO.builder().resumeId(resumeId).build()) != 0) {
                boResumeMapper.updateResumeLanguage(BoResumeDTO.builder()
                        .conversationCd(boResumeDTO.getConversationCd())
                        .languageCd(boResumeDTO.getLanguageCd())
                        .languageCertificationCd(boResumeDTO.getLanguageCertificationCd())
                        .languageInput(boResumeDTO.getLanguageInput())
                        .obtainYm(boResumeDTO.getObtainYm())
                        .testScore(boResumeDTO.getTestScore())
                        .testTypeCd(boResumeDTO.getTestTypeCd())
                        .resumeId(resumeId)
                        .build());
            } else {
                boResumeMapper.insertResumeLanguage(BoResumeDTO.builder()
                        .conversationCd(boResumeDTO.getConversationCd())
                        .languageCd(boResumeDTO.getLanguageCd())
                        .languageCertificationCd(boResumeDTO.getLanguageCertificationCd())
                        .languageInput(boResumeDTO.getLanguageInput())
                        .obtainYm(boResumeDTO.getObtainYm())
                        .testScore(boResumeDTO.getTestScore())
                        .testTypeCd(boResumeDTO.getTestTypeCd())
                        .resumeId(resumeId)
                        .build());
            }
        }

        // 자격증 저장
        if(StringUtils.isNotBlank(boResumeDTO.getLicenseName())) {
            if(boResumeMapper.selectResumeLicenseCnt(BoResumeDTO.builder().resumeId(resumeId).build()) != 0) {
                boResumeMapper.updateResumeLicense(BoResumeDTO.builder()
                        .agency(boResumeDTO.getAgency())
                        .name(boResumeDTO.getLicenseName())
                        .obtainYmLicense(boResumeDTO.getObtainYmLicense())
                        .resumeId(resumeId)
                        .build());
            } else {
                boResumeMapper.insertResumeLicense(BoResumeDTO.builder()
                        .agency(boResumeDTO.getAgency())
                        .name(boResumeDTO.getLicenseName())
                        .obtainYmLicense(boResumeDTO.getObtainYmLicense())
                        .resumeId(resumeId)
                        .build());
            }
        }

        // 수상 저장
        if(StringUtils.isNotBlank(boResumeDTO.getAwardName())) {
            if(boResumeMapper.selectResumeAwardCnt(BoResumeDTO.builder().resumeId(resumeId).build()) != 0) {
                boResumeMapper.updateResumeAward(BoResumeDTO.builder()
                        .agency(boResumeDTO.getAgency())
                        .awardYm(boResumeDTO.getAwardYm())
                        .name(boResumeDTO.getAwardName())
                        .resumeId(resumeId)
                        .build());
            } else {
                boResumeMapper.insertResumeAward(BoResumeDTO.builder()
                        .agency(boResumeDTO.getAgency())
                        .awardYm(boResumeDTO.getAwardYm())
                        .name(boResumeDTO.getAwardName())
                        .resumeId(resumeId)
                        .build());
            }
        }

        // 외부활동 저장
        if(StringUtils.isNotBlank(boResumeDTO.getContent())) {
            if(boResumeMapper.selectResumeExternalActivityCnt(BoResumeDTO.builder().resumeId(resumeId).build()) != 0) {
                boResumeMapper.updateResumeExternalActivity(BoResumeDTO.builder()
                        .content(boResumeDTO.getContent())
                        .resumeId(resumeId)
                        .build());
            } else {
                boResumeMapper.insertResumeExternalActivity(BoResumeDTO.builder()
                        .content(boResumeDTO.getContent())
                        .resumeId(resumeId)
                        .build());
            }
        }

        //포트폴리오 삭제
        if(boResumeDTO.getDeletePortfolioList().size()>0){
            boResumeMapper.updateDeleteResumeResumePortfolio(boResumeDTO);
        }

        // 포트폴리오 저장
        int i = 0;
        for (BoResumeDTO f : boResumeDTO.getPortfolioList()) {
            f.setResumeId(resumeId);
            if(StringUtils.isNotBlank(f.getId())) {
                if(StringUtils.equals("FILE", f.getAttachType())&&f.getFileChangeFlag().equals("Y")) {
                    Integer portFolioId = commonFileService.fileUpload(new MultipartFile[]{ boResumeDTO.getPortFolioFiles()[i] }, "PORTFOLIO");
                    f.setAttachFileId(String.valueOf(portFolioId));
                    i++;
                    boResumeMapper.updateResumePortfolio(f);
                } else if(StringUtils.equals("URL", f.getAttachType())){
                    boResumeMapper.updateResumePortfolio(f);
                }
            }else {
                if(StringUtils.equals("FILE", f.getAttachType())) {
                    Integer portFolioId = commonFileService.fileUpload(new MultipartFile[]{ boResumeDTO.getPortFolioFiles()[i] }, "PORTFOLIO");
                    f.setAttachFileId(String.valueOf(portFolioId));
                    i++;
                }
                boResumeMapper.insertResumePortfolio(f);
            }
        }

        // 영문이력서 수정
        if(boResumeDTO.getEnFileChangeFlag().equals("Y")){
            Integer enResumeId = commonFileService.fileUpload(boResumeDTO.getEnResumeFile(), "EN_RESUME");
            if(enResumeId != null){
                if(boResumeMapper.selectEnResumeCnt(boResumeDTO.builder().resumeId(resumeId).build()) != 0) {
                    boResumeMapper.updateResumeEnglish(BoResumeDTO.builder()
                            .attachType("FILE")
                            .url(null)
                            .attachFileId(String.valueOf(enResumeId))
                            .resumeId(resumeId)
                            .build());
                } else {
                    boResumeMapper.insertResumeEnglish(BoResumeDTO.builder()
                            .attachType("FILE")
                            .url(null)
                            .attachFileId(String.valueOf(enResumeId))
                            .resumeId(resumeId)
                            .build());
                }
            }
        }


        // 지역 저장
        boResumeMapper.updateResumeLocation(BoResumeDTO.builder()
                .desiredLocationCd(boResumeDTO.getDesiredLocationCd())
                .resumeId(resumeId)
                .build());
    }



    @Transactional
    public Map<String, Object> insertTempApplicant(BoResumeDTO boResumeDTO){
        Map<String, Object> resultMap = new HashMap<>();

        boResumeDTO.setPhone(boResumeDTO.getPhone().replaceAll("[^\\d.]", ""));	// 휴대폰 숫자 이외 문자 제거
        boResumeDTO.setEmail(boResumeDTO.getEmail().toLowerCase());
        boResumeDTO.setLoginId(boResumeDTO.getEmail().toLowerCase());
        boResumeDTO.setAgreeMarketing("0");
        boResumeDTO.setDtype("AP");

        // 휴대폰 중복 체크
        if(boResumeMapper.selectExistMember(boResumeDTO.builder().phone(boResumeDTO.getPhone()).build()) != null){
            resultMap.put("RESULT", "중복된 휴대폰번호입니다.");
            return resultMap;
        }

        // 이메일 중복 체크
        if(boResumeMapper.selectExistMember(boResumeDTO.builder().email(boResumeDTO.getEmail()).build()) != null){
            resultMap.put("RESULT", "중복된 이메일입니다.");
            return resultMap;
        }

        if(boResumeMapper.selectExistMember(boResumeDTO) != null){
            // 탈퇴 회원 체크
            if(StringUtils.equals("1", boResumeMapper.selectExistMember(boResumeDTO).getDeleted())) {
                resultMap.put("RESULT", "삭제된 회원입니다.");
                return resultMap;
            } else {
                resultMap.put("RESULT", "중복된 회원입니다.");
                return resultMap;
            }
        }

        boResumeDTO.setPassword(bCryptPasswordEncoder.encode(boResumeDTO.getPhone()));	// 임시비밀번호는 회원 핸드폰으로 설정
        boResumeDTO.setTemp(true);

        // 회원 등록
        boResumeMapper.insertMember(boResumeDTO);

        // 추천 코드 조회
        String referralCd = boResumeMapper.selectHeadhunterByMemberId(boResumeDTO);
        boResumeDTO.setReferralCode(referralCd);
        boResumeMapper.insertApplicant(boResumeDTO);

        boResumeDTO.setRoleId("1");
        boResumeMapper.insertMemberRole(boResumeDTO);

        resultMap.put("RESULT", "SUCCESS");
        resultMap.put("memberId", boResumeDTO.getJoinMemberId());
        return resultMap;
    }

}
