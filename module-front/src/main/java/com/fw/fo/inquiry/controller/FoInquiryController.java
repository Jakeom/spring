package com.fw.fo.inquiry.controller;

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
import com.fw.core.dto.fo.inquiry.FoInquiryDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.inquiry.service.FoInquiryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 1:1 문의 Controller
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoInquiryController {

	private final FoInquiryService foInquiryService;
	private final CommonFileService commonFileService;

	/**
	 * 1:1 문의 페이지
	 */
	@GetMapping("/fo/service/inquiry")
	public String inquiry(FoInquiryDTO foInquiryDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		foInquiryDTO.setTotalCount(foInquiryService.selectInquiryListCntForPaging(foInquiryDTO));
		foInquiryDTO.setRowSize(5);
		model.addAttribute("inquiryList", foInquiryService.selectInquiryListPaging(foInquiryDTO));
		model.addAttribute("searchInfo", foInquiryDTO);
		return "fo/service/inquiry";
	}

	/**
	 * 1:1 문의 상세보기
	 */
	@GetMapping("/fo/service/inquiry/detail")
	public String noticeDetail(FoInquiryDTO foInquiryDTO, ModelMap model, HttpServletRequest request, HttpSession session) {
		FoInquiryDTO fqd = foInquiryService.selectInquiryDetail(foInquiryDTO);
		fqd.setCommonFileList(commonFileService.selectFileDetailList(fqd.getFileSeq()));
		model.addAttribute("detail", fqd);
		return "/fo/service/inquiry-detail";
	}

	/**
	 * 1:1 문의 등록
	 */
	@PostMapping("/fo/service/inquiry")
	@ResponseBody
	public ResponseEntity<ResponseVO> apply(FoInquiryDTO foInquiryDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			foInquiryService.insertInquiry(foInquiryDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(foInquiryDTO.getId()).build());
	}

}