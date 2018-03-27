package com.visionet.syh_mall.entity;
/**
 *@Author DM
 *@version ：2017年10月17日下午5:02:30
 *实体类
 */
public class Token {
	 // 接口访问凭证֤  
	  private String accessToken;  
	  // 凭证有效期单位：second  
	  private int expiresIn;  
	  public String getAccessToken() {  
	    return accessToken;  
	  }  
	  public void setAccessToken(String accessToken) {  
	    this.accessToken = accessToken;  
	  }  
	  public int getExpiresIn() {  
	    return expiresIn;  
	  }  
	  public void setExpiresIn(int expiresIn) {  
	    this.expiresIn = expiresIn;  
	  }  
	}  
