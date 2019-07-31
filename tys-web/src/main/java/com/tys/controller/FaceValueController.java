package com.tys.controller;

import com.tys.service.FaceValueService;
import com.tys.util.vo.ReturnMessage;
import com.tys.entity.vo.FaceValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author haoxu
 * @Date 2019/3/6 13:52
 **/
@RestController
@RequestMapping(value = "/facevalue", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FaceValueController {

    @Autowired
    private FaceValueService faceValueService;

    @PostMapping
    public ReturnMessage insertFaceValue(@RequestBody @Valid FaceValue faceValue){
        int message = faceValueService.insert(faceValue);
        if(message == 0){
            return  new ReturnMessage("save failed");
        }
        return new ReturnMessage("save success",faceValue);
    }

    @GetMapping("/{id}")
    public ReturnMessage selectById(@PathVariable Integer id){
        return new ReturnMessage("query success",faceValueService.selectById(id));
    }

    @PutMapping
    public ReturnMessage updateFaceValue(@RequestBody @Valid FaceValue faceValue){
        int message = faceValueService.update(faceValue);
        if(message == 0){
            return  new ReturnMessage("modify failed");
        }
        return new ReturnMessage("success",faceValue);
    }

    @DeleteMapping("/{id}")
    public ReturnMessage deleteById(@PathVariable Integer id){
        int message = faceValueService.deleteById(id);
        if(message == 0){
            return  new ReturnMessage("delete failed");
        }
        return new ReturnMessage("success",id);
    }

    @GetMapping("/list")
    public ReturnMessage queryFaceValue( FaceValue faceValue){
        return new ReturnMessage("success",faceValueService.query(faceValue));
    }
}
