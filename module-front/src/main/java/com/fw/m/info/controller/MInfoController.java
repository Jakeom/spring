package com.fw.m.info.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MInfoController {

    @GetMapping("/m/info/privacy")
    public String info(){
        return "m/info/privacy";
    }

    @GetMapping("m/info/service")
    public String privacy() { return "m/info/service";}

}
