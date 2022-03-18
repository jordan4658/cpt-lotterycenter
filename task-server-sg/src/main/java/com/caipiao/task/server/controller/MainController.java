package com.caipiao.task.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping(value = {"", "/", "index.html"})


    public String index() {
        return "CoreTask is running ...";
    }

}
