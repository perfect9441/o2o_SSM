package com.zhxgkj.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.zhxgkj.o2o.BaseTest;
import com.zhxgkj.o2o.dto.ProductExecution;
import com.zhxgkj.o2o.entity.Product;
import com.zhxgkj.o2o.entity.ProductCategory;
import com.zhxgkj.o2o.entity.Shop;
import com.zhxgkj.o2o.enums.ProductStateEnum;
import com.zhxgkj.o2o.util.ImgUtil;

public class ProductServiceTest extends BaseTest{
	@Autowired
	private ProductService productService;
	@Ignore
	@Test
	public void testAddProduct() throws IOException {
//		创建shopId为1且productCategoryId为1的商品实例并给予其成员变量赋值
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setShop(shop);
		product.setProductName("测试商品1");
		product.setProductDesc("测试商品1");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		product.setLastEditTime(new Date());
//		缩略图文件流
		String filePath0 = "F:\\Download\\01.jpg";
		List<MultipartFile> productImgList = new ArrayList<>();
//		详情图片文件流
		String filePath1 = "F:\\Download\\02.png";
		MultipartFile productImg1 = ImgUtil.path2MultipartFile(filePath1);
		productImgList.add(productImg1);
		String filePath2 = "F:\\Download\\03.jpg";
		MultipartFile productImg2 = ImgUtil.path2MultipartFile(filePath2);
		productImgList.add(productImg2);
//		添加商品并验证
		ProductExecution se = productService.addProduct(product,ImgUtil.path2MultipartFile(filePath0),
				productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), se.getState());
	};
	@Test
	public void testModifyProduct() throws IOException{
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setProductId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("正式的商品");
		product.setProductDesc("正式的商品");
//		缩略图文件流
		String filePath0 = "F:\\Download\\001.jpg";
		List<MultipartFile> productImgList = new ArrayList<>();
//		详情图片文件流
		String filePath1 = "F:\\Download\\002.png";
		MultipartFile productImg1 = ImgUtil.path2MultipartFile(filePath1);
		productImgList.add(productImg1);
		String filePath2 = "F:\\Download\\003.jpg";
		MultipartFile productImg2 = ImgUtil.path2MultipartFile(filePath2);
		productImgList.add(productImg2);
//		修改商品信息并验证
		ProductExecution se = productService.modifyProduct(product, ImgUtil.path2MultipartFile(filePath0), productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), se.getState());
	}
}
