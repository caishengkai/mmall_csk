package com.csk.mmall.controller.portal;

import com.csk.mmall.common.Const;
import com.csk.mmall.common.ServerResponse;
import com.csk.mmall.pojo.Shipping;
import com.csk.mmall.pojo.User;
import com.csk.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: caishengkai
 * @time: 2019/11/18 9:41
 **/
@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(Shipping shipping, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录！");
        }

        return shippingService.add(shipping, user.getId());
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(Shipping shipping, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录！");
        }

        return shippingService.update(shipping, user.getId());
    }

    @RequestMapping("delete.do")
    @ResponseBody
    public ServerResponse delete(Integer shippingId, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录！");
        }

        return shippingService.delete(shippingId, user.getId());
    }

    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse select(Integer shippingId, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录！");
        }

        return shippingService.select(shippingId, user.getId());
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录！");
        }

        return shippingService.list(pageNum, pageSize, user.getId());
    }
}
