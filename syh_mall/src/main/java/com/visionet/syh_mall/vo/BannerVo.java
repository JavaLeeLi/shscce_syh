package com.visionet.syh_mall.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.visionet.syh_mall.entity.Banner;

public class BannerVo {

	private String bannerID;
	private String bannerShopID;//banner对应店铺id
	@NotBlank(message = "图片文建ID不能为空")
	private String bannerImgID;
	private String bannerLinkUrl;
	@NotNull(message = "banner项排序不能为空")
	private Integer bannerSort;
	private Integer versionNo=0;

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public String getBannerID() {
		return bannerID;
	}

	public void setBannerID(String bannerID) {
		this.bannerID = bannerID;
	}

	public String getBannerShopID() {
		return bannerShopID;
	}

	public void setBannerShopID(String bannerShopID) {
		this.bannerShopID = bannerShopID;
	}

	public String getBannerImgID() {
		return bannerImgID;
	}

	public void setBannerImgID(String bannerImgID) {
		this.bannerImgID = bannerImgID;
	}

	public String getBannerLinkUrl() {
		return bannerLinkUrl;
	}

	public void setBannerLinkUrl(String bannerLinkUrl) {
		this.bannerLinkUrl = bannerLinkUrl;
	}

	public Integer getBannerSort() {
		return bannerSort;
	}

	public void setBannerSort(Integer bannerSort) {
		this.bannerSort = bannerSort;
	}

	public class BannerReturn {
		private String bannerID;
		private String bannerImgID;
		private String bannerImgUrl;
		private String bannerClickUrl;
		private Integer bannerSort;
		private Integer versionNo;

		public Integer getVersionNo() {
			return versionNo;
		}

		public void setVersionNo(Integer versionNo) {
			this.versionNo = versionNo;
		}

		public String getBannerID() {
			return bannerID;
		}

		public void setBannerID(String bannerID) {
			this.bannerID = bannerID;
		}

		public String getBannerImgID() {
			return bannerImgID;
		}

		public void setBannerImgID(String bannerImgID) {
			this.bannerImgID = bannerImgID;
		}

		public String getBannerImgUrl() {
			return bannerImgUrl;
		}

		public void setBannerImgUrl(String bannerImgUrl) {
			this.bannerImgUrl = bannerImgUrl;
		}

		public String getBannerClickUrl() {
			return bannerClickUrl;
		}

		public void setBannerClickUrl(String bannerClickUrl) {
			this.bannerClickUrl = bannerClickUrl;
		}

		public Integer getBannerSort() {
			return bannerSort;
		}

		public void setBannerSort(Integer bannerSort) {
			this.bannerSort = bannerSort;
		}

	}

	/**
	 * @Title: convertPo @Description: Vo转Po @param 设定文件 @return void
	 *         返回类型 @throws
	 */
	public Banner convertPo(BannerVo bannerVo, Banner banner) {
		banner.setId(bannerVo.getBannerID());
		banner.setImageFileId(bannerVo.getBannerImgID());
		banner.setLinkHref(bannerVo.getBannerLinkUrl());
		banner.setBannerShopId(bannerVo.getBannerShopID());
		banner.setBannerSort(bannerVo.getBannerSort());
		banner.setVersionNo(bannerVo.getVersionNo());
		return banner;
	}

	/**
	 * @Title: changeModel @Description: Po转Model @param @param banners
	 *         设定文件 @return void 返回类型 @throws
	 */
	public static BannerReturn convertVo(Banner banner, String path) {
		BannerReturn bannerList = new BannerVo().new BannerReturn();
		bannerList.setBannerID(banner.getId());
		bannerList.setBannerImgID(banner.getImageFileId());
		bannerList.setBannerImgUrl(path);
		bannerList.setBannerClickUrl(banner.getLinkHref());
		bannerList.setBannerSort(banner.getBannerSort());
		bannerList.setVersionNo(banner.getVersionNo());
		return bannerList;
	}

	@Override
	public String toString() {
		return "BannerVo [bannerID=" + bannerID + ", bannerShopID=" + bannerShopID + ", bannerImgID=" + bannerImgID
				+ ", bannerLinkUrl=" + bannerLinkUrl + ", bannerSort=" + bannerSort + "]";
	}

}
