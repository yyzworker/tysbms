package com.tys.controller;

import com.tys.entity.vo.GrowthTrendVo;
import com.tys.entity.vo.HomeVo;
import com.tys.service.GrowthTrendService;
import com.tys.service.HomeService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王志浩
 * @date 2018年5月7日 下午4:59:47
 */
@RestController
@RequestMapping(value = "/home", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HomeController {
	
	@Autowired
	private HomeService homeService;

	@GetMapping("/getHomeInfo")
	public ReturnMessage getHomeInfo(){
		HomeVo homeVo = homeService.getHomeInfo();
		return new ReturnMessage("success", homeVo);
	}
}
