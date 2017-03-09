package vrbaidu.top.user.service;

import vrbaidu.top.user.dao.UserMapper;
import vrbaidu.top.user.model.User;
import vrbaidu.top.user.model.UserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/3/5.
 */
@Repository("UserService")
public class UserService implements UserIService {
    @Resource
    private UserMapper userMapper;

    @Override
    public int countByExample(UserExample example) {
        return 0;
    }

    @Override
    public int deleteByExample(UserExample example) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(User record) {
        return 0;
    }

    @Override
    public int insertSelective(User record) {
        return 0;
    }

    @Override
    public List<User> selectByExample(UserExample example) {
        return null;
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example) {
        return 0;
    }

    @Override
    public int updateByExample(@Param("record") User record, @Param("example") UserExample example) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }
}
