package com.tys.util.wx;

import com.alibaba.fastjson.JSONObject;
import com.tys.entity.vo.LocationInfo;
import com.tys.util.config.WxXcxConfig;
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
public class WxToken {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WxXcxConfig wxXcxConfig;


    @Cacheable(value="wxtoken", unless="#result == null")
    public String getToken(){
        HashMap<String, String> map = new HashMap<>();
        map.put("appid",wxXcxConfig.getAppId());
        map.put("secret",wxXcxConfig.getAppSecret());
        ResponseEntity<String> responseString= restTemplate.getForEntity("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}", String.class, map);
        String result = null;
        if(responseString.getStatusCode()==HttpStatus.OK){
            String rjson = responseString.getBody();
            JSONObject json = JSONObject.parseObject(rjson);

            if(json.get("errcode")==null){
                result = json.get("access_token").toString();
            }
            return result;
        }
        return result;
    }

}
