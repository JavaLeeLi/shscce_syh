package com.visionet.syh_mall.vo.finance;

import java.util.List;

public class FundSummaryVo {
	private int itemCount;
	private int curPageIndex;
	private int pageCount;
	private boolean hasNext;
	private List<FundSummary> fundSummarys;
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public List<FundSummary> getFundSummarys() {
		return fundSummarys;
	}
	public void setFundSummarys(List<FundSummary> fundSummarys) {
		this.fundSummarys = fundSummarys;
	}
	public int getCurPageIndex() {
		return curPageIndex;
	}
	public void setCurPageIndex(int curPageIndex) {
		this.curPageIndex = curPageIndex;
	}
}
