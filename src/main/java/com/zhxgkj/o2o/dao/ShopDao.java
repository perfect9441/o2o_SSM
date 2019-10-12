package com.zhxgkj.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhxgkj.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * @Param 当方法有多个参数时，需指定参数名称（唯一标识），只有一个参数时，不用指定参数名称（唯一标识）
	 */
	/**
	 * 分页查询店铺，可输入条件：店铺名、店铺状态、店铺类别、区域Id、owner 
	 * @param shopCondition
	 * @param roeIndex 从第几行开始取
	 * @param pageSize 返回的条数
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
	/**
	 * 返回queryShopList总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
	/**
	 * 通过shop id查询店铺
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
	/**
	 * 新增店铺 
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	/**
	 * 更新店鋪信息
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
	
	

}
