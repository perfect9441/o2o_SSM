package com.zhxgkj.o2o.service;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Path2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.zhxgkj.o2o.BaseTest;
import com.zhxgkj.o2o.dto.ShopExecution;
import com.zhxgkj.o2o.entity.Area;
import com.zhxgkj.o2o.entity.PersonInfo;
import com.zhxgkj.o2o.entity.Shop;
import com.zhxgkj.o2o.entity.ShopCategory;
import com.zhxgkj.o2o.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testAddShop() throws IOException {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("測試店鋪1");
		shop.setShopDesc("test1");
		shop.setShopAddr("test1");
		shop.setPhone("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("審核中");
		String filePath = "C:/Users/Administrator/Desktop/2.jpg";
		ShopExecution se = shopService.addShop(shop,path2MultipartFile(filePath));
		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
	}
	
	/**
	 * filePath to MultipartFile
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	private MultipartFile path2MultipartFile(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream input = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
				IOUtils.toByteArray(input));
		return multipartFile;
	}

	
}
