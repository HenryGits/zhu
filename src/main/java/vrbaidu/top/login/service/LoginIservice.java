package vrbaidu.top.login.service;

import vrbaidu.top.login.model.User;

/**
 * Created by Administrator on 2017/3/11.
 */
public interface LoginIService {

    /**
     * 根据用户唯一ID查询具体信息
     * @param id
     * @return
     */
    public User findByUsrId(Long id);
    /**
     * 登录
     * @param user
     * @return
     */
    public boolean login(User user);

    /**
     * 注册
     * @param user
     * @return
     */
    public int regUser(User user);

    public void updateByPrimaryKeySelective(User user);
}
