package com.linduohui.serviceMap.remote;

import com.linduohui.internalcommon.constant.AmapConfigConstants;
import com.linduohui.internalcommon.dto.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MapDirectionClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    public DirectionResponse direction(String depLongitude, String depLatitude,String destLongitude,String destLatitude){
        //组装请求调用url接口
        StringBuilder urlBuild = new StringBuilder();
        urlBuild.append(AmapConfigConstants.DIRECTION_URL);
        urlBuild.append("?origin=");
        urlBuild.append(depLongitude);
        urlBuild.append(",");
        urlBuild.append(depLatitude);
        urlBuild.append("&destination=");
        urlBuild.append(destLongitude);
        urlBuild.append(",");
        urlBuild.append(destLatitude);
        urlBuild.append("&extensions=all&output=json");
        urlBuild.append("&key=");
        urlBuild.append(amapKey);
        log.info(urlBuild.toString());
        //调用高德接口
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuild.toString(), String.class);
        log.info("高德地图路径规划返回："+directionEntity.getBody());
        //解析接口
        return parseDirectionEntity(directionEntity.getBody());
    }

    /**
     * 解析路径规划接口
     * @param directionString
     * @return
     */
    private DirectionResponse parseDirectionEntity(String directionString){
        DirectionResponse directionResponse = null;
        try {
            // 最外层
            JSONObject result = JSONObject.fromObject(directionString);
            if(result.has(AmapConfigConstants.STATUS)) {
                int status = result.getInt(AmapConfigConstants.STATUS);
                if (status == 1){
                    if (result.has(AmapConfigConstants.ROUTE)){
                        JSONObject routeObject = result.getJSONObject(AmapConfigConstants.ROUTE);
                        JSONArray pathsArray = routeObject.getJSONArray(AmapConfigConstants.PATHS);
                        JSONObject pathObject = pathsArray.getJSONObject(0);
                        directionResponse = new DirectionResponse();

                        if (pathObject.has(AmapConfigConstants.DISTANCE)){
                            int distance = pathObject.getInt(AmapConfigConstants.DISTANCE);
                            directionResponse.setDistance(distance);
                        }
                        if (pathObject.has(AmapConfigConstants.DURATION)){
                            int duration = pathObject.getInt(AmapConfigConstants.DURATION);
                            directionResponse.setDuration(duration);
                        }
                    }
                }
            }

        }catch (Exception e){

        }
        return directionResponse;
    }
}
