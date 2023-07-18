package com.fw.m.resume.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoPointDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import com.fw.core.dto.hh.HhPositionDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.resume.service.FoResumeService;
import com.fw.hh.position.service.HhPositionService;
import com.fw.m.resume.service.MResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

/**
 * 웹뷰 이력서 controller
 * @author YJW
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class MResumeController {

    private final FoResumeService foResumeService;
    private final MResumeService mResumeService;
    private final HhPositionService hhPositionService;

    @Value("${service.report_domain}")
    private String REPORT_DOMAIN;

    /**
     * front 이력서 상세보기 (headhunter)
     */
    @GetMapping("/m/resume/resume-detail-hh")
    public String resumeInfoHh(FoResumeDTO foResumeDTO, FoPointDTO foPointDTO, ModelMap model) {
        // FrontSession 사용
        FoSessionDTO session = new FoSessionDTO();
        session.setId(foResumeDTO.getMemberId());

        foResumeDTO.setId(foResumeDTO.getResumeId());
        foResumeDTO.setRegisterPathCd("POINT");
        boolean ticketExist = mResumeService.selectTicketExist(foResumeDTO); // 열람권 존재유무
        String expireFlag ="";
        if(ticketExist) { // 열람권 존재 시 만료여부 체크
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String now = dateFormat.format(new Date());
            FoResumeDTO ticketInfo = mResumeService.selectTicketInfo(foResumeDTO);
            if(ticketInfo.getExpireAt().compareTo(now) <= 0) { // 만료된 경우
                expireFlag = "Y";
            } else { // 만료되지 않은 경우
                expireFlag = "N";
            }
        } else { // 열람권 미존재 시 만료여부 Y
                expireFlag = "Y";
        }

        // 헤드헌터 진행중 포지션 리스트 조회
        HhPositionDTO hhPositionDTO = new HhPositionDTO();

        hhPositionDTO.setFrontSession(session);
        hhPositionDTO.setStatusList(Collections.singletonList("DOING"));

        model.addAttribute("expireFlag",expireFlag);
        model.addAttribute("parameter", foResumeDTO);
        model.addAttribute("point",mResumeService.selectMyPoint(foPointDTO));
        model.addAttribute("resumeInfo",foResumeService.selectResumeInfo(foResumeDTO));
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
        model.addAttribute("doingPositionList",hhPositionService.selectHhPositionList(hhPositionDTO));
        model.addAttribute("reportDomain", REPORT_DOMAIN);

        return "m/resume/resume-detail-hh";
    }

    /**
     * 포인트차감 -> 연락처보기
     */
    @PostMapping("/m/update/point")
    @ResponseBody
    public ResponseEntity<ResponseVO> updatePoint(@RequestBody FoPointDTO foPointDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
             foPointDTO.setType("SHOW_PHONE"); // type - 연락처보기
             foPointDTO.setAmount(2000); // 차감포인트 2000p
             mResumeService.updatePoint(foPointDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * front 이력서 상세보기 (AP)
     */
    @GetMapping("/m/resume/resume-detail-ap")
    public String resumeInfoAp(FoResumeDTO foResumeDTO, ModelMap model) {

        foResumeDTO.setId(foResumeDTO.getResumeId());
        model.addAttribute("applicantId",foResumeDTO.getApplicantId());
        model.addAttribute("searchInfo", foResumeDTO);
        model.addAttribute("resumeInfo",foResumeService.selectResumeInfo(foResumeDTO));
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
        model.addAttribute("reportDomain", REPORT_DOMAIN);

        return "m/resume/resume-detail-ap";
    }

    /**
     * 내인재삭제
     */
    @PostMapping("/m/delete/my-pool")
    @ResponseBody
    public ResponseEntity<ResponseVO> deleteMyPool(@RequestBody FoResumeDTO foResumeDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            mResumeService.deleteMyPool(foResumeDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
	 * 이력서 수정
	 */
	@GetMapping("/m/resume/resume-update")
	public String resumeUpdate(ModelMap model, FoResumeDTO foResumeDTO) {

        foResumeDTO.setId(foResumeDTO.getResumeId());
		
        model.addAttribute("applicantId",foResumeDTO.getApplicantId());
        model.addAttribute("resumeInfo",foResumeService.selectResumeInfo(foResumeDTO));
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
		return "m/resume/resume-update";
	}

    /**
     * 이력서 insert
     */
    @PostMapping(value = "/m/resume/resumeModifyFromHh", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ResponseVO> resumeModifyFromHh(Authentication authentication,
                                                        @RequestPart(value = "jsonData") FoResumeDTO foResumeDTO,
                                                        @RequestPart(value = "resumeImageFiles", required = false) MultipartFile[] resumeImageFiles,
                                                        @RequestPart(value = "enResumeFile", required = false) MultipartFile[] enResumeFile,
                                                        @RequestPart(value = "portFolioFiles", required = false) MultipartFile[] portFolioFiles
                                                        ) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {

            FoSessionDTO foSessionDTO = new FoSessionDTO();
            foSessionDTO.setId("4328");
            foResumeDTO.setFrontSession(foSessionDTO);

            foResumeDTO.setCreateMemberId(foResumeDTO.getFrontSession().getId());
            foResumeDTO.setResumeImageFiles(resumeImageFiles);
            foResumeDTO.setEnResumeFile(enResumeFile);
            foResumeDTO.setPortFolioFiles(portFolioFiles);
            foResumeDTO.setRepresentation("0");
            foResumeDTO = mResumeService.updateResume(foResumeDTO);
            
            
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(foResumeDTO.getId()).build());
    }
}