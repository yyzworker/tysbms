package com.tys.controller;

import com.tys.entity.vo.GrowthTrendVo;
import com.tys.entity.vo.MemberAnalysisVo;
import com.tys.service.GrowthTrendService;
import com.tys.util.vo.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value = "/growthTrend", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GrowthTrendController {
	
	@Autowired
	private GrowthTrendService growthTrendService;

	@GetMapping("/listDetectRecordByDay")
	public ReturnMessage listDetectRecordByDay(){
		List<GrowthTrendVo> listGrowthTrendVo = growthTrendService.listDetectRecordByDay();
        Map resultMap = process(listGrowthTrendVo);
        if(listGrowthTrendVo.size()>0){
            resultMap.put("yestodayAdd",listGrowthTrendVo.get(listGrowthTrendVo.size()-1).getCount());
        }else{
            resultMap.put("yestodayAdd",0);
        }
		return new ReturnMessage("success", resultMap);
	}

	@GetMapping("/listDetectRecordByWeek")
	public ReturnMessage listDetectRecordByWeek(){
		List<GrowthTrendVo> listGrowthTrendVo = growthTrendService.listDetectRecordByWeek();
        Map resultMap = process(listGrowthTrendVo);
        return new ReturnMessage("success", resultMap);
	}

	@GetMapping("/listDetectRecordByMonth")
	public ReturnMessage listDetectRecordByMonth() throws ParseException {
		List<GrowthTrendVo> listGrowthTrendVo = growthTrendService.listDetectRecordByMonth();
        Map resultMap = process(listGrowthTrendVo);
        return new ReturnMessage("success", resultMap);
	}
    @GetMapping("/listMemberByDay")
    public ReturnMessage listMemberByDay(){
        List<GrowthTrendVo> listGrowthTrendVo = growthTrendService.listMemberByDay();
        Map resultMap = process(listGrowthTrendVo);
        if(listGrowthTrendVo.size()>0){
            resultMap.put("yestodayAdd",listGrowthTrendVo.get(listGrowthTrendVo.size()-1).getCount());
        }else{
            resultMap.put("yestodayAdd",0);
        }
        return new ReturnMessage("success", resultMap);
    }

    @GetMapping("/listMemberByWeek")
    public ReturnMessage listMemberByWeek(){
        List<GrowthTrendVo> listGrowthTrendVo = growthTrendService.listMemberByWeek();
        Map resultMap = process(listGrowthTrendVo);
        return new ReturnMessage("success", resultMap);
    }

    @GetMapping("/listMemberByMonth")
    public ReturnMessage listMemberByMonth() throws ParseException{
        List<GrowthTrendVo> listGrowthTrendVo = growthTrendService.listMemberByMonth();
        Map resultMap = process(listGrowthTrendVo);
        return new ReturnMessage("success", resultMap);
    }

    @GetMapping("/listMemberSex")
    public ReturnMessage listMemberSex(){
        List<GrowthTrendVo> listGrowthTrendVo = growthTrendService.listMemberSex();
        return new ReturnMessage("success", listGrowthTrendVo);
    }

    @GetMapping("/listAgeRatio")
    public ReturnMessage listAgeRatio(){
        Map<String,List<Integer>> resultMap = growthTrendService.listAgeRatio();
        return new ReturnMessage("success", resultMap);
    }

    public Map process(List<GrowthTrendVo> listGrowthTrendVo){
        Map resultMap = new HashMap();
        List<String> xdata = new ArrayList<>();
        List<Integer> ydata = new ArrayList<>();
        for (GrowthTrendVo current : listGrowthTrendVo) {
            xdata.add(current.getTime());
            ydata.add(current.getCount());
        }
        resultMap.put("xdata",xdata);
        resultMap.put("ydata",ydata);
        return resultMap;
    }

}
