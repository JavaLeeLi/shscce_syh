package com.visionet.syh_mall.service.mobile.channel;

import java.util.List;
import java.util.Map;

public class GoodsChannelVo {

	private long itemCount; // 满足条件的记录总数
	private int pageCount; // 满足条件的记录分页数
	private int curPageIndex; // 当前返回记录页码
	private boolean hasNext;// 是否还有下一页数据
	private List<Map<String, Object>> result;

	public List<Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(List<Map<String, Object>> result) {
		this.result = result;
	}

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

}
