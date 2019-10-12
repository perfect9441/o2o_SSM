package com.zhxgkj.o2o.dto;

import java.util.List;

import com.zhxgkj.o2o.entity.Product;
import com.zhxgkj.o2o.enums.ProductStateEnum;

public class ProductExecution {
//	标识状态
	private int state;
//	状态标识
	private String stateInfo;
//	商品数量
	private int count;
//	操作的Product
	private Product product;
//	获取的product列表
	private List<Product> productList;
	
	public ProductExecution() {
	}
//操作失败的构造器
	public ProductExecution(ProductStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
//操作成功的构造器（单个）
	public ProductExecution(ProductStateEnum stateEnum, Product product) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.product = product;
	}
//操作成功的构造器（多个）
	public ProductExecution(ProductStateEnum stateEnum, List<Product> productList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productList = productList;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	
}
