package com.tys.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.tys.constant.Constant;
import com.tys.entity.vo.EquipMent;
import com.tys.service.EquipMentService;
import com.tys.upush.UPush;
import com.tys.util.tool.Md5Util;
import com.tys.util.vo.ReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-3-1 10:08
 */
@RestController
@RequestMapping(value = "/equipment")
public class EMController {

    private Logger log = LoggerFactory.getLogger(EMController.class);

    @Autowired
    private EquipMentService equipMentService;

    @Autowired
    private RedisTemplate redisTemplate;

    private UPush uPush = new UPush();

    @PostMapping
    public ReturnMessage insertEquipMent(@RequestBody @Valid EquipMent equipMent){
        int message = equipMentService.insertEquipMent(equipMent);
        if (message == 0) {
            return new ReturnMessage("save failed");
        }
        return new ReturnMessage("save success", equipMent);
    }
    @GetMapping("/{id}")
    public ReturnMessage queryEquipMentById(@PathVariable Integer id){
        EquipMent equipMent = equipMentService.queryEquipMentById(id);
        return new ReturnMessage("success",equipMent);
    }

    @GetMapping("/list")
    public ReturnMessage queryEquipMent(EquipMent equipMent){
        PageInfo<EquipMent> pageInfo = equipMentService.queryEquipMent(equipMent);
        return new ReturnMessage("success",pageInfo);
    }

    @DeleteMapping("/{id}")
    public ReturnMessage deleteEquipMent(@PathVariable Integer id){
        int message = equipMentService.deleteEquipMent(id);
        if(message == 0){
            return new ReturnMessage("delete failed");
        }
        return new ReturnMessage("delete success",id);
    }

    @PutMapping
    public ReturnMessage updateEquipMent(@RequestBody @Valid EquipMent equipMent){
        int message = equipMentService.updateEquipMent(equipMent);
        if(message == 0){
            return new ReturnMessage("modify failed");
        }
        return new ReturnMessage("modify success",equipMent);
    }

    @PutMapping("/advertise")
    public ReturnMessage updateEmAdvertise(@RequestBody EquipMent equipMent){
        int message = equipMentService.updateEmAdvertise(equipMent);
        if(message == 0){
            return new ReturnMessage("update failed");
        }
        return new ReturnMessage("update success",message);
    }


    @PostMapping("/android")
    public ReturnMessage updateEquipMentByImie(@RequestBody @Valid EquipMent record){
        return equipMentService.updateEquipMentByImie(record);
    }

    @PostMapping("/run")
    public ReturnMessage runEM(int emid,String key,int userid) {
        try{
            Map map = new HashMap<String,Object>();
            map.put("userid",userid);
            map.put("key",key);
            map.put("type","sys");
            String message = JSONUtils.toJSONString(map);
            String upushid = equipMentService.getUpushIdById(emid);
            uPush.sendMessage(upushid,message);
            log.info("设备启动成功，eid:{},推送message:{}",emid,message);
            return new ReturnMessage("send success",null);
        }catch (Exception e){
            log.error("启动错误，emid:{},error:{}",emid,e.getMessage());
        }
        return new ReturnMessage("send err");
    }

    @PostMapping("/upgrade")
    public ReturnMessage upgrade(@RequestBody EquipMent record) {
        try{
            Map map = new HashMap<String,Object>();
            map.put("type","upgrade");
            HashOperations hOp = redisTemplate.opsForHash();
            Map appVersion = null;
            if(record.getUpdateType() == 0){//测试版
                appVersion = hOp.entries("appBetaVersion");
            }else if(record.getUpdateType() == 1){//正式版
                appVersion = hOp.entries("appVersion");
            }
            if(appVersion == null){
                log.error("设备升级失败，未查询到设备升级信息");
                return new ReturnMessage("upgrade error");
            }
            map.put("version",appVersion.get("version"));
            map.put("summary",appVersion.get("summary"));
            String message = JSONUtils.toJSONString(map);
            EquipMent equipMent = equipMentService.queryEquipMentById(record.getId());
            uPush.sendMessage(equipMent.getEmUpushId(),message);
            equipMentService.updateEMStatusByImie(equipMent.getEmImie(),Byte.valueOf("2") );
            log.info("设备升级成功，eid:{},推送message:{}",record.getId(),message);
            return new ReturnMessage("upgrade success",null);
        }catch (Exception e){
            log.error("设备升级失败，emid:{},error:{}",record.getId() ,e.getMessage());
        }
        return new ReturnMessage("upgrade error");
    }


    @PostMapping("/reboot")
    public ReturnMessage rebootEM(@RequestBody EquipMent record) {
        try{
            Map map = new HashMap<String,Object>();
            map.put("type","reboot");
            String message = JSONUtils.toJSONString(map);
            ValueOperations vOp = redisTemplate.opsForValue();
            String key = Constant.EMHEARTBEAT + record.getId();
            if(vOp.get(key) != null){
                redisTemplate.delete(key);
                EquipMent equipMent = equipMentService.queryEquipMentById(record.getId());
                uPush.sendMessage(equipMent.getEmUpushId(),message);
                equipMentService.updateEMStatusByImie(equipMent.getEmImie(),Byte.valueOf("2") );
                log.info("设备重启成功，eid:{},推送message:{}",record.getId(),message);
            }
            return new ReturnMessage("reboot success",null);
        }catch (Exception e){
            log.error("设备重启失败，emid:{},error:{}",record.getId() ,e.getMessage());
        }
        return new ReturnMessage("reboot err");
    }

    //@PostMapping("/shutdown")
    public ReturnMessage shutdownEM(@RequestBody EquipMent record) {
        try{
            Map map = new HashMap<String,Object>();
            map.put("type","shutdown");
            String message = JSONUtils.toJSONString(map);
            EquipMent equipMent = equipMentService.queryEquipMentById(record.getId());
            uPush.sendMessage(equipMent.getEmUpushId(),message);
            equipMentService.updateEMStatusByImie(equipMent.getEmImie(),Byte.valueOf("3") );
            log.info("设备关机成功，eid:{},推送message:{}",record.getId(),message);
            return new ReturnMessage("shutdown success",null);
        }catch (Exception e){

        }
        return new ReturnMessage("shutdown err");
    }

    @PostMapping("/addApkVersion/{type}")
    public ReturnMessage addApkVersion(@RequestParam MultipartFile file,@PathVariable Integer type){
        try {
            String summary = Md5Util.md5FileInputStream(file.getInputStream());
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            String h = null;
            if(type == 0){
                h = "appBetaVersion";
            }else if(type == 1){
                h = "appVersion";
            }
            hashOps.put(h, "version", file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf(".apk")));
            hashOps.put(h, "summary", summary);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("MultipartFile转换为File时异常,fileName:{}",file.getOriginalFilename());
            return new ReturnMessage("save error");
        }
        return new ReturnMessage("save success", null);
    }

    @GetMapping("/getCurrentApkVersion")
    public ReturnMessage getCurrentApkVersion(){
        Map version = Maps.newHashMap();
        HashOperations hOp = redisTemplate.opsForHash();
        Map appVersion = hOp.entries("appVersion");
        Map appBetaVersion = hOp.entries("appBetaVersion");
        if(appVersion != null ){
            version.put("appVersion",appVersion.get("version"));
        }
        if(appBetaVersion != null){
            version.put("appBetaVersion",appBetaVersion.get("version"));
        }
        return new ReturnMessage("success",version);
    }

    @GetMapping("/getAdvertiseInfo/{id}")
    public ReturnMessage getAdvertiseInfo(@PathVariable Integer id){
       return new ReturnMessage("success", equipMentService.getAdvertiseInfo(id));
    }
}
