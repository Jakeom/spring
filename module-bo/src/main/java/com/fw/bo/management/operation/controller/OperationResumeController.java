package com.fw.bo.management.operation.controller;

import com.fw.bo.management.operation.service.OperationResumeService;
import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.CommonDTO;
import com.fw.core.dto.bo.BoHhResumeFormDTO;
import com.fw.core.dto.bo.BoParsingErrorAcceptDTO;
import com.fw.core.dto.bo.BoResumeDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 이력서 관리 Controller
 * 
 * @author dongbeom
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OperationResumeController {

    private final CommonService commonService;
    private final OperationResumeService operationResumeService;

    /**
     * front 이력서 상세보기
     */
    @GetMapping("/bo/management/operation/resume/status/{id}")
    public String resumeInfo(BoResumeDTO boResumeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        boResumeDTO.setResumeId(boResumeDTO.getId());
        model.addAttribute("resumeInfo",operationResumeService.selectResumeInfo(boResumeDTO));
        model.addAttribute("academicInfo",operationResumeService.selectAcademicInfo(boResumeDTO));
        model.addAttribute("careerInfo",operationResumeService.selectCareerInfo(boResumeDTO));
        model.addAttribute("specInfo",operationResumeService.selectSpecInfo(boResumeDTO));
        model.addAttribute("desiredLocationInfo",operationResumeService.selectDesiredLocationInfo(boResumeDTO));
        model.addAttribute("languageInfo",operationResumeService.selectLanguageInfo(boResumeDTO));
        model.addAttribute("licenseInfo",operationResumeService.selectLicenseInfo(boResumeDTO));
        model.addAttribute("awardInfo",operationResumeService.selectAwardInfo(boResumeDTO));
        model.addAttribute("activityInfo",operationResumeService.selectActivityInfo(boResumeDTO));
        model.addAttribute("portfolioInfo",operationResumeService.selectPortfolioInfo(boResumeDTO));
        model.addAttribute("englishResumeInfo",operationResumeService.selectEnglishResumeInfo(boResumeDTO));
        return "/bo/management/operation/resume/status/detail";
    }

    /**
     * front 이력서 상세보기
     */
    @GetMapping("/bo/management/operation/resume/status/resume-edit/{id}")
    public String resumeEdit(BoResumeDTO boResumeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        boResumeDTO.setResumeId(boResumeDTO.getId());
        model.addAttribute("resumeInfo",operationResumeService.selectResumeInfo(boResumeDTO));
        model.addAttribute("academicInfo",operationResumeService.selectAcademicInfo(boResumeDTO));
        model.addAttribute("careerInfo",operationResumeService.selectCareerInfo(boResumeDTO));
        model.addAttribute("specInfo",operationResumeService.selectSpecInfo(boResumeDTO));
        model.addAttribute("desiredLocationInfo",operationResumeService.selectDesiredLocationInfo(boResumeDTO));
        model.addAttribute("languageInfo",operationResumeService.selectLanguageInfo(boResumeDTO));
        model.addAttribute("licenseInfo",operationResumeService.selectLicenseInfo(boResumeDTO));
        model.addAttribute("awardInfo",operationResumeService.selectAwardInfo(boResumeDTO));
        model.addAttribute("activityInfo",operationResumeService.selectActivityInfo(boResumeDTO));
        model.addAttribute("portfolioInfo",operationResumeService.selectPortfolioInfo(boResumeDTO));
        model.addAttribute("englishResumeInfo",operationResumeService.selectEnglishResumeInfo(boResumeDTO));
        return "/bo/management/operation/resume/status/resume-edit";
    }

    /**
     * 이력서 현황 페이지
     */
    @GetMapping("bo/management/operation/resume/status")
    public String status(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "bo/management/operation/resume/status";
    }

    @GetMapping("bo/management/operation/resume/request-regist-resume/resume-write/{id}")
    public String resumeWrite(ModelMap model, BoParsingErrorAcceptDTO boParsingErrorAcceptDTO, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("detail", operationResumeService.selectParsingErrorAcceptList(boParsingErrorAcceptDTO));
        return "bo/management/operation/resume/request_regist_resume/resume-write";
    }

    /**
     * 오류신고 페이지
     */
    @GetMapping("bo/management/operation/resume/parsing-error")
    public String parsingError (ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "bo/management/operation/resume/parsing-error";
    }

    /**
     * 이력서 등록신청 페이지
     */
    @GetMapping("bo/management/operation/resume/request-regist-resume")
    public String requestRegistResume(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "bo/management/operation/resume/request-regist-resume";
    }

    /**
     * 전용이력서 등록신청 페이지
     */
    @GetMapping("bo/management/operation/resume/resume-form")
    public String resumeForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "bo/management/operation/resume/resume-form";
    }

    /**
     * 이력서 현황 리스트 
     */
    @GetMapping("/bo/management/operation/resume/status/list")
    public @ResponseBody ResponseEntity<ResponseVO> selectResumeList(BoResumeDTO boResumeDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;

        Map<String, Object> map = new HashMap<>();
        boResumeDTO.setTotalCount(operationResumeService.selectResumeListCnt(boResumeDTO));
        boResumeDTO.setPage((boResumeDTO.getStart() / boResumeDTO.getLength()) + 1);
        boResumeDTO.setRowSize(boResumeDTO.getLength());
        boResumeDTO.setRecordsTotal(boResumeDTO.getTotalCount());

        List<BoResumeDTO> list = operationResumeService.selectResumeList(boResumeDTO);
        map.put("draw", boResumeDTO.getDraw());
        map.put("recordsTotal", boResumeDTO.getTotalCount());
        map.put("recordsFiltered", list.size());
        map.put("data", list);

        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(map).build());
    }

    /**
     * 오류신고 리스트
     */
    @GetMapping("/bo/management/operation/resume/parsing-error/list")
    public ResponseEntity<ResponseVO> selectParsingErrorList(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(operationResumeService.selectParsingErrorList(boParsingErrorAcceptDTO)).build());
    }

    /**
     * 오류신고 리스트 옵션 조건
     */
    @GetMapping("/bo/management/operation/resume/parsing-error/list/option")
    public ResponseEntity<ResponseVO> selectParsingErrorOptionList(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        if (boParsingErrorAcceptDTO.getAcceptStatus().equals("ALL")) {
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(operationResumeService.selectParsingErrorList(boParsingErrorAcceptDTO)).build());
        } else {
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(operationResumeService.selectParsingErrorOptionList(boParsingErrorAcceptDTO)).build());
        }
    }

    /**
     * 오류신고 리스트 검색 조건
     */
    @GetMapping("/bo/management/operation/resume/parsing-error/list/search")
    public ResponseEntity<ResponseVO> selectParsingErrorOptionSearch(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        if (boParsingErrorAcceptDTO.getAcceptStatus().equals("ALL")) {
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(operationResumeService.selectParsingErrorList(boParsingErrorAcceptDTO)).build());
        } else {
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(operationResumeService.selectParsingErrorOptionList(boParsingErrorAcceptDTO)).build());
        }
    }

    /**
     * 전용이력서 등록신청 리스트
     */
    @GetMapping("/bo/management/operation/resume/resume-form/list")
    public ResponseEntity<ResponseVO> selectHhResumeFormList(BoHhResumeFormDTO boHhResumeFormDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(operationResumeService.selectHhResumeFormList(boHhResumeFormDTO)).build());
    }

    /**
     * 전용이력서 등록신청 리스트 옵션 조건
     */
    @GetMapping("/bo/management/operation/resume/resume-form/list/option")
    public ResponseEntity<ResponseVO> selectHhResumeFormOptionList(BoHhResumeFormDTO boHhResumeFormDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        if(boHhResumeFormDTO.getStatus().equals("ALL")){
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(operationResumeService.selectHhResumeFormList(boHhResumeFormDTO)).build());
        } else {
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(operationResumeService.selectHhResumeFormOptionList(boHhResumeFormDTO)).build());
        }
    }

    /**
     * 전용이력서 등록신청 리스트 검색 조건
     */
    @GetMapping("/bo/management/operation/resume/resume-form/list/search")
    public ResponseEntity<ResponseVO> selectHhResumeFormOptionSearch(BoHhResumeFormDTO boHhResumeFormDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        if (boHhResumeFormDTO.getStatus().equals("ALL")) {
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(operationResumeService.selectHhResumeFormList(boHhResumeFormDTO)).build());
        } else {
            return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(operationResumeService.selectHhResumeFormOptionList(boHhResumeFormDTO)).build());
        }
    }

    @PostMapping("/bo/management/operation/resume/request_regist_resume/list-with-paging")
    public @ResponseBody ResponseEntity<ResponseVO> selectParsingErrorAcceptList(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;

        // Datatable 대응
        Map<String, Object> map = new HashMap<>();
        boParsingErrorAcceptDTO.setTotalCount(operationResumeService.selectParsingErrorAcceptListCnt(boParsingErrorAcceptDTO));
        boParsingErrorAcceptDTO.setPage((boParsingErrorAcceptDTO.getStart() / boParsingErrorAcceptDTO.getLength()) + 1);
        boParsingErrorAcceptDTO.setRowSize(boParsingErrorAcceptDTO.getLength());
        boParsingErrorAcceptDTO.setRecordsTotal(boParsingErrorAcceptDTO.getTotalCount());

        List<BoParsingErrorAcceptDTO> list = operationResumeService.selectParsingErrorAcceptList(boParsingErrorAcceptDTO);
        map.put("draw", boParsingErrorAcceptDTO.getDraw());
        map.put("recordsTotal", boParsingErrorAcceptDTO.getTotalCount());
        map.put("recordsFiltered", list.size());
        map.put("data", list);

        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(map).build());
    }

    /**
     * 전용이력서 등록신청 리스트 승인 처리
     */
    @PostMapping("/bo/management/operation/resume/resume-form/accept/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateResumeFormAcceptStatus(@RequestBody BoHhResumeFormDTO boHhResumeFormDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            operationResumeService.updateResumeFormAcceptStatus(boHhResumeFormDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 전용이력서 등록신청 리스트 거절 처리
     */
    @PostMapping("/bo/management/operation/resume/resume-form/refuse/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateResumeFormRegistImpossibleStatus(@RequestBody BoHhResumeFormDTO boHhResumeFormDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            operationResumeService.updateResumeFormRegistImpossibleStatus(boHhResumeFormDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 이력서 등록신청 이력서 등록 불가 처리
     */
    @PostMapping("/bo/management/operation/resume/request-regist-resumerefuse/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateRefuseRequestRegistResume(@RequestBody BoParsingErrorAcceptDTO boParsingErrorAcceptDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            operationResumeService.updateRefuseRequestRegistResume(boParsingErrorAcceptDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
    
    /**
     * 이메일 발송
     */
    @PostMapping("/bo/management/operation/resume/parsing-error/sendEmail")
    public ResponseEntity<ResponseVO> sendEmail(BoParsingErrorAcceptDTO boParsingErrorAcceptDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            operationResumeService.sendEmail(boParsingErrorAcceptDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }


    /**
     * 고등학교 리스트
     */
    @GetMapping("/bo/common/high-school/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> getHighSchoolList(CommonDTO commonDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectHighSchoolList(commonDTO)).build());
    }

    /**
     * 대학교 리스트
     */
    @GetMapping("/bo/common/university/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> getUniversityList(CommonDTO commonDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectUniversityList(commonDTO)).build());
    }

    /**
     * 나이스 기업정보 리스트
     */
    @GetMapping("/bo/common/nice-company/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> getNiceCompanyList(CommonDTO commonDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectNiceCompanySearchList(commonDTO)).build());
    }


    /**
     * 기업정보 리스트
     */
    @GetMapping("/bo/common/company/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> getCompanyList(CommonDTO commonDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectCompanyList(commonDTO)).build());
    }

    @PostMapping(value = "/bo/user/resume/resume-write", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ResponseVO> resumeWriteInsert(
            @RequestPart(value = "jsonData") BoResumeDTO boResumeDTO,
            @RequestPart(value = "resumeImageFiles", required = false) MultipartFile[] resumeImageFiles,
            @RequestPart(value = "enResumeFile", required = false) MultipartFile[] enResumeFile,
            @RequestPart(value = "portFolioFiles", required = false) MultipartFile[] portFolioFiles, HttpServletRequest request, HttpServletResponse response
    ) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            Map<String, Object> memberMap;
            memberMap = operationResumeService.insertTempApplicant(boResumeDTO);
            if(!memberMap.get("RESULT").equals("SUCCESS")) {
                return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(memberMap.get("RESULT")).build());
            } else {
                boResumeDTO.setJoinMemberId((String) memberMap.get("memberId"));
            }
            boResumeDTO.setResumeImageFiles(resumeImageFiles);
            boResumeDTO.setEnResumeFile(enResumeFile);
            boResumeDTO.setPortFolioFiles(portFolioFiles);
            operationResumeService.insertResume(boResumeDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 이력서 수정하기 DO
     */
    @PostMapping(value = "/bo/management/operation/resume/status/resume-edit/save", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ResponseVO> resumeEditDo(
            @RequestPart(value = "jsonData") BoResumeDTO boResumeDTO,
            @RequestPart(value = "resumeImageFiles", required = false) MultipartFile[] resumeImageFiles,
            @RequestPart(value = "enResumeFile", required = false) MultipartFile[] enResumeFile,
            @RequestPart(value = "portFolioFiles", required = false) MultipartFile[] portFolioFiles, HttpServletRequest request, HttpServletResponse response
    ) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boResumeDTO.setResumeImageFiles(resumeImageFiles);
            boResumeDTO.setEnResumeFile(enResumeFile);
            boResumeDTO.setPortFolioFiles(portFolioFiles);
            operationResumeService.updateResume(boResumeDTO);
        } catch(Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
}