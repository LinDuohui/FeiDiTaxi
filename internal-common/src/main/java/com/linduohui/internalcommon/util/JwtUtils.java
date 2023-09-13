package com.linduohui.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.linduohui.internalcommon.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SIGN = "DKJKFDJK#$%@%^%";

    private static final String JWT_KEY_PHONE = "phone";
    //乘客 1 司机 2
    private static final String JWT_KEY_IDENTITY = "identity";
    //token类型
    private static final String JWT_TOKEN_TYPE = "tokenType";
    //token时间
    private static final String JWT_TOKEN_TIME = "tokenTime";

    /**
     * 生成token
     * @param phone
     * @return
     */
    public static String generateToken(String phone, String identity, String tokenType){
        Map<String,String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE,phone);
        map.put(JWT_KEY_IDENTITY,identity);
        map.put(JWT_TOKEN_TYPE,tokenType);
        map.put(JWT_TOKEN_TIME,Calendar.getInstance().getTime().toString());

        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

        String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    /**
     * 校验token是否合规
     * @param token
     * @return
     */
    public static TokenResult checkToken(String token){
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.parseToken(token);
        } catch (Exception e) {
        }
        return tokenResult;
    }


    public static void main(String[] args) {
        String token = generateToken("13677556677","1","accessToken");
        System.out.println(token);
        System.out.println("解析==========================");
        TokenResult tokenResult = parseToken(token);
        System.out.println("手机号码："+tokenResult.getPhone());
        System.out.println("身份："+tokenResult.getIdentity());
    }
}
