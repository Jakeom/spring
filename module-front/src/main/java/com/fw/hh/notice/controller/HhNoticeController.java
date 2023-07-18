package com.fw.hh.notice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.fw.core.dto.hh.HhNoticeDTO;
import com.fw.core.util.CookieUtil;
import com.fw.hh.notice.service.HhNoticeService;

/**
 * 로그인 Controller
 * 
 * @author jung
 */
@Controller
public class HhNoticeController {

	@Autowired
	private HhNoticeService hhNoticeService;

	/**
	 * front 메인 공고
	 */
	@GetMapping("/hh/service/notice")
	public String notice(HhNoticeDTO hhNoticeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		hhNoticeDTO.setTotalCount(hhNoticeService.selectNoticeListCntForPaging(hhNoticeDTO));
		model.addAttribute("noticeList", hhNoticeService.selectNoticeListPaging(hhNoticeDTO));
		model.addAttribute("searchInfo", hhNoticeDTO);
		return "hh/service/notice";
	}

	/**
	 * 공고 상세보기
	 */
	@GetMapping("/hh/service/notice/detail")
	public String noticeDetail(HhNoticeDTO hhNoticeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		if (!CookieUtil.mergeCookie(request, response, "notice", hhNoticeDTO.getNoticeSeq(), "/", 60 * 60 * 24)) {
			hhNoticeService.updateNoticeHit(hhNoticeDTO); // 조회수 증가
		}
		model.addAttribute("noticeInfo", hhNoticeService.selectNoticeInfo(hhNoticeDTO));
		return "hh/service/notice-detail";
	}

}