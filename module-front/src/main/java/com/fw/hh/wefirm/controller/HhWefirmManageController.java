package com.fw.hh.wefirm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.hh.HhWefirmDTO;
import com.fw.core.dto.hh.HhWefirmHeadhunterDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.hh.wefirm.service.HhMyWefirmService;
import com.fw.hh.wefirm.service.HhWefirmManageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HhWefirmManageController {

	private final HhWefirmManageService hhWefirmManageService;
	private final HhMyWefirmService hhMyWefirmService;

	/**
	 * 헤드헌터 리스트
	 */
	@GetMapping("/hh/wefirm/headhunter-list")
	public String headhunterList(ModelMap model, HhWefirmDTO hhWefirmDTO, HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
		hhWefirmDTO.setRowSize(10);
		hhWefirmDTO.setMemberId(hhWefirmDTO.getFrontSession().getId());
		hhWefirmDTO.setTotalCount(hhWefirmManageService.selectHeadhunterListCnt(hhWefirmDTO));
		model.addAttribute("searchInfo", hhWefirmDTO);
		model.addAttribute("wefirmHeadhunterList", hhWefirmManageService.selectHeadhunterList(hhWefirmDTO));
		model.addAttribute("selectWefirmAuth", hhMyWefirmService.selectWefirmAuth(hhWefirmHeadhunterDTO));
		return "hh/wefirm/headhunter-list";
	}

	/**
	 * 위펌 가입신청 승인
	 */
	@GetMapping("/hh/wefirm/subscription-approval")
	public String subscriptionApproval(ModelMap model, HhWefirmDTO hhWefirmDTO, HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
		hhWefirmDTO.setMemberId(hhWefirmDTO.getFrontSession().getId());
		hhWefirmDTO.setTotalCount(hhWefirmManageService.selectNoWefirmHeadhunterListCnt(hhWefirmDTO));
		model.addAttribute("searchInfo", hhWefirmDTO);
		model.addAttribute("wefirmNoHeadhunterList", hhWefirmManageService.selectNoWefirmHeadhunterList(hhWefirmDTO));
		model.addAttribute("selectWefirmAuth", hhMyWefirmService.selectWefirmAuth(hhWefirmHeadhunterDTO));
		return "hh/wefirm/subscription-approval";
	}

	/**
	 * We펌 관리
	 */
	@GetMapping("/hh/wefirm/management")
	public String management(ModelMap model, HhWefirmDTO hhWefirmDTO, HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
		hhWefirmDTO.setMemberId(hhWefirmDTO.getFrontSession().getId());
		model.addAttribute("detail", hhWefirmManageService.selectWefirmDetail(hhWefirmDTO));
		model.addAttribute("headhunterList", hhWefirmManageService.selectHeadhunterList(hhWefirmDTO));
		model.addAttribute("selectWefirmAuth", hhMyWefirmService.selectWefirmAuth(hhWefirmHeadhunterDTO));
		model.addAttribute("searchInfo", hhWefirmDTO);
		return "hh/wefirm/management";
	}

	/**
	 * We펌 운영자 등록
	 */
	@PostMapping("/hh/wefirm/add-admin")
	@ResponseBody
	public ResponseEntity<ResponseVO> addAdmin(HhWefirmDTO hhWefirmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhWefirmManageService.insertWefirmAdmin(hhWefirmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * We펌 수정
	 */
	@PostMapping("/hh/wefirm/update-wefirm")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateWefirm(@RequestPart(value = "jsonData") HhWefirmDTO hhWefirmDTO,
			@RequestPart(value = "logoFiles", required = false) MultipartFile[] logoFiles) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhWefirmDTO.setLogoFiles(logoFiles);
			hhWefirmManageService.updateWefirm(hhWefirmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * We펌 폐쇄 요청
	 */
	@PostMapping("/hh/wefirm/request-history")
	@ResponseBody
	public ResponseEntity<ResponseVO> wefirmRequestHistory(HhWefirmDTO hhWefirmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		String serviceCode = "SUCCESS";
		try {
			boolean requestExists = hhWefirmManageService.selectWefirmRequestDuplicateCheck(hhWefirmDTO);
			if (requestExists) {
				serviceCode = "DUPLICATE_REQUEST";
			} else {
				hhWefirmManageService.insertWefirmRequestHistory(hhWefirmDTO);
			}
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(serviceCode).build());
	}

	/**
	 * 위펌 헤드헌터 강제탈퇴
	 */
	@PostMapping("/hh/wefirm/deleteHeadhunter")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteHeadhunter(@RequestBody HhWefirmDTO hhWefirmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhWefirmManageService.deleteHeadhunter(hhWefirmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 위펌 가입신청 승인
	 */
	@PostMapping("/hh/wefirm/approvalHeadhunter")
	@ResponseBody
	public ResponseEntity<ResponseVO> approvalHeadhunter(@RequestBody HhWefirmDTO hhWefirmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhWefirmManageService.approvalHeadhunter(hhWefirmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}
}
