package com.tys.controller;

import com.tys.entity.es.EventType;
import com.tys.entity.vo.MemberInfo;
import com.tys.entity.vo.UserExpLog;
import com.tys.entity.vo.UserShareLog;
import com.tys.exception.NoSessionException;
import com.tys.service.MemberInfoService;
import com.tys.service.UserExpLogService;
import com.tys.service.UserShareLogService;
import com.tys.service.UserSignLogService;
import com.tys.util.tool.DateUtil;
import com.tys.util.vo.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
@Api(value="/userShare",description="用户分享",tags={"用户分享接口"})
@RestController
@RequestMapping(value = "/userShare")
public class UserShareLogController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private UserShareLogService userShareLogService;

    @Autowired
    private UserExpLogService userExpLogService;

    //分享任务
    @ApiOperation(notes = "用户分享任务接口", value = "用户分享任务接口")
    @PostMapping("/insert")
    @Transactional
    public ReturnMessage insertUserShareLog(HttpServletRequest request,@ApiParam(name = "rwId", value = "分享任务ID号", required = true)  @RequestParam Integer rwId){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        Integer userid = mi.getId();
        Map result = new HashMap();
        Object value = redisTemplate.opsForValue().get("userShare-" + rwId + "-" + mi.getId());
        if(value!=null) {
            return new ReturnMessage("inserted ");
        }
        UserShareLog  userShareLog = new UserShareLog();
        userShareLog.setUserId(mi.getId());
        Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
        userShareLog.setShareDate(now);
        result.put("data", userShareLogService.insertUserShareLog(userShareLog));
        redisTemplate.opsForValue().set("userShare-" + rwId + "-" + mi.getId(),userShareLog.getId(), DateUtil.getRemainSecByToday(null));
        memberInfoService.updateExpSumById(userid,EventType.FX_exp);
        mi.setExpSum(Integer.valueOf(mi.getExpSum()+EventType.FX_exp)); //同步到session中
        UserExpLog userExpLog =new UserExpLog();
        userExpLog.setUserId(userid);
        userExpLog.setEventDate(now);
        userExpLog.setEventId(userShareLog.getId());
        userExpLog.setEventName(EventType.FX_name);
        userExpLog.setEventType(EventType.FX);
        userExpLog.setPoint(EventType.FX_exp);
        userExpLogService.insertUserExpLog(userExpLog);
        if(userShareLogService.getUserShareCount(userid,rwId) == 1){ //如果首次分享
            memberInfoService.updateExpSumById(userid,EventType.SCFX_exp);
            mi.setExpSum(Integer.valueOf(mi.getExpSum()+EventType.SCFX_exp)); //同步到session中
            UserExpLog userExpLog_sc =new UserExpLog();
            userExpLog_sc.setUserId(userid);
            userExpLog_sc.setEventDate(now);
            userExpLog_sc.setEventId(userShareLog.getId());
            userExpLog_sc.setEventName(EventType.SCFX_name);
            userExpLog_sc.setEventType(EventType.SCFX);
            userExpLog_sc.setPoint(EventType.SCFX_exp); //首次分享为5积分
            userExpLogService.insertUserExpLog(userExpLog_sc);
        }
        request.getSession().setAttribute("memberInfo",mi);
        return new ReturnMessage("success",result);
    }
}
