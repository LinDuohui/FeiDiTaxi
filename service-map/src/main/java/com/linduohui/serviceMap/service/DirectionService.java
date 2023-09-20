package com.linduohui.serviceMap.service;

import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.request.ForecastPriceDTO;
import com.linduohui.serviceMap.remote.MapDirectionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class DirectionService {

    @Autowired
    private MapDirectionClient mapDirectionClient;

    /**
     * 计算距离和时长
     * @param forecastPriceDTO
     * @return
     */
    public ResponseResult driving(ForecastPriceDTO forecastPriceDTO){
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        log.info("起点经度："+depLongitude);
        log.info("起点纬度："+depLatitude);
        log.info("终点经度："+destLongitude);
        log.info("终点纬度："+destLatitude);

        //调用接口计算距离和时长

        return ResponseResult.success(mapDirectionClient.direction(depLongitude,depLatitude,destLongitude,destLatitude));
    }
}
