package com.visionet.syh_mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 用户/会员实体类
 * @author mulongfei
 * @date 2017年8月24日上午10:47:08
 */
@Entity
@Table(name="tbl_user")
public class MbrUser extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String wechatOpenId;//微信昵称
	private String phone;//手机号码
	private String loginName;//登录名
	private String imgFileId;//用户头像图片文件ID
	private String aliasName;//用户昵称
	private String mail;//邮箱
	private Integer channelLevel;//分销等级
	private Date createTime;//注册时间
	private Integer isDeleted;//是否删除
	public String getWechatOpenId() {
		return wechatOpenId;
	}
	public void setWechatOpenId(String wechatOpenId) {
		this.wechatOpenId = wechatOpenId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getImgFileId() {
		return imgFileId;
	}
	public void setImgFileId(String imgFileId) {
		this.imgFileId = imgFileId;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Integer getChannelLevel() {
		return channelLevel;
	}
	public void setChannelLevel(Integer channelLevel) {
		this.channelLevel = channelLevel;
	}
}
