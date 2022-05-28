package com.hdu.roommates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: TODO
 * @Author xyzhen
 */
@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @RequestMapping("/hello")    // 访问地址http://localhost:8080/roommates/alpha/hello
    @ResponseBody
    public String sayHello(){
        return "Hello Spring Boot.";
    }
}
