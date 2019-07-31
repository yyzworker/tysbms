package com.tys.controller;

import com.tys.entity.vo.MemberInfo;
import com.tys.entity.vo.Notice;
import com.tys.service.NoticeService;
import com.tys.util.vo.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(value="/notice",description="通知公告",tags={"通知公告接口"})
@RestController
@RequestMapping(value="/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @ApiOperation(value = "获取通知列表")
    @ApiResponses({
            @ApiResponse(code=200,message="请求成功",response= Notice.class)
    })
    @PostMapping("getList")
    public ReturnMessage getNoticeList(HttpServletRequest request ){
        return new ReturnMessage("success",noticeService.getNoticeList());
    }


}
