package com.zhxgkj.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.zhxgkj.o2o.dao.ProductDao;
import com.zhxgkj.o2o.dao.ProductImgDao;
import com.zhxgkj.o2o.dto.ProductExecution;
import com.zhxgkj.o2o.entity.Product;
import com.zhxgkj.o2o.entity.ProductImg;
import com.zhxgkj.o2o.enums.OperationStatusEnum;
import com.zhxgkj.o2o.enums.ProductStateEnum;
import com.zhxgkj.o2o.exceptions.ProductOperationException;
import com.zhxgkj.o2o.service.ProductService;
import com.zhxgkj.o2o.util.ImgUtil;
import com.zhxgkj.o2o.util.PathUtil;
@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;
	/**
	 * 1 处理缩略图，获取缩略图相对路径并赋值给product
	 * 2 往tb_product写入商品信息，获取productId
	 * 3 结合prodictId批量处理商品详情图
	 * 4 将商品详情图列表批量插入tb_product_img中
	 */
	@Override
//	事务管理
	@Transactional
	public ProductExecution addProduct(Product product,  MultipartFile productImg, List<MultipartFile> productImgList)
			throws ProductOperationException {
		if(product != null && product.getShop() != null && product.getShop().getShopId()!= null) {
//			设置默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
//			设置上架状态
			product.setEnableStatus(1);
			if(productImg != null) {
				addProductImg(product,productImg);
			}
			try {
				int effectedNum = productDao.insertProduct(product);
				if(effectedNum <= 0) {
					throw new ProductOperationException(ProductStateEnum.EDIT_ERROR.getStateInfo());
				}
			} catch (Exception e) {
				throw new ProductOperationException(ProductStateEnum.EDIT_ERROR.getStateInfo()+ e.toString());
			}
			if(productImgList != null && productImgList.size()>0) {
				addProductImgList(product,productImgList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS,product);
		}else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}
	/**
	 * 根据productId获取product的详细信息
	 */
	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}
	@Override
	@Transactional
	/**
	 * 1.若缩略图参数有值，则处理缩略图（若原先存在缩略图则先删除再添加新图，之后获取缩略图相对路径并赋值给product）
	 * 2.若商品详情图列表有值，则对商品详情图列表进行同样的操作
	 * 3.将tb_product_img下面的该商品原先的商品详情图记录全部删除
	 * 4.更新tb_product以及tb_product_img的信息
	 */
	public ProductExecution modifyProduct(Product product, MultipartFile productImg, List<MultipartFile> productImgList)
			throws ProductOperationException {
//		空值判断
		if(product != null && product.getShop() != null && product.getShop().getShopId() != null) {
//			给商品设置默认的属性
			product.setLastEditTime(new Date());
//			若商品缩略图不为空且原有的缩略图不为空则删除原有缩略图并添加
			if(productImg != null) {
//				获取一遍原有信息，原来的信息中有原图片地址
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if(tempProduct.getImgAddr() != null) {
					ImgUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addProductImg(product, productImg);
			}
//			若存在新的商品详情图切原详情图不为空，则先删除原详情图并添加
			if(productImgList != null && productImgList.size() > 0) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product, productImgList);
			}
			try {
				int effectedNum = productDao.updateProduct(product);
				if(effectedNum <= 0) {
					throw new ProductOperationException(ProductStateEnum.EDIT_ERROR.getStateInfo());
				}
				return new ProductExecution(ProductStateEnum.SUCCESS,product);
			}catch (Exception e) {
				throw new ProductOperationException(ProductStateEnum.EDIT_ERROR.getStateInfo()+e.toString());
			}
		}else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}
	/**
	 * 添加商品缩略图
	 * 
	 * @param product    商品
	 * @param productImg 商品缩略图
	 */
	private void addProductImg(Product product, MultipartFile productImg) {
		// 获取图片存储路径，将图片放在相应店铺的文件夹下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String productImgAddr = ImgUtil.generateThunbnail(productImg, dest);
		product.setImgAddr(productImgAddr);
	}
	
	/**
	 * 添加商品详情图
	 * 
	 * @param product        商品
	 * @param productImgList 商品详情图列表
	 */
	private void addProductImgList(Product product, List<MultipartFile> productImgList) {
		// 获取图片存储路径，将图片放在相应店铺的文件夹下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		
		List<ProductImg> productImgs = new ArrayList<ProductImg>();
		
		// 遍历商品详情图，并添加到productImg中
		for(MultipartFile multipartFile : productImgList){
			String imgAddr = ImgUtil.generateProductImg(multipartFile, dest);
			ProductImg productImg = new ProductImg();
			productImg.setProductId(product.getProductId());
			productImg.setImgAddr(imgAddr);
			productImg.setCreateTime(new Date());
			
			productImgs.add(productImg);
			
		}

		// 存入有图片，就执行批量添加操作
		if (productImgs.size() > 0) {
			try {
				int effectNum = productImgDao.batchInsertProductImg(productImgs);
				if (effectNum <= 0) {
					throw new ProductOperationException(OperationStatusEnum.PIC_UPLOAD_ERROR.getStateInfo());
				}
			} catch (Exception e) {
				throw new ProductOperationException(OperationStatusEnum.PIC_UPLOAD_ERROR.getStateInfo() + e.toString());
			}
		}
	}
	/**
	 * 删除某个商品下的详情图
	 * 
	 * @param productId
	 */
	private void deleteProductImgList(long productId) {
		// 根据productId获取原有的图片
		List<ProductImg> productImgList = productImgDao.queryProductImgListByProductId(productId);
		if (productImgList != null && !productImgList.isEmpty()) {
			for (ProductImg productImg : productImgList) {
				// 删除存的图片
				ImgUtil.deleteFileOrPath(productImg.getImgAddr());
			}
			// 删除数据库中图片
			productImgDao.deleteProductImgByProductId(productId);
		}
	}

	

}
