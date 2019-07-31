package com.tys.controller;

import com.tys.entity.vo.CompositionVo;
import com.tys.service.CompositionService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author ：我是金角大王
 * @date ：Created in 2019-3-30 10:08
 */
//@RestController
@RequestMapping("/composition")
public class CompositionController {
    @Autowired
    private CompositionService compositionService;

    @GetMapping("/list")
    public ReturnMessage searchComposition(String name, int pageNum){
        return new ReturnMessage("success",compositionService.search(name,pageNum));
    }

}
