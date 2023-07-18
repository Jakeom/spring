package com.fw.fo.main.controller;

import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.fw.core.code.ResponseCode;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.system.config.security.FoLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoCommonDTO;
import com.fw.core.dto.fo.FoPositionDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.fo.faq.FoFaqDTO;
import com.fw.core.dto.fo.notice.FoNoticeDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.common.service.FoCommonService;
import com.fw.fo.faq.service.FoFaqService;
import com.fw.fo.main.service.FoChatService;
import com.fw.fo.notice.service.FoNoticeService;
import com.fw.fo.position.service.FoPositionService;
import com.fw.fo.system.config.security.FoLoginService;

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
public class FoMainController {

	private final FoCommonService foCommonService;
	private final FoPositionService foPositionService;
	private final FoNoticeService foNoticeService;
	private final CommonFileService commonFileService;
	private final FoFaqService foFaqService;
	private final FoLoginService foLoginService;
	private final FoChatService foChatService;

	@Value("${service.web_domain}")
    private String WEB_DOMAIN;

	/**
	 * Front 메인 페이지
	 */
	@GetMapping({ "/" })
	public String index(ModelMap model, Authentication authentication, @RequestParam(value = "errorMessage", required = false) String errorMessage,
			HttpServletRequest request, PushHistDTO pushHistDTO) {
		Cookie[] cookies = request.getCookies(); // 모든 쿠키 가져오기

		if (!Objects.isNull(authentication)) {
			FoSessionDTO foSessionDTO = (FoSessionDTO) authentication.getPrincipal();
			if (!Objects.isNull(foSessionDTO) && StringUtils.equals("HH", foSessionDTO.getRoleName())) {
				return "redirect:/hh";
			}
		}
		model.addAttribute("errorMessage", errorMessage);

		// 상단 로고 취득
		FoCommonDTO foCommonDTO = new FoCommonDTO();
		foCommonDTO.setBannerTypeCd("FRONT_TOP_BANNER");
		model.addAttribute("topBannerList", foCommonService.selectBannerList(foCommonDTO));

		// 실시간 헤드헌팅 채용공고 취득
		FoPositionDTO foPositionDTO = new FoPositionDTO();
		model.addAttribute("positionList", foPositionService.selectPositionList(foPositionDTO));

		// 참여 서치펌 리스트
		foCommonDTO = new FoCommonDTO();
		foCommonDTO.setDisplayType("SEARCH");
		model.addAttribute("searchList", foCommonService.selectMainDisplayList(foCommonDTO));

		// 고객사 리스트
		foCommonDTO = new FoCommonDTO();
		foCommonDTO.setDisplayType("CUSTOMER");
		model.addAttribute("mainDisplayList", foCommonService.selectMainDisplayList(foCommonDTO));

		// 중단 컨텐츠
		foCommonDTO = new FoCommonDTO();
		foCommonDTO.setBannerTypeCd("BOTTOM_CONTENT");
		model.addAttribute("bottomContentList", foCommonService.selectBannerList(foCommonDTO));

		// 하단 컨텐츠
		foCommonDTO = new FoCommonDTO();
		foCommonDTO.setBannerTypeCd("FRONT_BOTTOM_CONTENT");
		model.addAttribute("frontBottomContentList", foCommonService.selectBannerList(foCommonDTO));

		// 하단 띠배너
		foCommonDTO = new FoCommonDTO();
		foCommonDTO.setBannerTypeCd("BOTTOM_LINE_BANNER");
		model.addAttribute("bottomLineBannerList", foCommonService.selectBannerList(foCommonDTO));

		// 비로그인 로고
		foCommonDTO = new FoCommonDTO();
		foCommonDTO.setBannerTypeCd("NON_LOGIN_BANNER");
		model.addAttribute("nonLoginLogoBannerList", foCommonService.selectBannerList(foCommonDTO));

		// 하단 공지사항
		FoNoticeDTO foNoticeDTO = new FoNoticeDTO();
		model.addAttribute("noticeList", foNoticeService.selectNoticeList(foNoticeDTO));

		// 하단 FAQ
		FoFaqDTO foFaqDTO = new FoFaqDTO();
		model.addAttribute("mainFaqList", foFaqService.selectMainFaqList(foFaqDTO));

		// 알림 개수
		model.addAttribute("myAlarm", foChatService.selectAlarmCnt(pushHistDTO));

		model.addAttribute("webDomain", WEB_DOMAIN);
		return "fo/index";
	}

	/**
	 * 이용약관
	 */
	@GetMapping("/fo/etc/policy")
	public String policy(FoCommonDTO foCommonDTO, ModelMap model, HttpServletRequest request) {
		foCommonDTO.setInfoType("POLICY");
		model.addAttribute("policy", foCommonService.selectInfo(foCommonDTO));

		return "fo/etc/policy";
	}

	/**
	 * 개인정보보호정책
	 */
	@GetMapping("/fo/etc/privacy")
	public String privacy(FoCommonDTO foCommonDTO, ModelMap model, HttpServletRequest request) {
		foCommonDTO.setInfoType("PRIVACY");
		model.addAttribute("privacy", foCommonService.selectInfo(foCommonDTO));

		return "fo/etc/privacy";
	}

	/**
	 * 로그아웃시 자동로그인 해제
	 */
	@PostMapping("/fo/auth/autoLoginLogout")
	@ResponseBody
	public ResponseEntity<ResponseVO> autoLoginLogout(FoSessionDTO foSessionDTO, ModelMap model) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			foLoginService.updateDeleteAutoLogin(foSessionDTO.getId());
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	@PostMapping("/fo/auth/checkTemp")
	@ResponseBody
	public ResponseEntity<ResponseVO> checkTemp(FoSessionDTO foSessionDTO, ModelMap model) {
		ResponseCode code = ResponseCode.SUCCESS;
		FoSessionDTO f = new FoSessionDTO();
		try {
			f = foLoginService.selectTempMember(foSessionDTO.getUsername());
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(f).build());
	}

	@PostMapping("/fo/auth/check-Approved")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectHhApproved(FoSessionDTO foSessionDTO, ModelMap model) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(foLoginService.selectHhApproved(foSessionDTO)).build());
	}
}
