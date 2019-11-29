package com.csk.mmall.dao;

import com.csk.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    int cartProductAllCheckedStatus(Integer userId);

    List<Cart> selectCartByUserId(Integer userId);

    Cart selectCartByUserAndProductId(@Param("productId") Integer productId, @Param("userId") Integer userId);

    void updateCartProductCheckedStatus(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("checked") Integer checked);

    void deleteByUserAndProductId(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);

    List<Cart> selectSelectedCartByUserId(Integer userId);
}