package com.csk.mmall.service.impl;

import com.csk.mmall.common.ServerResponse;
import com.csk.mmall.dao.CategoryMapper;
import com.csk.mmall.pojo.Category;
import com.csk.mmall.service.ICategoryService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: 商品类别service实现类
 * @author: caishengkai
 * @time: 2019/11/11 9:44
 **/
@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(int parentId, String categoryName) {
        Category category = new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(true); //设置为可用
        int resultCount = categoryMapper.insert(category);
        if(resultCount > 0){
            return ServerResponse.createBySuccess("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    @Override
    public ServerResponse setCategoryName(int categoryId, String categoryName) {
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int resultCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(resultCount > 0){
            return ServerResponse.createBySuccess("更新品类成功");
        }
        return ServerResponse.createByErrorMessage("更新品类失败");
    }

    @Override
    public ServerResponse<List<Category>> getCategory(int categoryId) {
        List<Category> list = categoryMapper.getChildByParentId(categoryId);
        if (CollectionUtils.isEmpty(list)) {
            return ServerResponse.createByErrorMessage("未找到当前分类的子分类！");
        }
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse<List<Category>> getDeepCategory(int categoryId) {
        Set<Category> set = new HashSet<>();
        getByParentId(set, categoryId);
        if (CollectionUtils.isEmpty(set)) {
            return ServerResponse.createByErrorMessage("未找到当前分类的子分类！");
        }
        List<Category> list = Lists.newArrayList(set);
        return ServerResponse.createBySuccess(list);
    }

    private void getByParentId(Set<Category> set, int parentId) {
        List<Category> list = categoryMapper.getChildByParentId(parentId);
        if (!CollectionUtils.isEmpty(list)) {
            set.addAll(list);
            for (Category category : list) {
                getByParentId(set, category.getId());
            }
        }
    }
}
