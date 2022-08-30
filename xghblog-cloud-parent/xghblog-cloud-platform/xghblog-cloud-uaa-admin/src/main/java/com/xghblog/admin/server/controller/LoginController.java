package com.xghblog.admin.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Value("${config}")
    private String config;

    @GetMapping("/get/{id}")
    public String getTest(@PathVariable("id") String id){
        return config+"/"+id;
    }




}
