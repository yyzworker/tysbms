package com.tys.service;

import com.tys.entity.vo.MemberInfo;

public interface MemberInfoService {

    MemberInfo selectByPhone(String phone);

    int insertSelective(MemberInfo record);

    int updateSelective(MemberInfo record);

    MemberInfo selectByOpenId(String openid);

    MemberInfo selectById(Integer id);

    int updatePhoneById(Integer id,String phone);

    int updateExpSumById(Integer id,Integer exp);
}
