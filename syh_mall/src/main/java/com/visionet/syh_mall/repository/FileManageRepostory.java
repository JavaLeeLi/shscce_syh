package com.visionet.syh_mall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.FileManage;
/**
 * 文件管理Dao层
 * @author mulongfei
 * @date 2017年8月30日上午9:35:14
 */
public interface FileManageRepostory extends BaseRepository<FileManage, String>{
	@Query("select f from FileManage f,GoodsPicLink gp where f.id=gp.maxImgId and gp.goodsId =?1")
	List<FileManage> findPicByGoodsId(String d);
	//通过文件Id查找文件图片路径
	@Query(value="SELECT f.filePath FROM  FileManage f WHERE f.id=?1")
	String findUrlById(String fileId);
	
	FileManage findByfileName(String fileId);
}
