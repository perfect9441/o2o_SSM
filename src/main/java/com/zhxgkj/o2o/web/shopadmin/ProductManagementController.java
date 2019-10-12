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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhxgkj.o2o.dto.ProductExecution;
import com.zhxgkj.o2o.entity.Product;
import com.zhxgkj.o2o.entity.ProductCategory;
import com.zhxgkj.o2o.entity.Shop;
import com.zhxgkj.o2o.enums.OperationStatusEnum;
import com.zhxgkj.o2o.enums.ProductStateEnum;
import com.zhxgkj.o2o.service.ProductCategoryService;
import com.zhxgkj.o2o.service.ProductService;
import com.zhxgkj.o2o.util.CodeUtil;
import com.zhxgkj.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
//	支持上传商品详情图的最大数量
	private static final int IMGMAXCOUNT = 9;
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addproduct",method = {RequestMethod.POST})
	@ResponseBody
	private Map<String,Object> addProduct(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		验证码校验
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
//		接受前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
//		获取商品信息
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
//		文件流处理
//		MultipartHttpServletRequest multipartHttpServletRequest = null;
//		缩略图处理
		MultipartFile productImg = null;
//		详情图处理
		List<MultipartFile> productImgs = new ArrayList<MultipartFile>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		try {
//			若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
			if(multipartResolver.isMultipart(request)) {
//				multipartResolver = (MultipartHttpServletRequest) request;
				productImg = handleImage(request, productImgs);
				product = mapper.readValue(productStr, Product.class);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传的图片不能为空");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;		
		}
		try {
//			将前端传过来的表单String流转换成Product实体类
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
//		如果Product信息，缩略图以及详情图列表为空，开始进行商品添加操作
		if(product !=null &&  productImg != null && productImgs.size()>0) {
			try {
//				从session中获取当前店铺的Id并赋值给product，减少对前端数据的依赖
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
//				执行添加操作
				ProductExecution pe = productService.addProduct(product, productImg, productImgs);
				if(pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}
	/**
	 * 
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/getproductbyid",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductById(@RequestParam Long productId){
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		非空判断
		if(productId > -1) {
//			获取商品信息
			Product product = productService.getProductById(productId);
			List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
			modelMap.put("product", product);
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}
	@RequestMapping(value="/modifyproduct",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyProduct(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		判断是商品编辑的时候还是上下架操作的时候，若为前者者进行验证码判断，否则跳过验证码判断
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		if(!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
//		参数第一个参数 商品信息
		String productStr = null;
		Product product = null;
		ObjectMapper mapper = new ObjectMapper();
//		将前端传输的字符串解析成product实体类
		try {
			productStr = HttpServletRequestUtil.getString(request, "productStr");
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
//		商品缩略图和商品详情图处理
		MultipartFile productImg = null;
		List<MultipartFile> productImgList = new ArrayList<MultipartFile>();
		try {
			MultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if(multipartResolver.isMultipart(request)) {
				productImg = handleImage(request, productImgList);
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
//		调用serverlet方法修改店铺信息
		
		if(product != null) {
//			将前端传输的shop字符串信息转换成shop实体类并赋值给product
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				ProductExecution pe = productService.modifyProduct(product, productImg, productImgList);
				System.out.println(pe.getState()+"&&"+OperationStatusEnum.SUCCESS.getState());
				if(pe.getState() == OperationStatusEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ProductStateEnum.PRODUCT_EMPTY.getStateInfo());
		}
		return modelMap;
////		接受前段参数变量的初始化，包括商品、缩略图、详情图列表实体类
//		ObjectMapper mapper = new ObjectMapper();
//		Product product  = null;
//		
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			productStr = 
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		MultipartFile productImg = null;
//		List<MultipartFile> productImgList = new ArrayList<MultipartFile>();
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//		try {
//			if(multipartResolver.isMultipart(request)) {
//				MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//				
//			}
//		} catch (Exception e) {
//			
//		}
	}
	/**
	 * 处理图片私有方法
	 * 
	 * @param request
	 * @param productDetailImgList
	 * @return
	 */
	private MultipartFile handleImage(HttpServletRequest request, List<MultipartFile> productDetailImgList) {
		MultipartHttpServletRequest multipartRequest;
		MultipartFile productImg;
		// 与前端约定使用productImg，得到商品缩略图
		multipartRequest = (MultipartHttpServletRequest) request;
		productImg = (MultipartFile) multipartRequest.getFile("productImg");

		// 得到商品详情的列表，和前端约定使用productDetailImg + i 传递
		for (int i = 0; i < IMGMAXCOUNT; i++) {
			MultipartFile productDetailImg = (MultipartFile) multipartRequest.getFile("productDetailImg" + i);
			if (productDetailImg != null) {
				productDetailImgList.add(productDetailImg);
			} else {
				// 如果从请求中获取的到file为空，终止循环
				break;
			}
		}
		return productImg;
	}
	
}
