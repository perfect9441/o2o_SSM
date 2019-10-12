package com.zhxgkj.o2o.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImgUtil {
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	/**
	 * 商铺缩略图
	 * @param thumbnail Spring自带的文件处理对象
	 * @param targetAddr 图片存储路径
	 * @return
	 */
	public static String generateThunbnail(MultipartFile thumbnail,String targetAddr) {
//		随机名称
		String realFileName = getRandomFileName();
//		文件后缀
		String extension = getFileExtension(thumbnail);
//		在文件夹不存在时创建
		makeDirPath(targetAddr);
//		拼接文件名
		String relativeAddr = targetAddr + realFileName + extension;
//		拼接文件路径
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
//			压缩图片（图片大小）
			Thumbnails.of(thumbnail.getInputStream()).size(200, 200)
//			添加水印(位置，图片，透明度)
			.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/1.jpg")),0.25f)
//			设置压缩率
			.outputQuality(0.8f)
//			输出文件到指定地址
			.toFile(dest);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}
	/**
	 * 创建目标路径所涉及到的目录
	 * 即/home/work/o2o/xxx.jpg
	 * 那么 home work o2o这三个文件夹都必须自动创建出来
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
//		获取文件的全路径
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if(!dirPath.exists()){
			dirPath.mkdirs();
		}
		
	}
	
	/**
	 * 处理首页头图
	 * 
	 * @param thumbnail  Spring自带的文件处理对象
	 * @param targetAddr 图片存储路径
	 * @return
	 */
	public static String generateHeadImg(MultipartFile thumbnail, String targetAddr) {
		// 获取随机文件名，防止文件重名
		String realFileName = getRandomFileName();
		// 获取文件扩展名
		String extension = getFileExtension(thumbnail);
		// 在文件夹不存在时创建
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(400, 300).outputQuality(0.9f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}

	/**
	 * 处理商品分类图
	 * 
	 * @param thumbnail  Spring自带的文件处理对象
	 * @param targetAddr 图片存储路径
	 * @return
	 */
	public static String generateShopCategoryImg(MultipartFile thumbnail, String targetAddr) {
		// 获取随机文件名，防止文件重名
		String realFileName = getRandomFileName();
		// 获取文件扩展名
		String extension = getFileExtension(thumbnail);
		// 在文件夹不存在时创建
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(50, 50).outputQuality(0.9f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}

	/**
	 * 处理商品缩略图
	 * 
	 * @param thumbnail  Spring自带的文件处理对象
	 * @param targetAddr 图片存储路径
	 * @return
	 */
	public static String generateProductImg(MultipartFile thumbnail, String targetAddr) {
		// 获取随机文件名，防止文件重名
		String realFileName = getRandomFileName();
		// 获取文件扩展名
		String extension = getFileExtension(thumbnail);
		// 在文件夹不存在时创建
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(337, 640)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/1.jpg")), 0.5f)
					.outputQuality(0.9f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}
	
	
	/**
	 * 获取输入文件流的扩展名
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(MultipartFile cFile) {
//		获取文件名
		String originalFileName = cFile.getOriginalFilename();
//		截取文件名最后一个“.”后面的后缀
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}
	/**
	 * 生成随机文件名，当前年月日小时分钟秒数+五位随机数
	 * @param targetAddr
	 */
	private static String getRandomFileName() {
//		获取随机的五位数
//		生成0-89999的随机数 加上10000即可保证生成的随机数为5位
		int runnum = r.nextInt(89999) + 10000;
//		返回当前时间
		String nowTimeStr = sDateFormat.format(new Date());
//		拼接随机文件名
		return nowTimeStr + runnum;
	}
	
	/**
	 * 删除文件或目录下的文件
	 * 
	 * @param storePath：文件路径或者目录路径
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		// 存在
		if (fileOrPath.exists()) {
			// 如果是目录
			if (fileOrPath.isDirectory()) {
				File[] files = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			// 删除文件或文件夹
			fileOrPath.delete();
		}
	}
	
	/**
	 * filePath to MultipartFile
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public static MultipartFile path2MultipartFile(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream input = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
				IOUtils.toByteArray(input));
		return multipartFile;
	}
	

	/**
	 * https://github.com/coobird/thumbnailator/wiki/Examples
	 */
	
	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("C:/Users/Administrator/Desktop/timg.jpg"))
		.size(200, 100)
		.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/1.jpg")),0.25f)
		.outputQuality(0.8f).toFile("C:/Users/Administrator/Desktop/newtimg.jpg");
	}
	
}
