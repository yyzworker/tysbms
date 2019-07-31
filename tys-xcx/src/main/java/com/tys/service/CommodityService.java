package com.tys.service;

import com.tys.entity.es.Commodity;
import com.tys.entity.vo.DetectRecord;
import com.tys.entity.vo.MemberInfo;
import com.tys.util.es.vo.Page;

import java.util.List;
import java.util.Map;

/**
 * @author ：我是金角大王
 * @date ：Created in 2019-4-2 13:22
 */
public interface CommodityService {

    Page<Commodity> search(String name, List<Byte> typeCode, List<Byte> suitableSkinCodes, Byte fitSensitive, Byte pregnantCautionCount, int pageNumber);

    Page<Commodity> searchByCompName(String name, List<Byte> typeCode, List<Byte> suitableSkinCodes, Byte fitSensitive, Byte pregnantCautionCount, int pageNumber);

    Commodity queryById(Integer id);

    Map computeSuitability(MemberInfo memberInfo, DetectRecord detectRecord,List<Commodity> commodities);

    Map computeSuitabilityOne(MemberInfo memberInfo,Integer cid);

}
