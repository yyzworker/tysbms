package com.tys.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.google.common.collect.Maps;
import com.tys.config.OssStsConfig;
import com.tys.constant.Constant;
import com.tys.entity.vo.EquipMent;
import com.tys.service.EquipMentService;
import com.tys.util.vo.ReturnMessage;
import com.tys.util.wx.WxToken;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private OssStsConfig ossStsConfig;

    @Autowired
    private EquipMentService equipMentService;

    @Autowired
    private WxToken wxToken;

    @Autowired
    private RedisTemplate redisTemplate;


    private Logger logger = LoggerFactory.getLogger(TokenController.class);

    @GetMapping("/oss/{id}/{secretKey}")
    public JSONObject getOssToken(@PathVariable Integer id,@PathVariable String secretKey) {
        //校验获取token的权限
        JSONObject tokenJson = new JSONObject(true);

        boolean flag = validatePermission(id,secretKey);

        if(!flag){
            tokenJson.put("StatusCode","500");
            tokenJson.put("ErrorCode","500");
            tokenJson.put("ErrorMessage","鉴权失败！");
            return tokenJson;
        }

        return  getTokenJson();
    }

    @GetMapping("/hardware/{id}/{secretKey}/{status}")
    public ReturnMessage setHardWare(@PathVariable Integer id,@PathVariable String secretKey,@PathVariable Byte status){
        boolean flag = validatePermission(id,secretKey);
        if(!flag){
            logger.warn("hardware validate failed，id:{}",id);
            return new ReturnMessage("validate failed");
        }
        int msg = equipMentService.updateHardware(id,status);
        if(msg > 0){
            logger.info("hardware success，id:{},status:{}",id,status);
            return new ReturnMessage("success",null);
        }else {
            logger.warn("hardware failed，id:{},status:{}",id,status);
            return new ReturnMessage("update failed");
        }
    }

    @GetMapping("/appversion/{id}/{secretKey}")
    public ReturnMessage getVersion(@PathVariable Integer id,@PathVariable String secretKey) {
        //校验获取token的权限
        Map map = Maps.newHashMap();
        boolean flag = validatePermission(id,secretKey);
        if(!flag){
            return new ReturnMessage("validate failed");
        }
        String key = Constant.EMHEARTBEAT +id.toString();
        ValueOperations<String, String> valueOps =  redisTemplate.opsForValue();
        if(valueOps.get(key) == null) {
            equipMentService.updateEMStatusById(id, (byte) 1);
        }
        valueOps.set(key, String.valueOf(System.currentTimeMillis()),90, TimeUnit.SECONDS);
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        Map app = hashOps.entries("appVersion");
        map.put("version",app.get("version"));
        map.put("summary",app.get("summary"));



        logger.info("appversion success，id:{},version:{}",id,app.get("version"));
        return  new ReturnMessage("success",map);
    }

    @GetMapping("/wx/{id}/{secretKey}")
    public ReturnMessage getWxToken(@PathVariable Integer id,@PathVariable String secretKey) {
        boolean flag = validatePermission(id,secretKey);
        if(!flag){
            return new ReturnMessage("鉴权失败！");
        }
        return new ReturnMessage("",wxToken.getToken());
    }


    public EquipMent queryEquipMentById(Integer id){
        EquipMent equipMent = equipMentService.queryEquipMentById(id);
        return equipMent;
    }

    public boolean validatePermission(Integer id,String secretKey){
        if(id == null || secretKey == null){
            return false;
        }
        EquipMent equipMent =  queryEquipMentById(id);
        if(null != equipMent){
            String em_imie = equipMent.getEmImie();
            String compare = md5(em_imie+id);
            if(secretKey.equals(compare)){
                return true;
            }
        }
        return  false;
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    @GetMapping("/getOssToken")
    public JSONObject getTokenWithOutValidate() {
        //校验获取token的权限
        return getTokenJson();
    }
    public JSONObject getTokenJson(){
        JSONObject tokenJson = new JSONObject(true);
        String endpoint = ossStsConfig.getEndpoint();
        String accessKeyId = ossStsConfig.getAccessKeyId();
        String accessKeySecret = ossStsConfig.getAccessKeySecret();
        String roleArn = ossStsConfig.getRoleArn();
        String roleSessionName = "session-name";
        try {
            DefaultProfile.addEndpoint("eu-central-1", "eu-central-1", "Sts", endpoint);
            IClientProfile profile = DefaultProfile.getProfile("eu-central-1", accessKeyId, accessKeySecret);
            // 用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setDurationSeconds(3600L);
            final AssumeRoleResponse response = client.getAcsResponse(request);
            tokenJson.put("AccessKeySecret",response.getCredentials().getAccessKeySecret());
            tokenJson.put("StatusCode","200");
            tokenJson.put("SecurityToken",response.getCredentials().getSecurityToken());
            tokenJson.put("Expiration",response.getCredentials().getExpiration());
            tokenJson.put("AccessKeyId",response.getCredentials().getAccessKeyId());
            return tokenJson;
        } catch (ClientException e) {
            tokenJson.put("StatusCode","500");
            tokenJson.put("ErrorCode",e.getErrCode());
            tokenJson.put("ErrorMessage",e.getErrMsg());
            return tokenJson;
        }

    }
}
