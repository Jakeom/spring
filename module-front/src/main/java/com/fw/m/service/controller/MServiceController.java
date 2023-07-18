package com.fw.m.service.controller;

import com.fw.core.dto.m.MServiceDTO;
import com.fw.m.service.service.MServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MServiceController {

    private final MServiceService mServiceService;

    /**
     * 공지사항 상세 parameter - noticeSeq
     */
    @GetMapping("/m/service/notice-detail")
    public String noticeDetail(ModelMap model, MServiceDTO mServiceDTO) {
        model.addAttribute("noticeInfo",mServiceService.selectNoticeInfoM(mServiceDTO));
        return "m/service/notice-detail";
    }

    /**
     * FAQ 상세 parameter - faqSeq
     */
    @GetMapping("m/service/faq-detail")
    public String faqDetail(ModelMap model, MServiceDTO mServiceDTO) {
        model.addAttribute("faqInfo", mServiceService.selectFaqInfoM(mServiceDTO));
        return "m/service/faq-detail";
    }
}
