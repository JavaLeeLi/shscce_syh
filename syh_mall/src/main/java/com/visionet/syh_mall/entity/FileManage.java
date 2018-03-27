package com.visionet.syh_mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.visionet.syh_mall.service.fileManage.FilePathUtils;

/**
 * 文件管理表
 * @author chenghongzhan
 * @version 2017年8月24日 下午4:43:57
 *
 */
@Entity
@Table(name="tbl_file_manage")
public class FileManage extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String fileStyle;//文件格式
	private String fileName;//文件名字
	private Integer fileUsage;//文件用途
	private Integer fileSize;//文件大小
	private String filePath;//文件路径
	private Integer pixelHeight;//图片高度
	private Integer pixelWidth;//图片宽度
	private Date createTime;//创建时间
	
	//实体返回路径
	private String absolutePath;
	
	@Transient
	public String getAbsolutePath() {
		absolutePath = FilePathUtils.fileUrl(filePath);
		return absolutePath;
	}
	
	
	public String getFileStyle() {
		return fileStyle;
	}
	public void setFileStyle(String fileStyle) {
		this.fileStyle = fileStyle;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getFileUsage() {
		return fileUsage;
	}
	public void setFileUsage(Integer fileUsage) {
		this.fileUsage = fileUsage;
	}
	public Integer getFileSize() {
		return fileSize;
	}
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	public String getFilePath() {
		return  filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getPixelHeight() {
		return pixelHeight;
	}
	public void setPixelHeight(Integer pixelHeight) {
		this.pixelHeight = pixelHeight;
	}
	public Integer getPixelWidth() {
		return pixelWidth;
	}
	public void setPixelWidth(Integer pixelWidth) {
		this.pixelWidth = pixelWidth;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
