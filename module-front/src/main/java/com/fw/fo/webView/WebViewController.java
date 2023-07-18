package com.fw.fo.webView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebViewController {

    @GetMapping("/fo/webView/find-id")
    public String findId(){
        return "/fo/webView/find-id";
    }

}
