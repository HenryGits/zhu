package vrbaidu.top.city.service;

import vrbaidu.top.login.model.User;

import java.util.Set;

/**
 * Created by Administrator on 2017/3/11.
 */
public interface CityMsgIService {
//    public User getUser(final long id) ;
//    public void saveUser(final User user) ;
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
    public void insertReg(User user);

    public void updateByPrimaryKeySelective(User user);

    User findUserByUsername(Long id);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);
}
