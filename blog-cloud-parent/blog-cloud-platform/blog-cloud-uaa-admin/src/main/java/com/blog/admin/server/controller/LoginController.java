package com.blog.admin.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.blog.common.model.ResultBody;
import com.blog.common.security.OpenHelper;
import com.blog.common.security.OpenUserDetail;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Api("用户认证中心")
@RestController
@Slf4j
public class LoginController {


    @Value("${config}")
    private String config;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/aaaa")
    public String testSecurity()
    {
        OpenUserDetail user = OpenHelper.getUser();
        return "测试哇";
    }


    @PostMapping("/login/token")
    public ResultBody loginGetToken(@RequestParam String username, @RequestParam String password, @RequestHeader HttpHeaders httpHeaders)
    {
        Map map = getToken(username, password, httpHeaders);
        if (map.containsKey("access_token"))
        {
            return ResultBody.ok().data(map);
        }
        return null;
    }


    public JSONObject getToken(String userName, String password,HttpHeaders headers) {

        String url = "http://localhost:8222/oauth/token";
        // 使用oauth2密码模式登录.
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("username", userName);
        postParameters.add("password", password);
        postParameters.add("client_id", "c1");
        postParameters.add("client_secret","secret");
        postParameters.add("grant_type", "password");


        // 使用客户端的请求头,发起请求
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 强制移除 原来的请求头,防止token失效
        headers.remove(HttpHeaders.AUTHORIZATION);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(postParameters, headers);
        JSONObject result = restTemplate.postForObject(url, request, JSONObject.class);
        return result;
    }

}
