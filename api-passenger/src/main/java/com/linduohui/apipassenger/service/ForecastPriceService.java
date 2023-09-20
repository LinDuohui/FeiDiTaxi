package com.linduohui.apipassenger.service;

import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.request.ForecastPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastPriceService {


    public ResponseResult forecastPrice(ForecastPriceDTO forecastPriceDTO){
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        log.info("起点经度："+depLongitude);
        log.info("起点纬度："+depLatitude);
        log.info("终点经度："+destLongitude);
        log.info("终点纬度："+destLatitude);
        //调用计价服务

        return ResponseResult.success();
    }
}
