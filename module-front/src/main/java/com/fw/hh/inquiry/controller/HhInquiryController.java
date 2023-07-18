package com.fw.hh.inquiry.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.hh.HhInquiryDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.hh.inquiry.service.HhInquiryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 1:1 문의 Controller
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HhInquiryController {

	private final HhInquiryService hhInquiryService;
	private final CommonFileService commonFileService;

	/**
	 * 1:1 문의 페이지
	 */
	@GetMapping("/hh/service/inquiry")
	public String inquiry(HhInquiryDTO hhInquiryDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		hhInquiryDTO.setTotalCount(hhInquiryService.selectInquiryListCntForPaging(hhInquiryDTO));
		hhInquiryDTO.setRowSize(5);
		model.addAttribute("inquiryList", hhInquiryService.selectInquiryListPaging(hhInquiryDTO));
		model.addAttribute("searchInfo", hhInquiryDTO);
		return "hh/service/inquiry";
	}

	/**
	 * 1:1 문의 상세보기
	 */
	@GetMapping("/hh/service/inquiry/detail")
	public String noticeDetail(HhInquiryDTO hhInquiryDTO, ModelMap model, HttpServletRequest request, HttpSession session) {
		HhInquiryDTO fqd = hhInquiryService.selectInquiryDetail(hhInquiryDTO);
		fqd.setCommonFileList(commonFileService.selectFileDetailList(fqd.getFileSeq()));
		model.addAttribute("detail", fqd);
		return "hh/service/inquiry-detail";
	}

	/**
	 * 1:1 문의 등록
	 */
	@PostMapping("/hh/service/inquiry")
	@ResponseBody
	public ResponseEntity<ResponseVO> apply(HhInquiryDTO hhInquiryDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhInquiryService.insertInquiry(hhInquiryDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(hhInquiryDTO.getId()).build());
	}

}