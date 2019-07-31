package com.tys.util.map;

import com.alibaba.fastjson.JSONObject;
import com.tys.entity.vo.LocationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-3-5 12:06
 */
@Service
public class GpsToAddress {
    @Autowired
    private RestTemplate restTemplate;

    private String key = "XJ4BZ-DTB65-5WOI5-QI2RN-OFGIT-Y7B5J";


    @Cacheable(value="gpsaddress",key="#gpsaddress")
    public LocationInfo getAdress(String gpsaddress){
        HashMap<String, String> map = new HashMap<>();
        map.put("location",gpsaddress);
        map.put("key",key);
        ResponseEntity<String> responseString= restTemplate.getForEntity("https://apis.map.qq.com/ws/geocoder/v1/?location={location}&get_poi=0&key={key}", String.class, map);
        if(responseString.getStatusCode()==HttpStatus.OK){
            String rjson = responseString.getBody();
            JSONObject json = JSONObject.parseObject(rjson);
            LocationInfo result = null;
            if(json.get("status").equals(0)){
                result = JSONObject.parseObject(JSONObject.toJSONString(json.get("result")), LocationInfo.class);
                result.setStatus(json.get("status").toString());
                result.setMessage(json.get("message").toString());
            }else{
                result = new LocationInfo();
                result.setStatus(json.get("status").toString());
                result.setMessage(json.get("message").toString());
            }
            return result;
        }
        return null;
    }

}
