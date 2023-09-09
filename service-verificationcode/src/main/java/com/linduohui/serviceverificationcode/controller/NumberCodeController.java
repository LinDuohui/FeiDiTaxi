package com.linduohui.serviceverificationcode.controller;

import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lin Duohui
 * @Date 2023/9/4 20:16
 */
@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size){
        System.out.println("验证码位数："+size);
        double random = (Math.random()*9+1)*Math.pow(10,size-1);
        int code = (int)random;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",1);
        jsonObject.put("message","success");
        JSONObject data = new JSONObject();
        data.put("numberCode",code);
        jsonObject.put("data",data.toString());
        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(code);
        return ResponseResult.success(response);
    }
}
