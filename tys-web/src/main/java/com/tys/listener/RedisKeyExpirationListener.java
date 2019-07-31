package com.tys.listener;

import com.tys.constant.Constant;
import com.tys.service.imp.dao.EquipMentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @Author haoxu
 * @Date 2019/6/26 10:21
 **/
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    private final static Logger logger = LoggerFactory.getLogger(RedisKeyExpirationListener.class);

    @Autowired
    private EquipMentMapper equipMentMapper;


    public RedisKeyExpirationListener(RedisMessageListenerContainer redisMessageListenerContainer) {
        super(redisMessageListenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        if(expiredKey.contains(Constant.EMHEARTBEAT)){
            Integer id = Integer.valueOf(expiredKey.substring(Constant.EMHEARTBEAT.length()));
            equipMentMapper.updateEMStatusById(id,(byte)3);
            logger.info("equipment heartbeat timeout ，key:{},id:{}",expiredKey,id);
        }
    }

}
