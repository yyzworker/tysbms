package com.tys.controller;

import com.tys.entity.vo.Goods;
import com.tys.entity.vo.UserWagerLog;
import com.tys.service.GoodsService;
import com.tys.util.vo.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="/goods",description="奖品相关接口",tags={"奖品相关接口"})
@RestController
@RequestMapping(value="/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @ApiOperation(value = "获取奖口列表")
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功",response= Goods.class)
    })
    @PostMapping("/list")
    public ReturnMessage getGoodsList(){
        return new ReturnMessage("success",goodsService.getGoodsList());
    }
}
