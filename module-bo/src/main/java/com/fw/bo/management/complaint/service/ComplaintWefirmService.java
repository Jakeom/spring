package com.fw.bo.management.complaint.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.bo.BoWefirmDTO;
import com.fw.core.mapper.db1.bo.BoWefirmMapper;
import com.fw.core.util.ExcelUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ComplaintWefirmService {

	private final BoWefirmMapper boWefirmMapper;
	private final CommonFileService commonFileService;

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * 위펌 리스트 취득
	 */
	public List<BoWefirmDTO> selectWefirmList(BoWefirmDTO boWefirmDTO) {
		return boWefirmMapper.selectWefirmList(boWefirmDTO);
	}

	/**
	 * 위펌사항상세 취득
	 */
	public BoWefirmDTO selectWefirmModify(BoWefirmDTO boWefirmDTO) {
		return boWefirmMapper.selectWefirmModify(boWefirmDTO);
	}

	/**
	 * 위펌사항상세 취득
	 */
	public BoWefirmDTO selectWefirmDetail(BoWefirmDTO boWefirmDTO) {
		BoWefirmDTO dto = boWefirmMapper.selectWefirmDetail(boWefirmDTO);
			String fileSeq = dto.getLogoFileId();
			if(!Objects.isNull(fileSeq)){
				if(StringUtils.isNotBlank(fileSeq)){
					dto.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
				}
		}
		return dto;
	}

	/**
	 * 위펌관리 검색
	 */
	public List<BoWefirmDTO> saerchWefirmList(BoWefirmDTO boWefirmDTO) {
		return boWefirmMapper.searchWefirmList(boWefirmDTO);
	}

	/**
	 * 위펌등록
	 */
	public void insertWefirm(BoWefirmDTO boWefirmDTO) {
		boWefirmMapper.insertWefirm(boWefirmDTO);
	}

	/**
	 * 위펌 상태 수정
	 */
	public void updateWefirmStatus(BoWefirmDTO boWefirmDTO) {
		if(boWefirmDTO.getClosed().equals("1")) {
			boWefirmMapper.updateWefirmStatusHH(boWefirmDTO);
			boWefirmMapper.updateHHWefirm(boWefirmDTO);
		} else {
			boWefirmMapper.updateWefirmStatus(boWefirmDTO);
		}
	}

	/**
	 * 위펌수정
	 */
	public void updateWefirm(BoWefirmDTO boWefirmDTO) {
		boWefirmMapper.updateWefirm(boWefirmDTO);
	}

	/**
	 * 위펌삭제
	 */
	public void deleteWefirm(BoWefirmDTO boWefirmDTO) {
		boWefirmMapper.deleteWefirm(boWefirmDTO);
	}

	/**
	 * 위펌상세 페이지
	 * headhunter 상세  리스트
	 */
	public List<BoWefirmDTO> selectHeadhunterDetail(BoWefirmDTO boWefirmDTO) {
		return boWefirmMapper.selectHeadhunterDetail(boWefirmDTO);
	}

	/**
	 * 위펌상세 페이지
	 * 채용 상세 리스트
	 */
	public List<BoWefirmDTO> selectRecruitDetail(BoWefirmDTO boWefirmDTO) {
		return boWefirmMapper.selectRecruitDetail(boWefirmDTO);
	}

	/**
	 * 위펌상세 페이지
	 * Notice 리스트
	 */
	public List<BoWefirmDTO> selectWefirmNotice(BoWefirmDTO boWefirmDTO) {
		return boWefirmMapper.selectWefirmNotice(boWefirmDTO);
	}
	
	/**
	 * 위펌 상태 확인
	 * */
    public List<BoWefirmDTO> saerchWefirmStatus(BoWefirmDTO boWefirmDTO) {
		return boWefirmMapper.selectWefirmStatus(boWefirmDTO);
    }

	/**
	 * 엑셀 다운로드
	 */
	public void ExcelFileDownload(BoWefirmDTO vo, HttpServletResponse response) throws Exception {
		try{
			XSSFWorkbook wb = new XSSFWorkbook();

			// 스타일
			XSSFFont font = wb.createFont();
			XSSFSheet sheet01 = wb.createSheet("Sheet0");
			XSSFRow row = sheet01.createRow(0);

			sheet01.setColumnWidth(1 , 5000);
			sheet01.setColumnWidth(3 , 5000);
			sheet01.setColumnWidth(4 , 7000);
			sheet01.setColumnWidth(6 , 4000);

			/* 첫번째 시트 */
			ExcelUtils.createDefaultHeaderCellStyle(row, 0, font, "No");
			ExcelUtils.createDefaultHeaderCellStyle(row, 1, font, "We펌 가입 일시");
			ExcelUtils.createDefaultHeaderCellStyle(row, 2, font, "이름");
			ExcelUtils.createDefaultHeaderCellStyle(row, 3, font, "연락처");
			ExcelUtils.createDefaultHeaderCellStyle(row, 4, font, "이메일");
			ExcelUtils.createDefaultHeaderCellStyle(row, 5, font, "진행중 공고");
			ExcelUtils.createDefaultHeaderCellStyle(row, 6, font, "이력서 등록수");
			ExcelUtils.createDefaultHeaderCellStyle(row, 7, font, "비고");

			List<BoWefirmDTO> list = boWefirmMapper.selectHeadhunterDetail(vo);

			int i=1;
			for(BoWefirmDTO excelVo : list) {
				row = sheet01.createRow(i++);
				ExcelUtils.createDefaultValueCellStyle(row, 0, font, excelVo.getMemberId());
				ExcelUtils.createDefaultValueCellStyle(row, 1, font, excelVo.getCreatedAt());
				ExcelUtils.createDefaultValueCellStyle(row, 2, font, excelVo.getMemberName());
				ExcelUtils.createDefaultValueCellStyle(row, 3, font, excelVo.getMemberPhone());
				ExcelUtils.createDefaultValueCellStyle(row, 4, font, excelVo.getMemberEmail());
				ExcelUtils.createDefaultValueCellStyle(row, 5, font, excelVo.getPositionCnt());
				ExcelUtils.createDefaultValueCellStyle(row, 6, font, excelVo.getResumeCnt());
				ExcelUtils.createDefaultValueCellStyle(row, 7, font, "-");
			}
			BoWefirmDTO wefirmDetail = boWefirmMapper.selectWefirmDetail(vo);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
			String today = sdf.format(new Date());
			String filename = URLEncoder.encode(wefirmDetail.getSfName() + "_소속헤드헌터_" + today + ".xlsx", "UTF-8");
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
