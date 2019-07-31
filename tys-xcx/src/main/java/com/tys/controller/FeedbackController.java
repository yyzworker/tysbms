package com.tys.controller;

import com.tys.entity.vo.Feedback;
import com.tys.entity.vo.MemberInfo;
import com.tys.exception.NoSessionException;
import com.tys.service.FeedbackService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author haoxu
 * @Date 2019/6/14 14:05
 **/
//@RestController
@RequestMapping(value = "/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ReturnMessage insertFeedback(HttpServletRequest request,Feedback feedback){
        MemberInfo mi = (MemberInfo)request.getSession().getAttribute("memberInfo");
        if(mi==null){
            throw new NoSessionException();
        }
        feedback.setMemberId(mi.getId());
        feedback.setStatus((byte)1);
        int msg = feedbackService.insertFeedback(feedback);
        if(msg > 0)
            return new ReturnMessage("insert success",null);
        else
            return new ReturnMessage("insert failed");
    }
}
