package com.csk.mmall.service;

import com.csk.mmall.common.ServerResponse;

import java.util.Map;

public interface IOrderService {
    ServerResponse pay(Long orderId, Integer id, String path);

    ServerResponse alipayCallback(Map<String, String> params);
}
