package vrbaidu.top.city.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vrbaidu.top.city.service.CityMsgIService;
import vrbaidu.top.login.controller.LoginController;
import vrbaidu.top.login.dao.RoleMapper;
import vrbaidu.top.login.dao.UserMapper;
import vrbaidu.top.login.dao.UserPermissionMapper;
import vrbaidu.top.login.model.User;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/11.
 */
@Service
public class CityMsgService implements CityMsgIService {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserPermissionMapper permissionMapper;
//    @Autowired
//    protected RedisTemplate<Serializable, Serializable> redisTemplate;
//
//    public void saveUser(final User user) {
//        redisTemplate.execute(new RedisCallback<Object>() {
//            @Override
//            public Object doInRedis(RedisConnection connection)  {
//                connection.set(redisTemplate.getStringSerializer().serialize("user.uid." + user.getId()),
//                        redisTemplate.getStringSerializer().serialize(user.getNickname()));
//                logger.debug(redisTemplate.getStringSerializer().serialize("user.uid." + user.getId()).toString());
//                return null;
//            }
//        });
//    }
//
//    @Override
//    public User getUser(final long id) {
//        return redisTemplate.execute(new RedisCallback<User>() {
//            @Override
//            public User doInRedis(RedisConnection connection)  {
//                byte[] key = redisTemplate.getStringSerializer().serialize("user.uid." + id);
//                logger.debug(key.toString());
//                if (connection.exists(key)) {
//                    byte[] value = connection.get(key);
//                    String name = redisTemplate.getStringSerializer().deserialize(value);
//                    User user = new User();
//                    user.setId(id);
//                    user.setNickname(name);
//                    return user;
//                }
//                return null;
//            }
//        });
//    }
    @Override
    public User findByUsrId(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean login(User user) {
        if (userMapper.checkLogin(user) != null){
            return true;
        }
        return false;
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Override
    public void insertReg(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateByPrimaryKeySelective(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User findUserByUsername(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Set<String> findRoles(String username) {
        return (Set<String>) roleMapper.selectByExample(username);
    }

    @Override
    public Set<String> findPermissions(String username) {
        return (Set<String>) permissionMapper.selectByExample(username);
    }
}
