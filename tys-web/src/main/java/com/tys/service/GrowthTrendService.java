package com.tys.service;

import com.tys.entity.vo.GrowthTrendVo;
import com.tys.entity.vo.MemberAnalysisVo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface GrowthTrendService {
    List<GrowthTrendVo> listDetectRecordByDay();

    List<GrowthTrendVo> listDetectRecordByWeek();

    List<GrowthTrendVo> listDetectRecordByMonth() throws ParseException;

    List<GrowthTrendVo> listMemberByDay();

    List<GrowthTrendVo> listMemberByWeek();

    List<GrowthTrendVo> listMemberByMonth() throws ParseException;

    List<GrowthTrendVo> listMemberSex();

    Map<String,List<Integer>> listAgeRatio();
}
