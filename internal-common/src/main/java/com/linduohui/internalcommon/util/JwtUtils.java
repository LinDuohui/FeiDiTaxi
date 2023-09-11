package com.linduohui.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.xml.internal.ws.util.xml.CDATA;
import org.apache.commons.beanutils.converters.CalendarConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SIGN = "DKJKFDJK#$%@%^%";

    private static final String JWT_KEY = "phone";
    /**
     * 生成token
     * @param phone
     * @return
     */
    public static String generateToken(String phone){
        Map<String,String> map = new HashMap<>();
        map.put(JWT_KEY,phone);
        //token 过期时间 一天后过期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

        builder.withExpiresAt(date);

        String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public static String parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        Claim claim = verify.getClaim(JWT_KEY);
        return claim.toString();
    }
    public static void main(String[] args) {
        String token = generateToken("13677556677");
        System.out.println(token);

        String parseToken = parseToken(token);
        System.out.println("解析之后的token："+parseToken);
    }
}
