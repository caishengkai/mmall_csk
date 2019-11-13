package com.csk.mmall.controller.portal;

import com.csk.mmall.util.FtpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 文件下载测试controller
 * @author: caishengkai
 * @time: 2019/11/13 14:59
 **/
@Controller
@RequestMapping("/file/")
public class FileController {

    @RequestMapping("download.do")
    public void download(String fileName, HttpServletResponse response) {
        try {
            //设置文件下载头
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            FtpUtil.downloadFile(fileName, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
