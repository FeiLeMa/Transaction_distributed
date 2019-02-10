package com.alag.test.business.module.b.server.mapper;

import com.alag.test.business.module.b.api.model.BUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_user
     *
     * @mbggenerated Sat Feb 09 14:10:43 CST 2019
     */
    int deleteByPrimaryKey(Integer uNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_user
     *
     * @mbggenerated Sat Feb 09 14:10:43 CST 2019
     */
    int insert(BUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_user
     *
     * @mbggenerated Sat Feb 09 14:10:43 CST 2019
     */
    int insertSelective(BUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_user
     *
     * @mbggenerated Sat Feb 09 14:10:43 CST 2019
     */
    BUser selectByPrimaryKey(Integer uNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_user
     *
     * @mbggenerated Sat Feb 09 14:10:43 CST 2019
     */
    int updateByPrimaryKeySelective(BUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_user
     *
     * @mbggenerated Sat Feb 09 14:10:43 CST 2019
     */
    int updateByPrimaryKey(BUser record);

    int addMoneyById(@Param("id") int i, @Param("money") long money);

    int minusMoneyById(@Param("id") int i, @Param("money") long money);
}