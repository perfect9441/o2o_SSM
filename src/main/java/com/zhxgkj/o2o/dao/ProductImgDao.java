package com.zhxgkj.o2o.dao;

import java.util.List;

import com.zhxgkj.o2o.entity.ProductImg;

public interface ProductImgDao {
	/**
	 * 查询产品的图片列表
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgListByProductId(long productId);
	/**
	 * 批量添加商品详情图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	/**
	 * 根据商品id删除图片列表
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
}
