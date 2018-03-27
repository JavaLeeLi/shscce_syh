package com.visionet.syh_mall.service.export;

import java.math.BigDecimal;

public class ExportEvaluationRestVo {
	private String collectCode;
	private String collectName;
	private String ratingScore;//评级分数
	private String serialNumber;//志号
	private String faceValue;//面值
	private String releaseYear;//发行年份
	private String releaseUnit;//发行单位
	private String texture;//材质
	private String Watermark;//水印
	private String printing;//版别，工艺
	private String collectType;//藏品分类
	private String QRCode;//二维码
	public String getCollectCode() {
		return collectCode;
	}
	public void setCollectCode(String collectCode) {
		this.collectCode = collectCode;
	}
	public String getCollectName() {
		return collectName;
	}
	public void setCollectName(String collectName) {
		this.collectName = collectName;
	}
	public String getRatingScore() {
		return ratingScore;
	}
	public void setRatingScore(String ratingScore) {
		this.ratingScore = ratingScore;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}
	public String getReleaseUnit() {
		return releaseUnit;
	}
	public void setReleaseUnit(String releaseUnit) {
		this.releaseUnit = releaseUnit;
	}
	public String getTexture() {
		return texture;
	}
	public void setTexture(String texture) {
		this.texture = texture;
	}
	public String getWatermark() {
		return Watermark;
	}
	public void setWatermark(String watermark) {
		Watermark = watermark;
	}
	public String getPrinting() {
		return printing;
	}
	public void setPrinting(String printing) {
		this.printing = printing;
	}
	public String getCollectType() {
		return collectType;
	}
	public void setCollectType(String collectType) {
		this.collectType = collectType;
	}
	public String getQRCode() {
		return QRCode;
	}
	public void setQRCode(String qRCode) {
		QRCode = qRCode;
	}
	@Override
	public String toString() {
		return "ExportEvaluationRestVo [collectCode=" + collectCode + ", collectName=" + collectName + ", ratingScore="
				+ ratingScore + ", serialNumber=" + serialNumber + ", faceValue=" + faceValue + ", releaseYear="
				+ releaseYear + ", releaseUnit=" + releaseUnit + ", texture=" + texture + ", Watermark=" + Watermark
				+ ", printing=" + printing + ", collectType=" + collectType + ", QRCode=" + QRCode + "]";
	}
	public String getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}
}
