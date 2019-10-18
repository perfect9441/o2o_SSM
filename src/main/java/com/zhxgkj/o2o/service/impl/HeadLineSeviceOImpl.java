package com.zhxgkj.o2o.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhxgkj.o2o.dao.HeadLineDao;
import com.zhxgkj.o2o.entity.HeadLine;
import com.zhxgkj.o2o.service.HeadLineService;

@Service
public class HeadLineSeviceOImpl implements HeadLineService{

	@Autowired
	private HeadLineDao headLineDao;
	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
		return headLineDao.queryHeadLine(headLineCondition);
	}

}
