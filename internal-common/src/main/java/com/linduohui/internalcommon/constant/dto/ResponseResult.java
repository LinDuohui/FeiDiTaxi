package com.linduohui.internalcommon.constant.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Lin Duohui
 * @Date 2023/9/4 20:43
 */
@Data
@Accessors(chain = true)
public class ResponseResult<T> {

    private int code;
    private String message;
    private T data;

}
