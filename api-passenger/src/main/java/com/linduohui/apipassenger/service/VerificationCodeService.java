package com.linduohui.apipassenger.service;

import com.linduohui.apipassenger.remote.ServicePassengerUserClient;
import com.linduohui.apipassenger.remote.ServiceVerificationCodeClient;
import com.linduohui.internalcommon.constant.CommonStatusEnum;
import com.linduohui.internalcommon.constant.IdentityConstant;
import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.request.VerificationCodeDTO;
import com.linduohui.internalcommon.response.NumberCodeResponse;
import com.linduohui.internalcommon.response.TokenResponse;
import com.linduohui.internalcommon.util.JwtUtils;
import org.apache.commons.lang.StringUtils;
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
    private ServicePassengerUserClient servicePassengerUserClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //乘客验证码的前缀
    private String verificationCodePrefix = "passenger-verification-code-";

    /**
     * 生成验证码
     * @param phone
     * @return
     */
    public ResponseResult generateCode(String phone){
        //调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> responseResponseResult = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = responseResponseResult.getData().getNumberCode();
        System.out.println("remoute numbercode:"+numberCode);
        //将验证码存入redis
        System.out.println("将验证码存入redis");
        //key value 过期时间
        String key = generateKey(phone);
        //存入redis
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);

        //通过短信服务商，将对应的验证码发送到手机上，阿里短信服务，腾讯短信通，华信，容联
        return ResponseResult.success("");
    }

    /**
     * 生成Key
     * @param phone
     * @return
     */
    private String generateKey(String phone){
        return verificationCodePrefix+phone;
    }


    /**
     * 校验验证码
     * @param passengerPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult verificationCodeCheck(String passengerPhone, String verificationCode){

        //根据手机号，查询redis中的验证码
        System.out.println("根据手机号，查询redis中的验证码");
        String key = generateKey(passengerPhone);
        String numberCodeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("Redis中拿到的验证码："+numberCodeRedis);
        //校验验证码
        System.out.println("校验验证码");
        if(StringUtils.isBlank(numberCodeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        if(!numberCodeRedis.trim().equals(verificationCode.trim())){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        //判断原来是否有用户，并进行相应处理
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);

        //颁发令牌
        System.out.println("颁发令牌");
        String token = JwtUtils.generateToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);
        return ResponseResult.success(tokenResponse);
    }
}
