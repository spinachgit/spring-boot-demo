package com.roncoo.example.util.configuration;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * redis 自定义缓存管理器
 * 
 * @author wujing
 */
@Configuration
public class RedisCacheConfiguration extends CachingConfigurerSupport {
	private Logger logger = LoggerFactory.getLogger(RedisCacheConfiguration.class);

	/**
	 * 自定义缓存管理器.
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		// 设置默认的过期时间
		cacheManager.setDefaultExpiration(20);
		Map<String, Long> expires = new HashMap<String, Long>();
		// 单独设置
		expires.put("roncooCache", 200L);
		cacheManager.setExpires(expires);
		return cacheManager;
	}

	/**
	 * 自定义key. 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key,即使@Cacheable中的value属性一样，key也会不一样。
	 */
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object o, Method method, Object... objects) {
				StringBuilder sb = new StringBuilder();
				sb.append(o.getClass().getName());
				sb.append(method.getName());
				for (Object obj : objects) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}

	/**
	 * 新增一个Cache配置类，继承CachingConfigurerSupport类，实现errorHandler()方法，我们可以覆盖Spring默认的DefaultErrorHandler。
	 * 为了保证数据一致性，缓存清除方法出错时，我们按原样抛出异常，在查询和插入缓存时如果出现了Redis连接的异常，
	 * 不做异常抛出处理，这样会继续从数据库中读取/写入数据，不影响正常业务服务，等缓存连接恢复后，又可以正常的提供服务了。
	 * @author:whh
	 * @date:2017年12月4日下午3:26:30
	 * @return
	 */
	@Override
	public CacheErrorHandler errorHandler() {
		return new AppCacheErrorHandler();
	}

	public class AppCacheErrorHandler implements CacheErrorHandler {
		@Override
		public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {

			if (e instanceof JedisConnectionException || e instanceof RedisConnectionFailureException) {
				logger.warn("redis has lose connection:", e);
				return;
			}
			throw e;
		}

		@Override
		public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
			if (e instanceof JedisConnectionException || e instanceof RedisConnectionFailureException) {
				logger.warn("redis has lose connection:", e);
				return;
			}
			throw e;
		}

		@Override
		public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
			throw e;
		}

		@Override
		public void handleCacheClearError(RuntimeException e, Cache cache) {
			throw e;
		}
	}
}