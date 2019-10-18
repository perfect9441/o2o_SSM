package com.zhxgkj.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhxgkj.o2o.dao.ProductCategoryDao;
import com.zhxgkj.o2o.dao.ProductDao;
import com.zhxgkj.o2o.dto.ProductCategoryExecution;
import com.zhxgkj.o2o.entity.ProductCategory;
import com.zhxgkj.o2o.enums.OperationStatusEnum;
import com.zhxgkj.o2o.enums.ProductCategoryStateEnum;
import com.zhxgkj.o2o.exceptions.ProductCategoryOperationException;
import com.zhxgkj.o2o.service.ProductCategoryService;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private ProductDao productDao;
	@Override
	/**
	 * 查询制定某个店铺下的所有商品类别信息
	 */
	public List<ProductCategory> getProductCategoryList(long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}
	@Override
//	事务管理
	@Transactional
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {
		if(productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectionNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if(effectionNum <= 0) {
					throw new ProductCategoryOperationException("创建店铺类别失败");
				}else {
					return new ProductCategoryExecution(OperationStatusEnum.SUCCESS, productCategoryList, effectionNum);
				}
			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory error:" + e.getMessage());
			}
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPETY_LIST);
		}
	}
	@Override
//	事务管理
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
//		解除tb_product里的商品与productcategoryId的关联
//		将此商品类别下的商品的id置为空
		try {
			int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
			if(effectedNum < 0) {
				throw new ProductCategoryOperationException("商品类别更新失败！");
			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error" + e.getMessage());
		}
//		删除该productCategory
		try {
			int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if(effectedNum <= 0) {
				throw new ProductCategoryOperationException("商品类被删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
		}
	}
}
