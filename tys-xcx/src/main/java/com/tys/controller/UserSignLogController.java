package com.tys.controller;

import com.tys.entity.es.EventType;
import com.tys.entity.vo.MemberInfo;
import com.tys.entity.vo.UserExpLog;
import com.tys.entity.vo.UserSignLog;
import com.tys.exception.NoSessionException;
import com.tys.service.MemberInfoService;
import com.tys.service.UserExpLogService;
import com.tys.service.UserSignLogService;
import com.tys.util.tool.DateUtil;
import com.tys.util.vo.ReturnMessage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Api(value="/userSign",description="用户签到",tags={"用户签到接口"})
@RestController
@RequestMapping(value="/userSign")
public class UserSignLogController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private UserSignLogService userSignLogService;

    /**
     * 用户签到
     */
    @Autowired
    private UserExpLogService userExpLogService;
    @PostMapping("/insert")
    @Transactional
    public ReturnMessage insertSignLog(HttpServletRequest request ){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        Integer userid = mi.getId();
        //检查是否已经登录过
        Map result = new HashMap();
       Object value = redisTemplate.opsForValue().get("userSign-"+userid);
       if(value!=null) {
           return new ReturnMessage("inserted");
       }
        UserSignLog  userSignLog = new UserSignLog();
        userSignLog.setUserId(userid);
        Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
        userSignLog.setSignDate(now);
        result.put("data", userSignLogService.insertUserSignLog(userSignLog));
        redisTemplate.opsForValue().set("userSign-"+userid,userSignLog.getId(), DateUtil.getRemainSecByToday(null));
        memberInfoService.updateExpSumById(userid,EventType.QD_exp);
        mi.setExpSum(mi.getExpSum() +EventType.QD_exp );
        UserExpLog userExpLog =new UserExpLog();
        userExpLog.setUserId(userid);
        userExpLog.setEventDate(now);
        userExpLog.setEventId(userSignLog.getId());
        userExpLog.setEventName("userSignLog");
        userExpLog.setEventType(EventType.QD);
        userExpLog.setPoint(EventType.QD_exp);
        userExpLogService.insertUserExpLog(userExpLog);
        request.getSession().setAttribute("memberInfo",mi);
        return new ReturnMessage("success",result);
    }
}
