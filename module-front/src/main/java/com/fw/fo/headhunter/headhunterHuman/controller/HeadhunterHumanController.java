package com.fw.fo.headhunter.headhunterHuman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 인재검색 Controller
 * @author jung
 */
@Controller
public class HeadhunterHumanController {

    /**
     * 인재검색
     */
    @GetMapping("/fo/headhunter/headhunterHuman/headhunter-search")
    public String mypage(ModelMap model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        return "fo/headhunter/headhunterHuman/headhunter_search";
    }

}