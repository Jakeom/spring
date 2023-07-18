package com.fw.bo.service.status.controller;

import com.fw.bo.service.status.service.StatusService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoStatusDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 유입/이탈현황 (month) 화면
 *
 * @author dongbeom
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class StatusMonthController {

    private final StatusService statusService;

    /**
     * 유입/이탈현황 (month) 화면
     */
    @GetMapping("bo/service/status/month")
    public String Month(ModelMap model, BoStatusDTO boStatusDTO, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("memberCnt", statusService.selectMemberCnt(boStatusDTO));
        model.addAttribute("monthMemberCnt", statusService.selectMonthMemberCnt(boStatusDTO));
        model.addAttribute("resumeCnt", statusService.selectResumeCnt(boStatusDTO));
        model.addAttribute("monthResumeCnt", statusService.selectMonthResumeCnt(boStatusDTO));
        return "bo/service/status/month";
    }

    /**
     * 유입/이탈현황 (month) 검색
     */
    @PostMapping("/bo/service/status/month/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> MonthList(BoStatusDTO boStatusDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(statusService.selectMonthStatus(boStatusDTO)).build());
    }

    /**
     * 유입/이탈현황 (month) 엑셀 다운로드
     */
    @GetMapping("/bo/service/status/month/downloadExcel")
    public void MonthExcelDownload(HttpServletRequest request, HttpServletResponse response, BoStatusDTO boStatusDTO) {
        try{
            statusService.MonthExcelFileDownload(boStatusDTO, response);
        } catch (Exception e) {
            log.info("{}", e);
        }
    }
}
