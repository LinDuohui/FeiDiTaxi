package com.linduohui.servicepassengeruser.service;

import com.linduohui.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public ResponseResult loginOrReg(String passengerPhone){
        System.out.println("user service");

        return ResponseResult.success();
    }
}
