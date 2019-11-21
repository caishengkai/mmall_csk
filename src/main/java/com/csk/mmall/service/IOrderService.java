package com.csk.mmall.service;

import com.csk.mmall.common.ServerResponse;

public interface IOrderService {
    ServerResponse pay(Long orderId, Integer id, String path);
}
