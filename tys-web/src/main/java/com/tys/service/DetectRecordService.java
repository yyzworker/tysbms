package com.tys.service;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.DetectMemberVo;
import com.tys.entity.vo.DetectRecordVo;
import com.tys.entity.vo.DetectScoreVo;
import com.tys.entity.vo.PhotoMsg;
import com.tys.util.vo.ReturnMessage;


public interface DetectRecordService {

    ReturnMessage uploadDetectRecord(PhotoMsg photoMsg);

    ReturnMessage computeDetectRecord(DetectScoreVo detectScoreVo);

    PageInfo<DetectMemberVo> queryDetectMember(DetectMemberVo detectMemberVo);

    DetectRecordVo queryDrSettingData(Integer id);

    int updateThreeDate(Integer rid);
}
