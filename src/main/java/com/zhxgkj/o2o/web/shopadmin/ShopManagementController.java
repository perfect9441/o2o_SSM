package com.zhxgkj.o2o.web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhxgkj.o2o.dto.ShopExecution;
import com.zhxgkj.o2o.entity.Area;
import com.zhxgkj.o2o.entity.PersonInfo;
import com.zhxgkj.o2o.entity.Shop;
import com.zhxgkj.o2o.entity.ShopCategory;
import com.zhxgkj.o2o.enums.ShopStateEnum;
import com.zhxgkj.o2o.service.AreaService;
import com.zhxgkj.o2o.service.ShopCategoryService;
import com.zhxgkj.o2o.service.ShopService;
import com.zhxgkj.o2o.util.CodeUtil;
import com.zhxgkj.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	/**
	 * 店铺管理操作
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getshopmanagementinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId <= 0) {
			Object currentShopObject = request.getSession().getAttribute("surrentShop");
			if(currentShopObject == null) {
				modelMap.put("redirect",true);
				modelMap.put("url","/o2o/shopadmin/shoplist");
			}else {
				Shop currentShop = (Shop) currentShopObject;
				modelMap.put("redirect",false);
				modelMap.put("shopId",currentShop.getShopId());
			}
		}else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
	}
	/**
	 * 获取店铺列表信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getshoplist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopList(HttpServletRequest request){
		Map<String, Object> modelMap= new HashMap<String, Object>();
		PersonInfo user = new PersonInfo();
		user.setUserId(1L);
		user.setName("test");
		request.getSession().setAttribute("user", user);
		user = (PersonInfo) request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("user",user);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	/**
	 * 根据id获取店铺信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getshopbyid",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopById(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId >-1) {
			try {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	
	/**
	 * 获取初始化信息
	 * @return
	 */
	@RequestMapping(value="/getshopinitinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopInitInfo(){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList",shopCategoryList);
			modelMap.put("areaList",areaList);
			modelMap.put("success",true);
		} catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());	
		}
		return modelMap;
	}
	
	/**
	 * 注册店铺
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/registershop",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success",false);
			modelMap.put("errMsg","输入了错误的验证码");
			return modelMap;
		}
//		1.接受并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
//		====pojo与json的相互转化====
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
//			将字符串转换成对应的实体类
			shop = mapper.readValue(shopStr,Shop.class);
		} catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
//		接收前段上传的图片文件信息
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver CommonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(CommonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");	
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","上传图片不能为空！");
			return modelMap;
		}
//		2.注册店铺
		if(shop != null && shopImg != null) {
			PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
			shop.setOwner(owner);
			ShopExecution se = shopService.addShop(shop, shopImg);
			if(se.getState() == ShopStateEnum.CHECK.getState()) {
				modelMap.put("success", true);
//				该用户可操作的店铺列表
				@SuppressWarnings("unchecked")
				List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
				if(shopList == null || shopList.size() == 0) {
					shopList = new ArrayList<Shop>();
				}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
				
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","店铺信息不能为空！");
			return modelMap;
		}
	}
	
	/**
	 * 修改店铺信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/modifyshop",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> modifyShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success",false);
			modelMap.put("errMsg","输入了错误的验证码");
			return modelMap;
		}
//		1.接受并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr,Shop.class);
		} catch (Exception e) {
			modelMap.put("success",false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
//		接收前段上传的图片文件信息
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver CommonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(CommonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");	
		}
//		2.修改店铺
		if(shop != null && shop.getShopId() != null) {
			ShopExecution se;
			if(shopImg == null) {
				se = shopService.modifyShop(shop, null);
			}else {
				se = shopService.modifyShop(shop, shopImg);
			}
			if(se.getState() == ShopStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","店铺id不能为空！");
			return modelMap;
		}
	}
}
