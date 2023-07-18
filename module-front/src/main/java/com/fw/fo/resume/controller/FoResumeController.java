package com.fw.fo.resume.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.fo.FoApplicantDTO;
import com.fw.core.dto.fo.FoPointDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.resume.service.FoResumeService;
import com.fw.fo.reward.service.FoRewardService;
import com.fw.fo.system.config.security.FoLoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 Controller
 * @author jung
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoResumeController {

    private final FoResumeService foResumeService;

    private final FoLoginService foLoginService;

    private final CommonFileService commonFileService;
    private final FoRewardService foRewardService;

    @Value("${service.wss_domain}")
    private String WSS_DOMAIN;

    @Value("${service.report_domain}")
    private String REPORT_DOMAIN;

    /**
     * front 내이력서
     */
    @GetMapping("/fo/mypage/resume-list")
    public String resume(FoResumeDTO foResumeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        foResumeDTO.setTotalCount(foResumeService.selectResumeListCnt(foResumeDTO));
        model.addAttribute("resumeList",foResumeService.selectResumeList(foResumeDTO));
        model.addAttribute("searchInfo",foResumeDTO);
        model.addAttribute("reportDomain",REPORT_DOMAIN);
        return "fo/mypage/resume-list";
    }

    /**
     * front 이력서 상세보기
     */
    @GetMapping("/fo/user/resume/resume-Info")
    public String resumeInfo(FoResumeDTO foResumeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("resumeInfo",foResumeService.selectResumeInfo(foResumeDTO));
        model.addAttribute("holdOffice", foResumeService.selectHoldOffice(foResumeDTO));
        model.addAttribute("academicInfo",foResumeService.selectAcademicInfo(foResumeDTO));
        model.addAttribute("careerInfo",foResumeService.selectCareerInfo(foResumeDTO));
        model.addAttribute("finalCareerInfo",foResumeService.selectFinalCareerInfo(foResumeDTO));
        model.addAttribute("specInfo",foResumeService.selectSpecInfo(foResumeDTO));
        model.addAttribute("desiredLocationInfo",foResumeService.selectDesiredLocationInfo(foResumeDTO));
        model.addAttribute("languageInfo",foResumeService.selectLanguageInfo(foResumeDTO));
        model.addAttribute("licenseInfo",foResumeService.selectLicenseInfo(foResumeDTO));
        model.addAttribute("awardInfo",foResumeService.selectAwardInfo(foResumeDTO));
        model.addAttribute("activityInfo",foResumeService.selectActivityInfo(foResumeDTO));
        model.addAttribute("portfolioInfo",foResumeService.selectPortfolioInfo(foResumeDTO));
        model.addAttribute("englishResumeInfo",foResumeService.selectEnglishResumeInfo(foResumeDTO));
        return "fo/user/resume/resume-detail";
    }

    /**
     * 이력서 등록
     */
    @GetMapping("/fo/user/resume/resume-write")
    public String resumeWrite(FoResumeDTO foResumeDTO, ModelMap model) {
        return "fo/user/resume/resume-write";
    }

    /**
     * 이력서 등록(RPA)
     */
    @GetMapping("/fo/user/resume/resume-write-rpa")
    public String resumeWriteRpa(FoResumeDTO foResumeDTO, ModelMap model) {
        model.addAttribute("wssDomain", WSS_DOMAIN);
        return "fo/user/resume/resume-write-rpa";
    }

    /**
     * 이력서 insert
     */
    @PostMapping(value = "/fo/user/resume/resume-write", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ResponseVO> resumeWriteInsert(
                                                        @RequestPart(value = "jsonData") FoResumeDTO foResumeDTO,
                                                        @RequestPart(value = "resumeImageFiles", required = false) MultipartFile[] resumeImageFiles,
                                                        @RequestPart(value = "enResumeFile", required = false) MultipartFile[] enResumeFile,
                                                        @RequestPart(value = "portFolioFiles", required = false) MultipartFile[] portFolioFiles
                                                        ) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {

            foResumeDTO.setResumeImageFiles(resumeImageFiles);
            foResumeDTO.setEnResumeFile(enResumeFile);
            foResumeDTO.setPortFolioFiles(portFolioFiles);
            foResumeDTO.setMemberId(foResumeDTO.getFrontSession().getId());
            if(foResumeService.selectResumeRepresentationCnt(foResumeDTO)>0){
                foResumeDTO.setRepresentation("0");
                foResumeService.insertResume(foResumeDTO);
            }else {
                foResumeDTO.setRepresentation("1");
                foResumeService.insertResume(foResumeDTO);
                //이력서 최초등록시 hh코드입력여부
                FoResumeDTO fR = foResumeService.selectHhReferralCode(foResumeDTO);
                if(fR != null){
                    if(!StringUtils.isEmpty(fR.getHhReferralCode())){
                        FoPointDTO f = new FoPointDTO();
                        f.setFrontSession(foResumeDTO.getFrontSession());
                        f.setMemberId(fR.getMemberId());
                        f.setFreeIncrease(20000);
                        f.setPaidIncrease(0);
                        f.setType("ADD_RESUME_FREE");
                        foResumeService.updateHhPoint(f);
                    }
                }

            }
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * front 이력서 수정하기
     */
    @GetMapping("/fo/user/resume/resume-edit")
    public String resumeEdit(FoResumeDTO foResumeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("resumeInfo",foResumeService.selectResumeInfo(foResumeDTO));
        model.addAttribute("academicInfo",foResumeService.selectAcademicInfo(foResumeDTO));
        model.addAttribute("careerInfo",foResumeService.selectCareerInfo(foResumeDTO));
        model.addAttribute("specInfo",foResumeService.selectSpecInfo(foResumeDTO));
        model.addAttribute("desiredLocationInfo",foResumeService.selectDesiredLocationInfo(foResumeDTO));
        model.addAttribute("languageInfo",foResumeService.selectLanguageInfo(foResumeDTO));
        model.addAttribute("licenseInfo",foResumeService.selectLicenseInfo(foResumeDTO));
        model.addAttribute("awardInfo",foResumeService.selectAwardInfo(foResumeDTO));
        model.addAttribute("activityInfo",foResumeService.selectActivityInfo(foResumeDTO));
        model.addAttribute("portfolioInfo",foResumeService.selectPortfolioInfo(foResumeDTO));
        model.addAttribute("englishResumeInfo",foResumeService.selectEnglishResumeInfo(foResumeDTO));
        return "fo/user/resume/resume-edit";
    }

    /**
     * 이력서 수정하기 DO
     */
    @PostMapping(value = "/fo/user/resume/resume-edit", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ResponseVO> resumeEditDo(
            @RequestPart(value = "jsonData") FoResumeDTO foResumeDTO,
            @RequestPart(value = "resumeImageFiles", required = false) MultipartFile[] resumeImageFiles,
            @RequestPart(value = "enResumeFile", required = false) MultipartFile[] enResumeFile,
            @RequestPart(value = "portFolioFiles", required = false) MultipartFile[] portFolioFiles
    ) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foResumeDTO.setResumeImageFiles(resumeImageFiles);
            foResumeDTO.setEnResumeFile(enResumeFile);
            foResumeDTO.setPortFolioFiles(portFolioFiles);
            foResumeService.updateResume(foResumeDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * front 이력서 복사하기
     */
    @PostMapping("/fo/user/resume/resume-copy")
    @ResponseBody
    public ResponseEntity<ResponseVO> resumeCopy(FoResumeDTO foResumeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foResumeService.insertResumeClone(foResumeDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }


    /**
     * 이력서 공개설정
     */
    @GetMapping("/fo/user/resume/resume-setting")
    public String resumeSetting(FoApplicantDTO foApplicantDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("resumeSettingInfo",foResumeService.selectResumeOpened(foApplicantDTO));
        return "fo/user/resume/resume-setting";
    }

    /**
     * 이력서 공개설정
     */
    @PostMapping("/fo/user/resume/resume-restricted")
    @ResponseBody
    public ResponseEntity<ResponseVO> resumeRestricted(FoApplicantDTO foApplicantDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        String serviceCode = "false";
        try {
            foResumeService.updateResumeRestricted(foApplicantDTO);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            FoSessionDTO foSessionDTO = (FoSessionDTO) authentication.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication, foSessionDTO.getUsername()));
            serviceCode = "true";
        } catch(Exception e) {
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(serviceCode).build());
    }

    /**
     * 구직중 공개설정
     */
    @PostMapping("/fo/user/resume/resume-findingJob")
    @ResponseBody
    public ResponseEntity<ResponseVO> resumeFindingJob(FoApplicantDTO foApplicantDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        String serviceCode = "false";
        try {
            foResumeService.updateFindingJob(foApplicantDTO);
            serviceCode = "true";
        } catch(Exception e) {
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(serviceCode).build());
    }

    /**
     * 맞춤채용공고
     */
    @GetMapping("/fo/user/resume/customized-posting")
    public String resumeCustomized(FoResumeDTO foResumeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("customizedPostingList",foResumeService.selectPositionAlert(foResumeDTO));

        return "fo/user/resume/customized-posting";
    }

    /**
     * 맞춤채용공고 저장
     */
    @PostMapping("/fo/user/resume/customized-posting/insert")
    public ResponseEntity<ResponseVO> insertResumeCustomized(FoResumeDTO foResumeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        List<FoResumeDTO> customizedPostingList = new ArrayList<>();
        try {
            foResumeService.insertPositionAlert(foResumeDTO);
            customizedPostingList = foResumeService.selectPositionAlert(foResumeDTO);
        } catch(Exception e) {
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(customizedPostingList).build());
    }

    /**
     * 맞춤채용공고 삭제
     */
    @PostMapping("/fo/user/resume/customized-posting/delete")
    public ResponseEntity<ResponseVO> deleteResumeCustomized(FoResumeDTO foResumeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        List<FoResumeDTO> customizedPostingList = new ArrayList<>();
        try {
            foResumeService.deletePositionAlert(foResumeDTO);
            customizedPostingList = foResumeService.selectPositionAlert(foResumeDTO);
        } catch(Exception e) {
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(customizedPostingList).build());
    }

    /**
     * @description 새로운 인증 생성
     * @param currentAuth 현재 auth 정보
     * @param username	현재 사용자 Id
     * @return Authentication
     * @author Armton
     */
    protected Authentication createNewAuthentication(Authentication currentAuth, String userId) {
        FoSessionDTO foSessionDTO = (FoSessionDTO) foLoginService.loadUserByUsername(userId);
        List<FileDTO> fileList = commonFileService.selectFileDetailList(foSessionDTO.getProfilePictureFileId());
        if(fileList != null) {
            foSessionDTO.setProfileUrl(fileList.get(0).getUrl());
        }
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(foSessionDTO, currentAuth.getCredentials(), foSessionDTO.getAuthorities());
        newAuth.setDetails(currentAuth.getDetails());
        return newAuth;
    }

    /**
     * 기본이력서 변경
     */
    @PostMapping("/fo/user/resume/resume-representation")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateResumeRepresentation(FoResumeDTO foResumeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foResumeService.updateResumeRepresentation(foResumeDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 이력서 삭제
     */
    @PostMapping("/fo/user/resume/resume-delete")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateDeleteResume(FoResumeDTO foResumeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foResumeService.updateDeleteResume(foResumeDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

}