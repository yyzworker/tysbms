package com.tys.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author haoxu
 * @Date 2019/6/11 11:46
 **/
@Service
public class RedisInit {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    public void initRedis(){
        List<String> scorts = jdbcTemplate.query("select result from detectrecord where result is not null and status = 1",new RowMapper<String>(){
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                String res = resultSet.getString("result");
                Object score = JSON.parseObject(res).get("comprehensiveScore");
                if(score != null)
                    return score.toString();
                return null;
            }

        });
        Map map = Maps.newHashMap();
        map.put("69","0");
        map.put("79","0");
        map.put("89","0");
        map.put("99","0");
        map.put("100","0");
        scorts.stream().forEach(s ->{
            if(s != null){
                int score = Integer.valueOf(s);
                if(score < 69){
                    map.put("69",Integer.valueOf(map.get("69").toString())+1+"");
                }else if(score <= 79 && score > 69){
                    map.put("79",Integer.valueOf(map.get("79").toString())+1+"");
                }else if(score <= 89 && score > 79){
                    map.put("89",Integer.valueOf(map.get("89").toString())+1+"");
                }else if(score <= 99 && score > 89){
                    map.put("99",Integer.valueOf(map.get("99").toString())+1+"");
                }else if(score == 100){
                    map.put("100",Integer.valueOf(map.get("100").toString())+1+"");
                }
            }
        });
        redisTemplate.opsForHash().putAll("detectScoreSort",map);
    }
}
