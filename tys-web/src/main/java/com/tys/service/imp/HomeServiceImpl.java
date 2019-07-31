package com.tys.service.imp;

import com.tys.entity.vo.HomeVo;
import com.tys.service.HomeService;
import com.tys.service.imp.dao.HomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeMapper homeMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "HomeInfo" , unless="#result == null")
    public HomeVo getHomeInfo() {
        HomeVo result = new HomeVo();
        List<HomeVo> memberList = homeMapper.listMember();
        List<HomeVo> detectRecordList = homeMapper.listDetectRecord();
        List<HomeVo> equipmentList = homeMapper.listEquipment();
        if(ObjectUtils.isEmpty(memberList)){
            result.setMemberMan("0%");
            result.setMemberWoman("0%");
            result.setMemberTotal("0");
            result.setMemberNewAdd("0");
        }else{
            HomeVo memberVo = memberList.get(0);
            int man = Integer.parseInt(memberVo.getMemberMan());
            int woman = Integer.parseInt(memberVo.getMemberWoman());
            int total = Integer.parseInt(memberVo.getMemberTotal());
            result.setMemberMan(compute(man,total));
            result.setMemberWoman(compute(woman,total));
            result.setMemberTotal(parseNumber(new BigDecimal(memberVo.getMemberTotal())));
            result.setMemberNewAdd(memberList.get(0).getMemberNewAdd());
        }

        if(ObjectUtils.isEmpty(detectRecordList)){
            result.setDetectMan("0%");
            result.setDetectWoman("0%");
            result.setDetectTotal("0");
            result.setDetectNewAdd("0");
        }else{
            HomeVo detectVo = detectRecordList.get(0);
            int man = Integer.parseInt(detectVo.getDetectMan());
            int woman = Integer.parseInt(detectVo.getDetectWoman());
            int total = Integer.parseInt(detectVo.getDetectTotal());
            result.setDetectMan(compute(man,total));
            result.setDetectWoman(compute(woman,total));
            result.setDetectTotal(parseNumber(new BigDecimal(detectVo.getDetectTotal())));
            result.setDetectNewAdd(parseNumber(new BigDecimal(detectVo.getDetectNewAdd())));
        }

        if(ObjectUtils.isEmpty(equipmentList)){
            result.setEquipmentNormal("0");
            result.setEquipmentAbNormal("0");
            result.setEquipmentShutDown("0");
            result.setEquipmentTotal("0");
        }else{
            HomeVo equipmentVo = equipmentList.get(0);
            result.setEquipmentNormal(parseNumber(new BigDecimal(equipmentVo.getEquipmentNormal())));
            result.setEquipmentAbNormal(parseNumber(new BigDecimal(equipmentVo.getEquipmentAbNormal())));
            result.setEquipmentShutDown(parseNumber(new BigDecimal(equipmentVo.getEquipmentShutDown())));
            result.setEquipmentTotal(parseNumber(new BigDecimal(equipmentVo.getEquipmentTotal())));
        }
        return result;
     }

    public  String parseNumber(BigDecimal bd) {
        String pattern = ",###,###";
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(bd);
    }
    public String compute(int a , int b){
        //首先判断分母和分子为0
        String result = "";
        if(a == 0 || b == 0){
            result =  "0%";
        }else{
            Float num = (float) a*100/b;
            DecimalFormat df = new DecimalFormat("0.00");
            String s = df.format(num);
            result =  s + "%";
        }
        //如果整除则不保留小数。
      return result.replace(".00","");
    }
}
