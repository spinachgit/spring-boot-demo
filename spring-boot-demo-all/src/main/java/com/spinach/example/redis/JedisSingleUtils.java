package com.spinach.example.redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spinach.example.common.utils.GsonUtils;
import com.spinach.example.common.utils.ResourceUtils;
import com.spinach.example.common.utils.StringUtil;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <p>
 *  redis工具类
 * </p>
 * @author wanghuihui
 * @date 2017-7-20上午11:12:28
 */
public class JedisSingleUtils {
	public static Logger  logger = LoggerFactory.getLogger(JedisSingleUtils.class);
	private static JedisPool jedisPool = null;

	static {
		//从属性文件读取配置
		String host = "127.0.0.1";
		String port = "6379";
		int index = 0;
		String auth = null;
		try {
			host = ResourceUtils.getPropertyInSystem("spring.redis.host")+"";
			port = ResourceUtils.getPropertyInSystem("spring.redis.port")+"";
			index = StringUtil.isNotEmpty(ResourceUtils.getPropertyInSystem("spring.redis.database")+"")?Integer.valueOf(ResourceUtils.getPropertyInSystem("spring.redis.database")+""):0;
			/*String env_path = ResourceUtils.SYSTEM_PROPERTIES;
			env_path = env_path.replaceAll(".properties", "").replaceAll("/", ".");
			ResourceBundle bundle = ResourceBundle.getBundle(env_path,Locale.CHINESE);
			host = bundle.getString("spring.redis.host")+"";
			port = bundle.getString("spring.redis.port")+"";
			index = StringUtil.isNotEmpty(bundle.getString("spring.redis.database")+"")?Integer.valueOf(bundle.getString("redis.index")+""):0;
			*/
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		try {
			JedisPoolConfig config = new JedisPoolConfig();
			//config.setMaxTotal(500);
			config.setMaxIdle(50);
			//config.setMaxWaitMillis(1000);
			//config.setMaxWait(1000);
			config.setTestOnBorrow(true);
			// 在还回给pool时，是否提前进行validate操作
			config.setTestOnReturn(true);
			//连接池设置
			jedisPool = new JedisPool(config, host, Integer.parseInt(port), 1800000, auth, index);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * <p>
	 *  获得REDIS客户端
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-20下午1:40:08
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void closeJedis(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
			//jedis.close();
		}
	}
	
	/**
	 * <p>
	 *  获得KEY所指定的对象。
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-14下午4:21:14
	 * @param key
	 */
	public static Object getObjectByKey(String key){
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			String type = jedis.type(key);
			if("string".equalsIgnoreCase(type)){
				return jedis.get(key);
			}else if("hash".equalsIgnoreCase(type) || "map".equalsIgnoreCase(type)){
				//map对应的类型 相当于 redis中的hash
				return jedis.hgetAll(key);
			}else if("list".equalsIgnoreCase(type)){
				Long size = jedis.llen(key);
				return jedis.lrange(key, 0, size);
			}else if("set".equalsIgnoreCase(type)){
				return jedis.smembers(key);
			}else if("zset".equalsIgnoreCase(type)){
				//有序结果集和无序结果集是一样的，都是SET
				//Long size = jedis.zrank(key, member)(key);
				 return jedis.zrange(key, 0, 10);
			}else{
				return null;
			}
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
		return null;
	}
	
	/**
	 * <p>
	 *  获得KEY所指定的对象。
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-20下午3:09:13
	 * @param key 键
	 * @param value 值
	 * @param isCover 如果已经存在KEY:是否覆盖
	 * @return
	 */
	public static  Object setObjectByKey(String key,Object value,boolean isCover){
		if(null == key || "".equals(key) || key.isEmpty()){
			logger.info("key不能空！");
			return false;
		}
		if(null == value || "".equals(value)){
			logger.info("value不能空！");
			return false;
		}
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			boolean isExists = jedis.exists(key);
			if(isExists && isCover){
				jedis.del(key);
			}
			String type = jedis.type(key);
			
			if(value instanceof String){//字符串处理
				if(isExists && !isCover && !"string".equals(type)){
					throw new RuntimeException("类型不匹配！不能添加");
				}else{
					jedis.set(key, value.toString());
					return true;
				}
			}else if(value instanceof List){//LIST处理
				if(isExists && !isCover && !"list".equals(type)){
					throw new RuntimeException("类型不匹配！不能添加");
				}else{
					List<Object> list = (List)value;
					if(null == list || list.size()==0){
						throw new RuntimeException("对象集合为空！");
					}
					for(Object obj:list){
						//String temp = JsonUtil.getJSONString(obj);
						String temp = GsonUtils.toJson(obj);
						jedis.lpush(key,temp);
					}
					return true;
				}
			}else if(value instanceof Map){//MAP处理
				if(isExists && !isCover && !"hash".equals(type)){
					throw new RuntimeException("类型不匹配！不能添加");
				}	
				Map map = (Map)value;
				if(null == map || map.size()==0){
					throw new RuntimeException("对象集合为空！");
				}
				Iterator interator = map.entrySet().iterator();
				while (interator.hasNext()) {
					 Map.Entry enry = (Map.Entry) interator.next();
					 String t1 = enry.getKey().toString();
					 Object t2 = enry.getValue();
					 if(!(t2 instanceof String)){
						 String temp = GsonUtils.toJson(t2);
						 map.put(t1, temp);
					 }else{
						 map.put(t1, t2);
					 }
				}
				jedis.hmset(key, map);
				return true;
			}else if(value instanceof Set){//set处理
				if(isExists && !isCover && !"set".equals(type)){
					throw new RuntimeException("类型不匹配！不能添加");
				}
				Set set = (Set)value;
				if(null == set || set.size()==0){
					throw new RuntimeException("对象集合为空！");
				}
				Iterator iterator = set.iterator();
				if(iterator.hasNext()){
					Object obj = iterator.next();
					jedis.sadd(key, GsonUtils.toJson(obj));
				}
				return true;
			}else{
				jedis.set(key, GsonUtils.toJson(value));
				return true;
			}
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
		return null;
	}
	
	/**
	 * <p>
	 *  获得KEY所指定的对象。
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-20下午3:09:13
	 * @param key 键
	 * @param value 值
	 * @param isCover 如果已经存在KEY:是否覆盖
	 * @return
	 */
	public static  Object setObjectToStrByKey(String key,Object value,boolean isCover){
		if(null == key || "".equals(key) || key.isEmpty()){
			logger.info("key不能空！");
			return false;
		}
		if(null == value || "".equals(value)){
			logger.info("value不能空！");
			return false;
		}
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			boolean isExists = jedis.exists(key);
			if(isExists && isCover){
				jedis.del(key);
			}
			jedis.set(key, GsonUtils.toJson(value));
			return true;
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
		return null;
	}
	
	/**
	 * <p>
	 *  str put
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-14下午2:43:58
	 * @param key
	 * @param value
	 */
	public static void strPut(String key, String value) {
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			jedis.set(key, value);
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
	}
	
	/**
	 * <p>
	 *  获得KEY对应的字符串
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-20下午1:40:50
	 * @param key
	 * @return
	 */
	public static String strGet(String key) {
		String value = "";
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			value = jedis.get(key);
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
		return value;
	}
	
	/**
	 * <p>
	 *  删除KEY对应的字符串
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-20下午1:40:50
	 * @param key
	 * @return
	 */
	public static Long strDel(String... key) {
		Long value = 0L;
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			value = jedis.del(key);
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
		return value;
	}
	
	/**
	 * <p>
	 *  发布订阅
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-20下午1:41:38
	 * @param theme
	 * @param message
	 */
	public static void publish(String theme, String message) {
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			jedis.publish(theme, message);
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
	}
	
	/**
	 * <p>
	 *  设置过期时间
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-20下午1:41:54
	 * @param key
	 * @param seconds
	 */
	public static void keyExpire(String key, int seconds) {
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			jedis.expire(key, seconds);
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
	}
	
	/**
	 * <p>
	 *  判断是否存在
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-20下午1:42:10
	 * @param key
	 * @return
	 */
	public static boolean keyExists(String key) {
		boolean bool = false;
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			bool = jedis.exists(key);
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
		return bool;
	}
	
	/**
	 * <p>
	 *  key对应的VALUE增加 integer.
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-20下午1:42:26
	 * @param key
	 * @param integer
	 */
	public static void valueIncr(String key,long integer) {
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			jedis.incrBy(key,integer);
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
	}

	/**
	 * <p>
	 * set集合增加。
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-14下午2:13:39
	 * @param key
	 * @param members
	 */
	public static void setAdd(String key, String... members) {
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			jedis.sadd(key, members);
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			//jedisPool.destroy();
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
	}
	
	/**
	 * <p>
	 * 如果成员是存储在键中的集合的成员，则返回1，否则返回0。
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-14下午2:04:54
	 * @param key
	 * @param members
	 * @return
	 */
	public static boolean setExists(String key, String members) {
		Jedis jedis = null;
		boolean bool = false;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			bool = jedis.sismember(key, members);
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
		return bool;
	}
	/**
	 * <p>
	 *  有序集合
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-14下午3:05:13
	 * @param key：键
	 * @param score：排序依据，SCORE越小越前。
	 * @param members ：成员
	 */
	public static void zSetAdd(String key, double  score , String member) {
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			jedis.zadd(key, score,member);
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
	}
	/**
	 * <p>
	 * 有序集合查询：相当于排序分页查询。
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-14下午3:24:58
	 * @param key:
	 * @param start 开始
	 * @param limit 结束
	 */
	public static void zSetQuery(String key, int  start , int limit) {
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			jedis.zrange(key, start,limit);
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
	}
	/**
	 * <p>
	 *  清空当前数据库
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-14下午4:04:08
	 */
	public static  void flushDBAll(){
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			jedis.flushDB();
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
	}
	
	/**
	 * <p>
	 *  获得KEY对应的的实体LIST,注意此方法不是通用方法，
	 *  keys所对应的VALUE必须为实体的JSON实符串，否则会转换出错。
	 * </p>
	 * @author wanghuihui
	 * @date 2017-7-21下午3:01:02
	 * @param redisKey : redis KEY
	 * @param mapKey : MAP KEY
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getEntityListFromMap(String redisKey,String mapKey, Class<T> clazz) {
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			String type = jedis.type(redisKey);
			if(!"hash".equals(type)){
				return null;
			}
			
			Map<String,String> map = jedis.hgetAll(redisKey);
			Set<Entry<String, String>> set = map.entrySet();
			List<T> list = new ArrayList<T>();
			for(Entry<String, String> entry:set){
				if(entry.getKey().startsWith(mapKey)){
					JSONObject value = JSONObject.fromObject(entry.getValue()); 
					list.add((T) JSONObject.toBean(value, clazz));
				}
			}
			return list;  
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
		return null;
	}
	
	public static <T> List<T> getEntityListFromString(String redisKey,Class<T> clazz) {
		Jedis jedis = null;
		// 从池中获取一个jedis实例
		try {
			jedis = getJedis();
			Set<String> set = jedis.keys(redisKey);
			Iterator<String> iterator = set.iterator();
			List<T> list = new ArrayList<T>();  
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Map<String, String> result = jedis.hgetAll(key);
				JSONObject value = JSONObject.fromObject(result.get(key)); 
				list.add((T) JSONObject.toBean(value, clazz));  
			}
			return list;  
		} catch (Exception e) {
			// 销毁对象
			jedisPool.returnBrokenResource(jedis);
			logger.error(e.getMessage(), e);
		} finally {
			// 还回到连接池
			closeJedis(jedis);
		}
		return null;
	}
}