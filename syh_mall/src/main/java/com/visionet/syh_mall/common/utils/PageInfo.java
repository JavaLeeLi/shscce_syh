package com.visionet.syh_mall.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageInfo {
	private int pageIndex;
	private int itemCount;
	private Direction type = Direction.DESC;
	private String sortTypeStr = "DESC";
	// 默认根据id排序
	private String sortColumn = "createTime";

	private int totalCount;
	private PageRequest pageRequest;

	public PageInfo() {
		this.pageIndex = 1;
		this.itemCount = 10;
	}
	public PageInfo(Integer pageNumber, Integer pageSize) {
		this.pageIndex = pageNumber == null ? 1 : pageNumber;
		this.itemCount = pageSize == null ? 10 : pageSize;
	}

	public PageInfo(Integer pageIndex, Integer itemCount, Direction sortType) {
		this.pageIndex = pageIndex == null ? 1 : pageIndex;
		this.itemCount = itemCount == null ? 10 : itemCount;
		this.type = sortType == null ? Direction.DESC : sortType;
	}

	public PageInfo(Integer pageIndex, Integer itemCount, String sortColumn, Direction sortType) {
		this.pageIndex = pageIndex == null ? 1 : pageIndex;
		this.itemCount = itemCount == null ? 10 : itemCount;
		this.type = sortType == null ? Direction.DESC : sortType;
		this.sortColumn = sortColumn == null ? "id" : sortColumn;
	}

	public int getPageIndex() {
		return pageIndex == 0 ? 1 : pageIndex;
	}

	public PageInfo setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		return this;
	}

	public int getItemCount() {
		return itemCount == 0 ? 10 : itemCount;
	}

	public PageInfo getItemCount(int itemCount) {
		this.itemCount = itemCount;
		return this;
	}

	public Direction getType() {
		return type;
	}

	public PageInfo setType(Direction type) {
		this.type = type;
		return this;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public PageInfo setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
		return this;
	}

	public PageRequest getPageRequestInfo() {
		if (Validator.isNotNull(sortColumn)) {
			if ("auto".equals(sortColumn)) {
				sortColumn = "id";
			}
			Sort sort = new Sort(type, sortColumn);
			pageRequest = new PageRequest(pageIndex - 1, itemCount, sort);
		} else {
			pageRequest = new PageRequest(pageIndex - 1, itemCount);
		}
		return pageRequest;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getSortTypeStr() {
		return sortTypeStr;
	}

	public void setSortTypeStr(String sortTypeStr) {
		this.sortTypeStr = sortTypeStr;
	}

	public int getCurrentPageNumber() {
		return (this.getPageIndex() - 1) * this.getItemCount();
	}
	/**
	 * 获取当前页数据
	 * @param 
	 * @return List<T>
	 * @throws
	 */
	public static <T> List<T> getPageContent(List<T> allContent,int pageIndex,int itemCount){
		List<T> list = new ArrayList<T>();
		int startItem = 0;
		int endItem = 0;
		int allIndex = 0;
		if(allContent.size()%itemCount==0){			
			allIndex = allContent.size()/itemCount;
		}
		if(allContent.size()%itemCount!=0){			
			allIndex = allContent.size()/itemCount+1;
		}
		if(pageIndex==allIndex){
			startItem = (pageIndex-1)*itemCount;
			endItem = allContent.size();
		}
		if(pageIndex < allIndex){
			startItem = (pageIndex-1)*itemCount;			
			endItem = pageIndex*itemCount;
		}
		for(int i = startItem;i<endItem;i++){
			list.add(allContent.get(i));
		}
		return list;
	}
}
