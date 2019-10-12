package com.zhxgkj.o2o.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.zhxgkj.o2o.dto.ShopExecution;
import com.zhxgkj.o2o.entity.Shop;
import com.zhxgkj.o2o.exceptions.ShopOperationException;

public interface ShopService {
	/**
	 * 根据shopCondition分页返回相应列表数据
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	/**
	 * 通过店铺Id获取店铺信息
	 * @param shopId
	 * @return
	 */
	public Shop getByShopId(long shopId);
	/**
	 * 更新店铺信息 包括图片处理
	 * @param shop
	 * @param shopImg
	 * @return
	 */
	public ShopExecution modifyShop(Shop shop,MultipartFile shopImg) throws ShopOperationException;
	/**
	 * 注册店铺信息 包括图片处理
	 * @param shop
	 * @param shopImg
	 * @return
	 */
	public ShopExecution addShop(Shop shop,MultipartFile shopImg) throws ShopOperationException;
}
