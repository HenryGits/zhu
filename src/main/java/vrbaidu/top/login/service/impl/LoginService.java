package vrbaidu.top.login.service.impl;

import org.springframework.stereotype.Service;
import vrbaidu.top.login.dao.UserMapper;
import vrbaidu.top.login.model.User;
import vrbaidu.top.login.service.LoginIService;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/11.
 */
@Service
public class LoginService implements LoginIService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUsrId(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean login(User user) {
        return false;
    }

    @Override
    public int regUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public void updateByPrimaryKeySelective(User user) {
        userMapper.updateByPrimaryKey(user);
    }
}
