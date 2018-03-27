package com.visionet.syh_mall.service;
import java.security.cert.CertificateException;  
import java.security.cert.X509Certificate;  
import javax.net.ssl.X509TrustManager;
/**
 *@Author DM
 *@version ：2017年10月17日下午5:10:29
 *实体类
 */
/* 
 * 证书管理器的作用是让它新人我们指定的证书， 
 * 此类中的代码意味着信任所有的证书，不管是不是权威机构颁发的。 
 */  
public class MyX509TrustManager implements X509TrustManager {  
  // 检查客户端证书  
  public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
  }  
  // 检查服务器端证书  
  public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
  }  
  // 返回受信任的X509证书数组  
  public X509Certificate[] getAcceptedIssuers() {  
    return null;  
  }  
}  