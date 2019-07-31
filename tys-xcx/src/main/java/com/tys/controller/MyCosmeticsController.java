package com.tys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tys.entity.es.Commodity;
import com.tys.entity.vo.DetectRecord;
import com.tys.entity.vo.MemberInfo;
import com.tys.entity.vo.MyCosmeticsVo;
import com.tys.exception.NoSessionException;
import com.tys.service.CommodityService;
import com.tys.service.DetectRecordService;
import com.tys.service.MemberInfoService;
import com.tys.service.MyCosmeticsService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：王志浩
 * @date ：Created in 2019-4-8 14:20
 */
//@RestController
@RequestMapping(value = "/mycosmetics")
public class MyCosmeticsController {

    @Autowired
    private MyCosmeticsService myCosmeticsService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private DetectRecordService detectRecordService;

    private static Map<Integer,String> commodityType = Maps.newHashMap();

    {
        commodityType.put(1,"洁面");
        commodityType.put(2,"化妆水");
        commodityType.put(3,"精华");
        commodityType.put(4,"乳霜");
        commodityType.put(5,"眼霜");
        commodityType.put(6,"面膜");
        commodityType.put(7,"防晒");
        commodityType.put(8,"洗护");
    }

    @GetMapping(value = "/queryMyCosmetics")
    public ReturnMessage queryMyCosmetics(HttpServletRequest request) {
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        Map resultJson = new HashMap();
        List<MyCosmeticsVo> myCosmeticsVoList =  myCosmeticsService.queryMyCosmetics(mi.getId());
        Map<String,Commodity> commoditysMap  = esIdsQuery(myCosmeticsVoList);
        List<Commodity> commodityList = new ArrayList<>();
        Map<String,List> resultMap = new HashMap<>();
        for(MyCosmeticsVo mcv:myCosmeticsVoList){
            List cosmeticInfo = resultMap.get(mcv.getTypeCode().toString());
            if (cosmeticInfo == null) {
                cosmeticInfo = new ArrayList();
                Commodity currentCommodity = commoditysMap == null ? null:commoditysMap.get(mcv.getCosmeticId());
                if(currentCommodity != null) {
                    Commodity returnCommodity = copyParameters(currentCommodity);
                    cosmeticInfo.add(returnCommodity);
                    commodityList.add(currentCommodity);
                    resultMap.put(mcv.getTypeCode().toString(), cosmeticInfo);
                }
            } else {
                Commodity currentCommodity = commoditysMap == null ? null:commoditysMap.get(mcv.getCosmeticId());
                if(currentCommodity != null) {
                    Commodity returnCommodity = copyParameters(currentCommodity);
                    cosmeticInfo.add(returnCommodity);
                    commodityList.add(currentCommodity);
                }
            }
        }
        resultJson.put("ygdzp",resultMap);
        MemberInfo memberInfo = memberInfoService.selectById(mi.getId());
        DetectRecord detectRecord = detectRecordService.selectValidLastTime(mi.getId());
        Map compareDegree = commodityService.computeSuitability(memberInfo,detectRecord,commodityList);
        resultJson.put("zpyx",computeZpyx(myCosmeticsVoList));
        resultJson.put("ppd",compareDegree);
        return new ReturnMessage("success", resultJson);
    }

    @PostMapping(value = "/insertMyCosmetics")
    public ReturnMessage insertMyCosmetics(HttpServletRequest request,@Valid MyCosmeticsVo myCosmeticsVo){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        myCosmeticsVo.setMemberId(mi.getId());
        Map resultJson = new HashMap();
        List<Commodity> commodityList = new ArrayList<>();
        MemberInfo memberInfo = memberInfoService.selectById(myCosmeticsVo.getMemberId());
        List<MyCosmeticsVo> myCosmeticsVoList = myCosmeticsService.insertMyCosmetics(myCosmeticsVo);
        DetectRecord detectRecord = detectRecordService.selectValidLastTime(myCosmeticsVo.getMemberId());
        commodityList.add( commodityService.queryById(Integer.parseInt(myCosmeticsVo.getCosmeticId())));
        Map compareDegree = commodityService.computeSuitability(memberInfo,detectRecord,commodityList);
        resultJson.put("zpyx",computeZpyx(myCosmeticsVoList));
        resultJson.put("ppd",compareDegree);
        return new ReturnMessage("success", resultJson);
    }

    @PostMapping(value = "/deleteMyCosmetics")
    public ReturnMessage deleteMyCosmetics(HttpServletRequest request,MyCosmeticsVo myCosmeticsVo){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        myCosmeticsVo.setMemberId(mi.getId());
        List<MyCosmeticsVo> myCosmeticsVoList = myCosmeticsService.deleteMyCosmetics(myCosmeticsVo);
        JSONObject resultJson = new JSONObject();
        resultJson.put("zpyx",computeZpyx(myCosmeticsVoList));
        return new ReturnMessage("delete success", resultJson);
    }

    public Map<String,List> computeMyCosmeticsInfo(List<MyCosmeticsVo> myCosmeticsVoList){
        Map<String,List> redisMap = new HashMap<>();
        for(MyCosmeticsVo mcv:myCosmeticsVoList){
          List cosmeticInfo = redisMap.get(mcv.getTypeCode().toString());
            if (cosmeticInfo == null) {
                cosmeticInfo = new ArrayList();
                cosmeticInfo.add(commodityService.queryById(Integer.parseInt(mcv.getCosmeticId())));
                redisMap.put(mcv.getTypeCode().toString(), cosmeticInfo);
            }
            else {
                cosmeticInfo.add(commodityService.queryById(Integer.parseInt(mcv.getCosmeticId())));
            }
        }
        return redisMap;
    }

    public JSONArray computeZpyx(List<MyCosmeticsVo> myCosmeticsVoListOriginal ){
        JSONArray zpyxArray = new JSONArray();
        Map<String,List> resultMap = new HashMap<>();
        //计算妆品印象时，妆品信息有可能在产品库中删除，需要排除掉
        Map<String,Commodity> commoditysMap  = esIdsQuery(myCosmeticsVoListOriginal);
        List<MyCosmeticsVo> myCosmeticsVoList = filterDeletedProduct(commoditysMap,myCosmeticsVoListOriginal);
        //根据妆品信息计算妆品印象
        //第一个维度
        int myCosmeticsSize = myCosmeticsVoList.size();
        if(myCosmeticsSize == 0){
            zpyxArray.add("空旷的梳妆台");
        }else if(myCosmeticsSize >= 1 && myCosmeticsSize<=5){
            zpyxArray.add("新晋护肤王牌");
        }else if(myCosmeticsSize >= 6 && myCosmeticsSize<=9){
            zpyxArray.add("护肤能力者");
        }else if(myCosmeticsSize >= 10){
            zpyxArray.add("剁手王者");
        }

        //第二个维度
        for(MyCosmeticsVo mcv:myCosmeticsVoList){
            List cosmeticInfo = resultMap.get(mcv.getTypeCode().toString());
            if (cosmeticInfo == null) {
                cosmeticInfo = new ArrayList();
                cosmeticInfo.add(mcv);
                resultMap.put(mcv.getTypeCode().toString(), cosmeticInfo);
            }
            else {
                cosmeticInfo.add(mcv);
            }
        }
       Integer myCosmeticsTypeNumber = resultMap.size();
        if(myCosmeticsTypeNumber>=1 && myCosmeticsTypeNumber<=3){
            zpyxArray.add("佛系护肤");
        }else if(myCosmeticsTypeNumber>=4 && myCosmeticsTypeNumber<=5){
            zpyxArray.add("潮流风向标");
        }else if(myCosmeticsTypeNumber>=6){
            zpyxArray.add("美妆大神");
        }

        //第三个维度
        if(null != resultMap){
            int maxSize = 0;
            int currentSize = 0;
            String maxTypeName ="";
            for(int i=0;i<8;i++){
                if(null != resultMap.get(String.valueOf(i))){
                    currentSize = resultMap.get(String.valueOf(i)).size();
                }
                if(currentSize>maxSize){
                    maxSize = currentSize;
                    maxTypeName = commodityType.get(i);
                }
            }
            if(maxSize > 0 &&  !"".equals(maxTypeName)){
                if(maxSize >= 3 && maxSize <=5){
                    zpyxArray.add(maxTypeName+"狂热爱好者");
                }else if(maxSize >= 6 && maxSize <=9){
                    zpyxArray.add(maxTypeName+"行走种草机");
                }else if(maxSize >= 10){
                    zpyxArray.add(maxTypeName+"收藏家");
                }
            }
        }


        return zpyxArray ;
    }

    private List<MyCosmeticsVo> filterDeletedProduct(Map<String,Commodity> commoditysMap, List<MyCosmeticsVo> myCosmeticsVoListOriginal) {
        List<MyCosmeticsVo>  myCosmeticsVoList = new ArrayList<>();
        for(MyCosmeticsVo curMcv : myCosmeticsVoListOriginal){
            if(commoditysMap.get(curMcv.getCosmeticId())!=null){
                myCosmeticsVoList.add(curMcv);
            }
        }
        return myCosmeticsVoList;
    }

    public Map<String,Commodity> esIdsQuery(List<MyCosmeticsVo> myCosmeticsVoList) {
        Map<String,Commodity> commodityMap = new HashMap();
        List<Commodity> comodityList = myCosmeticsService.searchEsByIds(myCosmeticsVoList);
        if(ObjectUtils.isEmpty(comodityList)){
            return null;
        }
        for(Commodity commodity:comodityList){
            if(commodity.getStatus() == 1){
                commodityMap.put(commodity.getId().toString(),commodity);
            }
        }
        return commodityMap;
    }

    public Commodity copyParameters(Commodity original){
        Commodity returnCommodity = new Commodity();
        returnCommodity.setId(original.getId());
        returnCommodity.setImgUrl(original.getImgUrl());
        returnCommodity.setName(original.getName());
        returnCommodity.setEnName(original.getEnName());
        returnCommodity.setSafeGrade(original.getSafeGrade());
        returnCommodity.setTypeCode(original.getTypeCode());
        return returnCommodity;
    }

}
