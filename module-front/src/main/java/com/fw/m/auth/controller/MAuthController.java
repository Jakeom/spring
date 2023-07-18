package com.fw.m.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MAuthController {

    @Value("${cert.server}")
    private String CERT_SERVER;

    @Value("${cert.callback-url}")
    private String CERT_CALLBACK_URL;

    /**
     * 아이디 찾기 WebView
     */
    @GetMapping("/m/auth/find-id")
    public String findId(ModelMap model) {
        model.addAttribute("certServer", CERT_SERVER);                       // 본인인증 URL
        model.addAttribute("certCallbackUrl", CERT_CALLBACK_URL);            // 본인인증 CALLBACK URL
        return "m/auth/find-id";
    }

    /**
     * 비밀번호 재설정 WebView
     */
    @GetMapping("m/auth/pw-reset")
    public String pwReset(ModelMap model) {
        model.addAttribute("certServer", CERT_SERVER);                       // 본인인증 URL
        model.addAttribute("certCallbackUrl", CERT_CALLBACK_URL);            // 본인인증 CALLBACK URL
        return "m/auth/pw-reset";
    }
}
