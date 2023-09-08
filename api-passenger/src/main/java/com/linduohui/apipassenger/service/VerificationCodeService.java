package com.linduohui.apipassenger.service;

import com.linduohui.apipassenger.remote.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Lin Duohui
 * @Date 2023/9/2 15:55
 */
@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //乘客验证码的前缀
    private String verificationCodePrefix = "passenger-verification-code-";

    public String generateCode(String phone){
        //调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> responseResponseResult = serviceVerificationCodeClient.getNumberCode(5);
        int numberCode = responseResponseResult.getData().getNumberCode();
        System.out.println("remoute numbercode:"+numberCode);
        //将验证码存入redis
        System.out.println("将验证码存入redis");
        //key value 过期时间
        String key = verificationCodePrefix+phone;
        //存入redis
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);

        //封装返回Json
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",200);
        jsonObject.put("message","success");
        return jsonObject.toString();
    }
}
