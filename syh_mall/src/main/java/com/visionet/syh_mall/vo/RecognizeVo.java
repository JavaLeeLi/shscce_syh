package com.visionet.syh_mall.vo;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.goods.GoodsDraft;
import com.visionet.syh_mall.entity.goods.GoodsPicLink;

/**
 * @ClassName: RecognizeVo
 * @Description: 鉴评的Vo
 * @author chenghongzhan
 * @date 2017年10月23日 上午10:21:47
 *
 */
public class RecognizeVo {
	private String goodsID;
	@NotBlank(message = "商品名称不能为空")
	private String goodsName;
	@NotBlank(message = "商品分类ID不能为空")
	private String goodsKindID;
	@NotNull(message = "商品是否真品不能为空")
	private Integer goodsIsRecognized;
	private String goodsTypeCode;
	private String goodsQualityCode;
	private Integer goodsQualityScore;
	@NotBlank(message = "商品鉴评识别码不能为空")
	private String goodsRecognizeCode;
	@NotBlank(message = "所属会员手机号不能为空")
	private String goodsOwnerPhone;
	private String goodsSN;
	@NotNull(message = "商品数量不能为空")
	private Integer goodsNum;
	private BigDecimal goodsPrice;
	private String goodsDesc;
	@NotNull(message = "是否开启批量录入不能为空")
	private Boolean isBatchCreate;
	private List<GoodsPicLinkVo> goodsImgs;

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsKindID() {
		return goodsKindID;
	}

	public void setGoodsKindID(String goodsKindID) {
		this.goodsKindID = goodsKindID;
	}

	public Integer getGoodsIsRecognized() {
		return goodsIsRecognized;
	}

	public void setGoodsIsRecognized(Integer goodsIsRecognized) {
		this.goodsIsRecognized = goodsIsRecognized;
	}

	public String getGoodsQualityCode() {
		return goodsQualityCode;
	}

	public void setGoodsQualityCode(String goodsQualityCode) {
		this.goodsQualityCode = goodsQualityCode;
	}

	public Integer getGoodsQualityScore() {
		return goodsQualityScore;
	}

	public void setGoodsQualityScore(Integer goodsQualityScore) {
		this.goodsQualityScore = goodsQualityScore;
	}

	public String getGoodsRecognizeCode() {
		return goodsRecognizeCode;
	}

	public void setGoodsRecognizeCode(String goodsRecognizeCode) {
		this.goodsRecognizeCode = goodsRecognizeCode;
	}

	public String getGoodsOwnerPhone() {
		return goodsOwnerPhone;
	}

	public void setGoodsOwnerPhone(String goodsOwnerPhone) {
		this.goodsOwnerPhone = goodsOwnerPhone;
	}

	public String getGoodsSN() {
		return goodsSN;
	}

	public void setGoodsSN(String goodsSN) {
		this.goodsSN = goodsSN;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public Boolean getIsBatchCreate() {
		return isBatchCreate;
	}

	public void setIsBatchCreate(Boolean isBatchCreate) {
		this.isBatchCreate = isBatchCreate;
	}

	public List<GoodsPicLinkVo> getGoodsImgs() {
		return goodsImgs;
	}

	public void setGoodsImgs(List<GoodsPicLinkVo> goodsImgs) {
		this.goodsImgs = goodsImgs;
	}

	/**
	 * @Title: convertPo @Description: Goods的Vo转Po @param @param
	 *         recognizeVo @param @param goods @param @return 设定文件 @return Goods
	 *         返回类型 @throws
	 */
	public GoodsDraft convertPo(RecognizeVo recognizeVo, GoodsDraft goods) {
		goods.setId(recognizeVo.getGoodsID());
		goods.setGoodsName(recognizeVo.getGoodsName());
		goods.setGoodsKindId(recognizeVo.getGoodsKindID());
		goods.setIsRecognized(recognizeVo.getGoodsIsRecognized());
		goods.setGoodsQualityCode(recognizeVo.getGoodsQualityCode());
		goods.setGoodsQualityScore(recognizeVo.getGoodsQualityScore());
		goods.setGoodsTypeCode(recognizeVo.getGoodsTypeCode());
		goods.setRecognizedCode(recognizeVo.getGoodsRecognizeCode());
		goods.setGoodsSn(recognizeVo.getGoodsSN());
		goods.setStockNum(recognizeVo.getGoodsNum());
		goods.setGoodsPrice(recognizeVo.getGoodsPrice());
		goods.setGoodsDescription(recognizeVo.getGoodsDesc());
		return goods;
	}

	/**
	 * @Title: convertList @Description: 鉴评结果的图片集@param @param
	 *         recognizeVo @param @return 设定文件 @return List
	 *         <GoodsPicLink> 返回类型 @throws
	 */
	public List<GoodsPicLink> convertList(RecognizeVo recognizeVo, List<GoodsPicLink> picLinks,String goodsID) {
		List<GoodsPicLinkVo> imgs = recognizeVo.getGoodsImgs();
		if (picLinks.size() > 0) {
			for (int i = 0; i < recognizeVo.goodsImgs.size(); i++) {
				GoodsPicLinkVo linkVo = recognizeVo.getGoodsImgs().get(i);
				if (!StringUtils.isEmpty(picLinks.get(i))) {
					GoodsPicLink picLink = picLinks.get(i);
					picLink.setGoodsId(goodsID);
					picLink.setMaxImgId(linkVo.getMaxImgID());
					picLink.setMidImgId(linkVo.getMidImgID());
					picLink.setMinImgId(linkVo.getMinImgID());
					picLink.setUpdateTime(DateUtil.getCurrentDate());
				} else {
					GoodsPicLink picLink = new GoodsPicLink();
					picLink.setGoodsId(goodsID);
					picLink.setMaxImgId(linkVo.getMaxImgID());
					picLink.setMidImgId(linkVo.getMidImgID());
					picLink.setMinImgId(linkVo.getMinImgID());
				}
			}
			return picLinks;
		}
		for (GoodsPicLinkVo goodsPicLinkVo : imgs) {
			GoodsPicLink picLink = new GoodsPicLink();
			picLink.setGoodsId(goodsID);
			picLink.setMaxImgId(goodsPicLinkVo.getMaxImgID());
			picLink.setMidImgId(goodsPicLinkVo.getMidImgID());
			picLink.setMinImgId(goodsPicLinkVo.getMinImgID());
			picLinks.add(picLink);
		}
		return picLinks;
	}

	@Override
	public String toString() {
		return "RecognizeVo [goodsID=" + goodsID + ", goodsName=" + goodsName + ", goodsKindID=" + goodsKindID
				+ ", goodsIsRecognized=" + goodsIsRecognized + ", goodsQualityCode=" + goodsQualityCode
				+ ", goodsQualityScore=" + goodsQualityScore + ", goodsRecognizeCode=" + goodsRecognizeCode
				+ ", goodsOwnerPhone=" + goodsOwnerPhone + ", goodsSN=" + goodsSN + ", goodsNum=" + goodsNum
				+ ", goodsPrice=" + goodsPrice + ", goodsDesc=" + goodsDesc + ", isBatchCreate=" + isBatchCreate
				+ ", goodsImgs=" + goodsImgs + "]";
	}

	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

}
