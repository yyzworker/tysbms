package com.tys.controller;

import com.tys.entity.vo.WagerLog;
import com.tys.service.WagerLogService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//@RestController
//获取赌注列表信息，暂时不用
@RequestMapping(value="/wager")
public class WagerLogController {
    @Autowired
    private WagerLogService wagerLogService;


    @PostMapping("getWager")
    public ReturnMessage getNewRecord(HttpServletRequest request){
       WagerLog wagerLog =  wagerLogService.getNewRecord();
       if(wagerLog!=null){
           return new ReturnMessage("success",wagerLog);
       }else{
           return new ReturnMessage("norecored");
       }
    }
}
