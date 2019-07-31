package com.tys.service;


import com.tys.entity.vo.EquipMent;

import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/6 13:53
 **/
public interface EquipMentService {

    public List<EquipMent> getEquipMentsByIds(List<Integer> ids);

}
