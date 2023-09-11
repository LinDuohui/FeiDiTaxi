package com.linduohui.apipassenger.controller;

import com.linduohui.apipassenger.service.VerificationCodeService;
import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lin Duohui
 * @Date 2023/9/2 15:47
 */
@RestController
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 根据手机号获取验证码
     * @param verificationCodeDTO
     * @return
     */
    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("乘客手机号："+passengerPhone);
        return verificationCodeService.generateCode(passengerPhone);
    }

    /**
     * 校验手机号和验证码
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/verification-code-check")
    public ResponseResult verificationCodeCheck(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String verificationCode = verificationCodeDTO.getVerificationCode();
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("手机号："+passengerPhone+";验证码："+verificationCode);
        return verificationCodeService.verificationCodeCheck(passengerPhone,verificationCode);
    }
}
