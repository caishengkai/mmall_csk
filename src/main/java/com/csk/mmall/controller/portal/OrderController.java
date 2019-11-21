package com.csk.mmall.controller.portal;

import com.csk.mmall.common.Const;
import com.csk.mmall.common.ServerResponse;
import com.csk.mmall.pojo.User;
import com.csk.mmall.service.IOrderService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * @description:
 * @author: caishengkai
 * @time: 2019/11/21 11:02
 **/
@Controller
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping("pay.do")
    @ResponseBody
    public ServerResponse pay(Integer orderId, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录！");
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        return orderService.pay(orderId, user.getId(), path);
    }

    @RequestMapping("alipayCallback.do")
    @ResponseBody
    public ServerResponse alipayCallback(HttpServletRequest request) {
        Map<String, String> map = Maps.newHashMap();
        for (Iterator<String> iterator = map.keySet().iterator();iterator.hasNext();) {
            String name = iterator.next();
            String value = map.get(name);
            System.out.println("key：" + name + "，value：" + value);
        }
    }
}
