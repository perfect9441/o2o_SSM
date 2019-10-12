package com.zhxgkj.o2o.util;

public class PageCalculator {
	public static int calculaterRowIndex(int pageindex,int pageSize) {
		return (pageindex > 0) ? (pageindex-1)*pageSize :0;
	}
}
