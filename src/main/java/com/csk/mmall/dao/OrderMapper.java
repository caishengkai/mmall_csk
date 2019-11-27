package com.csk.mmall.dao;

import com.csk.mmall.pojo.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByOrderAndUserId(@Param("orderNo") Long orderNo, @Param("userId") Integer userId);

    Order selectByOrderNo(Long orderNo);
}