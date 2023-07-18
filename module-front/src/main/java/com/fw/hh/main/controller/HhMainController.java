package com.fw.hh.main.controller;

import java.util.Arrays;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.hh.HhCommonDTO;
import com.fw.core.dto.hh.HhFaqDTO;
import com.fw.core.dto.hh.HhHeadhunterDTO;
import com.fw.core.dto.hh.HhMyapListDTO;
import com.fw.core.dto.hh.HhNoticeDTO;
import com.fw.core.dto.hh.HhPointDTO;
import com.fw.core.dto.hh.HhPositionDTO;
import com.fw.fo.main.service.FoChatService;
import com.fw.hh.common.service.HhCommonService;
import com.fw.hh.faq.service.HhFaqService;
import com.fw.hh.myap.service.HhMyApService;
import com.fw.hh.notice.service.HhNoticeService;
import com.fw.hh.position.service.HhPositionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Main Controller
 * 
 * @author sjpaik
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HhMainController {

	private final HhCommonService HhCommonService;
	private final HhPositionService hhPositionService;
	private final HhNoticeService HhNoticeService;
	private final CommonFileService commonFileService;
	private final HhFaqService HhFaqService;
	private final HhMyApService hhMyApService;
	private final FoChatService foChatService;

	/**
	 * Front 메인 페이지
	 */
	@GetMapping({ "/hh" })
	public String index(ModelMap model, Authentication authentication, HhPositionDTO hhPositionDTO, HhMyapListDTO hhMyapListDTO,
			PushHistDTO pushHistDTO, HhHeadhunterDTO hhHeadhunterDTO, HhPointDTO hhPointDTO,
			@RequestParam(value = "errorMessage", required = false) String errorMessage) {
		if (!Objects.isNull(authentication)) {
			FoSessionDTO foSessionDTO = (FoSessionDTO) authentication.getPrincipal();
			if (!Objects.isNull(foSessionDTO) && StringUtils.equals("AP", foSessionDTO.getRoleName())) {
				return "redirect:/";
			}
		}
		model.addAttribute("errorMessage", errorMessage);

		// 포인트, 열람권 취득
		model.addAttribute("myPoint", hhMyApService.selectPoint(hhPointDTO));

		// 알림 개수
		model.addAttribute("myAlarm", foChatService.selectAlarmCnt(pushHistDTO));

		// 상단 로고 취득
		HhCommonDTO hhCommonDTO = new HhCommonDTO();
		hhCommonDTO.setBannerTypeCd("FRONT_TOP_BANNER");
		model.addAttribute("topBannerList", HhCommonService.selectBannerList(hhCommonDTO));

		// 내 포지션 취득
		hhPositionDTO.setStatusList(Arrays.asList("DOING"));
		model.addAttribute("positionList", hhPositionService.selectHhPositionList(hhPositionDTO));

		// 내 인재 취득
		model.addAttribute("myApList", hhMyApService.selectHhMyapList(hhMyapListDTO));

		// 채용현황
		model.addAttribute("status", hhPositionService.selectPositionStatus(hhPositionDTO));

		// 참여 서치펌 리스트
		hhCommonDTO = new HhCommonDTO();
		hhCommonDTO.setDisplayType("SEARCH");
		model.addAttribute("searchList", HhCommonService.selectMainDisplayList(hhCommonDTO));

		// 고객사 리스트
		hhCommonDTO = new HhCommonDTO();
		hhCommonDTO.setDisplayType("CUSTOMER");
		model.addAttribute("mainDisplayList", HhCommonService.selectMainDisplayList(hhCommonDTO));

		// 중단 컨텐츠
		hhCommonDTO = new HhCommonDTO();
		hhCommonDTO.setBannerTypeCd("BOTTOM_CONTENT");
		model.addAttribute("bottomContentList", HhCommonService.selectBannerList(hhCommonDTO));

		// 하단 컨텐츠
		hhCommonDTO = new HhCommonDTO();
		hhCommonDTO.setBannerTypeCd("FRONT_BOTTOM_CONTENT");
		model.addAttribute("frontBottomContentList", HhCommonService.selectBannerList(hhCommonDTO));

		// 하단 띠배너
		hhCommonDTO = new HhCommonDTO();
		hhCommonDTO.setBannerTypeCd("BOTTOM_LINE_BANNER");
		model.addAttribute("bottomLineBannerList", HhCommonService.selectBannerList(hhCommonDTO));

		// 하단 공지사항
		HhNoticeDTO hhNoticeDTO = new HhNoticeDTO();
		model.addAttribute("hhNoticeList", HhNoticeService.selectNoticeList(hhNoticeDTO));

		// 하단 FAQ
		HhFaqDTO hhFaqDTO = new HhFaqDTO();
		model.addAttribute("hhMainFaqList", HhFaqService.selectMainFaqList(hhFaqDTO));

		// 위펌 , 서치펌
		model.addAttribute("detail", HhCommonService.selectHeadhunterInfo(hhHeadhunterDTO));

		return "hh/index";
	}

	/**
	 * 이용약관
	 */
	@GetMapping("/hh/etc/policy")
	public String policy(HhCommonDTO hhCommonDTO, ModelMap model, HttpServletRequest request) {
		hhCommonDTO.setInfoType("POLICY");
		model.addAttribute("policy", HhCommonService.selectInfo(hhCommonDTO));

		return "/hh/etc/policy";
	}

	/**
	 * 개인정보보호정책
	 */
	@GetMapping("/hh/etc/privacy")
	public String privacy(HhCommonDTO hhCommonDTO, ModelMap model, HttpServletRequest request) {
		hhCommonDTO.setInfoType("PRIVACY");
		model.addAttribute("privacy", HhCommonService.selectInfo(hhCommonDTO));

		return "/hh/etc/privacy";
	}

}
