package com.tys.controller;

import com.tys.service.TagInfoService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/19 16:53
 **/
//@RestController
public class TagInfoController {

    @Autowired
    private TagInfoService xcxTagInfoService;

    @GetMapping(value = "/taginfo")
    public ReturnMessage getTagInf(@RequestParam Integer type){
        return new ReturnMessage("success",xcxTagInfoService.getTagInfos(type));
    }

    @GetMapping(value = "/tagsinfo")
    public ReturnMessage getTagsInf(@RequestParam List<Integer> types){
        return new ReturnMessage("success",xcxTagInfoService.getTagsInfos(types));
    }
}
