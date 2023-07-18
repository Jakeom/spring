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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 유입/이탈현황 (day) 화면
 *
 * @author dongbeom
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class StatusDayController {

    private final StatusService statusService;

    /**
     * 유입/이탈현황 day 화면
     */
    @GetMapping("/bo/service/status/day")
    public String Day(ModelMap model, BoStatusDTO boStatusDTO, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("memberCnt", statusService.selectMemberCnt(boStatusDTO));
        model.addAttribute("toDayMemberCnt", statusService.selectTodayMemberCnt(boStatusDTO));
        model.addAttribute("resumeCnt", statusService.selectResumeCnt(boStatusDTO));
        model.addAttribute("toDayResumeCnt", statusService.selectTodayResumeCnt(boStatusDTO));

        return "/bo/service/status/day";
    }

    /**
     * 유입/이탈현황 검색
     */
    @PostMapping("/bo/service/status/day/search")
    @ResponseBody
    public ResponseEntity<ResponseVO> DayList(BoStatusDTO boStatusDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).data(statusService.selectDayStatus(boStatusDTO)).build());
    }

    /**
     * 유입/이탈현황 day 엑셀 다운로드
     */
    @GetMapping("/bo/service/status/day/downloadExcel")
    public void DayExcelDownload(HttpServletRequest request, HttpServletResponse response, BoStatusDTO boStatusDTO) {
        try{
            statusService.DayExcelFileDownload(boStatusDTO, response);
        } catch (Exception e) {
            log.info("{}", e);
        }
    }

}
