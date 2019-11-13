package com.csk.mmall.controller.backend;

import com.csk.mmall.common.Const;
import com.csk.mmall.common.ServerResponse;
import com.csk.mmall.pojo.Product;
import com.csk.mmall.pojo.User;
import com.csk.mmall.service.IFileService;
import com.csk.mmall.service.IProductService;
import com.csk.mmall.service.IUserService;
import com.csk.mmall.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 后台产品管理模块
 * @author: caishengkai
 * @time: 2019/11/13 10:24
 **/
@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IFileService fileService;

    /**
     * 新增产品
     * @param product
     * @param session
     * @return
     */
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(Product product, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆！");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return productService.saveOrUpdateProduct(product);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限！");
        }
    }

    /**
     * 设置产品状态
     * @param productId
     * @param status
     * @param session
     * @return
     */
    @RequestMapping("setSaleStatus.do")
    @ResponseBody
    public ServerResponse setSaleStatus(int productId, int status, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆！");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return productService.setSaleStatus(productId, status);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限！");
        }
    }

    /**
     * 产品详情
     * @param productId
     * @param session
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(int productId, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆！");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return productService.manageProductDetail(productId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限！");
        }
    }

    /**
     * 产品列表,带查询
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, Product product, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆！");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            return productService.getList(pageNum, pageSize, product);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限！");
        }
    }

    /**
     * 上传文件
     * @param file
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file") MultipartFile file, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆！");
        }

        if (userService.checkAdminRole(user).isSuccess()) {
            String path = session.getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(path, file);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("uri", targetFileName);
            resultMap.put("url", url);
            return ServerResponse.createBySuccess(resultMap);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限！");
        }
    }
}
