package com.tys.controller;

import com.tys.entity.vo.DetectMemberVo;
import com.tys.entity.vo.DetectScoreVo;
import com.tys.entity.vo.PhotoMsg;
import com.tys.service.DetectRecordService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @Author haoxu
 * @Date 2019/3/14 9:52
 **/
@RestController
@RequestMapping(value = "/detectrecord", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DetectRecordController {


    @Autowired
    private DetectRecordService detectRecordService;

    @PostMapping
    public ReturnMessage uploadDetectRecord(@RequestBody PhotoMsg photoMsg) {
        return detectRecordService.uploadDetectRecord(photoMsg);
    }

    @PostMapping("/threed")
    public ReturnMessage threeDetectRecord(@RequestBody Map data) {
        if(data.get("rid") != null){
            int msg = detectRecordService.updateThreeDate(Integer.valueOf(data.get("rid").toString()));
            if (msg == 0) {
                return new ReturnMessage("record not found or photo path not found");
            } else
                return new ReturnMessage("success", null);
        }else {
            return new ReturnMessage("rid is required");
        }
    }


    @PostMapping("/score")
    public ReturnMessage computeDetectRecord(@RequestBody DetectScoreVo detectScoreVo) {
        return detectRecordService.computeDetectRecord(detectScoreVo);
    }

    @GetMapping("/list")
    public ReturnMessage queryDetectMember(DetectMemberVo detectMemberVo) {
        return new ReturnMessage("success", detectRecordService.queryDetectMember(detectMemberVo));
    }

    @GetMapping("/drsettingdata/{id}")
    public ReturnMessage queryDrSettingDataById(@PathVariable Integer id) {
        return new ReturnMessage("success", detectRecordService.queryDrSettingData(id));
    }
}
