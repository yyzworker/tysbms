package com.tys.service.imp;

import com.tys.entity.vo.MemberInfo;
import com.tys.service.MemberInfoService;
import com.tys.service.imp.dao.MemberInfoMapper;
import com.tys.util.tool.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * @Author haoxu
 * @Date 2019/3/13 11:47
 **/
@Service
@Transactional
public class MemberInfoServiceImpl implements MemberInfoService {

    @Autowired
    private MemberInfoMapper xcxMemberInfoMapper;

    @Override
    public int updateExpSumById(Integer id , Integer exp) {
        return xcxMemberInfoMapper.updateExpSumById(id,exp);
    }

    private Logger logger = LoggerFactory.getLogger(MemberInfoService.class);

    @Transactional(readOnly = true)
    @Override
    public MemberInfo selectByPhone(String phone) {
        return xcxMemberInfoMapper.selectByPhone(phone);
    }

    @Override
    public int insertSelective(MemberInfo record) {
        return xcxMemberInfoMapper.insertSelective(record);
    }

    @Override
    public int updateSelective(MemberInfo record) {
        if (record.getBirthdate() != null) {
            try {
                record.setAge(DateUtil.getAge(new Date(record.getBirthdate().getTime())));
            } catch (Exception e) {
                logger.error("根据出生日期计算年龄出错,mid:{},birthdate:{},error:{}", record.getId(), record.getBirthdate(), e.getMessage());
            }
        }
        try {
            return xcxMemberInfoMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新memberInfo数据错误，mid:{},error:{}", record.getId(), e.getMessage());
        }
        return 0;
    }

    @Transactional(readOnly = true)
    @Override
    public MemberInfo selectByOpenId(String openId) {
        try {
            return xcxMemberInfoMapper.selectByOpenId(openId);
        } catch (Exception e) {
            logger.error("memberInfo根据openId查询错误，openId:{},error:{}", openId, e.getMessage());
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public MemberInfo selectById(Integer id) {
        try {
            return xcxMemberInfoMapper.selectById(id);
        } catch (Exception e) {
            logger.error("memberInfo根据id查询错误，id:{},error:{}", id, e.getMessage());
        }
        return null;
    }

    @Override
    public int updatePhoneById(Integer id, String phone) {
        try {
            return xcxMemberInfoMapper.updatetelPhoneById(id, phone);
        } catch (Exception e) {
            logger.error("memberInfo更新电话错误，mid:{},phone:{},error:{}", id,phone, e.getMessage());
        }
        return 0;
    }

}
