package com.visionet.syh_mall.vo;
/**
 *@Author mulongfei
 *@version ：2017年8月16日下午1:30:34
 *实体类
 */
public class IntegralRuleQo {
	private int pageNumber=1;
	private int pageSize=5;
	private String id;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
