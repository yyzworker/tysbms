package com.tys.controller;

import com.tys.entity.es.EventType;
import com.tys.entity.vo.*;
import com.tys.exception.NoSessionException;
import com.tys.service.MemberInfoService;
import com.tys.service.UserExpLogService;
import com.tys.service.UserWagerLogService;
import com.tys.service.WagerLogService;
import com.tys.util.tool.DateUtil;
import com.tys.util.vo.ReturnMessage;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="/userWager",description="用户下注",tags={"用户下注相关接口"})
@RestController
@RequestMapping(value = "/userWager")
public class UserWagerLogController {

    @Autowired
    private UserWagerLogService userWagerLogService;

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private UserExpLogService userExpLogService;

    @Autowired
    private WagerLogService wagerLogService;

    @ApiOperation(value = "获取下注状态信息")
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功",response=WagerStatus.class)
    })
    @PostMapping("/getStatus")

    public ReturnMessage getWagerStatus(HttpServletRequest request){
        HttpSession session =  request.getSession();
        WagerStatus result = new WagerStatus();
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        //获取需要投注的赌注信息
        WagerLog wagerLog =  wagerLogService.getNewRecord();
        Integer wagerId = wagerLog.getId();
       List<Map<String,Object>> list =  userWagerLogService.getUserWagerByWagerId(wagerId);
      for(Map<String,Object> map:list){
          if(map.get("type").equals("0")){
              result.setDownCount((Long)map.get("zs"));
          }
          if(map.get("type").equals("1")){
              result.setUpCount((Long)map.get("zs"));
          }
      }
      UserWagerLog userWagerLog = userWagerLogService.getUserWagerLogByUserId(mi.getId(),wagerId);
        result.setWagerId(wagerId);
        result.setSecCount(DateUtil.getRemainSecByToday(wagerLog.getEndDate()));
        if(userWagerLog!=null){
            result.setDone("1");
            result.setType(userWagerLog.getType());
        }else{
            result.setDone("0");
        }
        return new ReturnMessage("success",result);
    }
    /** //不需要wagerId
     * 执行下注
     * @param request
     * @param wagerId
     * @param type
     * @return
     */
    @ApiOperation(value = "用户下注接口")
    @PostMapping("/insert")
    public ReturnMessage exeUserWagger(HttpServletRequest request,Integer wagerId,@ApiParam(name = "type", value = "下注类型 0(买跌)/1(买涨)", required = true) String type){
        //检查用户分数够不够
       HttpSession session =  request.getSession();
        Map result = new HashMap();
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        Integer expsum  = mi.getExpSum();
        if(expsum< EventType.XZ_exp){
            return new ReturnMessage("no_expsum");
        }
        Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
        UserWagerLog userWagerLog = new UserWagerLog();
        userWagerLog.setUsePoint(EventType.XZ_exp);
        userWagerLog.setType(type);
        userWagerLog.setWagerId(wagerId);
        userWagerLog.setUserId(mi.getId());
        userWagerLog.setCreateDate(now);
        userWagerLogService.insertUserWagerLog(userWagerLog);
        //扣用户积分
        memberInfoService.updateExpSumById(mi.getId(),EventType.XZ_exp*(-1));
        mi.setExpSum(mi.getExpSum() + EventType.XZ_exp*(-1));
        //记录扣积分日志
        UserExpLog userExpLog = new UserExpLog();
        userExpLog.setPoint(EventType.XZ_exp*-1);
        userExpLog.setEventType(EventType.XZ);
        userExpLog.setEventName(EventType.XZ_name);
        userExpLog.setEventDate(now);
        userExpLog.setEventId(userWagerLog.getId());
        userExpLog.setUserId(mi.getId());
        userExpLogService.insertUserExpLog(userExpLog);
        //记录完毕
        request.getSession().setAttribute("memberInfo",mi);
        return new ReturnMessage("success",result);
    }

    /**
     * 获取用户最近下注列表
     * @param request
     * @return
     */
    @ApiOperation(value = "获取用户近期战绩列表")
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功",response=UserWagerLog.class)
    })
    @PostMapping("/getUserWagerList")
    public ReturnMessage getUserWagerLogList(HttpServletRequest request){
        HttpSession session =  request.getSession();
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        Integer userId  = mi.getId();
        List<UserWagerLog> result =userWagerLogService.getUserWagerLogList(userId);
        return new ReturnMessage("success",result);
    }
}
