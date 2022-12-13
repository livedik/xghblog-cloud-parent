package com.blog.services.demoSpring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloWord {

    @GetMapping("/test")
    public String test()
    {
        return "hello word!";
    }

    @GetMapping("/test/index")
    public String test1()
    {
        return "hello word111111!";
    }

    @GetMapping("/test2")
    public String test2()
    {
        return "hello word1111112222222!";
    }
}
