package com.linduohui.apipassenger.service;

import com.linduohui.apipassenger.remote.ServicePassengerUserClient;
import com.linduohui.internalcommon.constant.CommonStatusEnum;
import com.linduohui.internalcommon.constant.TokenConstants;
import com.linduohui.internalcommon.dto.PassengerUser;
import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.dto.TokenResult;
import com.linduohui.internalcommon.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;
    /**
     *
     * @param accessToken
     */
    public ResponseResult getUserByAccessToken(String accessToken){
        //解析token
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        if(tokenResult==null){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR);
        }
        String phone = tokenResult.getPhone();
        //根据手机号查询客户信息
        ResponseResult responseResult = servicePassengerUserClient.getUser(phone);
        return ResponseResult.success(responseResult.getData());
    }
}
