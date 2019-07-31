package com.tys.controller;

import com.tys.util.vo.ReturnMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author haoxu
 * @Date 2019/5/20 16:02
 **/
@RestController
@RequestMapping(value = "/status")
public class StatusController {

    @GetMapping
    public ReturnMessage status(){
        return new ReturnMessage("success", null);
    }

}
