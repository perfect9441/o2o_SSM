package com.zhxgkj.o2o.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhxgkj.o2o.entity.Product;
import com.zhxgkj.o2o.service.ProductService;
import com.zhxgkj.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ProductDetailController {
	@Autowired
	private ProductService productService;
	/**
	 * 获取商品详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/listproductdetailpageinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductDetailPageInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		Product product = null;
		if(productId != -1L) {
			product = productService.getProductById(productId);
			modelMap.put("success", true);
			modelMap.put("product", product);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}

}
