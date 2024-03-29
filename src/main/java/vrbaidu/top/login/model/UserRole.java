package vrbaidu.top.login.model;

import java.io.Serializable;

public class UserRole implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table u_user_role
     *
     * @mbggenerated Sat Mar 11 22:46:39 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_user_role.uid
     *
     * @mbggenerated Sat Mar 11 22:46:39 CST 2017
     */
    private Long uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_user_role.rid
     *
     * @mbggenerated Sat Mar 11 22:46:39 CST 2017
     */
    private Long rid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_user_role.uid
     *
     * @return the value of u_user_role.uid
     *
     * @mbggenerated Sat Mar 11 22:46:39 CST 2017
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_user_role.uid
     *
     * @param uid the value for u_user_role.uid
     *
     * @mbggenerated Sat Mar 11 22:46:39 CST 2017
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_user_role.rid
     *
     * @return the value of u_user_role.rid
     *
     * @mbggenerated Sat Mar 11 22:46:39 CST 2017
     */
    public Long getRid() {
        return rid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_user_role.rid
     *
     * @param rid the value for u_user_role.rid
     *
     * @mbggenerated Sat Mar 11 22:46:39 CST 2017
     */
    public void setRid(Long rid) {
        this.rid = rid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_user_role
     *
     * @mbggenerated Sat Mar 11 22:46:39 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uid=").append(uid);
        sb.append(", rid=").append(rid);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_user_role
     *
     * @mbggenerated Sat Mar 11 22:46:39 CST 2017
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserRole other = (UserRole) that;
        return (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getRid() == null ? other.getRid() == null : this.getRid().equals(other.getRid()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_user_role
     *
     * @mbggenerated Sat Mar 11 22:46:39 CST 2017
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getRid() == null) ? 0 : getRid().hashCode());
        return result;
    }
}