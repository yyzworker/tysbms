package com.tys.controller;

import com.tys.entity.vo.CompositionVo;
import com.tys.service.CompositionService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author haoxu
 * @Date 2019/3/28 14:45
 **/
@RestController
@RequestMapping(value = "/composition", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CompositionController {

    @Autowired
    private CompositionService compositionService;


    @GetMapping("/list")
    public ReturnMessage searchComposition(CompositionVo composition){
        return new ReturnMessage("success",compositionService.search(composition));
    }

    @PostMapping
    public ReturnMessage saveComposition(@RequestBody CompositionVo composition){
        return new ReturnMessage("success",compositionService.save(composition));
    }

    @PutMapping
    public ReturnMessage updateComposition(@RequestBody  CompositionVo composition){
        return new ReturnMessage("success",compositionService.update(composition));
    }

    @DeleteMapping("/{id}")
    public ReturnMessage deleteComposition(@PathVariable Integer id){
        return new ReturnMessage("success",compositionService.delete(id));
    }

    @GetMapping("/{id}")
    public ReturnMessage queryCompositionById(@PathVariable Integer id){
        return new ReturnMessage("success",compositionService.queryById(id));
    }

}
