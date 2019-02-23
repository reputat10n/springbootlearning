package com.chau.abear.controller;

import com.chau.abear.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        User user = new User();
        user.setUserId(1L);
        user.setPassWord("123456");
        user.setNickName("Jacky");
        return "{\"name\":\"zhangsan\"}";
    }
}
