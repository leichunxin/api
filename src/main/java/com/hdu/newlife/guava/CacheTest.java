package com.hdu.newlife.guava;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hdu.newlife.bean.UserBean;
import com.hdu.newlife.service.UserService;
import com.hdu.newlife.service.impl.UserServiceImpl;

public class CacheTest {
	
	UserService userService = new UserServiceImpl();
	
    private Cache<Integer, UserBean> cache = CacheBuilder.newBuilder().maximumSize(100000).
            expireAfterWrite(30, TimeUnit.MINUTES).build();

	public UserBean get(int id) {
        try {
            return cache.get(id, () -> {
            	UserBean token = userService.getById(id);
                if (token == null)
                	throw new RuntimeException("记录不存在");
                return token;
            });
        } catch (ExecutionException e) {
            throw new RuntimeException("获取数据失败");
        }
	}
	
    public void clear() {
        cache.invalidateAll();
    }

    public long cacheSize() {
        return cache.size();
    }

    public static void main(String[] args) {
    	CacheTest g = new CacheTest();
    	System.out.println(JSON.toJSONString(g.get(1)));
    	System.out.println(JSON.toJSONString(g.get(2)));
    	System.out.println(JSON.toJSONString(g.get(1)));
    	System.out.println(JSON.toJSONString(g.get(2)));
    	System.out.println(JSON.toJSONString(g.get(3)));
    }
    
}
