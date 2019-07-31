package com.tys.controller;

import com.alibaba.fastjson.JSONObject;
import com.tys.core.WXBizDataCrypt;
import com.tys.entity.es.EventType;
import com.tys.entity.vo.MemberInfo;
import com.tys.entity.wx.WXPhoneInfo;
import com.tys.entity.wx.WXUserInfo;
import com.tys.exception.NoSessionException;
import com.tys.service.MemberInfoService;
import com.tys.util.config.WxXcxConfig;
import com.tys.util.tool.DateUtil;
import com.tys.util.vo.ReturnMessage;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-3-11 10:12
 */
@Api(value="/memberinfo",description="用户相关接口",tags={"用户操作接口"})
@RestController
@RequestMapping(value = "/memberinfo")
public class MemberController {
    @Autowired
    private WXBizDataCrypt wXBizDataCrypt;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WxXcxConfig wxXcxConfig;

    @Autowired
    private MemberInfoService memberInfoService;

    private Logger log = LoggerFactory.getLogger(MemberController.class);

    @PutMapping
    public ReturnMessage updateMemberInfo(HttpServletRequest request,MemberInfo memberInfo){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");if(mi==null){
            throw new NoSessionException();
        }
        memberInfo.setId(mi.getId());
        memberInfoService.updateSelective(memberInfo);
        request.getSession().setAttribute("memberInfo",memberInfo);
        return new ReturnMessage("success",null);
    }

    @ApiOperation(notes = "登录测试接口", value = "测试登录接口，后端开发测试使用")
    @PostMapping("/login_test")
    @Transactional
    public ReturnMessage login_test(HttpServletRequest request,@ApiParam(name = "openId", value = "微信用户openId", required = true) @RequestParam String openId){
//        String openId = (String)params.get("openId");
        HashMap<String, String> map = new HashMap<>();
        MemberInfo mi =  memberInfoService.selectByOpenId(openId);
        if(mi!=null){
            HttpSession session =request.getSession();
            session.setAttribute("memberInfo",mi);
        }else{
           return new ReturnMessage("no user");
        }
        return new ReturnMessage("success",mi);
    }

// @PostMapping("/login")
 @Transactional
public ReturnMessage login(@RequestBody Map params){
        String code = (String)params.get("code");
     HashMap<String, String> map = new HashMap<>();
     map.put("appid",wxXcxConfig.getAppId());
     map.put("appsecret",wxXcxConfig.getAppSecret());
     map.put("code",code);
     ResponseEntity<String> responseString= restTemplate.getForEntity("https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={appsecret}&js_code={code}&grant_type=authorization_code", String.class, map);
     if(responseString.getStatusCode()==HttpStatus.OK){
         String rjson = responseString.getBody();
         JSONObject json = JSONObject.parseObject(rjson);
         if (json == null || json.get("session_key") == null) {
             log.warn("insertMemberInfo error, no session_key");
             return new ReturnMessage("no session_key");
         }else{

         }
     }
     return new ReturnMessage(responseString.toString());
}

    @ApiOperation(notes = "微信小程序登录接口", value = "微信小程序登录接口")
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功",response=MemberInfo.class)
    })
    @PostMapping
    @Transactional
    public ReturnMessage insertMemberInfo(HttpServletRequest request, String code, String encryptData, String iv, Integer inviter){
        System.out.println(code);
        HashMap<String, String> map = new HashMap<>();
        map.put("appid",wxXcxConfig.getAppId());
        map.put("appsecret",wxXcxConfig.getAppSecret());
        map.put("code",code);
        ResponseEntity<String> responseString= restTemplate.getForEntity("https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={appsecret}&js_code={code}&grant_type=authorization_code", String.class, map);
        if(responseString.getStatusCode()==HttpStatus.OK){
            String rjson = responseString.getBody();
            JSONObject json = JSONObject.parseObject(rjson);
            if (json == null || json.get("session_key") == null) {
                log.warn("insertMemberInfo error, no session_key");
                return new ReturnMessage("no session_key");
            }
            WXUserInfo userinfo = wXBizDataCrypt.getWXuserInfo(json.get("session_key").toString(),encryptData,iv,wxXcxConfig.getAppId());
            if(userinfo == null || userinfo.getOpenId() == null){
                return new ReturnMessage("no user id");
            }
            String openid = userinfo.getOpenId();
            Map result = new HashMap();
            MemberInfo memberInfo = memberInfoService.selectByOpenId(openid);
            if(memberInfo == null){
                result.put("type","i");
                memberInfo = new MemberInfo();
                memberInfo.setOpenId(userinfo.getOpenId());
                memberInfo.setUnionId(userinfo.getUnionId());
                memberInfo.setName(userinfo.getNickName());
                memberInfo.setAvatarUrl(userinfo.getAvatarUrl());
                if(userinfo.getGender()==1){
                    memberInfo.setSex((byte)1);
                }else if (userinfo.getGender()==2||userinfo.getGender()==0){
                    memberInfo.setSex((byte)2);
                }
                memberInfo.setInviter(inviter);
                new Timestamp(Calendar.getInstance().getTimeInMillis());
                try {
                    Date birth = new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01");
                    memberInfo.setBirthdate(new Timestamp(birth.getTime()));
                    memberInfo.setAge(DateUtil.getAge(birth));
                } catch (Exception e) {
                    return new ReturnMessage(e.getMessage());
                }
                Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
                memberInfo.setRegistrationDate(now);
                memberInfo.setDetectionsLasttime(now);
                memberInfo.setDetectionsNumber(0);
                memberInfo.setSilentNumber(0);
                memberInfo.setExpSum(EventType.ZC_exp);
                memberInfo.setTopWinTime(0);
                memberInfo.setTopWinTime(0);
                memberInfoService.insertSelective(memberInfo);
                //增加积分事件

            }else{
                result.put("type","s");
            }
            result.put("memberInfo",memberInfo);
            HttpSession session =request.getSession();
            session.setAttribute("memberInfo",memberInfo);
            result.put("sessionid",session.getId());
            return new ReturnMessage("success",result);
        }
        return new ReturnMessage(responseString.toString());
    }


    @PostMapping("/phone")
    @Transactional
    public ReturnMessage insertMemberPhone(HttpServletRequest request,String code, String encryptData, String iv){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("appid",wxXcxConfig.getAppId());
        map.put("appsecret",wxXcxConfig.getAppSecret());
        map.put("code",code);
        ResponseEntity<String> responseString= restTemplate.getForEntity("https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={appsecret}&js_code={code}&grant_type=authorization_code", String.class, map);
        if(responseString.getStatusCode()==HttpStatus.OK){
            String rjson = responseString.getBody();
            JSONObject json = JSONObject.parseObject(rjson);
            if(json.get("session_key") == null){
                log.warn("session_key is null");
                return new ReturnMessage("session_key is null");
            }else{
                WXPhoneInfo wxphoneinfo = wXBizDataCrypt.getWXphoneInfo(json.get("session_key").toString(),encryptData,iv,wxXcxConfig.getAppId());
                if(wxphoneinfo == null || wxphoneinfo.getPhoneNumber() == null){
                    return new ReturnMessage("no phone number");
                }
                String phone = wxphoneinfo.getPhoneNumber();
                memberInfoService.updatePhoneById(mi.getId(),phone);
                return new ReturnMessage("insert sucess",phone);
            }
        }
        return new ReturnMessage(responseString.toString());
    }

    /**
     * 获取全国积分排名
     * @param request
     * @return
     */
    @ApiOperation(notes = "获取全国积分排名", value = "获取全国积分排名")
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功",response=MemberInfo.class)
    })
    @PostMapping("/getRanking")
    public ReturnMessage getRanking(HttpServletRequest request){
        List<MemberInfo> miList= null;
        return new ReturnMessage("insert sucess",miList);
    }

    /**
     * 获取自己邀请好友排名
     * @param request
     * @return
     */
    @ApiOperation(notes = "获取自己邀请好友排名", value = "获取自己邀请好友排名")
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功",response=MemberInfo.class)
    })
    @PostMapping("/getRankingByInviter")
    public ReturnMessage getRankingByUserId(HttpServletRequest request){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }

        List<MemberInfo> miList= null;

        return new ReturnMessage("insert sucess",miList);

    }
}
