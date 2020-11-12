package com.github.binarywang.demo.wx.mp.dao;

import com.github.binarywang.demo.wx.mp.entity.THjcczzTablelist;
import com.github.binarywang.demo.wx.mp.entity.THjcczzTablelistExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface THjcczzTablelistMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hjcczz_tablelist
     *
     * @mbggenerated Fri Nov 06 10:11:29 CST 2020
     */
    int countByExample(THjcczzTablelistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hjcczz_tablelist
     *
     * @mbggenerated Fri Nov 06 10:11:29 CST 2020
     */
    int deleteByExample(THjcczzTablelistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hjcczz_tablelist
     *
     * @mbggenerated Fri Nov 06 10:11:29 CST 2020
     */
    int insert(THjcczzTablelist record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hjcczz_tablelist
     *
     * @mbggenerated Fri Nov 06 10:11:29 CST 2020
     */
    int insertSelective(THjcczzTablelist record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hjcczz_tablelist
     *
     * @mbggenerated Fri Nov 06 10:11:29 CST 2020
     */
    List<THjcczzTablelist> selectByExample(THjcczzTablelistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hjcczz_tablelist
     *
     * @mbggenerated Fri Nov 06 10:11:29 CST 2020
     */
    int updateByExampleSelective(@Param("record") THjcczzTablelist record, @Param("example") THjcczzTablelistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_hjcczz_tablelist
     *
     * @mbggenerated Fri Nov 06 10:11:29 CST 2020
     */
    int updateByExample(@Param("record") THjcczzTablelist record, @Param("example") THjcczzTablelistExample example);
}