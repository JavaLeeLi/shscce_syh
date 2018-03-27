package com.visionet.syh_mall.vo;

import java.util.List;

/**
 *@Author DM
 *@version ：2017年9月29日上午11:03:03
 *实体类
 */
public class MbrAuthenVo {
	private long itemCount;	//满足条件的记录总数
	private int pageCount;	//满足条件的记录分页数
	private int curPageIndex;	//当前返回记录页码
	private boolean hasNext;//是否还有下一页数据
	private List<UserVo> mbrAuthenList;
	public long getItemCount() {
		return itemCount;
	}
	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getCurPageIndex() {
		return curPageIndex;
	}
	public void setCurPageIndex(int curPageIndex) {
		this.curPageIndex = curPageIndex;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public List<UserVo> getMbrAuthenList() {
		return mbrAuthenList;
	}
	public void setMbrAuthenList(List<UserVo> mbrAuthenList) {
		this.mbrAuthenList = mbrAuthenList;
	} 
}
