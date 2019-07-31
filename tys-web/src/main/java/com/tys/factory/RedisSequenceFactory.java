package com.tys.factory;

import java.util.Date;
import java.util.concurrent.TimeUnit;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class RedisSequenceFactory {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	/** 
	* @Title: set 
	* @Description: set cache.
	* @param key
	* @param value
	* @param expireTime      
	*/  
	public void set(String key,int value,Date expireTime) {
		RedisAtomicInteger counter = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
		counter.set(value);
		counter.expireAt(expireTime);		
	}
	
	/** 
	* @Title: set 
	* @Description: set cache.
	* @param key
	* @param value
	* @param timeout
	* @param unit      
	*/  
	public void set(String key,int value,int timeout,TimeUnit unit) {
		RedisAtomicInteger counter = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
		counter.set(value);
		counter.expire(timeout, unit);
	}
	
	/** 
	* @Title: generate 
	* @Description: Atomically increments by one the current value.
	* @param key
	* @return      
	*/  
	public int generate(String key) {
		RedisAtomicInteger counter = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
		return counter.incrementAndGet();
	}	
	
	/** 
	* @Title: generate 
	* @Description: Atomically increments by one the current value.
	* @param key
	* @return      
	*/  
	public int generate(String key,Date expireTime) {
		RedisAtomicInteger counter = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
		counter.expireAt(expireTime);
		return counter.incrementAndGet();	      
	}		
	
	/** 
	* @Title: generate 
	* @Description: Atomically adds the given value to the current value.
	* @param key
	* @param increment
	* @return      
	*/  
	public int generate(String key,int increment) {
		RedisAtomicInteger counter = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
		return counter.addAndGet(increment);		      
	}
	
	/** 
	* @Title: generate 
	* @Description: Atomically adds the given value to the current value.
	* @param key
	* @param increment
	* @param expireTime
	* @return      
	*/  
	public int generate(String key,int increment,Date expireTime) {
		RedisAtomicInteger counter = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
		counter.expireAt(expireTime);
		return counter.addAndGet(increment);		      
	}	
}