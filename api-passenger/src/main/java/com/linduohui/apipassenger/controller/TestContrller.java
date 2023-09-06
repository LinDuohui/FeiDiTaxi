package com.linduohui.apipassenger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lin Duohui
 * @Date 2023/9/2 13:13
 */
@RestController
public class TestContrller {

    @GetMapping("/test")
    public String test(){
        return "test api passenger";
    }
}
