package vrbaidu.top.core.shiro.session;

import vrbaidu.top.login.model.User;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/20.
 */
public class UserOnlineBo {

    private Long id;
    private Date lastAccess;        //最后一次和系统交互的时间
    private Date startTime;         //session创建时间
    private Date lastLoginTime;     //session最后一次与系统交互的时间
    private String host;            //IP地址
    private String sessionId;       //唯一标识
    private long timeout;           //超时时间
    private boolean sessionStatus;  //用户状态

    public UserOnlineBo(User obj) {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public String getHost() {
        return host;
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getTimeout() {
        return timeout;
    }

    public boolean isSessionStatus() {
        return sessionStatus;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setSessionStatus(boolean sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
}
