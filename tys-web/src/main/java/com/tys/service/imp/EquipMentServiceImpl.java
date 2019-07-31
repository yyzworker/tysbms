package com.tys.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tys.constant.Constant;
import com.tys.entity.vo.CarouselmapVo;
import com.tys.entity.vo.EmAdvertise;
import com.tys.entity.vo.EquipMent;
import com.tys.entity.vo.LocationInfo;
import com.tys.service.EmAdvertiseService;
import com.tys.service.EquipMentService;
import com.tys.service.imp.dao.CarouselmapMapper;
import com.tys.service.imp.dao.EquipMentMapper;
import com.tys.util.map.GpsToAddress;
import com.tys.util.vo.ReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class EquipMentServiceImpl implements EquipMentService {

    private Logger log = LoggerFactory.getLogger(EquipMentService.class);

    @Autowired
    private EquipMentMapper equipMentMapper;

    @Autowired
    private EmAdvertiseService emAdvertiseService;

    @Autowired
    private CarouselmapMapper carouselmapMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GpsToAddress gpsToAddress;

    @Override
    public int insertEquipMent(EquipMent equipMent) {
        return equipMentMapper.insertSelective(equipMent);
    }

    @Override
    public int deleteEquipMent(Integer id) {
        return equipMentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateEquipMent(EquipMent equipMent) {
        EmAdvertise emAdvertise = new EmAdvertise();
        emAdvertise.setAdvertiseType(equipMent.getAdvertiseType());
        emAdvertise.setAdvertise(equipMent.getAdvertise());
        emAdvertise.setStatus((byte)1);
        emAdvertise.setEmIds(Lists.newArrayList(equipMent.getId()));
        emAdvertiseService.updateMultipleEmAdvertise(emAdvertise);
        return equipMentMapper.updateByPrimaryKeySelective(equipMent);
    }

    @Override
    public int updateEmAdvertise(EquipMent equipMent) {
        if(!ObjectUtils.isEmpty(equipMent.getEmIds())){
            List<Integer> emIds = equipMent.getEmIds();
            EmAdvertise emAdvertise = new EmAdvertise();
            emAdvertise.setAdvertise(equipMent.getAdvertise());
            emAdvertise.setAdvertiseType(equipMent.getAdvertiseType());
            emAdvertise.setStatus((byte)1);
            if(0 == emIds.iterator().next()){
                emIds = equipMentMapper.selectAllIds();
            }
            emAdvertise.setEmIds(emIds);
            return emAdvertiseService.updateMultipleEmAdvertise(emAdvertise);
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = true)
    public EquipMent queryEquipMentById(Integer id) {
        return equipMentMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public PageInfo<EquipMent> queryEquipMent(EquipMent equipMent) {
        PageInfo<EquipMent> pageInfo = null;
        if(equipMent.getCurrentPage() != null && equipMent.getPageSize() != null){
            pageInfo = PageHelper.startPage(equipMent.getCurrentPage(), equipMent.getPageSize()).doSelectPageInfo(() -> equipMentMapper.queryByEntity(equipMent));
        }else {
            pageInfo = new PageInfo(equipMentMapper.queryByEntity(equipMent));
        }
        ValueOperations<String, String> valueOps =  redisTemplate.opsForValue();
        return pageInfo;
    }


    @Override
    public ReturnMessage updateEquipMentByImie(EquipMent record) {
        String latitude = record.getEmLatitude();
        String longitude = record.getEmLongitude();
        if(ObjectUtils.isEmpty(latitude) || ObjectUtils.isEmpty(longitude)){
            log.warn("注册失败，地理坐标为空，latitude:{},longitude:{}",latitude,longitude);
            return  new ReturnMessage("地理坐标为空");
        }
        LocationInfo locationInfo = gpsToAddress.getAdress(latitude + "," + longitude);
        if(!"0".equals(locationInfo.getStatus())){
            log.warn("设备注册失败，获取地理位置信息错误，latitude:{},longitude:{},msg:{}",latitude,longitude,locationInfo.getMessage());
            return new ReturnMessage(locationInfo.getMessage());
        }
        record.setEmAddress(locationInfo.getAddress());
        record.setPlaceAddress(locationInfo.getAddress());
        record.setEmProvince(locationInfo.getAddress_component().getProvince());
        record.setEmCity(locationInfo.getAddress_component().getCity());
        EquipMent equipMent = equipMentMapper.selectByImie(record.getEmImie());
        if(equipMent != null){
            equipMent.setEmNumber(record.getEmNumber());
            equipMent.setEmIccid(record.getEmIccid());
            equipMent.setEmAddress(record.getEmAddress());
            equipMent.setEmLatitude(record.getEmLatitude());
            equipMent.setEmLongitude(record.getEmLongitude());
            equipMent.setEmProvince(record.getEmProvince());
            equipMent.setEmCity(record.getEmCity());
            equipMent.setEmUpushId(record.getEmUpushId());
            equipMent.setEmVersion(record.getEmVersion());
            equipMent.setPlaceAddress(record.getPlaceAddress());
            equipMent.setEmPhoto(record.getEmPhoto());
            equipMent.setEmStatus((byte)1);
            equipMentMapper.updateByPrimaryKeySelective(equipMent);
            log.info("设备启动成功n,id:{},location:{},imie:{},version:{}，iccid:{}",equipMent.getId(),locationInfo.getAddress(),record.getEmImie(),record.getEmVersion(),record.getEmIccid());
        }else {
            equipMent = new EquipMent();
            equipMent.setEmNumber(record.getEmNumber());
            equipMent.setEmIccid(record.getEmIccid());
            equipMent.setEmImie(record.getEmImie());
            equipMent.setEmName(locationInfo.getAddress());
            equipMent.setEmAddress(record.getEmAddress());
            equipMent.setEmLatitude(record.getEmLatitude());
            equipMent.setEmLongitude(record.getEmLongitude());
            equipMent.setEmProvince(record.getEmProvince());
            equipMent.setEmCity(record.getEmCity());
            equipMent.setEmUpushId(record.getEmUpushId());
            equipMent.setEmVersion(record.getEmVersion());
            equipMent.setPlaceAddress(record.getPlaceAddress());
            equipMent.setEmStatus((byte)1);
            equipMent.setEmHardware((byte)1);
            equipMent.setEmPhoto(record.getEmPhoto());
            equipMentMapper.insertSelective(equipMent);
            log.info("设备注册成功1,id:{},location:{},imie:{},version:{},iccid:{}",equipMent.getId(),locationInfo.getAddress(),record.getEmImie(),record.getEmVersion(),record.getEmIccid());
        }
        if(ObjectUtils.isEmpty(equipMent.getStatus())){
            EmAdvertise emAdvertise = new EmAdvertise();
            emAdvertise.setEmId(equipMent.getId());
            emAdvertise.setStatus((byte)0);
            emAdvertiseService.insertEmAdvertise(emAdvertise);
        }
        String key = Constant.EMHEARTBEAT + equipMent.getId().toString();
        ValueOperations<String, String> valueOps =  redisTemplate.opsForValue();
        valueOps.set(key, String.valueOf(System.currentTimeMillis()));
        redisTemplate.expire(key,90, TimeUnit.SECONDS);
        GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
        BigDecimal x = new BigDecimal(equipMent.getEmLongitude());
        BigDecimal y = new BigDecimal(equipMent.getEmLatitude());
        geoOps.add("eml"+locationInfo.getAd_info().getCity_code(),new Point(x.doubleValue(), y.doubleValue()),String.valueOf(equipMent.getId()));
        JSONObject json = new JSONObject();
        json.put("id",equipMent.getId());
        return new ReturnMessage(locationInfo.getMessage(),json);
    }

    @Override
    public int updateLasttime(Integer id, Timestamp lasttime) {
        return equipMentMapper.updateLasttime(id,lasttime);
    }

    @Override
    public int updateHardware(Integer id, Byte emHardware) {
        return equipMentMapper.updateHardware(id,emHardware);
    }

    @Override
    public String getImieById(Integer id) {
        return equipMentMapper.queryImieById(id);
    }

    @Override
    public String getUpushIdById(Integer id) {
        return equipMentMapper.queryUpushIdById(id);
    }

    @Override
    public int updateEMStatusByImie(String emImie,Byte emStatus) {
        return equipMentMapper.updateEMStatusByImie(emImie,emStatus);
    }

    @Override
    public int updateEMStatusById(Integer id,Byte emStatus) {
        return equipMentMapper.updateEMStatusById(id,emStatus);
    }



    @Override
    @Transactional(readOnly = true)
    public List<CarouselmapVo> getAdvertiseInfo(Integer id) {
        List<EmAdvertise> emAdvertises =  emAdvertiseService.selectEmAdvertiseByEmId(id);
        if(!ObjectUtils.isEmpty(emAdvertises) ){
            EmAdvertise emAdvertise = emAdvertises.iterator().next();
            String advertise = emAdvertise.getAdvertise();
            if(!ObjectUtils.isEmpty(advertise)){
                String[] advertiseIds = advertise.split(",");
                List<CarouselmapVo> carouselmaps = carouselmapMapper.getAdvertiseInfo(Lists.newArrayList(advertiseIds));
                carouselmaps.stream().forEach(carouselmapVo -> {
                    carouselmapVo.setUpdateTime(emAdvertise.getUpdateTime());
                });
                return carouselmaps;
            }
        }
        return Lists.newArrayList();
    }
}
