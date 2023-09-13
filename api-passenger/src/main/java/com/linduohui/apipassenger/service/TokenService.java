package com.linduohui.apipassenger.service;

import com.linduohui.internalcommon.constant.CommonStatusEnum;
import com.linduohui.internalcommon.constant.TokenConstants;
import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.dto.TokenResult;
import com.linduohui.internalcommon.response.TokenResponse;
import com.linduohui.internalcommon.util.JwtUtils;
import com.linduohui.internalcommon.util.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult tokenRefresh(String refreshTokenSrc){
        //解析token
        TokenResult tokenResult = JwtUtils.checkToken(refreshTokenSrc);
        if(tokenResult==null){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR);
        }
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();

        //获取redis中的refreshToken
        String refreshTokenKey = RedisPrefixUtils.generateTokenKey(phone,identity, TokenConstants.REFRESH_TOKEN);
        String refreshTokenRedis = stringRedisTemplate.opsForValue().get(refreshTokenKey);
        if("".equals(refreshTokenRedis)||(!refreshTokenRedis.trim().equals(refreshTokenSrc.trim()))){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR);
        }
        //生成新的token
        String accessToken = JwtUtils.generateToken(phone, identity, TokenConstants.ACCESS_TOKEN);
        String refreshToken = JwtUtils.generateToken(phone, identity, TokenConstants.REFRESH_TOKEN);
        //存入redis
        String accessTokenKey = RedisPrefixUtils.generateTokenKey(phone,identity,TokenConstants.ACCESS_TOKEN);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,30, TimeUnit.DAYS);
        //返回新的token
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(refreshToken);
        tokenResponse.setAccessToken(accessToken);
        return ResponseResult.success(tokenResponse);
    }
}
