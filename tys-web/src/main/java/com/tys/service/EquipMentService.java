package com.tys.service;

import com.github.pagehelper.PageInfo;
import com.tys.entity.vo.CarouselmapVo;
import com.tys.entity.vo.EquipMent;
import com.tys.util.vo.ReturnMessage;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/6 13:53
 **/
public interface EquipMentService {

    int insertEquipMent(EquipMent equipMent);

    int deleteEquipMent(Integer id);

    int updateEquipMent(EquipMent equipMent);

    int updateEmAdvertise(EquipMent equipMent);

    EquipMent queryEquipMentById(Integer id);

    PageInfo<EquipMent> queryEquipMent(EquipMent equipMent);

    ReturnMessage updateEquipMentByImie(EquipMent equipMent);

    int updateLasttime(Integer id, Timestamp lasttime);

    int updateHardware(Integer id, Byte emHardware);

    String getImieById(Integer id);

    String getUpushIdById(Integer id);

    int updateEMStatusByImie(String emImie,Byte emStatus);

    int updateEMStatusById(Integer id,Byte emStatus);

    List<CarouselmapVo> getAdvertiseInfo(Integer id);
}
