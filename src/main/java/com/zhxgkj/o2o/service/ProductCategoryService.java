package com.zhxgkj.o2o.service;

import java.util.List;

import com.zhxgkj.o2o.dto.ProductCategoryExecution;
import com.zhxgkj.o2o.entity.ProductCategory;
import com.zhxgkj.o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {
	/**
	 * 查询制定某个店铺下的所有商品类别信息
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getProductCategoryList(long shopId);
	/**
	 * 增加指定店铺下的商品类别信息
	 * @param productCategorylist
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategorylist)throws ProductCategoryOperationException;
	/**
	 * 根据条件删除指定店铺下的商品类别信息（将此类别下的商品的类别id置为空，再删除该商品类别）
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId)throws ProductCategoryOperationException;
}
