package com.linduohui.internalcommon.request;

import lombok.Data;

/**
 * @author Lin Duohui
 * @Date 2023/9/2 15:49
 */
@Data
public class VerificationCodeDTO {

    private String passengerPhone;
    private String verificationCode;

}
