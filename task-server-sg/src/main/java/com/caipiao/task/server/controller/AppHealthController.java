package com.caipiao.task.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app/health")
public class AppHealthController {

    @RequestMapping("/ok")
    @ResponseBody
    public static String ok() {
        return "ok";
    }
}
