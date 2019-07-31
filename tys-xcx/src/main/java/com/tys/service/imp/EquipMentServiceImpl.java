package com.tys.service.imp;

import com.tys.entity.vo.EquipMent;
import com.tys.service.EquipMentService;
import com.tys.service.imp.dao.EquipMentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EquipMentServiceImpl implements EquipMentService {

    @Autowired
    private EquipMentMapper xcxEquipMentMapper;

    private Logger logger = LoggerFactory.getLogger(EquipMentService.class);

    @Override
    public List<EquipMent> getEquipMentsByIds(List<Integer> ids) {
        try {
            return xcxEquipMentMapper.selectByPrimaryKeys(ids);
        }catch (Exception e){
            logger.error("设备getEquipMentsByIds错误，ids:{},error:{}", Arrays.toString(ids.toArray()),e.getMessage());
        }
        return  null;
    }
}
