package com.tys.controller;

import com.tys.service.TagInfoService;
import com.tys.util.vo.ReturnMessage;
import com.tys.entity.vo.TagInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author haoxu
 * @Date 2019/3/6 14:36
 **/
@RestController
@RequestMapping(value = "/taginfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TagInfoController {
    
    @Autowired
    private TagInfoService tagInfoService;
    
    @PostMapping
    public ReturnMessage insertTagInfo(@RequestBody @Valid TagInfo record){
        int msg = tagInfoService.insert(record);
        if(msg == 0)
            return new ReturnMessage("save failed");
        return new ReturnMessage("save success",record);
    }

    @PutMapping
    public ReturnMessage updateTagInfo(@RequestBody @Valid TagInfo record){
        int msg = tagInfoService.update(record);
        if(msg == 0){
            return new ReturnMessage("modify failed");
        }
        return new ReturnMessage("modify success",record);
    }

    @DeleteMapping("/{id}")
    public ReturnMessage deleteTagInfo(@PathVariable Integer id){
        int msg = tagInfoService.deleteById(id);
        if(msg == 0)
            return new ReturnMessage("delete failed");
        return new ReturnMessage("delete success",id);
    }

    @GetMapping("/{id}")
    public ReturnMessage selectTagInfoById(@PathVariable Integer id){
        return new ReturnMessage("query success",tagInfoService.selectById(id));
    }

    @GetMapping("/list")
    public ReturnMessage queryTagInfo(TagInfo record){
        return new ReturnMessage("query success",tagInfoService.query(record));
    }
}
