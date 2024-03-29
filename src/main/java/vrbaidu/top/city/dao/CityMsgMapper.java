package vrbaidu.top.city.dao;

import com.istock.base.mybatis.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import vrbaidu.top.city.model.CityMsg;
import vrbaidu.top.city.model.CityMsgExample;

public interface CityMsgMapper extends BaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table varbaidu_code_msg
     *
     * @mbggenerated Sat Apr 08 15:08:34 CST 2017
     */
    int countByExample(CityMsgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table varbaidu_code_msg
     *
     * @mbggenerated Sat Apr 08 15:08:34 CST 2017
     */
    int deleteByExample(CityMsgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table varbaidu_code_msg
     *
     * @mbggenerated Sat Apr 08 15:08:34 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table varbaidu_code_msg
     *
     * @mbggenerated Sat Apr 08 15:08:34 CST 2017
     */
    int insert(CityMsg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table varbaidu_code_msg
     *
     * @mbggenerated Sat Apr 08 15:08:34 CST 2017
     */
    int insertSelective(CityMsg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table varbaidu_code_msg
     *
     * @mbggenerated Sat Apr 08 15:08:34 CST 2017
     */
    List<CityMsg> selectByExample(CityMsgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table varbaidu_code_msg
     *
     * @mbggenerated Sat Apr 08 15:08:34 CST 2017
     */
    CityMsg selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table varbaidu_code_msg
     *
     * @mbggenerated Sat Apr 08 15:08:34 CST 2017
     */
    int updateByExampleSelective(@Param("record") CityMsg record, @Param("example") CityMsgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table varbaidu_code_msg
     *
     * @mbggenerated Sat Apr 08 15:08:34 CST 2017
     */
    int updateByExample(@Param("record") CityMsg record, @Param("example") CityMsgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table varbaidu_code_msg
     *
     * @mbggenerated Sat Apr 08 15:08:34 CST 2017
     */
    int updateByPrimaryKeySelective(CityMsg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table varbaidu_code_msg
     *
     * @mbggenerated Sat Apr 08 15:08:34 CST 2017
     */
    int updateByPrimaryKey(CityMsg record);
}