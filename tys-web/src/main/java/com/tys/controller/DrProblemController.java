package com.tys.controller;

import com.tys.entity.vo.DrProblemSetting;
import com.tys.service.DrProblemService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Author haoxu
 * @Date 2019/3/25 17:31
 **/
@RestController
@RequestMapping(value = "/drproblem", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DrProblemController {

    @Autowired
    private DrProblemService drProblemService;

    @PutMapping
    public ReturnMessage updateDrProblem(@RequestBody DrProblemSetting record){
        int msg = drProblemService.updateDrProblem(record);
        if(msg == 0){
            return new ReturnMessage("update failed");
        }
        return new ReturnMessage("success",null);
    }

    @GetMapping("/list")
    public ReturnMessage queryDrProblem(DrProblemSetting record){
        return new ReturnMessage("success",drProblemService.queryDrProblems(record));
    }
}
