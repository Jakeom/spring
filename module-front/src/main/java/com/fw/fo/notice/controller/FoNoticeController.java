package com.fw.fo.notice.controller;

import com.fw.core.dto.fo.notice.FoNoticeDTO;
import com.fw.core.util.CookieUtil;
import com.fw.fo.notice.service.FoNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 로그인 Controller
 * @author jung
 */
@Controller
public class FoNoticeController {

    @Autowired
    private FoNoticeService foNoticeService;

    /**
     * front 메인 공고
     */
    @GetMapping("/fo/service/notice")
    public String notice(FoNoticeDTO foNoticeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        foNoticeDTO.setTotalCount(foNoticeService.selectNoticeListCntForPaging(foNoticeDTO));
        model.addAttribute("noticeList",foNoticeService.selectNoticeListPaging(foNoticeDTO));
        model.addAttribute("searchInfo",foNoticeDTO);
        return "fo/service/notice";
    }

    /**
     * 공고 상세보기
     */
    @GetMapping("/fo/service/notice/detail")
    public String noticeDetail(FoNoticeDTO foNoticeDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        if (!CookieUtil.mergeCookie(request, response, "notice", foNoticeDTO.getNoticeSeq(), "/", 60 * 60 * 24)) {
            foNoticeService.updateNoticeHit(foNoticeDTO); // 조회수 증가
        }
        model.addAttribute("noticeInfo",foNoticeService.selectNoticeInfo(foNoticeDTO));
        return "fo/service/notice-detail";
    }

}