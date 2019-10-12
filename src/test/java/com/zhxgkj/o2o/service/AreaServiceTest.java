package com.zhxgkj.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhxgkj.o2o.BaseTest;
import com.zhxgkj.o2o.entity.Area;

/**
 * service层测试类
 * @author Administrator
 *
 */

public class AreaServiceTest extends BaseTest{
	@Autowired
	private AreaService areaService;
	@Test
	public void testGetAreaList() {
		List<Area> areaList = areaService.getAreaList();
		assertEquals("张掖",areaList.get(0).getAreaName());
	}
	
}
