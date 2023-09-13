package com.linduohui.internalcommon.util;

/**
 * redis key 工具类
 */
public class RedisPrefixUtils {
    //乘客验证码的前缀
    public static String verificationCodePrefix = "passenger-verification-code-";
    //token前缀
    public static String tokenPrefix = "token-";

    /**
     * 生成Key
     * @param phone
     * @return
     */
    public static String generateKey(String phone){
        return verificationCodePrefix+phone;
    }
    /**
     * 生成Key
     * @param phone
     * @return
     */
    public static String generateTokenKey(String phone, String identity, String tokenType){
        return tokenPrefix+phone+"-"+identity+"-"+tokenType;
    }
}
