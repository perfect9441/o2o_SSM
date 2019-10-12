package com.zhxgkj.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhxgkj.o2o.BaseTest;
import com.zhxgkj.o2o.entity.Area;
/**
 * dao层测试类
 * @author Administrator
 *
 */
public class AreaDaoTest extends BaseTest{
	@Autowired
	private AreaDao areaDao;
//	定义测试方法
	@Test
	public void testQueryArea() {
		List<Area> areaList = areaDao.queryArea();
		assertEquals(3, areaList.size());
	}
}
