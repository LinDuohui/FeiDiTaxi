package com.linduohui.servicepassengeruser.service;

import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.servicepassengeruser.dto.PassengerUser;
import com.linduohui.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passengerPhone){
        System.out.println("user service");
        //查询数据库中是否存在该用户
        Map<String,Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        System.out.println(passengerUsers.size()==0?"无记录":passengerUsers.get(0).getPassengerPhone());
        //判断用户是否存在
        if(passengerUsers.size()==0){
            //不存在则插入数据，保存用户
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("张三");
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setPassengerGender((byte) 0);
            passengerUser.setState((byte) 0);
            LocalDateTime localDateTime = LocalDateTime.now();
            passengerUser.setGmtCreate(localDateTime);
            passengerUser.setGmtModified(localDateTime);
            passengerUserMapper.insert(passengerUser);
        }

        return ResponseResult.success();
    }
}
