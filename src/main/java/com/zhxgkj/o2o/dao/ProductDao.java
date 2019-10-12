package com.zhxgkj.o2o.dao;

import org.apache.ibatis.annotations.Param;

import com.zhxgkj.o2o.entity.Product;

public interface ProductDao {
//	int queryProductCount(@Param("productCondition") Product productCondition);
	/**
	 * 通过productId查询唯一的商品信息
	 * @param productId
	 * @return
	 */
	Product queryProductById(long productId);
	/**
	 * 插入商品
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);
	/**
	 * 更新商品信息
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);
}
