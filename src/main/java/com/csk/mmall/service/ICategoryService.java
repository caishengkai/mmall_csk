package com.csk.mmall.service;

import com.csk.mmall.common.ServerResponse;
import com.csk.mmall.pojo.Category;

import java.util.List;

public interface ICategoryService {
    ServerResponse addCategory(int parentId, String categoryName);

    ServerResponse setCategoryName(int categoryId, String categoryName);

    ServerResponse<List<Category>> getCategory(int categoryId);

    ServerResponse<List<Category>> getDeepCategory(int categoryId);
}
