package vrbaidu.top.cache;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.exceptions.JedisConnectionException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Administrator on 2017/3/15.
 * 使用第三方内存数据库Redis作为二级缓存
 * 实现mybatis下的接口:org.apache.ibatis.cache
 */
public class RedisCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private  final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static JedisConnectionFactory jedisConnectionFactory;
    private final String id;

    public RedisCache(final String id) {
        if (id == null){
            throw new IllegalArgumentException("参数异常(向方法传递了一个不合法或不正确的参数)====>Cache instances require an ID");
        }
        logger.debug("MybatisRedisCache:========>id=" + id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getSize() {
        int result = 0;
        JedisConnection jc = null;
        try {
            jc = jedisConnectionFactory.getConnection();
            result = Integer.parseInt(jc.dbSize().toString());
            logger.debug("获取到的数据总量==============>", result);
        }catch (Exception e){
            logger.error("无法获取到数据库结果数=========>", e);
        }finally {
            jc.close();
        }
        return result;
    }

    /**
     * 向redis塞值
     * @param key 键
     * @param value 值
     */
    @Override
    public void putObject(Object key, Object value) {
        JedisConnection jc = null;
        try{
            jc = jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            jc.set(serializer.serialize(key), serializer.serialize(value));
            logger.debug("插入的key值为=========>",key,"value值为==========>",value);
        }catch (JedisConnectionException e){
            logger.error("插入错误，原因是=========>", e);
        }finally{
            if (jc != null) {
                jc.close();
            }
        }
    }

    /**
     * 通过key查找value值
     * @param key
     * @return
     */
    @Override
    public Object getObject(Object key) {
        JedisConnection jc = null;
        Object result = null;
        try {
            jc = jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            //通过序列化后的key查找
            result = serializer.deserialize(jc.get(serializer.serialize(key)));
            logger.debug("通过redis的key值获取对应value值为：=============>", result);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("通过redis的key值获取对应value值失败=============>", e);
        }finally {
            jc.close();
        }
        return result;
    }

    @Override
    public Object removeObject(Object key) {
        JedisConnection jc = null;
        Object result = null;
        try {
            jc = jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            result = jc.expire(serializer.serialize(key), 0);
            logger.debug("通过key：", key, "删除value成功：=============>", result);
        }catch (Exception e){
            logger.error("通过key值获取对应value值失败=============>", e);
        }finally {
            if (jc != null){
                jc.close();
            }
        }
        return result;
    }

    /**
     * 清空redis缓存数据
     */
    @Override
    public void clear() {
        JedisConnection jc = null;
        try {
            jc = jedisConnectionFactory.getConnection();
            jc.flushDb();
            jc.flushAll();
        }catch (Exception e){
            logger.error("数据清除失败，原因是：========>"+e);
        }finally {
            jc.close();
        }
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    public static void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        RedisCache.jedisConnectionFactory = jedisConnectionFactory;
    }
}
