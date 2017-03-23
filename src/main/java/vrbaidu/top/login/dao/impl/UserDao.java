package vrbaidu.top.login.dao.impl;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import vrbaidu.top.login.dao.UserMapper;
import vrbaidu.top.login.model.User;
import vrbaidu.top.login.model.UserExample;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public class UserDao implements UserMapper {
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
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
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

    @Override
    public int countByExample(Object o) {
        return 0;
    }

    @Override
    public int deleteByExample(Object o) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Serializable id) {
        return 0;
    }

    @Override
    public int insert(Serializable record) {
        return 0;
    }

    @Override
    public int insertSelective(Serializable record) {
        return 0;
    }

    @Override
    public List selectByExample(Object o) {
        return null;
    }

    @Override
    public List selectByExample(Object o, RowBounds rowBounds) {
        return null;
    }

    @Override
    public Serializable selectByPrimaryKey(Serializable id) {
        return null;
    }

    @Override
    public int updateByExampleSelective(@Param("record") Serializable record, @Param("example") Object o) {
        return 0;
    }

    @Override
    public int updateByExample(@Param("record") Serializable record, @Param("example") Object o) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(Serializable record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Serializable record) {
        return 0;
    }
}
