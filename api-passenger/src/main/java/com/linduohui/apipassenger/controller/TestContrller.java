package com.linduohui.apipassenger.controller;

import com.linduohui.internalcommon.dto.ResponseResult;
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


    /**
     * 需要有token
     * @return
     */
    @GetMapping("/authTest")
    public ResponseResult authTest(){
        return ResponseResult.success("auth test");
    }

    /**
     * 没有token也能正常返回
     * @return
     */
    @GetMapping("/noauthTest")
    public ResponseResult noauthTest(){
        return ResponseResult.success("noauth test");
    }
}
