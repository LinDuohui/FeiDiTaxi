package com.linduohui.apipassenger.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author Lin Duohui
 * @Date 2023/9/2 15:55
 */
@Service
public class VerificationCodeService {
    public String generateCode(String phone){
        //调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        //将验证码存入redis
        System.out.println("将验证码存入redis");
        //封装返回Json
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",200);
        jsonObject.put("message","success");
        return jsonObject.toString();
    }
}
