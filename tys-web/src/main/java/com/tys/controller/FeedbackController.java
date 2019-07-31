package com.tys.controller;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.Feedback;
import com.tys.service.FeedbackService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author wangzhihao
 * @Date 2019/6/14 14:05
 **/
@RestController
@RequestMapping(value = "/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;


    @PutMapping("/{id}")
    public ReturnMessage deleteMember(@PathVariable Integer id){
        int message = feedbackService.processFeedback(id);
        if (message == 0) {
            return new ReturnMessage("process failed");
        }
        return new ReturnMessage("process success", null);
    }

    @GetMapping("/list")
    public ReturnMessage queryMember(Feedback feedback){
        PageInfo<Feedback> feedbackInfo = feedbackService.queryFeedback(feedback);
        return new ReturnMessage("query success",feedbackInfo);
    }


}
