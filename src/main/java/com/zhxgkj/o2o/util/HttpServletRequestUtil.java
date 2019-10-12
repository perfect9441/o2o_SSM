package com.zhxgkj.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
//	字符串转换成int类型
	public static int getInt(HttpServletRequest request,String key) {
		try {
			return Integer.decode(request.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
	}
//	字符串转换成Long类型
	public static long getLong(HttpServletRequest request,String key) {
		try {
			return Long.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
	}
//	字符串转换成double类型
	public static Double getDouble(HttpServletRequest request,String key) {
		try {
			return Double.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return -1d;
		}
	}
//	字符串转换成boolean类型
	public static boolean getBoolean(HttpServletRequest request,String key) {
		try {
			return Boolean.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return false;
		}
	}
//	转换成字符串处理
	public static String getString(HttpServletRequest request,String key) {
		try {
			String result = request.getParameter(key);
			if(result != null) {
				result = result.trim();
			}
			if("".equals(result)) {
				result = null;
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
