/**
 * 
 */
package vrbaidu.top.redisCache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;
import vrbaidu.top.redisCache.dao.CommonMapper;


/**
 * @author luoyang
 *
 */
@Service
public class TestIntfImpl extends AbstractBaseService implements TestIntf {

	@Autowired
	JedisCluster jedisCluster;
	
	@Autowired
	CommonMapper mapper;
	
	public void todo(String param) {
		
		CommonMapper commonmapper = (CommonMapper)getProxy(CommonMapper.class,mapper);
		
		commonmapper.deleteArea(null);
		
	}

}
