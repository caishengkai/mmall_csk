package com.csk.mmall.service;

import com.csk.mmall.common.ServerResponse;

public interface IOrderService {
    ServerResponse pay(Integer orderId, Integer id, String path);
}
