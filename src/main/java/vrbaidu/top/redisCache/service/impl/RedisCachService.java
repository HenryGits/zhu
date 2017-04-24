package vrbaidu.top.redisCache.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenjie on 2016/11/14.
 */
@Service
public class RedisCachService<T> {
    private Logger logger = Logger.getLogger(RedisCachService.class);

    @Qualifier("redisTemplate")
    private RedisTemplate<Serializable, Object> redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate
                .opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 缓存List数据
     * @param key         缓存的键值
     * @param dataList    待缓存的List数据
     * @return            缓存的对象
     */
    public <T> ListOperations<String, T> setCachList(String key, List<T> dataList) {
        ListOperations listOperations = redisTemplate.opsForList();
        if (listOperations != null){
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                listOperations.rightPush(key, dataList.get(i));
                logger.debug("缓存的键值为：" + key+ "缓存的List数据为：" +dataList.get(i));
            }
        }
        return listOperations;
    }

    /**
     * 获得缓存的list对象
     * @param key    缓存的键值
     * @return        缓存键值对应的数据
     */
    public <T> List<T> getCachList(String key) {
        List<T> dataList = new ArrayList<>();
        ListOperations opsForList = redisTemplate.opsForList();
        Long size= opsForList.size(key);
        for (int i = 0; i < size; i++) {
            dataList.add((T) opsForList.leftPop(key));
        }
        return dataList;
    }

    /**
     * 缓存Set
     * @param key        缓存键值
     * @param dataSet    缓存的数据
     * @return            缓存数据的对象
     */
    public <T> BoundSetOperations<Serializable, Object> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<Serializable, Object> setOperation = redisTemplate.boundSetOps(key);
//        T[] t = (T[]) dataSet.toArray();
//             setOperation.add(t);
        Iterator<T> it = dataSet.iterator();
        while(it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     * @param key
     * @param operation
     * @return
     */
    public Set<T> getCacheSet(String key, BoundSetOperations<String,T> operation) {
        Set<T> dataSet = new HashSet<T>();
        BoundSetOperations<Serializable, Object> operations = redisTemplate.boundSetOps(key);
        Long size = operation.size();
        for(int i = 0 ; i < size ; i++) {
            dataSet.add(operation.pop());
        }
        return dataSet;
    }

    /**
     * 缓存Map
     * @param key
     * @param dataMap
     * @return
     */
    public <T> HashOperations<String,String,T> setCacheMap(String key,Map<String,T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if(null != dataMap) {
            for (Map.Entry<String, T> entry : dataMap.entrySet()) {
                hashOperations.put(key,entry.getKey(),entry.getValue());
                logger.debug("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     * @param key
     * @param hashOperation
     * @return
     */
    public <T> Map<String,T> getCacheMap(String key, HashOperations<String,String,T> hashOperation) {
       // Map<String, T> map = redisTemplate.opsForHash().entries(key);
        Map<String, T> map = hashOperation.entries(key);
        return map;
    }

    /**
     * 获得缓存的Map
     * @param key
     * @return
     */
    public <T> Map<Object, Object> getCacheMap(String key) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
