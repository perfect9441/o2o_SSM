package com.zhxgkj.o2o.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhxgkj.o2o.entity.Area;
import com.zhxgkj.o2o.service.AreaService;

//声明在spring容器中，这是一个controller
@Controller
//设置进入该controller的名称
@RequestMapping("/superadmin")
public class AreaController {
	
	Logger logger = LoggerFactory.getLogger(AreaController.class);
	
//	自动实现声明的类
	@Autowired
	private AreaService areaService;
//	设置请求的名称以及请求方式
	@RequestMapping(value = "/listarea",method = RequestMethod.GET)
//	自动将结果转换成为json格式
	@ResponseBody
	private Map<String,Object> listArea(){
		logger.info("======start======");
		long startTime = System.currentTimeMillis();
		Map<String,Object> modelMap = new HashMap<String, Object>();
		List<Area> list = new ArrayList<Area>();
		try {
			list = areaService.getAreaList();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());
		}catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		logger.error("test error");
		long endTime = System.currentTimeMillis();
		logger.debug("costTime[{}ms]",endTime-startTime);
		logger.info("======end======");
		return modelMap;
	}
}
