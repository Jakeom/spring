package com.fw.bo.service.status.service;

import com.fw.core.dto.bo.BoStatusDTO;
import com.fw.core.dto.bo.BoWefirmDTO;
import com.fw.core.mapper.db1.bo.BoStatusMapper;
import com.fw.core.util.ExcelUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusService {

    private final BoStatusMapper boStatusMapper;

    private final Log logger = LogFactory.getLog(getClass());

    public BoStatusDTO selectMemberCnt(BoStatusDTO boStatusDTO) {
        return boStatusMapper.selectMemberCnt(boStatusDTO);
    }

    public BoStatusDTO selectTodayMemberCnt(BoStatusDTO boStatusDTO) {
        return boStatusMapper.selectTodayMemberCnt(boStatusDTO);
    }
    public BoStatusDTO selectMonthMemberCnt(BoStatusDTO boStatusDTO) {
        return boStatusMapper.selectMonthMemberCnt(boStatusDTO);
    }

    public BoStatusDTO selectResumeCnt(BoStatusDTO boStatusDTO) {
        return boStatusMapper.selectResumeCnt(boStatusDTO);
    }

    public BoStatusDTO selectTodayResumeCnt(BoStatusDTO boStatusDTO) {
        return boStatusMapper.selectTodayResumeCnt(boStatusDTO);
    }

    public BoStatusDTO selectMonthResumeCnt(BoStatusDTO boStatusDTO) {
        return boStatusMapper.selectMonthResumeCnt(boStatusDTO);
    }

    public List<BoStatusDTO> selectDayStatus(BoStatusDTO boStatusDTO) {
        List<BoStatusDTO> statusDTOList = new ArrayList<>();
        String start = boStatusDTO.getStartDate();
        String end = boStatusDTO.getEndDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try{
            while(true) {
                boStatusDTO.setDate(end);
                statusDTOList.add(boStatusMapper.selectDayStatus(boStatusDTO));
                if(start.equals(end)) break;
                Date dt = dateFormat.parse(end);
                cal.setTime(dt);
                cal.add(Calendar.DATE, -1);
                end = dateFormat.format(cal.getTime());
            }
        } catch (Exception e) {
            log.info("{}", e);
        }

        return statusDTOList;
    }
    public List<BoStatusDTO> selectMonthStatus(BoStatusDTO boStatusDTO) {
        List<BoStatusDTO> statusDTOList = new ArrayList<>();
        String start = boStatusDTO.getStartDate();
        String end = boStatusDTO.getEndDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        try{
            while(true) {
                boStatusDTO.setDate(end);
                statusDTOList.add(boStatusMapper.selectMonthStatus(boStatusDTO));
                if(start.equals(end)) break;
                Date dt = dateFormat.parse(end);
                cal.setTime(dt);
                cal.add(Calendar.MONTH, -1);
                end = dateFormat.format(cal.getTime());
            }
        } catch (Exception e) {
            log.info("{}", e);
        }

        return statusDTOList;
    }

    public void DayExcelFileDownload(BoStatusDTO vo, HttpServletResponse response) throws Exception {
        List<BoStatusDTO> statusDTOList = new ArrayList<>();
        String start = vo.getStartDate();
        String end = vo.getEndDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try{
            while(true) {
                vo.setDate(end);
                statusDTOList.add(boStatusMapper.selectDayStatus(vo));
                if(start.equals(end)) break;
                Date dt = dateFormat.parse(end);
                cal.setTime(dt);
                cal.add(Calendar.DATE, -1);
                end = dateFormat.format(cal.getTime());
            }

            XSSFWorkbook wb = new XSSFWorkbook();

            // 스타일
            XSSFFont font = wb.createFont();
            XSSFSheet sheet01 = wb.createSheet("Sheet0");
            XSSFRow row = sheet01.createRow(0);

            /* 첫번째 시트 */
            ExcelUtils.createDefaultHeaderCellStyle(row, 0, font, "날짜");
            ExcelUtils.createDefaultHeaderCellStyle(row, 1, font, "AP 가입");
            ExcelUtils.createDefaultHeaderCellStyle(row, 2, font, "AP 탈퇴");
            ExcelUtils.createDefaultHeaderCellStyle(row, 3, font, "이력서 기본");
            ExcelUtils.createDefaultHeaderCellStyle(row, 4, font, "이력서 공개");
            ExcelUtils.createDefaultHeaderCellStyle(row, 5, font, "이력서 비공개");
            ExcelUtils.createDefaultHeaderCellStyle(row, 6, font, "HH 가입");
            ExcelUtils.createDefaultHeaderCellStyle(row, 7, font, "HH 탈퇴");

            int i=1;
            for(BoStatusDTO excelVo : statusDTOList) {
                row = sheet01.createRow(i++);
                ExcelUtils.createDefaultValueCellStyle(row, 0, font, excelVo.getDate());
                ExcelUtils.createDefaultValueCellStyle(row, 1, font, excelVo.getApMemberCnt());
                ExcelUtils.createDefaultValueCellStyle(row, 2, font, excelVo.getQuitApMemberCnt());
                ExcelUtils.createDefaultValueCellStyle(row, 3, font, excelVo.getResumeCnt());
                ExcelUtils.createDefaultValueCellStyle(row, 4, font, excelVo.getOpenResumeCnt());
                ExcelUtils.createDefaultValueCellStyle(row, 5, font, excelVo.getCloseResumeCnt());
                ExcelUtils.createDefaultValueCellStyle(row, 6, font, excelVo.getHhMemberCnt());
                ExcelUtils.createDefaultValueCellStyle(row, 7, font, excelVo.getQuitHhMemberCnt());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
            String today = sdf.format(new Date());
            String filename = URLEncoder.encode("유입_이탈_현황(Day)" + today + ".xlsx", "UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // xlsx
            response.setHeader("Content-disposition", "attachment; filename=" + filename);
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Connection", "close");

            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            logger.error("## PRINT STACK TRACE : ", e);
            logger.error("## EXCEPTION MESSAGE : " + e.getMessage());
            logger.error("## REQUEST PARAMETER : x");
        }
    }

    public void MonthExcelFileDownload(BoStatusDTO vo, HttpServletResponse response) throws Exception {
        List<BoStatusDTO> statusDTOList = new ArrayList<>();
        String start = vo.getStartDate();
        String end = vo.getEndDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        if(!start.equals(end)) {
            while(true) {
                vo.setDate(end);
                statusDTOList.add(boStatusMapper.selectMonthStatus(vo));
                if(start.equals(end)) {
                    break;
                }
                Date dt = dateFormat.parse(end);
                cal.setTime(dt);
                cal.add(Calendar.MONTH, -1);
                end = dateFormat.format(cal.getTime());
            }
        } else {
            vo.setDate(end);
            statusDTOList.add(boStatusMapper.selectMonthStatus(vo));
        }

        try{
            XSSFWorkbook wb = new XSSFWorkbook();
            // 스타일
            XSSFFont font = wb.createFont();
            XSSFSheet sheet01 = wb.createSheet("Sheet0");
            XSSFRow row = sheet01.createRow(0);

            /* 첫번째 시트 */
            ExcelUtils.createDefaultHeaderCellStyle(row, 0, font, "날짜");
            ExcelUtils.createDefaultHeaderCellStyle(row, 1, font, "AP 가입");
            ExcelUtils.createDefaultHeaderCellStyle(row, 2, font, "AP 탈퇴");
            ExcelUtils.createDefaultHeaderCellStyle(row, 3, font, "이력서 기본");
            ExcelUtils.createDefaultHeaderCellStyle(row, 4, font, "이력서 공개");
            ExcelUtils.createDefaultHeaderCellStyle(row, 5, font, "이력서 비공개");
            ExcelUtils.createDefaultHeaderCellStyle(row, 6, font, "HH 가입");
            ExcelUtils.createDefaultHeaderCellStyle(row, 7, font, "HH 탈퇴");

            int i=1;
            for(BoStatusDTO excelVo : statusDTOList) {
                row = sheet01.createRow(i++);
                ExcelUtils.createDefaultValueCellStyle(row, 0, font, excelVo.getDate());
                ExcelUtils.createDefaultValueCellStyle(row, 1, font, excelVo.getApMemberCnt());
                ExcelUtils.createDefaultValueCellStyle(row, 2, font, excelVo.getQuitApMemberCnt());
                ExcelUtils.createDefaultValueCellStyle(row, 3, font, excelVo.getResumeCnt());
                ExcelUtils.createDefaultValueCellStyle(row, 4, font, excelVo.getOpenResumeCnt());
                ExcelUtils.createDefaultValueCellStyle(row, 5, font, excelVo.getCloseResumeCnt());
                ExcelUtils.createDefaultValueCellStyle(row, 6, font, excelVo.getHhMemberCnt());
                ExcelUtils.createDefaultValueCellStyle(row, 7, font, excelVo.getQuitHhMemberCnt());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
            String today = sdf.format(new Date());
            String filename = URLEncoder.encode("유입_이탈_현황(Month)" + today + ".xlsx", "UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // xlsx
            response.setHeader("Content-disposition", "attachment; filename=" + filename);
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Connection", "close");

            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            logger.error("## PRINT STACK TRACE : ", e);
            logger.error("## EXCEPTION MESSAGE : " + e.getMessage());
            logger.error("## REQUEST PARAMETER : x");
        }
    }

}
