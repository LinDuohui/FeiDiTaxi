package com.linduohui.internalcommon.constant;

/**
 * @author Lin Duohui
 * @Date 2023/9/4 20:40
 */
public enum CommonStatusEnum {
    SUCCESS(1,"success"),
    FAILED(2,"fail")
    ;

    private int code;
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
