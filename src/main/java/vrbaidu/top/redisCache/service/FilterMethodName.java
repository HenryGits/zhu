/**
 * 
 */
package vrbaidu.top.redisCache.service;

/**
 * 缓存注解
 *
 */
public @interface FilterMethodName {

	//过滤的方法
	String methodName();
	//过滤的dao类
	Class<?> type();
}
