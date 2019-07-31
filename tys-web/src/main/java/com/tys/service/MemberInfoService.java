package com.tys.service;

import com.github.pagehelper.PageInfo;
import com.tys.entity.es.Commodity;
import com.tys.entity.vo.MemberInfo;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/6 11:30
 **/
public interface MemberInfoService {

    int insertMember(MemberInfo memberInfo);

    PageInfo<MemberInfo> queryMember(MemberInfo memberInfo);

    List<String> queryMyCosmetics(Integer mid);
}
