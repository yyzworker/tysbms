package com.tys.controller;

import com.google.common.collect.Maps;
import com.tys.entity.vo.MemberInfo;
import com.tys.entity.vo.MyCosmeticsVo;
import com.tys.exception.NoSessionException;
import com.tys.service.CommodityService;
import com.tys.service.MyCosmeticsService;
import com.tys.util.vo.ReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-4-2 13:16
 */
//@RestController
@RequestMapping(value = "/commodity")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @Autowired
    private MyCosmeticsService myCosmeticsService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/list")
    public ReturnMessage searchcommodity(String name,@RequestParam(required=false) List<Byte> typeCode, @RequestParam(required=false) List<Byte> suitableSkinCodes, Byte fitSensitive, Byte pregnantCautionCount, int pageNum){
        logger.info("searchValue:"+name);
        return new ReturnMessage("success",commodityService.search(name,typeCode,suitableSkinCodes,fitSensitive,pregnantCautionCount,pageNum));
    }

    @GetMapping("/list/composition")
    public ReturnMessage searchCommodityByCompName(String name,@RequestParam(required=false) List<Byte> typeCode, @RequestParam(required=false) List<Byte> suitableSkinCodes, Byte fitSensitive, Byte pregnantCautionCount, int pageNum){
        logger.info("searchCompositionValue:"+name);
        return new ReturnMessage("success",commodityService.searchByCompName(name,typeCode,suitableSkinCodes,fitSensitive,pregnantCautionCount,pageNum));
    }

    @GetMapping("/{id}")
    public ReturnMessage queryById(@PathVariable Integer id){
        return new ReturnMessage("success",commodityService.queryById(id));
    }

    @GetMapping("/suitability/{id}")
    public ReturnMessage computeSuitability(@PathVariable Integer id, HttpServletRequest request){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        Map map = commodityService.computeSuitabilityOne(mi,id);
        List<MyCosmeticsVo> myCosmeticsVos =myCosmeticsService.queryMyCosmetics(mi.getId());
        if(map == null){
            map = Maps.newHashMap();
        }
        if(!ObjectUtils.isEmpty(myCosmeticsVos)){
            Optional op = myCosmeticsVos.stream().filter(myCosmeticsVo -> myCosmeticsVo.getCosmeticId().equals(id.toString())).findAny();
            if(op.isPresent()){
                map.put("collected",true);
            }else {
                map.put("collected",false);
            }
        }else {
            map.put("collected",false);
        }
        return new ReturnMessage("success",map);
    }

}
