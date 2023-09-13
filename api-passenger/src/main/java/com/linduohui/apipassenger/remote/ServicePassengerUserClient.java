package com.linduohui.apipassenger.remote;

import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

    @RequestMapping(method = RequestMethod.POST,value = "/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

    /**
     * 调用 根据手机号查询用户信息接口
     * 注意：Feign 使用body传递参数时 会自定将get请求转化为POST请求
     * @param phone
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String phone);
}
