package com.fw.bo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 로그인 Controller
 * @author dongbeom
 */
@Controller
public class LoginController {

    /**
     * BO Dashboard 페이지
     */
    @GetMapping({"/", "/bo"})
    public String bo() {
        return "/bo/dashboard";
    }

    /**
     * BO 로그인 페이지
     */
    @GetMapping("/bo/login")
    public String login() {
        return "/bo/login";
    }

}