package vrbaidu.top.redisCache.service;

import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;
import vrbaidu.top.redisCache.dao.CommonMapper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * redis缓存实现类
 * CommonDaoCacheHandler类只负责CommonMapper接口的方法的缓存处理
 */
@FilterMethodName(methodName = "query1,query2,query3", type = CommonMapper.class)
@Component
public class CommonDaoCacheHandler implements CacheHandler {

	public Object process(Object[] param, Method method, Object instance) {

		JedisCluster jedisCluster = (JedisCluster) ApplicationContextUtil
				.getApplicationContext().getBean("jedisCluster");

		Object result = null;
		if (jedisCluster.exists("key")) {
			result = jedisCluster.lrange("key", 0, -1);
		} else {
			try {
				result = method.invoke(instance, param);
				jedisCluster.lpush("key", result.toString());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
