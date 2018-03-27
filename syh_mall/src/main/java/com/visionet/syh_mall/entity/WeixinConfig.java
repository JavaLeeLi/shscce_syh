package com.visionet.syh_mall.entity;


import com.google.common.collect.Lists;

import java.util.List;

public class WeixinConfig {

    private boolean debug;
    private String appId;
    private long timestamp;
    private String nonceStr;
    private String signature;
    private List<String> jsApiList = Lists.newArrayList();

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<String> getJsApiList() {
        return jsApiList;
    }

    public void setJsApiList(List<String> jsApiList) {
        this.jsApiList = jsApiList;
    }

	@Override
	public String toString() {
		return "WeixinConfig [debug=" + debug + ", appId=" + appId
				+ ", timestamp=" + timestamp + ", nonceStr=" + nonceStr
				+ ", signature=" + signature + ", jsApiList=" + jsApiList + "]";
	}
    
    
}
