package com.visionet.syh_mall.entity.goods;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.visionet.syh_mall.entity.IdEntity;

/**
 * 
 * 商品图片实体类
 * 
 * @author mulongfei
 * @date 2017年8月23日下午8:46:45
 */
@Entity
@Table(name = "tbl_goods_pic_link")
public class GoodsPicLink extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String goodsId;// 商品id
	private String maxImgId;// 大尺寸图片文件id
	private String midImgId;// 中尺寸图片文件id
	private String minImgId;// 小尺寸图片文件id
	private Date createTime = new Date();// 创建时间
	private Date updateTime = new Date();// 更新时间
	private Integer isDeleted = 0;

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getMaxImgId() {
		return maxImgId;
	}

	public void setMaxImgId(String maxImgId) {
		this.maxImgId = maxImgId;
	}

	public String getMidImgId() {
		return midImgId;
	}

	public void setMidImgId(String midImgId) {
		this.midImgId = midImgId;
	}

	public String getMinImgId() {
		return minImgId;
	}

	public void setMinImgId(String minImgId) {
		this.minImgId = minImgId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
