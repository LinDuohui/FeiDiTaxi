package com.linduohui.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
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

        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.parseToken(token);
        } catch (SignatureVerificationException e) {
            resultString = "token sign error";
            result = false;
        } catch (TokenExpiredException e) {
            resultString = "token time out";
            result = false;
        } catch (AlgorithmMismatchException e) {
            resultString = "token SignatureVerificationException";
            result = false;
        } catch (Exception e) {
            resultString = "token valid";
            result = false;
        }
        if(tokenResult == null){
            resultString = "token valid";
            result = false;
        }else {
            //从redis中获取token
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generateTokenKey(phone,identity);
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if("".equals(tokenRedis)||!tokenRedis.trim().equals(token.trim())){
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
