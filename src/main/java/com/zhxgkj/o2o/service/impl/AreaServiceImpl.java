package com.zhxgkj.o2o.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhxgkj.o2o.dao.AreaDao;
import com.zhxgkj.o2o.entity.Area;
import com.zhxgkj.o2o.service.AreaService;
//声明是service层的实现类
@Service
public class AreaServiceImpl implements AreaService{
//	将需要的对象注入进来
	@Autowired
	private AreaDao areaDao;
	@Override
	public List<Area> getAreaList() {
		return areaDao.queryArea();
	}
}
