package com.linduohui.servicepassengeruser.controller;

import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.request.VerificationCodeDTO;
import com.linduohui.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult user(@RequestBody VerificationCodeDTO verificationCodeDTO){

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("乘客手机号："+passengerPhone);
        return userService.loginOrReg(passengerPhone);
    }
}
