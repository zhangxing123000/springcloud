package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//证明是controller层并且返回json
@Controller
@RequestMapping(value = "/user")
public class UserController {
    //依赖注入
    @Autowired
    UserService userService;

    @RequestMapping(value = "/cs")
    public User cs() {
        //调用dao层
        User user = userService.selectUserByName("beyondLi");
        return user;
    }
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request) {
        User user = userService.findUserByName("zhangxing");
        request.setAttribute("user",user);
        return "index";
    }

    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request) {
        return "test";
    }
}