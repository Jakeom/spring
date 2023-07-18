package com.fw.fo.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class FoCertificationController {

    /**
     * 경력인증
     */
    @RequestMapping("/fo/auth/certification")
    public String certification(HttpServletRequest request, ModelMap model){
        model.addAttribute("name", request.getParameter("name"));
        model.addAttribute("data", request.getParameter("data"));
        return "fo/auth/certification";
    }

}
