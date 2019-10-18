package com.zhxgkj.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 解析路由并转发到相应的html页面
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="shopadmin",method={RequestMethod.GET})
public class ShopAdminController {
//	转发至店铺注册/编辑页面
	@RequestMapping(value="/shopopreate")
	public String shopOperation() {
		return "shop/shopoperation";
	}
//	转发至店铺列表页面
	@RequestMapping(value="/shoplist")
	public String shopList() {
		return "shop/shoplist";
	}
//	转发至店铺管理页面
	@RequestMapping(value="/shopmanagement")
	public String shopManagement() {
		return "shop/shopmanagement";
	}
//	转发至商品类别管理页面
	@RequestMapping(value="/productcategorymanagement",method = RequestMethod.GET)
	public String productCategoryManageMent() {
		return "shop/productcategorymanagement";
	}
//	转发至商品添加/编辑页面
	@RequestMapping(value="/productoperation",method = RequestMethod.GET)
	public String productOperation() {
		return "shop/productoperation";
	}
////	转发至商品管理页面
	@RequestMapping(value="/productmanagement", method = RequestMethod.GET)
	public String productManagement() {
		return "shop/productmanagement";
	}
}
