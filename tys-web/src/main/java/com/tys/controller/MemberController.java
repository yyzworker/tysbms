package com.tys.controller;


import com.github.pagehelper.PageInfo;
import com.tys.service.MemberInfoService;
import com.tys.util.vo.ReturnMessage;
import com.tys.entity.vo.MemberInfo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author haoxu
 * @Date 2019/3/6 11:30
 **/
@RestController
@RequestMapping(value = "/memberinfo")
public class MemberController {

    @Autowired
    private MemberInfoService memberInfoService;

    @GetMapping("/list")
    public ReturnMessage queryMember(MemberInfo memberInfo){
        PageInfo<MemberInfo> pageInfo = memberInfoService.queryMember(memberInfo);
        return new ReturnMessage("query success",pageInfo);
    }

    @GetMapping("/mycosmetics/{id}")
    public ReturnMessage queryMyCosmetics(@PathVariable Integer id){
        return new ReturnMessage("success",memberInfoService.queryMyCosmetics(id));
    }

}
