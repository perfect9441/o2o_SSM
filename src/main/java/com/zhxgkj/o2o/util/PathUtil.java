package com.zhxgkj.o2o.util;
/**
 * 根路径+子路径组成图片的完整存储路径
 */
public class PathUtil {
	private static String seperator = System.getProperty("file.separator");
	/**
	 * 返回项目图片的根路径（根据不同操作系统确定根存储路径）
	 * @return
	 */
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if(os.toLowerCase().startsWith("win")){
			basePath = "D:/projectdev/images/";
		}else {
			basePath = "/home/images/"; 
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	/**
	 * 根据需求返回图片的子路径（不同根路径下确定相同的子存储路径）
	 * @param shopId
	 * @return
	 */
	public static String getShopImagePath(long shopId) {
		String imagePath = "upload/item/shop/" + shopId + "/" ;
		return imagePath.replace("/", seperator);
		
	}
}
