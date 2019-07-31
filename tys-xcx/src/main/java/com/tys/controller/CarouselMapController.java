package com.tys.controller;

import com.tys.service.CarouselMapService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author haoxu
 * @Date 2019/5/29 14:55
 **/
//@RestController
@RequestMapping(value = "/carouselmap")
public class CarouselMapController {

    @Autowired
    private CarouselMapService carouselMapService;

    @GetMapping
    public ReturnMessage queryEffective(){
        return new ReturnMessage("success",carouselMapService.selectEffective());
    }
}
