package com.fw.bo.system.screen.controller;

import com.fw.core.dto.FileDTO;
import com.fw.core.dto.bo.BoNoticeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.fw.bo.system.screen.service.ScreenBannerService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoBannerDTO;
import com.fw.core.vo.ResponseVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ScreenBannerController {

	private final ScreenBannerService bannerService;

	/**
	 * 배너관리
	 */
	@GetMapping("/bo/system/screen/banner")
	public String banner(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "/bo/system/screen/banner";
	}

	/**
	 * 배너등록 페이지
	 */
	@GetMapping("/bo/system/screen/banner/form")
	public String bannerForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "/bo/system/screen/banner/form";
	}

	/**
	 * 배너상세 페이지
	 */
	@GetMapping("/bo/system/screen/banner/detail")
	public String bannerDetail(BoBannerDTO boBannerDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("bannerDetail", bannerService.selectBannerDetail(boBannerDTO));
		model.addAttribute("file", bannerService.selectBannerFileList(boBannerDTO));
		return "/bo/system/screen/banner/detail";
	}

	/**
	 * 배너상세 수정페이지
	 */
	@GetMapping("/bo/system/screen/banner/modify")
	public String bannerModify(BoBannerDTO boBannerDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("bannerModify", bannerService.selectBannerModify(boBannerDTO));
		model.addAttribute("file", bannerService.selectBannerFileList(boBannerDTO));
		return "/bo/system/screen/banner/modify";
	}

	/**
	 * 배너관리 리스트
	 */
	@GetMapping("/bo/system/screen/banner/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectbannerList(BoBannerDTO boBannerDTO, HttpServletRequest request, HttpServletResponse response) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(bannerService.selectBannerList(boBannerDTO)).build());
	}

	/**
	 * 배너등록
	 */
//	@PostMapping("/bo/system/screen/banner/insert")
//	@ResponseBody
//	public ResponseEntity<ResponseVO> insertBanner(@RequestBody BoBannerDTO boBannerDTO) {
//		ResponseCode code = ResponseCode.SUCCESS;
//		try {
//			bannerService.insertBanner(boBannerDTO);
//		} catch (Exception e) {
//			log.error("error", e);
//			code = ResponseCode.INTERNAL_SERVER_ERROR;
//		}
//		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
//	}

	/**
	 * 배너수정
	 */
	@PostMapping("/bo/system/screen/banner/update")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateBanner(@RequestPart(value = "jsonData") BoBannerDTO boBannerDTO,
												   @RequestPart(value = "bannerFiles", required = false) MultipartFile[] bannerFiles, HttpServletRequest request, HttpServletResponse response) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			boBannerDTO.setBannerFiles(bannerFiles);
			bannerService.updateBanner(boBannerDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 배너삭제
	 */
	@PostMapping("/bo/system/screen/banner/delete")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteBanner(BoBannerDTO boBannerDTO, HttpServletRequest request, HttpServletResponse response) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			bannerService.deleteBanner(boBannerDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 파일 삭제
	 */
	@PostMapping("/bo/system/homepage/banner/deleteFile")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteFile(FileDTO fileDTO, HttpServletRequest request, HttpServletResponse response) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			bannerService.updateFile(fileDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}
}