package com.csk.mmall.service.impl;

import com.csk.mmall.common.ServerResponse;
import com.csk.mmall.dao.ProductMapper;
import com.csk.mmall.pojo.Category;
import com.csk.mmall.pojo.Product;
import com.csk.mmall.service.IProductService;
import com.csk.mmall.util.DateUtil;
import com.csk.mmall.util.PropertiesUtil;
import com.csk.mmall.vo.ProductDetailVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: caishengkai
 * @time: 2019/11/13 10:26
 **/
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if (StringUtils.isNotBlank(product.getSubImages())) {
            String[] subImageArray = product.getSubImages().split(",");
            product.setMainImage(subImageArray[0]);
        }

        if (product.getId() != null) {
            int resultCount = productMapper.updateByPrimaryKey(product);
            if (resultCount > 0) {
                return ServerResponse.createBySuccessMessage("更新产品成功！");
            }
            return ServerResponse.createByErrorMessage("更新产品失败！");
        } else {
            int resultCount = productMapper.insert(product);
            if (resultCount > 0) {
                return ServerResponse.createBySuccessMessage("添加产品成功！");
            }
            return ServerResponse.createByErrorMessage("添加产品失败！");
        }
    }

    @Override
    public ServerResponse setSaleStatus(int productId, int status) {
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int resultCount = productMapper.updateByPrimaryKeySelective(product);
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("更新产品状态成功！");
        }
        return ServerResponse.createByErrorMessage("更新产品状态失败！");
    }

    @Override
    public ServerResponse manageProductDetail(int productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            ServerResponse.createByErrorMessage("产品不存在！");
        }
        ProductDetailVo productDetailVo = this.assembleProductDetailVo(product);
        return  ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * pojo转vo
     * @param product
     * @return
     */
    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        productDetailVo.setCreateTime(DateUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    @Override
    public ServerResponse getList(int pageNum, int pageSize, Product product) {
        //设置pageNum和pageSize后，遇到第一个sql查询时会自动拼接分页条件
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectList(product);

        List<ProductDetailVo> productDetailVos = new ArrayList<>();
        for (Product item : products) {
            ProductDetailVo productDetailVo = this.assembleProductDetailVo(item);
            productDetailVos.add(productDetailVo);
        }
        PageInfo pageInfo = new PageInfo(products);
        pageInfo.setList(productDetailVos);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
