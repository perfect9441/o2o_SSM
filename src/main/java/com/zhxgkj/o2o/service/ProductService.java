package com.zhxgkj.o2o.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.zhxgkj.o2o.dto.ProductExecution;
import com.zhxgkj.o2o.entity.Product;
import com.zhxgkj.o2o.exceptions.ProductOperationException;

public interface ProductService {
	/**
	 * 添加商品信息、缩略图以及详情图片
	 * @param product
	 * @param productImg
	 * @param productImgList
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product,MultipartFile productImg,List<MultipartFile> productImgList)throws ProductOperationException;
	/**
	 * 通过商品id查询唯一的商品信息
	 * @param productId
	 * @return
	 */
	Product getProductById(long productId);
	/**
	 * 修改商品信息及图片处理
	 * @param product
	 * @param porductImg
	 * @param productImgList
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution modifyProduct(Product product,MultipartFile productImg,List<MultipartFile> productImgList) throws ProductOperationException;
}
