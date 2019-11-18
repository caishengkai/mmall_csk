package com.csk.mmall.service.impl;

import com.csk.mmall.common.ServerResponse;
import com.csk.mmall.dao.ShippingMapper;
import com.csk.mmall.pojo.Shipping;
import com.csk.mmall.service.IShippingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: caishengkai
 * @time: 2019/11/18 9:41
 **/
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Shipping shipping, Integer userId) {
        shipping.setUserId(userId);
        int resultCount = shippingMapper.insert(shipping);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("添加收货地址成功！");
        } else {
            return ServerResponse.createBySuccessMessage("添加收货地址失败！");
        }
    }

    @Override
    public ServerResponse update(Shipping shipping, Integer userId) {
        shipping.setUserId(userId);
        int resultCount = shippingMapper.updateByShipping(shipping);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("更新收货地址成功！");
        } else {
            return ServerResponse.createBySuccessMessage("更新收货地址失败！");
        }
    }

    @Override
    public ServerResponse delete(Integer shippingId, Integer userId) {
        int resultCount = shippingMapper.deleteByShippingAndUserId(shippingId, userId);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("删除收货地址成功！");
        } else {
            return ServerResponse.createBySuccessMessage("删除收货地址失败！");
        }
    }

    @Override
    public ServerResponse select(Integer shippingId, Integer userId) {
        Shipping shipping = shippingMapper.selectByShippingAndUserId(shippingId, userId);
        return ServerResponse.createBySuccess(shipping);
    }

    @Override
    public ServerResponse list(int pageNum, int pageSize, Integer userId) {
        //设置pageNum和pageSize后，遇到第一个sql查询时会自动拼接分页条件
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
