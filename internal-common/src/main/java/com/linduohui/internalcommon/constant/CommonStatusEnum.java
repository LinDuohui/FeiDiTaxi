package com.linduohui.internalcommon.constant;

import lombok.Data;

/**
 * @author Lin Duohui
 * @Date 2023/9/4 20:40
 */
public enum CommonStatusEnum {
    /**
     * 1000-1099 验证码错误
     */
    VERIFICATION_CODE_ERROR(1099,"验证码不正确"),
    /**
     * 1100-1199 token错误
     */
    TOKEN_ERROR(1199,"token错误"),
    SUCCESS(1,"success"),
    FAILED(2,"fail")
    ;

    private int code;
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
