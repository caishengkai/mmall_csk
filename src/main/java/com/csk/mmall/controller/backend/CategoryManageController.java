package com.csk.mmall.controller.backend;

import com.csk.mmall.common.Const;
import com.csk.mmall.common.ServerResponse;
import com.csk.mmall.pojo.Category;
import com.csk.mmall.pojo.User;
import com.csk.mmall.service.ICategoryService;
import com.csk.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 商品类别Controller
 * Create by csk 2019.11.11
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICategoryService categoryService;

    @RequestMapping("addCategory.do")
    @ResponseBody
    public ServerResponse addCategory(@RequestParam(value = "parentId", defaultValue = "0") int parentId, String categoryName, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆！");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.addCategory(parentId, categoryName);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限！");
        }
    }

    @RequestMapping("setCategoryName.do")
    @ResponseBody
    public ServerResponse setCategoryName(int categoryId, String categoryName, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆！");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.setCategoryName(categoryId, categoryName);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限！");
        }
    }

    @RequestMapping("getCategory.do")
    @ResponseBody
    public ServerResponse<List<Category>> getCategory(int categoryId, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆！");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.getCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限！");
        }
    }

    @RequestMapping("getDeepCategory.do")
    @ResponseBody
    public ServerResponse<List<Category>> getDeepCategory(int categoryId, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆！");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.getDeepCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限！");
        }
    }
}
