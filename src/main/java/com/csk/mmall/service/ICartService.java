package com.csk.mmall.service;

import com.csk.mmall.common.ServerResponse;
import com.csk.mmall.vo.CartVo;

public interface ICartService {
    ServerResponse<CartVo> list(Integer userId);

    ServerResponse add(Integer productId, Integer count, Integer userId);

    ServerResponse update(Integer productId, Integer count, Integer userId);

    ServerResponse delete(String productIds, Integer userId);

    ServerResponse select(Integer productId, Integer checked, Integer userId);
}
