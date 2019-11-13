package com.csk.mmall.service;

import com.csk.mmall.common.ServerResponse;
import com.csk.mmall.pojo.Product;

public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse setSaleStatus(int productId, int status);

    ServerResponse manageProductDetail(int productId);

    ServerResponse getList(int pageNum, int pageSize, Product product);
}
