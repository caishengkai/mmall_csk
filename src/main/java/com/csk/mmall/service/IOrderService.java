package com.csk.mmall.service;

import com.csk.mmall.common.ServerResponse;

import java.util.Map;

public interface IOrderService {
    ServerResponse pay(Long orderId, Integer userId, String path);

    ServerResponse alipayCallback(Map<String, String> params);

    ServerResponse createOrder(Integer shipping, Integer userId);

    ServerResponse cancel(Long orderNo, Integer userId);

    ServerResponse getOrderDeatil(Long orderNo, Integer userId);

    ServerResponse getOrderList(int pageNum, int pageSize, Integer id);

    ServerResponse getManageList(int pageNum, int pageSize);

    ServerResponse manageDeatil(Long orderNo);

    ServerResponse sendGoods(Long orderNo);
}
