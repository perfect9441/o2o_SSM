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

import com.zhxgkj.o2o.dto.ShopExecution;
import com.zhxgkj.o2o.entity.Area;
import com.zhxgkj.o2o.entity.Shop;
import com.zhxgkj.o2o.entity.ShopCategory;
import com.zhxgkj.o2o.service.AreaService;
import com.zhxgkj.o2o.service.ShopCategoryService;
import com.zhxgkj.o2o.service.ShopService;
import com.zhxgkj.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
	@Autowired
	private AreaService areaService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private ShopService shopService;
	/**
	 * 返回商品列表页面里面的ShopCategory列表（二级或者一级），以及区域信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listshopspageinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShopsPageInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		从前端获取parentId	
		long parentId = HttpServletRequestUtil.getLong(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		if(parentId != -1) {
//			如果parentId存在，则取出该一级ShopCategory下的二级shopCategory列表
			try {
				ShopCategory shopCategoryCondition = new ShopCategory();
				ShopCategory parent = new ShopCategory();
				parent.setShopCategoryId(parentId);
				shopCategoryCondition.setParent(parent);
				shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}else {
//			如果parentId存在，则取出一级所有ShopCategory（用户在首页选择的是全部商品列表）
			try {
				shopCategoryList = shopCategoryService.getShopCategoryList(null);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());				
			}
		}
		modelMap.put("shopCategoryList", shopCategoryList);
		List<Area> areaList = null;
		try {
			areaList = areaService.getAreaList();
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());			
		}
		return modelMap;
	}
	/**
	 * 获取指定查询条件下的店铺列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listshops",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listShops(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
//		获取每页显示条数
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
//		非空判断
		if((pageIndex>-1)&&(pageSize>-1)) {
//			获取一级类别的id
			long parentId = HttpServletRequestUtil.getLong(request, "parentId");
//			获取二级类别的id
			long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
//			获取区域的id
			int areaId = HttpServletRequestUtil.getInt(request, "areaId");
//			获取模糊查询的名字
			String shopName = HttpServletRequestUtil.getString(request, "shopName");
//			获取组合之后的查询条件
			Shop shopCondition = compactShopCondition4Search(parentId,shopCategoryId,areaId,shopName);
//			根据查询条件和分页信息获取店铺列表，并返回总数
			ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex");
		}
		return modelMap;
	}
	/**
	 * 组合查询条件，并将条件封装到ShopCondition对象里返回
	 * @param parnetId
	 * @param shopCategoryId
	 * @param areaId
	 * @param shopName
	 * @return
	 */
	private Shop compactShopCondition4Search(long parnetId,long shopCategoryId,int areaId,String shopName) {
		Shop shopCondition = new Shop();
//		查询某个一级ShopCategory下面的所有二级ShopCategory里面的店铺列表
		if(parnetId != -1L) {
			ShopCategory childCategory = new ShopCategory();
			ShopCategory parentCategory = new ShopCategory();
			parentCategory.setShopCategoryId(parnetId);
			childCategory.setParent(parentCategory);
			shopCondition.setShopCategory(childCategory);
			
		}
//			查询某个二级shopCategory下的店铺列表
		if(shopCategoryId != -1L) {
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategory(shopCategory);
		}
//			查询位于某个区域Id下面的店铺列表
		if(areaId != -1L) {
			Area area = new Area();
			area.setAreaId(areaId);
			shopCondition.setArea(area);
		}
//		查询名字里包含shopName的店铺列表
		if(shopName != null) {
			shopCondition.setShopName(shopName);
		}
//		前端展示的都是审核成功的店铺3
		shopCondition.setEnableStatus(1);
		return shopCondition;
	}
}
