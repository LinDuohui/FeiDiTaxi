package com.linduohui.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.linduohui.internalcommon.constant.TokenConstants;
import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.dto.TokenResult;
import com.linduohui.internalcommon.util.JwtUtils;
import com.linduohui.internalcommon.util.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * token拦截器
 */
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean result = true;
        String resultString = "";
        String token = request.getHeader("Authorization");

        TokenResult tokenResult = JwtUtils.checkToken(token);
        if(tokenResult == null){
            resultString = "token valid";
            result = false;
        }else {
            //从redis中获取token
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String accessTokenKey = RedisPrefixUtils.generateTokenKey(phone,identity, TokenConstants.ACCESS_TOKEN);
            String accessTokenRedis = stringRedisTemplate.opsForValue().get(accessTokenKey);
            if("".equals(accessTokenRedis)||(!accessTokenRedis.trim().equals(token.trim()))){
                resultString = "token valid";
                result = false;
            }
        }

        if(!result){
            PrintWriter printWriter = response.getWriter();
            printWriter.print(ResponseResult.fail(resultString).toString());
        }
        return result;
    }

}
