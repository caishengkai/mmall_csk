package com.csk.mmall.controller.backend;

import com.csk.mmall.common.Const;
import com.csk.mmall.common.ServerResponse;
import com.csk.mmall.pojo.User;
import com.csk.mmall.service.IOrderService;
import com.csk.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: caishengkai
 * @time: 2019/11/29 18:13
 **/
@Controller
@RequestMapping("/manage/order/")
public class OrderManageController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IOrderService orderService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆！");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return orderService.getManageList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限！");
        }
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(Long orderNo, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆！");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return orderService.manageDeatil(orderNo);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限！");
        }
    }

    @RequestMapping("sendGoods.do")
    @ResponseBody
    public ServerResponse sendGoods(Long orderNo, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆！");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return orderService.sendGoods(orderNo);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限！");
        }
    }

}
