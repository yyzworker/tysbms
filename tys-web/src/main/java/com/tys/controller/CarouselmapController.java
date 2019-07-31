package com.tys.controller;

import com.tys.entity.vo.CarouselmapVo;
import com.tys.service.CarouselmapService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author 王志浩
 * @date 2018年4月26日 下午4:59:47
 */
@RestController
@RequestMapping(value = "/carouselmap", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CarouselmapController {
	
	@Autowired
	private CarouselmapService carouselmapservice;

	@PostMapping
	public ReturnMessage insertCarouselmap(@RequestBody @Valid CarouselmapVo carouselmap) {
		int message = carouselmapservice.insertCarouselmap(carouselmap);
		if (message == 0) {
			return new ReturnMessage("保存失败");
		}
		return new ReturnMessage("保存成功", carouselmap);
	}

	@GetMapping("/list")
	public ReturnMessage queryCarouselmaps(CarouselmapVo carouselmapVo){
		return new ReturnMessage("success",carouselmapservice.queryCarouselmaps(carouselmapVo));
	}

	@PutMapping
	public ReturnMessage updateUser(@RequestBody @Valid CarouselmapVo carouselmap){
		int message = carouselmapservice.updateCarouselmap(carouselmap);
		if(message == 0){
			return new ReturnMessage("modify failed");
		}
		return new ReturnMessage("modify success",carouselmap);
	}

	@GetMapping("/{id}")
	public ReturnMessage queryCarouselmapById(@PathVariable Integer id){
		return new ReturnMessage("success",carouselmapservice.getCarouselmapById(id));
	}

	@DeleteMapping("/{id}")
	public ReturnMessage deleteCarouselmapById(@PathVariable Integer id){
		return new ReturnMessage("success",carouselmapservice.deleteCarouselmapById(id));
	}

	@PostMapping("/name")
    public ReturnMessage exitsName(@RequestBody Map param){
        Object id = param.get("id");
        Integer idInt = null;
        if(id != null){
            idInt = Integer.parseInt(id.toString());
        }
        String name =param.get("name").toString();
        int size = carouselmapservice.exitsName(idInt,name);
        return new ReturnMessage("success",size == 0);
    }

}
