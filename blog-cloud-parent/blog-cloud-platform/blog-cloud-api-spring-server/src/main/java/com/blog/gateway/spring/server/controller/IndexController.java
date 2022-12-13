package com.blog.gateway.spring.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: liuyadu
 * @date: 2018/11/5 16:33
 * @description:
 */
@Controller
public class IndexController {


    @GetMapping("/")
    public String index() {
            return "redirect:swagger-ui.html";
    }
}
