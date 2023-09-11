package com.linduohui.servicepassengeruser.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 乘客用户对象
 */
@Data
public class PassengerUser {

    private Long id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String passengerPhone;
    private String passengerName;
    private byte state;
    private byte profilePhoto;
}
