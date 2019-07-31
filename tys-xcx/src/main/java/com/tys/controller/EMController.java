package com.tys.controller;

import com.tys.entity.vo.EquipMent;
import com.tys.entity.vo.LocationInfo;
import com.tys.service.EquipMentService;
import com.tys.util.map.GpsToAddress;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.*;


/**
 * @author ：我是金角大王
 * @date ：Created in 2019-3-11 10:12
 */
//@RestController
@RequestMapping(value = "/xcxem")
public class EMController {

    @Autowired
    private GpsToAddress gpsToAddress;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EquipMentService xcxEquipMentService;

    @GetMapping("/ems")
    public ReturnMessage insertMemberInfo(String longitude,String latitude,String citycode){
        if(citycode==null){
            GeoResults<RedisGeoCommands.GeoLocation<String>> radiusGeo = null;
            GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
            BigDecimal x = new BigDecimal(longitude);
            BigDecimal y = new BigDecimal(latitude);
            LocationInfo locationInfo = gpsToAddress.getAdress(latitude + "," + longitude);
            //设置geo查询参数
            RedisGeoCommands.GeoRadiusCommandArgs geoRadiusArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs();
            //查询返回结果包括距离和坐标
            geoRadiusArgs = geoRadiusArgs.includeCoordinates().includeDistance().sortAscending();
            try{
                radiusGeo = geoOps.radius("eml"+locationInfo.getAd_info().getCity_code(), new Circle(new Point(x.doubleValue(), y.doubleValue()), new Distance(Double.valueOf("50000000").doubleValue(), RedisGeoCommands.DistanceUnit.METERS)), geoRadiusArgs);
            }catch (Exception e){
                Map map = new HashMap();
                map.put("city",locationInfo.getAd_info().getCity());
                return new ReturnMessage("",map);
            }
            if(radiusGeo.getContent().size() == 0){
                Map map = new HashMap();
                map.put("city",locationInfo.getAd_info().getCity());
                return new ReturnMessage("",map);
            }
            List<Integer> ids = new ArrayList<>();
            Map<Integer,String> distancemap = new HashMap<>();
            for(Iterator it = radiusGeo.iterator(); it.hasNext(); ) {
                GeoResult geoResult = (GeoResult) it.next();
                RedisGeoCommands.GeoLocation<String> geoinfo = (RedisGeoCommands.GeoLocation<String>) geoResult.getContent();
                int id = Integer.valueOf(geoinfo.getName());
                double longval =geoResult.getDistance().getValue();
                ids.add(id);
                distancemap.put(id,String.valueOf(longval));
            }
            List<EquipMent> lists = xcxEquipMentService.getEquipMentsByIds(ids);
            for(EquipMent em : lists){
                em.setDistanceVal(distancemap.get(em.getId()));
            }
            Map map = new HashMap();
            map.put("lists",lists);
            map.put("city",locationInfo.getAd_info().getCity());
            return new ReturnMessage("",map);
        }else {
            GeoResults<RedisGeoCommands.GeoLocation<String>> radiusGeo = null;
            GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
            BigDecimal x = new BigDecimal(longitude);
            BigDecimal y = new BigDecimal(latitude);
            //设置geo查询参数
            RedisGeoCommands.GeoRadiusCommandArgs geoRadiusArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs();
            //查询返回结果包括距离和坐标
            geoRadiusArgs = geoRadiusArgs.includeCoordinates().includeDistance().sortAscending();
            try{
                radiusGeo = geoOps.radius("eml"+citycode, new Circle(new Point(x.doubleValue(), y.doubleValue()), new Distance(Double.valueOf("50000000").doubleValue(), RedisGeoCommands.DistanceUnit.METERS)), geoRadiusArgs);
            }catch (Exception e){
                Map map = new HashMap();
                return new ReturnMessage("",map);
            }
            if(radiusGeo.getContent().size() == 0){
                Map map = new HashMap();
                return new ReturnMessage("",map);
            }
            List<Integer> ids = new ArrayList<>();
            Map<Integer,String> distancemap = new HashMap<>();
            for(Iterator it = radiusGeo.iterator(); it.hasNext(); ) {
                GeoResult geoResult = (GeoResult) it.next();
                RedisGeoCommands.GeoLocation<String> geoinfo = (RedisGeoCommands.GeoLocation<String>) geoResult.getContent();
                int id = Integer.valueOf(geoinfo.getName());
                double longval =geoResult.getDistance().getValue();
                ids.add(id);
                distancemap.put(id,String.valueOf(longval));
            }
            List<EquipMent> lists = xcxEquipMentService.getEquipMentsByIds(ids);
            for(EquipMent em : lists){
                em.setDistanceVal(distancemap.get(em.getId()));
            }
            Map map = new HashMap();
            map.put("lists",lists);
            return new ReturnMessage("",map);
        }
    }

}
