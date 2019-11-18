package com.csk.mmall.dao;

import com.csk.mmall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int updateByShipping(Shipping shipping);

    int deleteByShippingAndUserId(@Param("shippingId") Integer shippingId, @Param("userId") Integer userId);

    Shipping selectByShippingAndUserId(@Param("shippingId") Integer shippingId, @Param("userId") Integer userId);

    List<Shipping> selectByUserId(Integer userId);
}