 package com.visionet.syh_mall.vo;

import com.visionet.syh_mall.common.utils.PageInfo;


/**
 *@Author DM
 *@version ：2017年8月16日下午1:30:34
 *实体类
 */
public class NoticeQo {
	private int pageNumber;
	private int pageSize;
	private int type;
	private String id;
	private PageInfo pageInfo;//分页信息	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
}
