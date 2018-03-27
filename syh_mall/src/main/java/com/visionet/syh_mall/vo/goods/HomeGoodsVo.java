package com.visionet.syh_mall.vo.goods;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.HomeGoods;

/**
 * @ClassName: HomeGoodsVo
 * @Description: 热推商品的Vo
 * @author chenghongzhan
 * @date 2017年10月18日 下午3:41:14
 *
 */
public class HomeGoodsVo {
	private String homeGoodsID;
	@NotBlank(message = "商品ID不能为空")
	private String goodsID;
	@NotNull(message = "热推商品项排序不能为空")
	private Integer goodsSort;

	public String getHomeGoodsID() {
		return homeGoodsID;
	}

	public void setHomeGoodsID(String homeGoodsID) {
		this.homeGoodsID = homeGoodsID;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public Integer getGoodsSort() {
		return goodsSort;
	}

	public void setGoodsSort(Integer goodsSort) {
		this.goodsSort = goodsSort;
	}

	/**
	 * @Title: converPo @Description: 热门商铺的Vo转Po @param @param
	 *         homeGoods @param @param goodsVo @param @param
	 *         goods @param @return 设定文件 @return HomeGoods 返回类型 @throws
	 */
	public HomeGoods converPo(HomeGoods homeGoods, HomeGoodsVo goodsVo, Goods goods) {
		homeGoods.setGoodsId(goodsVo.getGoodsID());
		homeGoods.setItemSort(goodsVo.getGoodsSort());
		homeGoods.setShopId(goods.getShopId());
		return homeGoods;
	}

	@Override
	public String toString() {
		return "HomeGoodsVo [homeGoodsID=" + homeGoodsID + ", goodsID=" + goodsID + ", goodsSort=" + goodsSort + "]";
	}

}
