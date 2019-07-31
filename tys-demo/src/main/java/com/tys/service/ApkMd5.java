package com.tys.service;

import com.tys.util.tool.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

/**
 * @Author haoxu
 * @Date 2019/5/7 15:27
 **/
@Service
public class ApkMd5 {

    @Autowired
    private RedisTemplate redisTemplate;

    public void md5(File file) throws  Exception{
        String summary = Md5Util.md5(file);
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        hashOps.put("appVersion", "version", file.getName().substring(0,file.getName().lastIndexOf(".apk")));
        hashOps.put("appVersion", "summary", summary);
    }
}
