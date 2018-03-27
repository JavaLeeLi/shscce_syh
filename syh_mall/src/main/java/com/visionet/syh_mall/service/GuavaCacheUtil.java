package com.visionet.syh_mall.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.visionet.syh_mall.exception.RestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class GuavaCacheUtil {
    private static Logger logger = LoggerFactory.getLogger(GuavaCacheUtil.class);
    
    @Autowired
    
    private static LoadingCache<String, String> cache =null;
    private static LoadingCache<String, String> cache1 =null;
    private static void initCache() throws Exception {
        // 设置缓存最大个数为5000，缓存过期时间为24小时
        cache = CacheBuilder.newBuilder().maximumSize(5000)
                .expireAfterAccess(1, TimeUnit.HOURS).build(new CacheLoader<String,String>() {
                    @Override
                    public String load(String key) throws Exception {
                        logger.debug("fetch from database userId:" + key);
//                        return queryRemindCount(key);
                        return null;
                    }
                });
    }
    private static void initTokenCache() throws Exception {
    	// 设置缓存最大个数为5000，缓存过期时间为24小时
    	cache1 = CacheBuilder.newBuilder().maximumSize(5000)
    			.expireAfterAccess(1, TimeUnit.HOURS).build(new CacheLoader<String,String>() {
    				@Override
    				public String load(String key) throws Exception {
    					logger.debug("fetch from database userId:" + key);
//                        return queryRemindCount(key);
    					return null;
    				}
    			});
    }


    public static String get(String key) throws Exception{
        if(cache==null){
            initCache();
        }
        if(cache==null){
            throw new RestException("Guava AccessTokenCache is null!");
        }
        String value=null;
        try {
        	logger.info("cache的结果:{}",cache);
            value = cache.get(key);
        }catch(Exception e){
        }
        return value;
    }
    public static String getToken(String key) throws Exception{
    	if(cache1==null){
    		initTokenCache();
    	}
    	if(cache1==null){
    		throw new RestException("Guava AccessTokenCache is null!");
    	}
    	String value=null;
    	try {
    		logger.info("cache1的结果:{}",cache1);
    		value = cache1.get(key);
    	}catch(Exception e){
    	}
    	return value;
    }

//    public Map<Integer,Long> queryRemindCount(Long userId) {
//        Map<Integer,Long> map = Maps.newHashMap();
//        List<Object[]> list = remindDao.getRemindNumber(userId);
//        for(Object[] objects:list){
//            map.put((Integer)objects[0],(Long)objects[1]);
//        }
//        return map;
//    }

    public static void set(String key,String value){
        try {
            if (cache == null) {
                return;
            }
            cache.put(key, value);
        }catch (Exception e){
            logger.error("GuavaCache refreshRemind error:",e);
        }
    }
    public static void setToken(String key,String value){
    	try {
    		if (cache1 == null) {
    			return;
    		}
    		cache1.put(key, value);
    	}catch (Exception e){
    		logger.error("GuavaCache refreshRemind error:",e);
    	}
    }

    public static void refresh(String key){
        try {
            if (cache == null) {
                return;
            }
            cache.refresh(key);
        }catch (Exception e){
            logger.error("GuavaCache refreshRemind error:",e);
        }
    }
    public static void clear(){
        try {
            if (cache == null) {
                return;
            }
            cache.cleanUp();
        }catch (Exception e){
            logger.error("GuavaCache clearRemind error:",e);
        }
    }

}
