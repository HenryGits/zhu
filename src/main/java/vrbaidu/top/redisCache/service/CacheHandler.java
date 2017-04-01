package vrbaidu.top.redisCache.service;

import java.lang.reflect.Method;

/**
 * 缓存拦截器
 */
public interface CacheHandler {

	Object process(Object[] param, Method method, Object instance);
}
