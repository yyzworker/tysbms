package com.tys.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tys.entity.es.Commodity;
import com.tys.entity.vo.MemberInfo;
import com.tys.entity.vo.MyCosmeticsVo;
import com.tys.service.MemberInfoService;
import com.tys.service.imp.dao.MemberInfoMapper;
import com.tys.util.es.server.ElasticsearchServer;
import com.tys.util.es.vo.Condition;
import com.tys.util.es.vo.QueryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/3/6 11:30
 **/
@Service
@Transactional
public class MemberInfoServiceImpl implements MemberInfoService {

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Autowired
    private ElasticsearchServer elasticsearchServer;


    @Override
    public int insertMember(MemberInfo memberInfo) {
        return memberInfoMapper.insertSelective(memberInfo);
    }


    @Override
    @Transactional(readOnly = true)
    public PageInfo<MemberInfo> queryMember(MemberInfo memberInfo) {
        PageInfo<MemberInfo> pageInfo = null;
        if(memberInfo.getCurrentPage() != null && memberInfo.getPageSize() != null){
            pageInfo = PageHelper.startPage(memberInfo.getCurrentPage(), memberInfo.getPageSize()).doSelectPageInfo(() -> memberInfoMapper.queryMemberInfo(memberInfo));
        }else {
            pageInfo = new PageInfo(memberInfoMapper.queryMemberInfo(memberInfo));
        }
        return pageInfo;
    }


    @Override
    public List<String> queryMyCosmetics(Integer mid) {
        List<MyCosmeticsVo> cosmetics = memberInfoMapper.queryMyCosmetics(mid);
        List<String> names = Lists.newLinkedList();
        if(!ObjectUtils.isEmpty(cosmetics)){
            List<String> ids = Lists.newLinkedList();
            cosmetics.stream().forEach(cosmetic -> {
                ids.add(cosmetic.getCosmeticId().toString());
            });
            if(ObjectUtils.isEmpty(ids)){
               return null;
            }
            QueryInfo queryInfo = new QueryInfo(Commodity.class);
            List<Condition> conditions = Lists.newLinkedList();
            String[] includes = {"name"};
            queryInfo.setIncludes(includes);
            try {
               List<Commodity> commodities = elasticsearchServer.getByIds(queryInfo, ids.toArray(new String[ids.size()]));
                commodities.stream().forEach(commodity -> {
                    names.add(commodity.getName());
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return names;
    }
}
