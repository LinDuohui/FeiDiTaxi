package com.linduohui.apipassenger.controller;


import com.linduohui.apipassenger.service.ForecastPriceService;
import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.request.ForecastPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ForecastPriceController {
    @Autowired
    private ForecastPriceService forecastPriceService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO){
        log.info("起点经度："+forecastPriceDTO.getDepLongitude());
        log.info("起点纬度："+forecastPriceDTO.getDepLatitude());
        log.info("终点经度："+forecastPriceDTO.getDestLongitude());
        log.info("终点纬度："+forecastPriceDTO.getDestLatitude());
        return forecastPriceService.forecastPrice(forecastPriceDTO);
    }
}
