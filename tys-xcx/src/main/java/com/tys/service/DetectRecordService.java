package com.tys.service;

import com.tys.entity.vo.DetectRecord;
import com.tys.entity.vo.DetectRecordVo;

import java.util.List;
import java.util.Map;

public interface DetectRecordService {

    List<DetectRecord> selectByMemberId(Integer memberId);

    Map selectTwoByMemberId(Integer memberId);

    Map queryTimesByMemberId(Integer memberId);

    DetectRecord selectValidLastTime(Integer mid);

    DetectRecordVo queryDrSettingData(Integer id);

    String selectThreePath(Integer mid);
}
