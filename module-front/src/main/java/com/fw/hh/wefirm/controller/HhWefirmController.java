package com.fw.hh.wefirm.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.hh.HhWefirmDTO;
import com.fw.core.dto.hh.HhWefirmHeadhunterDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.hh.wefirm.service.HhMyWefirmService;
import com.fw.hh.wefirm.service.HhWefirmService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HhWefirmController {

	private final HhWefirmService hhWefirmService;
	private final HhMyWefirmService hhMyWefirmService;

	/**
	 * We펌 권한에 따른 페이지 노출
	 */
	@GetMapping("/hh/wefirm/wefirm-list")
	public String wefirmList(ModelMap model, HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO, RedirectAttributes redirectAttributes) {
		HhWefirmHeadhunterDTO auth = hhMyWefirmService.selectWefirmAuth(hhWefirmHeadhunterDTO);
		if (StringUtils.equals(auth.getWefirmAuth(), "NO")) { // 위펌 미소속 헤드헌터의 경우
			return "redirect:/hh/wefirm/search";
		}
		return "redirect:/hh/wefirm/view-position";
	}

	/**
	 * We펌 검색
	 */
	@GetMapping("/hh/wefirm/search")
	public String search(ModelMap model, HhWefirmDTO hhWefirmDTO, HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
		hhWefirmDTO.setTotalCount(hhWefirmService.selectWefirmListCnt(hhWefirmDTO));
		model.addAttribute("wefirmList", hhWefirmService.selectWefirmList(hhWefirmDTO));
		model.addAttribute("searchInfo", hhWefirmDTO);
		model.addAttribute("selectWefirmAuth", hhMyWefirmService.selectWefirmAuth(hhWefirmHeadhunterDTO));
		return "hh/wefirm/search";
	}

	/**
	 * We펌 신청
	 */
	@PostMapping("/hh/wefirm/apply")
	@ResponseBody
	public ResponseEntity<ResponseVO> apply(HhWefirmDTO hhWefirmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		String serviceCode = "";
		try {
			hhWefirmDTO.setMemberId(hhWefirmDTO.getFrontSession().getId());
			serviceCode = hhWefirmService.insertWefirmJoinRequest(hhWefirmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(serviceCode).build());
	}

	/**
	 * 가입승인상태
	 */
	@GetMapping("/hh/wefirm/subscription-approval-status")
	public String subscriptionApprovalStatus(ModelMap model, HhWefirmDTO hhWefirmDTO, HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
		hhWefirmDTO.setMemberId(hhWefirmDTO.getFrontSession().getId());
		model.addAttribute("requestList", hhWefirmService.selectWefirmJoinRequestList(hhWefirmDTO));
		model.addAttribute("selectWefirmAuth", hhMyWefirmService.selectWefirmAuth(hhWefirmHeadhunterDTO));
		return "hh/wefirm/subscription-approval-status";
	}

	/**
	 * We펌 신청취소
	 */
	@PostMapping("/hh/wefirm/cancel-join")
	@ResponseBody
	public ResponseEntity<ResponseVO> cancelJoin(HhWefirmDTO hhWefirmDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhWefirmService.deleteCancelWefirmJoin(hhWefirmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * We펌 개설신청
	 */
	@GetMapping("/hh/wefirm/application-open")
	public String applicationOpen(ModelMap model, HhWefirmDTO hhWefirmDTO, HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
		hhWefirmDTO.setMemberId(hhWefirmDTO.getFrontSession().getId());
		model.addAttribute("headhunter", hhWefirmService.selectHeadhunter(hhWefirmDTO));
		model.addAttribute("selectWefirmAuth", hhMyWefirmService.selectWefirmAuth(hhWefirmHeadhunterDTO));
		return "hh/wefirm/application-open";
	}

	/**
	 * We펌 개설신청
	 */
	@PostMapping("/hh/wefirm/application-open")
	public ResponseEntity<ResponseVO> applicationOpen(@RequestPart(value = "jsonData") HhWefirmDTO hhWefirmDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files,
			@RequestPart(value = "logoFiles", required = false) MultipartFile[] logoFiles) {
		ResponseCode code = ResponseCode.SUCCESS;
		String statusCode = "";
		try {
			hhWefirmDTO.setMemberId(hhWefirmDTO.getFrontSession().getId());
			hhWefirmDTO.setFiles(files);
			hhWefirmDTO.setLogoFiles(logoFiles);
			statusCode = hhWefirmService.insertWefirm(hhWefirmDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(statusCode).build());
	}

	/**
	 * 위펌 소속 헤드헌터 count
	 */
	@GetMapping("/hh/wefirm/selectWefirmHhCnt")
	public ResponseEntity<ResponseVO> selectWefirmHhCnt(HhWefirmDTO hhWefirmDTO, HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(hhWefirmService.selectWefirmHhCnt(hhWefirmDTO)).build());
	}

}
