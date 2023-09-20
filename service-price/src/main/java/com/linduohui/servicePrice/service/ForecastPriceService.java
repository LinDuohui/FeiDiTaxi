package com.linduohui.servicePrice.service;

import com.linduohui.internalcommon.constant.CommonStatusEnum;
import com.linduohui.internalcommon.dto.DirectionResponse;
import com.linduohui.internalcommon.dto.PriceRule;
import com.linduohui.internalcommon.dto.ResponseResult;
import com.linduohui.internalcommon.request.ForecastPriceDTO;
import com.linduohui.servicePrice.mapper.PriceRuleMapper;
import com.linduohui.servicePrice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ForecastPriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult forecastPrice(ForecastPriceDTO forecastPriceDTO){
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        log.info("起点经度："+depLongitude);
        log.info("起点纬度："+depLatitude);
        log.info("终点经度："+destLongitude);
        log.info("终点纬度："+destLatitude);
        //调用地图服务
        ResponseResult<DirectionResponse> direction = serviceMapClient.direction(forecastPriceDTO);
        log.info("规划路径距离："+direction.getData().getDistance());
        log.info("规划路径时长："+direction.getData().getDuration());
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("city_code","110000");
        queryMap.put("vehicle_type","1");
        List<PriceRule> priceRules = priceRuleMapper.selectByMap(queryMap);
        if(priceRules.size()==0){
            ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        return ResponseResult.success();
    }
}
