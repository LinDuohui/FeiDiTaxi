package com.linduohui.apipassenger.controller;

import com.linduohui.apipassenger.service.TokenService;
import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult tokenRefresh(@RequestBody TokenResponse tokenResponse){
        String refreshToken = tokenResponse.getRefreshToken();
        return tokenService.tokenRefresh(refreshToken);
    }
}
