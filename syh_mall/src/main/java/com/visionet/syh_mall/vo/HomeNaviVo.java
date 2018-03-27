package com.visionet.syh_mall.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.visionet.syh_mall.entity.HomeNavi;

public class HomeNaviVo {

	private String homeNaviID;
	@NotBlank(message = "快捷导航项名称不能为空")
	private String homeNaviName;
	@NotBlank(message = "快捷导航项图标文件ID不能为空")
	private String homeNaviImgID;
	@NotBlank(message = "快捷导航项跳转url链接不能为空")
	private String homeNaviLinkUrl;
	@NotNull(message = "快捷导航项排序不能为空")
	private Integer homeNaviSort;

	public String getHomeNaviID() {
		return homeNaviID;
	}

	public void setHomeNaviID(String homeNaviID) {
		this.homeNaviID = homeNaviID;
	}

	public String getHomeNaviName() {
		return homeNaviName;
	}

	public void setHomeNaviName(String homeNaviName) {
		this.homeNaviName = homeNaviName;
	}

	public String getHomeNaviImgID() {
		return homeNaviImgID;
	}

	public void setHomeNaviImgID(String homeNaviImgID) {
		this.homeNaviImgID = homeNaviImgID;
	}

	public String getHomeNaviLinkUrl() {
		return homeNaviLinkUrl;
	}

	public void setHomeNaviLinkUrl(String homeNaviLinkUrl) {
		this.homeNaviLinkUrl = homeNaviLinkUrl;
	}

	public Integer getHomeNaviSort() {
		return homeNaviSort;
	}

	public void setHomeNaviSort(Integer homeNaviSort) {
		this.homeNaviSort = homeNaviSort;
	}

	/**
	 * @ClassName: HomeNaviReturn
	 * @Description: 热门导航的返回结果
	 * @author chenghongzhan
	 * @date 2017年9月25日 下午1:41:03
	 *
	 */
	public class HomeNaviInfo {
		private String naviItemID;
		private String naviItemName;
		private String itemIconFileID;
		private String itemIconUrl;
		private String naviItemUrl;
		private Integer itemSort;

		public String getNaviItemID() {
			return naviItemID;
		}

		public void setNaviItemID(String naviItemID) {
			this.naviItemID = naviItemID;
		}

		public String getNaviItemName() {
			return naviItemName;
		}

		public void setNaviItemName(String naviItemName) {
			this.naviItemName = naviItemName;
		}

		public String getItemIconFileID() {
			return itemIconFileID;
		}

		public void setItemIconFileID(String itemIconFileID) {
			this.itemIconFileID = itemIconFileID;
		}

		public String getItemIconUrl() {
			return itemIconUrl;
		}

		public void setItemIconUrl(String itemIconUrl) {
			this.itemIconUrl = itemIconUrl;
		}

		public String getNaviItemUrl() {
			return naviItemUrl;
		}

		public void setNaviItemUrl(String naviItemUrl) {
			this.naviItemUrl = naviItemUrl;
		}

		public Integer getItemSort() {
			return itemSort;
		}

		public void setItemSort(Integer itemSort) {
			this.itemSort = itemSort;
		}

	}

	/**
	 * @Title: convertPo @Description: 热门导航Vo转PO @param @param
	 *         homeNaviVo @param @param navi @param @return 设定文件 @return
	 *         HomeNavi 返回类型 @throws
	 */
	public HomeNavi convertPo(HomeNaviVo homeNaviVo, HomeNavi homeNavi) {
		homeNavi.setId(homeNaviVo.getHomeNaviID());
		homeNavi.setNaviItemName(homeNaviVo.getHomeNaviName());
		homeNavi.setIconFileId(homeNaviVo.getHomeNaviImgID());
		homeNavi.setNaviItemUrl(homeNaviVo.getHomeNaviLinkUrl());
		homeNavi.setNaviItemSort(homeNaviVo.getHomeNaviSort());
		return homeNavi;
	}

	/**
	 * @Title: convertVo @Description: Po转Vo @param @param
	 *         homeNavi @param @param homeNaviVo 设定文件 @return void 返回类型 @throws
	 */
	public static HomeNaviInfo convertVo(HomeNavi homeNavi, String path) {
		HomeNaviInfo homeNaviInfo = new HomeNaviVo().new HomeNaviInfo();
		homeNaviInfo.setNaviItemID(homeNavi.getId());
		homeNaviInfo.setNaviItemName(homeNavi.getNaviItemName());
		homeNaviInfo.setItemIconFileID(homeNavi.getIconFileId());
		homeNaviInfo.setItemIconUrl(path);
		homeNaviInfo.setNaviItemUrl(homeNavi.getNaviItemUrl());
		homeNaviInfo.setItemSort(homeNavi.getNaviItemSort());
		return homeNaviInfo;
	}

	@Override
	public String toString() {
		return "HomeNaviVo [homeNaviID=" + homeNaviID + ", homeNaviName=" + homeNaviName + ", homeNaviImgID="
				+ homeNaviImgID + ", homeNaviLinkUrl=" + homeNaviLinkUrl + ", homeNaviSort=" + homeNaviSort + "]";
	}

}
