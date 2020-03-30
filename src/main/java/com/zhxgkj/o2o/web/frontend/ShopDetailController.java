package com.zhxgkj.o2o.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhxgkj.o2o.dto.ProductExecution;
import com.zhxgkj.o2o.entity.Product;
import com.zhxgkj.o2o.entity.ProductCategory;
import com.zhxgkj.o2o.entity.Shop;
import com.zhxgkj.o2o.service.ProductCategoryService;
import com.zhxgkj.o2o.service.ProductService;
import com.zhxgkj.o2o.service.ShopService;
import com.zhxgkj.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	/**
	 * 获取店铺信息以及该店铺下的商品分类列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/listshopdetailpageinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		获取前台传递过来的shopId
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		Shop shop = null;
		List<ProductCategory> productCategorylist = null;
		if(shopId != -1) {
//			根据shopId获取店铺信息
			shop = shopService.getByShopId(shopId);
//			获取店铺下面的商品类别信息
			productCategorylist = productCategoryService.getProductCategoryList(shopId);
			modelMap.put("shop", shop);
			modelMap.put("productCategoryList", productCategorylist);
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	/**
	 * 根据查询条件分页出该店铺下面的所有商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/listproductsbyshop",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listproductsbyshop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		获取分页信息
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
//		获取shopId
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
//		空值判断
		if((pageIndex > -1)&&(pageSize > -1)&&(shopId > -1)) {
//			尝试获取商品类别Id
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
//			尝试获取模糊查询查找的商品名
			String productName = HttpServletRequestUtil.getString(request, "productName");
//			组合查询条件
			Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
//			按照传入的查询条件以及分页信息返回相应的商品列表以及总数
			ProductExecution pe =productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId or pageIndex or pageSize");
		}
		return modelMap;
	}
	/**
	 * 组合查询条件，并将查询条件封装到ProductCondition对象中返回
	 * @param shopId
	 * @param productCategoryId
	 * @param productName
	 * @return
	 */
	private Product compactProductCondition4Search(long shopId,long productCategoryId,String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		if(productCategoryId != -1L) {
//			查询某个商品类别下的商品列表
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if(productName != null) {
//			查询名字包含productName的店铺列表
			productCondition.setProductName(productName);
		}
//		只选择状态为允许上架的商品
		productCondition.setEnableStatus(1);
		return productCondition;
	}
}