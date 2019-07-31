package com.tys.controller;

import com.tys.entity.vo.Goods;
import com.tys.entity.vo.MemberInfo;
import com.tys.entity.vo.UserGoodsLog;
import com.tys.exception.NoSessionException;
import com.tys.service.GoodsService;
import com.tys.service.MemberInfoService;
import com.tys.service.UserGoodsLogService;
import com.tys.util.vo.ReturnMessage;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Api(value="/goods",description="用户兑奖相关接口",tags={"用户兑奖相关接口"})
@RestController
@RequestMapping(value="/userGoods")
public class UserGoodsLogController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserGoodsLogService userGoodsLogService;


    @Autowired
    private MemberInfoService memberInfoService;

    @ApiOperation(value = "获取兑奖条件")
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功",response= Goods.class)
    })
    @PostMapping("/getStatus")
    public ReturnMessage getStatus(HttpServletRequest request, Integer goodsId){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        Goods goods = goodsService.getGoodsById(goodsId);
        if(goods.getNum()<1){
            return new ReturnMessage("nonum");
        }
        if(goods.getPoint()>mi.getExpSum()){
            return new ReturnMessage("nopoint");
        }
        if(goods.getWinTime()>mi.getTopWinTime()){
            return new ReturnMessage("nowintime");
        }
        return new ReturnMessage("success",goods);
    }
    @ApiOperation(value = "执行兑奖")
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功",response= Goods.class)
    })
    @PostMapping("/insert")
    @Transactional
    public ReturnMessage insertUserGoodsLog(HttpServletRequest request, @ApiParam(name = "goodsId", value = "奖品ID号", required = true) @RequestParam Integer goodsId, @ApiParam(name = "phone", value = "电话", required = true) @RequestParam String phone, @ApiParam(name = "name", value = "昵称", required = true) @RequestParam String name,@ApiParam(name = "address", value = "地址", required = true) @RequestParam String address){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        Goods goods = goodsService.getGoodsById(goodsId);
        if(goods.getNum()<1){
            return new ReturnMessage("nonum");
        }
        UserGoodsLog userGoodsLog =new UserGoodsLog();
        int result =  userGoodsLogService.insertUserGoodsLog(userGoodsLog);
        memberInfoService.updateExpSumById(mi.getId(),goods.getPoint()*(-1));
        return new ReturnMessage("success",result);
    }
}
