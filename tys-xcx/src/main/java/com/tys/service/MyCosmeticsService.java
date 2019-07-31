package com.tys.service;

import com.tys.entity.es.Commodity;
import com.tys.entity.vo.MyCosmeticsVo;

import java.util.List;

public interface MyCosmeticsService {
    List<MyCosmeticsVo> insertMyCosmetics(MyCosmeticsVo record);

    List<MyCosmeticsVo> deleteMyCosmetics(MyCosmeticsVo record);

    List<MyCosmeticsVo> queryMyCosmetics(Integer memberId);

    List<Commodity> searchEsByIds(List<MyCosmeticsVo> myCosmeticsVosList);
}
