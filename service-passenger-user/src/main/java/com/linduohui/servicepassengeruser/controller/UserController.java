package com.linduohui.servicepassengeruser.controller;

import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.request.VerificationCodeDTO;
import com.linduohui.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult user(@RequestBody VerificationCodeDTO verificationCodeDTO){

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("乘客手机号："+passengerPhone);
        return userService.loginOrRegister(passengerPhone);
    }

    /**
     * 根据手机号查询用户信息
     * @param passengerPhone
     * @return
     */
    @GetMapping("/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String passengerPhone){
        System.out.println("service-passenger-user:user passengerPhone:"+passengerPhone);
        return userService.getUserByPhone(passengerPhone);
    }
}
