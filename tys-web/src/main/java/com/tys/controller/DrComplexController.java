package com.tys.controller;

import com.tys.entity.vo.DrComplexSetting;
import com.tys.service.DrComplexService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Author haoxu
 * @Date 2019/3/25 17:31
 **/
@RestController
@RequestMapping(value = "/drcomplex", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DrComplexController {

    @Autowired
    private DrComplexService drComplexService;


    @PutMapping
    public ReturnMessage updateComplex(@RequestBody DrComplexSetting record){
        int msg = drComplexService.updateDrComplex(record);
        if(msg == 0){
            return new ReturnMessage("update failed");
        }
        return new ReturnMessage("success",null);
    }

    @GetMapping("/list")
    public ReturnMessage queryComplex(DrComplexSetting record){
        return new ReturnMessage("success",drComplexService.queryDrComplex(record));
    }
}
