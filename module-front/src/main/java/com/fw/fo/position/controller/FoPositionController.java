package com.fw.fo.position.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoBlacklistHhDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.fo.headhunter.FoSearchHhDTO;
import com.fw.core.dto.fo.FoPositionDTO;
import com.fw.core.dto.fo.FoPositionApplicantDTO;
import com.fw.core.dto.fo.resume.FoHhReadingHistoryDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.position.service.FoPositionService;
import com.fw.fo.resume.service.FoResumeService;
import com.fw.fo.headhunter.service.FoHeadhunterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 로그인 Controller
 * @author jung
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoPositionController {

    private final FoPositionService foPositionService;
    private final FoResumeService foResumeService;
    private final FoHeadhunterService foSearchHhService;

    /**
     * 채용공고 메인
     */
    @GetMapping("/fo/position/position-list")
    public String posting(FoPositionDTO foPositionDTO, ModelMap model, HttpServletRequest request) {
        foPositionDTO.setTotalCount(foPositionService.selectPositionListCntForPaging(foPositionDTO));
        model.addAttribute("positionList",foPositionService.selectPositionListPaging(foPositionDTO));
        model.addAttribute("searchInfo", foPositionDTO);
        return "fo/position/position-list";
    }

    /**
     * index 채용공고 메인
     */
    @GetMapping("/fo/main/position/position-list")
    public String postingMain(FoPositionDTO foPositionDTO, ModelMap model, HttpServletRequest request) {
        foPositionDTO.setTotalCount(foPositionService.selectPositionListCntForPaging(foPositionDTO));
        model.addAttribute("positionList",foPositionService.selectPositionListPaging(foPositionDTO));
        foPositionDTO.setSearchVal("");
        model.addAttribute("searchInfo", foPositionDTO);
        return "fo/position/position-list";
    }

    /**
     * 헤드헌터 연락처 보기
     */
    @PostMapping("/fo/position/hh-reading")
    @ResponseBody
    public ResponseEntity<ResponseVO> reading(FoHhReadingHistoryDTO foHhReadingHistoryDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foPositionService.insertHhReadingHistory(foHhReadingHistoryDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 헤드헌터 블랙리스트 추가
     */
    @PostMapping("/fo/position/hh-black")
    @ResponseBody
    public ResponseEntity<ResponseVO> black(FoBlacklistHhDTO foBlacklistHhDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foPositionService.insertBlacklistHh(foBlacklistHhDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 채용공고 상세보기
     */
    @GetMapping("/fo/user/jop/posting/detail")
    public String postingDetail(ModelMap model, HttpServletRequest request, HttpSession session, FoPositionDTO foJopDTO, RedirectAttributes redirectAttributes) {
        model.addAttribute("searchInfo", foJopDTO);
        model.addAttribute("postingInfo",foPositionService.selectPositionInfo(foJopDTO));
        return "fo/user/jop/posting-detail";
    }

    /**
     * 채용공고 지원하기
     */
    @GetMapping("/fo/user/jop/posting/apply")
    public String postingApply(ModelMap model, FoPositionDTO foJopDTO, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        FoSessionDTO foSessionDTO = foJopDTO.getFrontSession();
        FoResumeDTO foResumeDTO = new FoResumeDTO();
        foResumeDTO.setFrontSession(foSessionDTO);
        //foResumeDTO.setMemberId(frontSessionDTO.getId());
        model.addAttribute("postingInfo",foPositionService.selectPositionInfo(foJopDTO));
        model.addAttribute("resumeList",foResumeService.selectResumeList(foResumeDTO));
        return "fo/user/jop/posting_apply";
    }

    /**
     * 이력서 선택
     */
    @PostMapping("/fo/user/jop/posting/apply")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectResume(ModelMap model, HttpServletRequest request, HttpSession session, FoResumeDTO foResumeDTO, RedirectAttributes redirectAttributes) {
        ResponseCode code = ResponseCode.SUCCESS;
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("resumeInfo",foResumeService.selectResumeInfo(foResumeDTO));
        dataMap.put("academicInfo",foResumeService.selectAcademicInfo(foResumeDTO));
        dataMap.put("careerInfo",foResumeService.selectCareerInfo(foResumeDTO));
        dataMap.put("specInfo",foResumeService.selectSpecInfo(foResumeDTO));
        dataMap.put("desiredLocationInfo",foResumeService.selectDesiredLocationInfo(foResumeDTO));
        dataMap.put("languageInfo",foResumeService.selectLanguageInfo(foResumeDTO));
        dataMap.put("licenseInfo",foResumeService.selectLicenseInfo(foResumeDTO));
        dataMap.put("awardInfo",foResumeService.selectAwardInfo(foResumeDTO));
        dataMap.put("activityInfo",foResumeService.selectActivityInfo(foResumeDTO));
        dataMap.put("portfolioInfo",foResumeService.selectPortfolioInfo(foResumeDTO));
        dataMap.put("englishResumeInfo",foResumeService.selectEnglishResumeInfo(foResumeDTO));
        try {

        } catch(Exception e) {
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(dataMap).build());
    }

    /**
     * 지원하기 submit
     */
    @PostMapping("/fo/user/jop/posting/doApply")
    @ResponseBody
    public ResponseEntity<ResponseVO> apply(ModelMap model, HttpServletRequest request, HttpSession session, FoPositionApplicantDTO foPoApplicantDTO, RedirectAttributes redirectAttributes) {
        ResponseCode code = ResponseCode.SUCCESS;
        foPoApplicantDTO.setPhoneOpened("0");
        foPoApplicantDTO.setPmSubmitted("0");
        foPoApplicantDTO.setProgressStep("CONTACT");
        foPoApplicantDTO.setReceiptPath("DIRECT");
        foPoApplicantDTO.setProposedEamil("0");
        foPoApplicantDTO.setProposedSms("0");
        foPoApplicantDTO.setRegPath("2");
        foPoApplicantDTO.setUpdateRequest("0");
        foPoApplicantDTO.setApMemberId(foPoApplicantDTO.getFrontSession().getId());

        FoPositionDTO foPositionDTO = new FoPositionDTO();
        foPositionDTO.setId(foPoApplicantDTO.getPositionId());
        FoPositionDTO resultDTO = foPositionService.selectPositionInfo(foPositionDTO);
        foPoApplicantDTO.setHhMemberId(resultDTO.getMemberId());

        try {
            //기본이력서일경우 복사후 지원
            if(StringUtils.equals("1",foPoApplicantDTO.getRepresentation())){
                FoResumeDTO foResumeDTO = new FoResumeDTO();
                foResumeDTO.setFrontSession(foPoApplicantDTO.getFrontSession());
                foResumeDTO.setId(foPoApplicantDTO.getResumeId());
                foResumeDTO.setRepresentation("0");
                foResumeService.insertResumeClone(foResumeDTO);
                foPoApplicantDTO.setResumeId(foResumeService.selectLastResume(foResumeDTO).getId());
            }
            foPositionService.insertPositionApplicant(foPoApplicantDTO);
        } catch(Exception e) {
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(foPoApplicantDTO.getId()).build());
    }

    /**
     * 헤드헌터보기
     */
    @GetMapping("/fo/position/headhunter-info")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectHhInfo(FoSearchHhDTO foSearchHhDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("hhInfo", foSearchHhService.selectHhInfo(foSearchHhDTO));
        dataMap.put("hhFieldInfo", foSearchHhService.selectHhFieldInfo(foSearchHhDTO));
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(dataMap).build());
    }

    /**
     * 헤드헌터 채용공고
     */
    @GetMapping("/fo/user/jop/posting-hunter")
    public String postingHh(FoPositionDTO foJopDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        foJopDTO.setTotalCount(foPositionService.selectPositionListCntForPaging(foJopDTO));
        model.addAttribute("positionList",foPositionService.selectPositionListPaging(foJopDTO));
        model.addAttribute("searchInfo",foJopDTO);
        return "fo/user/jop/posting-hunter-search";
    }

    /**
     * 이력서 수정 동의
     */
    @PostMapping("/fo/user/jop/posting/editResumeAgree")
    @ResponseBody
    public ResponseEntity<ResponseVO> editResumeAgree(ModelMap model, HttpServletRequest request, HttpSession session, FoPositionApplicantDTO foPoApplicantDTO, RedirectAttributes redirectAttributes) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            foPositionService.updateApplicant(foPoApplicantDTO);
        } catch(Exception e) {
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 스크랩 하기, 취소
     */
    @PostMapping("/fo/position/scrap")
    @ResponseBody
    public ResponseEntity<ResponseVO> scrap(ModelMap model, FoPositionDTO foJopDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            if(foJopDTO.getScrap().equals("addScrap")){
                foPositionService.insertScrap(foJopDTO);
            }else if(foJopDTO.getScrap().equals("delete")){
                foPositionService.updateScrap(foJopDTO);
            }
        } catch(Exception e) {
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 지원여부
     */
    @PostMapping("/fo/position/check-applicant")
    @ResponseBody
    public ResponseEntity<ResponseVO> checkApplicant(ModelMap model, FoPositionDTO foJopDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        int cnt = 0;
        try {
           cnt = foPositionService.selectApplicant(foJopDTO);
        } catch(Exception e) {
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(cnt).build());
    }
}