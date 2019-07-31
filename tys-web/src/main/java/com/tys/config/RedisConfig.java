package com.tys.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tys.util.tool.FastJsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
* @author 我是金角大王
* @date 2018年1月23日 上午8:54:19
*/
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 生成key的策略
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 管理缓存
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);//JSONObject
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer);
        RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(3000));//设置过期时间
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig,getRedisCacheConfigurationMap());
        ParserConfig.getGlobalInstance().addAccept("com.tys.entity.vo.");
        ParserConfig.getGlobalInstance().addAccept("com.tys.entity.wx.");
        ParserConfig.getGlobalInstance().addAccept("com.tys.entity.es.");
        return cacheManager;
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("wxtoken", getRedisCacheConfigurationWithTtl(5400));
        redisCacheConfigurationMap.put("DataCenterCacheListDetectRecordByDay", getRedisCacheConfigurationWithTtl(28800));
        redisCacheConfigurationMap.put("DataCenterCacheListDetectRecordByWeek", getRedisCacheConfigurationWithTtl(28800));
        redisCacheConfigurationMap.put("DataCenterCacheListDetectRecordByMonth", getRedisCacheConfigurationWithTtl(28800));
        redisCacheConfigurationMap.put("DataCenterCacheListMemberByDay", getRedisCacheConfigurationWithTtl(28800));
        redisCacheConfigurationMap.put("DataCenterCacheListMemberByWeek", getRedisCacheConfigurationWithTtl(28800));
        redisCacheConfigurationMap.put("DataCenterCacheListMemberByMonth", getRedisCacheConfigurationWithTtl(28800));
        redisCacheConfigurationMap.put("DataCenterCacheListMemberSex", getRedisCacheConfigurationWithTtl(28800));
        redisCacheConfigurationMap.put("DataCenterCacheListAgeRatio", getRedisCacheConfigurationWithTtl(28800));
        redisCacheConfigurationMap.put("HomeInfo", getRedisCacheConfigurationWithTtl(3600));
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);//JSONObject
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer);
        RedisCacheConfiguration redisCacheConfiguration=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(seconds));//设置过期时间
        return redisCacheConfiguration;
    }

    @Bean(name="redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        //使用fastjson序列化
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(new StringRedisSerializer());
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
