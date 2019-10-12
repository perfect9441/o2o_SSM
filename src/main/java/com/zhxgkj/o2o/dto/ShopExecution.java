package com.zhxgkj.o2o.dto;

import java.util.List;

import com.zhxgkj.o2o.entity.Shop;
import com.zhxgkj.o2o.enums.ShopStateEnum;

public class ShopExecution {
//	结果状态
	private int state;
//	状态标识
	private String stateInfo;
//	店铺数量
	private int count;
//	操作shop（增删改查店铺时用到）
	private Shop shop;
//	shop列表（查询店铺列表的时候使用）
	private List<Shop> shopList;
	
	public ShopExecution() {
		
	}
//	店铺操作失败构造器
	 public ShopExecution(ShopStateEnum sateEnum) {
		 this.state = sateEnum.getState();
		 this.stateInfo = sateEnum.getStateInfo();
	 }
//	 店铺操作成功构造器(返回单个结果)
	 public ShopExecution(ShopStateEnum sateEnum,Shop shop) {
		 this.state = sateEnum.getState();
		 this.stateInfo = sateEnum.getStateInfo();
		 this.shop = shop;
	 }
//	 店铺操作成功构造器(返回列表结果)
	 public ShopExecution(ShopStateEnum sateEnum,List<Shop> shopList) {
		 this.state = sateEnum.getState();
		 this.stateInfo = sateEnum.getStateInfo();
		 this.shopList = shopList;
	 }
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<Shop> getShopList() {
		return shopList;
	}
	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	 
}
