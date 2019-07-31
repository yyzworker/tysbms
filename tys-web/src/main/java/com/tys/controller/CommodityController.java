package com.tys.controller;

import com.tys.entity.vo.CommodityVo;
import com.tys.service.CommodityService;
import com.tys.util.vo.ReturnMessage;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author haoxu
 * @Date 2019/4/1 11:09
 **/
@RestController
@RequestMapping(value = "/commodity", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CommodityController {
    
    @Autowired
    private CommodityService commodityService;

    @GetMapping("/list")
    public ReturnMessage searchCommodity(CommodityVo commodity){
            return new ReturnMessage("success",commodityService.search(commodity));
    }

    @PostMapping
    public ReturnMessage saveCommodity(@RequestBody CommodityVo commodity){
        return new ReturnMessage("success",commodityService.save(commodity));
    }

    @PutMapping
    public ReturnMessage updateCommodity(@RequestBody CommodityVo commodity){
        return new ReturnMessage("success",commodityService.update(commodity));
    }

    @DeleteMapping("/{id}")
    public ReturnMessage deleteCommodity(@PathVariable Integer id){
        commodityService.delete(id);
        return new ReturnMessage("success",null);
    }

    @GetMapping("/{id}")
    public ReturnMessage queryCommodityById(@PathVariable Integer id){
        return new ReturnMessage("success",commodityService.queryById(id));
    }

    @PostMapping("/name")
    public ReturnMessage exitsName(@RequestBody Map param){
        int size = commodityService.exitsName(param.get("name").toString());
        return new ReturnMessage("success",size == 0);
    }

}
