package com.linduohui.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
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

    /**
     * 生成token
     * @param phone
     * @return
     */
    public static String generateToken(String phone, String identity){
        Map<String,String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE,phone);
        map.put(JWT_KEY_IDENTITY,identity);
        //token 过期时间 一天后过期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

//        builder.withExpiresAt(date);

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
    public static void main(String[] args) {
        String token = generateToken("13677556677","1");
        System.out.println(token);
        System.out.println("解析==========================");
        TokenResult tokenResult = parseToken(token);
        System.out.println("手机号码："+tokenResult.getPhone());
        System.out.println("身份："+tokenResult.getIdentity());
    }
}
