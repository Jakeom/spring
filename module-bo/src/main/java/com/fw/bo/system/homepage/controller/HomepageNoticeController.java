package com.fw.bo.system.homepage.controller;

import com.fw.core.dto.FileDTO;
import com.fw.core.dto.bo.BoFileMgrDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.fw.bo.system.homepage.service.HomepageNoticeService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoNoticeDTO;
import com.fw.core.vo.ResponseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 공지사항 Controller
 *
 * @author Ghazal
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomepageNoticeController {

	private final HomepageNoticeService noticeService;

	/**
	 * 공지사항 관리 페이지
	 */
	@GetMapping("/bo/system/homepage/notice")
	public String notice(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "/bo/system/homepage/notice";

	}
	/**
	 * 공지사항 등록 페이지
	 */
	@GetMapping("/bo/system/homepage/notice/form")
	public String noticeForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "/bo/system/homepage/notice/form";
	}

	/**
	 * 공지사항 상세 페이지
	 */
	@GetMapping("/bo/system/homepage/notice/detail")
	public String noticeDetail(BoNoticeDTO boNoticeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("detail", noticeService.selectNoticeDetail(boNoticeDTO));
		model.addAttribute("file", noticeService.selectNoticeFileList(boNoticeDTO));
		return "/bo/system/homepage/notice/detail";
	}

	/**
	 * 공지사항 상세 수정페이지
	 */
	@GetMapping("/bo/system/homepage/notice/modify")
	public String noticeModify(BoNoticeDTO boNoticeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("modify", noticeService.selectNoticeModify(boNoticeDTO));
		model.addAttribute("file", noticeService.selectNoticeFileList(boNoticeDTO));
		return "/bo/system/homepage/notice/modify";
	}

	/**
	 * 공지사항 관리 리스트
	 */
	@GetMapping("/bo/system/homepage/notice/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectNoticeList(BoNoticeDTO boNoticeDTO, HttpServletRequest request, HttpServletResponse response) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(noticeService.selectNoticeList(boNoticeDTO)).build());
	}

	/**
	 * 공지사항 Display 리스트
	 */
	@GetMapping("/bo/system/homepage/notice/display/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectNoticeDisplayList(BoNoticeDTO boNoticeDTO, HttpServletRequest request, HttpServletResponse response) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(noticeService.selectNoticeDisplayList(boNoticeDTO)).build());
	}

	/**
	 * 공지사항 관리 검색
	 */
	@PostMapping("/bo/system/homepage/notice/search")
	@ResponseBody
	public ResponseEntity<ResponseVO> searchNoticeList(BoNoticeDTO boNoticeDTO, HttpServletRequest request, HttpServletResponse response) {
		boNoticeDTO.setSearchStart(boNoticeDTO.getSearchStart());
		boNoticeDTO.setSearchEnd(boNoticeDTO.getSearchEnd());
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(noticeService.saerchNoticeList(boNoticeDTO)).build());
	}

	/**
	 * 공지사항 등록
	 */
	@PostMapping(value = "/bo/system/homepage/notice/insert", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@ResponseBody
	public ResponseEntity<ResponseVO> insertNotice(
			 		 	 	 	 		  	  	   @RequestPart(value = "jsonData") BoNoticeDTO boNoticeDTO,
												   @RequestPart(value = "noticeFiles", required = false) MultipartFile[] noticeFiles, HttpServletRequest request, HttpServletResponse response
													) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			boNoticeDTO.setNoticeFiles(noticeFiles);
			noticeService.insertNotice(boNoticeDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 공지사항 수정
	 */
	@PostMapping("/bo/system/homepage/notice/update")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateNotice(HttpServletRequest request,
												   @RequestPart(value = "jsonData") BoNoticeDTO boNoticeDTO,
												   @RequestPart(value = "noticeFiles", required = false) MultipartFile[] noticeFiles, HttpServletResponse response
													) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			boNoticeDTO.setNoticeFiles(noticeFiles);
			noticeService.updateNotice(boNoticeDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 공지사항 삭제
	 */
	@PostMapping("/bo/system/homepage/notice/delete")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteNotice(BoNoticeDTO boNoticeDTO, HttpServletRequest request, HttpServletResponse response) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			noticeService.deleteNotice(boNoticeDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * Display 공지사항에서 삭제
	 */
	@PostMapping("/bo/system/homepage/notice/deleteDisplayOrder")
	@ResponseBody
	public ResponseEntity<ResponseVO> deletedisplayOrder(BoNoticeDTO boNoticeDTO, HttpServletRequest request, HttpServletResponse response) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			noticeService.deleteDisplayOrder(boNoticeDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * Display 공지사항
	 */
	@PostMapping("/bo/system/homepage/notice/updateDisplayOrder")
	@ResponseBody
	public ResponseEntity<ResponseVO> updatedisplayOrder(BoNoticeDTO boNoticeDTO, HttpServletRequest request, HttpServletResponse response) {
		boNoticeDTO.setNoticeIds(boNoticeDTO.getNoticeIds());
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			noticeService.updateDisplayOrder(boNoticeDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 파일 삭제
	 */
	@PostMapping("/bo/system/homepage/notice/deleteFile")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteFile(FileDTO fileDTO, HttpServletRequest request, HttpServletResponse response) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			noticeService.updateFile(fileDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}
}