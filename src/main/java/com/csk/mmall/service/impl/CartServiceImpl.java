package com.csk.mmall.service.impl;

import com.csk.mmall.common.Const;
import com.csk.mmall.common.ResponseCode;
import com.csk.mmall.common.ServerResponse;
import com.csk.mmall.dao.CartMapper;
import com.csk.mmall.dao.ProductMapper;
import com.csk.mmall.pojo.Cart;
import com.csk.mmall.pojo.Product;
import com.csk.mmall.service.ICartService;
import com.csk.mmall.util.BigDecimalUtil;
import com.csk.mmall.util.PropertiesUtil;
import com.csk.mmall.vo.CartProductVo;
import com.csk.mmall.vo.CartVo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 购物车service实现类
 * @author: caishengkai
 * @time: 2019/11/17 14:15
 */
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse<CartVo> list(Integer userId) {
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> add(Integer productId, Integer count, Integer userId) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Cart cart = cartMapper.selectCartByUserAndProductId(productId, userId);
        if (cart == null) {
            //如果查不到则新增一条购物车商品记录
            Cart cartItem = new Cart();
            cartItem.setProductId(productId);
            cartItem.setQuantity(count);
            cartItem.setUserId(userId);
            cartItem.setChecked(Const.Cart.CHECKED); //新加入购物车中的商品默认选中
            cartMapper.insert(cartItem);
        } else {
            //查到了就更新购物车商品数据
            cart.setQuantity(cart.getQuantity() + count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        //添加完成后跳到购物车列表页面
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartVo> update(Integer productId, Integer count, Integer userId) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Cart cart = cartMapper.selectCartByUserAndProductId(productId, userId);
        cart.setQuantity(count);
        cartMapper.updateByPrimaryKey(cart);
        //更新完成后跳到购物车列表页面
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartVo> delete(String productIds, Integer userId) {
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if(CollectionUtils.isEmpty(productList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserAndProductId(userId,productList);
        //删除后跳到购物车列表页面
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartVo> select(Integer productId, Integer checked, Integer userId) {
        cartMapper.updateCartProductCheckedStatus(userId,productId,checked);
        //更新状态后跳到购物车列表页面
        return this.list(userId);
    }

    private CartVo getCartVoLimit(Integer userId) {
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        BigDecimal cartTotalPrice = new BigDecimal("0");

        if (CollectionUtils.isNotEmpty(cartList)) {
            for (Cart cartItem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setProductId(cartItem.getProductId());
                cartProductVo.setUserId(userId);
                cartProductVo.setId(cartItem.getId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                cartProductVo.setProductMainImage(product.getMainImage());
                cartProductVo.setProductName(product.getName());
                cartProductVo.setProductSubtitle(product.getSubtitle());
                cartProductVo.setProductStatus(product.getStatus());
                cartProductVo.setProductPrice(product.getPrice());
                cartProductVo.setProductStock(product.getStock());
                //判断购物车所选的商品数量是否小于商品现有库存
                int buyLimitCount = 0;
                if (product.getStock() >= cartItem.getQuantity()) {
                    //库存充足
                    buyLimitCount = cartItem.getQuantity();
                    cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                }else {
                    buyLimitCount = product.getStock();
                    cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                    //更新购物车中商品数量为现有商品最大库存
                    Cart cartForQuantity = new Cart();
                    cartForQuantity.setId(cartItem.getId());
                    cartForQuantity.setQuantity(buyLimitCount);
                    cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                }
                cartProductVo.setQuantity(buyLimitCount);
                //计算单个总价
                cartProductVo.setProductPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), buyLimitCount));
                cartProductVo.setProductChecked(cartItem.getChecked());
                //如果商品是勾选状态，要加入到总价格里
                if (cartItem.getChecked() == Const.Cart.CHECKED) {
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setImgHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        return cartVo;
    }

    private Boolean getAllCheckedStatus(Integer userId) {
        return cartMapper.cartProductAllCheckedStatus(userId) == 0;
    }
}
