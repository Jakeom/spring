package com.fw.fo.application.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoPositionApplicantDTO;
import com.fw.core.dto.fo.FoPositionDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.application.service.FoApplicationService;
import com.fw.fo.position.service.FoPositionService;
import com.fw.fo.resume.service.FoResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 지원현황 Controller
 * @author jung
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class FoApplicationController {
    private final FoApplicationService foApplicationService;
    private final FoPositionService foPositionService;
    private final FoResumeService foResumeService;

    @Value("${service.report_domain}")
    private String REPORT_DOMAIN;

    /**
     * front 메인 지원현황
     */
    @GetMapping("/fo/mypage/application-status")
    public String application(FoPositionApplicantDTO foPoApplicantDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        foPoApplicantDTO.setApMemberId(foPoApplicantDTO.getFrontSession().getId());

        foPoApplicantDTO.setMode("APPLICATION_STATUS");
        foPoApplicantDTO.setTotalCount(foApplicationService.selectApplicationListCntForPaging(foPoApplicantDTO));
        model.addAttribute("positionList",foApplicationService.selectApplicationListPaging(foPoApplicantDTO));

        foPoApplicantDTO.setMode("JOB_POSITION");
        foPoApplicantDTO.setTotalProposalCnt(foApplicationService.selectApplicationListCntForPaging(foPoApplicantDTO));

        foPoApplicantDTO.setMode("PAST_SUPPORT");
        foPoApplicantDTO.setSelectCondition("PASS");
        foPoApplicantDTO.setTotalPassCnt(foApplicationService.selectApplicationListCntForPaging(foPoApplicantDTO));
        model.addAttribute("searchInfo",foPoApplicantDTO);
        return "fo/user/application/application-status";
    }
    /**
     * front 지원현황 최종합격
     */
    @GetMapping("/fo/user/application/application-pass")
    public String applicationPass(FoPositionApplicantDTO foPoApplicantDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        foPoApplicantDTO.setApMemberId(foPoApplicantDTO.getFrontSession().getId());
        foPoApplicantDTO.setProposalStatus("ACCEPT");
        foPoApplicantDTO.setTotalCount(foApplicationService.selectApplicationListCntForPaging(foPoApplicantDTO));
        foPoApplicantDTO.setProposalStatus("PROPOSAL");//받은 포지션 제안
        foPoApplicantDTO.setTotalProposalCnt(foApplicationService.selectApplicationListCntForPaging(foPoApplicantDTO));
        foPoApplicantDTO.setProposalStatus("");
        foPoApplicantDTO.setProgressStep("PASS");//최종합격
        foPoApplicantDTO.setTotalPassCnt(foApplicationService.selectApplicationListCntForPaging(foPoApplicantDTO));
        model.addAttribute("searchInfo",foPoApplicantDTO);
        model.addAttribute("passPositionList",foApplicationService.selectApplicationListPaging(foPoApplicantDTO));
        return "fo/user/application/application-pass";
    }

    /**
     * 포지션 제안내역
     */
    @GetMapping("/fo/user/application/job-position")
    public String jopPosition(FoPositionApplicantDTO foPoApplicantDTO,ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        foPoApplicantDTO.setApMemberId(foPoApplicantDTO.getFrontSession().getId());
        foPoApplicantDTO.setMode("JOB_POSITION");
        foPoApplicantDTO.setTotalCount(foApplicationService.selectApplicationListCntForPaging(foPoApplicantDTO));
        model.addAttribute("searchInfo",foPoApplicantDTO);
        model.addAttribute("positionList",foApplicationService.selectApplicationListPaging(foPoApplicantDTO));
        return "fo/user/application/job-position";
    }

    /**
     * 제안수락 페이지
     */
    @GetMapping("/fo/user/application/proposal-accept")
    public String proposalAceept(FoPositionApplicantDTO foPoApplicantDTO,ModelMap model) {

        FoPositionDTO foPositionDTO = new FoPositionDTO();
        foPositionDTO.setId(foPoApplicantDTO.getPositionId());
        foPositionDTO.setFrontSession(foPoApplicantDTO.getFrontSession());
        model.addAttribute("postingInfo",foPositionService.selectPositionInfo(foPositionDTO));

        FoResumeDTO foResumeDTO = new FoResumeDTO();
        foResumeDTO.setFrontSession(foPoApplicantDTO.getFrontSession());
        model.addAttribute("resumeList",foResumeService.selectResumeList(foResumeDTO));
        model.addAttribute("applicantInfo", foApplicationService.selectPositionApplicantInfo(foPoApplicantDTO));
        model.addAttribute("searchInfo", foPoApplicantDTO);

        return "fo/user/application/proposal-accept";
    }

    /**
     * 포지션 제안 거절/수락
     */
    @PostMapping("/fo/user/application/proposal-acceptance")
    @ResponseBody
    public ResponseEntity<ResponseVO> acceptance(@RequestBody FoPositionApplicantDTO foPoApplicantDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            log.info("acceptance >>> {}",foPoApplicantDTO.getAcceptance());
            log.info("applicantId >>> {}",foPoApplicantDTO.getApplicantId());
            //기본이력서일경우 복사후 지원
            if(StringUtils.equals("1",foPoApplicantDTO.getRepresentation())){
                FoResumeDTO foResumeDTO = new FoResumeDTO();
                foResumeDTO.setFrontSession(foPoApplicantDTO.getFrontSession());
                foResumeDTO.setId(foPoApplicantDTO.getResumeId());
                foResumeDTO.setRepresentation("0");
                foResumeService.insertResumeClone(foResumeDTO);
                foPoApplicantDTO.setResumeId(foResumeService.selectLastResume(foResumeDTO).getId());
            }
            foApplicationService.updateProposalAceeptance(foPoApplicantDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }



    /**
     * 내 이력서 열람내역
     */
    @GetMapping("/fo/user/application/job-browse")
    public String jopBrowse(FoPositionApplicantDTO foPoApplicantDTO,ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        foPoApplicantDTO.setApMemberId(foPoApplicantDTO.getFrontSession().getId());
        foPoApplicantDTO.setTotalCount(foApplicationService.selectResumeReadingHistoryCntForPaging(foPoApplicantDTO));
        model.addAttribute("searchInfo",foPoApplicantDTO);
        model.addAttribute("positionList",foApplicationService.selectResumeReadingHistoryPaging(foPoApplicantDTO));
        return "fo/user/application/job-browse";
    }

    /**
     * 과거 지원 결과
     */
    @GetMapping("/fo/user/application/past-support")
    public String pastSupport(FoPositionApplicantDTO foPoApplicantDTO,ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        foPoApplicantDTO.setApMemberId(foPoApplicantDTO.getFrontSession().getId());
        foPoApplicantDTO.setMode("PAST_SUPPORT");
        foPoApplicantDTO.setTotalCount(foApplicationService.selectApplicationListCntForPaging(foPoApplicantDTO));
        model.addAttribute("searchInfo",foPoApplicantDTO);
        model.addAttribute("positionList",foApplicationService.selectApplicationListPaging(foPoApplicantDTO));
        return "fo/user/application/past-support";

    }

    /**
     * 취업활동 증명서 발급
     */
    @GetMapping("/fo/user/application/employment-certificate")
    public String employmentCertificate(FoPositionApplicantDTO foPoApplicantDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        foPoApplicantDTO.setApMemberId(foPoApplicantDTO.getFrontSession().getId());
        foPoApplicantDTO.setMode("EMPLOYMENT_CERTIFICATE");
        foPoApplicantDTO.setTotalCount(foApplicationService.selectApplicationListCntForPaging(foPoApplicantDTO));
        foPoApplicantDTO.setTotalPassCnt(foApplicationService.selectApplicationListCntForPaging(foPoApplicantDTO));
        model.addAttribute("searchInfo",foPoApplicantDTO);
        model.addAttribute("positionList",foApplicationService.selectApplicationListPaging(foPoApplicantDTO));
        model.addAttribute("reportDomain", REPORT_DOMAIN);
        return "/fo/user/application/employment-certificate";
    }

}