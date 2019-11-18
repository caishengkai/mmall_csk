package com.csk.mmall.service;

import com.csk.mmall.common.ServerResponse;
import com.csk.mmall.pojo.Shipping;

public interface IShippingService {
    ServerResponse add(Shipping shipping, Integer userId);

    ServerResponse update(Shipping shipping, Integer userId);

    ServerResponse delete(Integer shippingId, Integer userId);

    ServerResponse select(Integer shippingId, Integer userId);

    ServerResponse list(int pageNum, int pageSize, Integer userId);
}
