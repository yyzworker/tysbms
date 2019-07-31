package com.tys.controller;

import com.tys.entity.vo.MemberInfo;
import com.tys.exception.NoSessionException;
import com.tys.service.DetectRecordService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author haoxu
 * @Date 2019/3/14 13:37
 **/
//@RestController
@RequestMapping(value = "/detectrecord", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DetectRecordController {

    @Autowired
    private DetectRecordService detectRecordService;

    @GetMapping
    public ReturnMessage queryRecordsByMemberId(HttpServletRequest request){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        return new ReturnMessage("success",detectRecordService.selectByMemberId(mi.getId()));
    }

    @GetMapping("/times")
    public ReturnMessage queryTimesByMemberId(HttpServletRequest request){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        return new ReturnMessage("success",detectRecordService.queryTimesByMemberId(mi.getId()));
    }

    @GetMapping("/two")
    public ReturnMessage queryTwoRecords(HttpServletRequest request){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        return new ReturnMessage("success",detectRecordService.selectTwoByMemberId(mi.getId()));
    }

    @GetMapping("/valid")
    public ReturnMessage selectValidLastTime(HttpServletRequest request){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        return new ReturnMessage("success",detectRecordService.selectValidLastTime(mi.getId()));
    }

    @GetMapping("/drsettingdata/{id}")
    public ReturnMessage queryDrSettingDataById(@PathVariable Integer id){
        return new ReturnMessage("success",detectRecordService.queryDrSettingData(id));
    }

    @GetMapping("/threepath")
    public ReturnMessage queryThreePath(HttpServletRequest request){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        return new ReturnMessage("success",detectRecordService.selectThreePath(mi.getId()));
    }
}
