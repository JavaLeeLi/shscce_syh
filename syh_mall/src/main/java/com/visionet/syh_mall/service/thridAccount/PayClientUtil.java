package com.visionet.syh_mall.service.thridAccount;

import ime.service.client.SOAClient;
import ime.service.util.RSAUtil;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 第三方支付PayClientUtil
 * @author xiaofb
 * @time 2017年10月9日
 */
@Component
public class PayClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(PayClientUtil.class);
	
	private static SOAClient soaClient = null;
	private static PublicKey publicKey;
	private static PrivateKey privateKey;
	private static String serverAddress;
	

	private static String signMethod = "SHA1WithRSA";
	//商户号
	public static String sysid;
	//密钥密码
	public static String pwd;
	//证书名称
	public static String alias;
	//证书文件路径
	public static String path;
	//账户集编号
	public static String accountSetNo;
	//通知地址
	public static String notifyAddress;
	//网关确认支付地址
	public static String gateWayUrl;
	//设置密码地址
	public static String payPwdAddress;
	//上邮汇专用账号
	public static String syhNo;

	@PostConstruct
	public void InitMethod(){
		logger.info("【sysid】："+sysid+ "【pwd】："+pwd +"【alias】："+alias+ "【path】："+path );
		logger.info("【accountSetNo】："+accountSetNo+ "【notifyAddress】："+notifyAddress +"【gateWayUrl】："+gateWayUrl+"【payPwdAddress】"+payPwdAddress);
		try {
			publicKey = RSAUtil.loadPublicKey(alias, path, pwd);
			privateKey = RSAUtil.loadPrivateKey(alias, path, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 获取SoaClient
	 * @return
	 */
	public static SOAClient getSOAClient(){
		logger.info("获取支付SoaClient...");
		logger.info("【sysid】："+sysid+ "【pwd】："+pwd +"【alias】："+alias+ "【path】："+path );
		logger.info("【accountSetNo】："+accountSetNo+ "【notifyAddress】："+notifyAddress +"【gateWayUrl】："+gateWayUrl);
		try {
			if(null != soaClient){
				return soaClient;
			}
			soaClient = new SOAClient();
			soaClient.setServerAddress(serverAddress);
			soaClient.setSignKey(privateKey);
			soaClient.setPublicKey(publicKey);
			soaClient.setSysId(sysid);
			soaClient.setSignMethod(signMethod);
		} catch (Exception e) {
			logger.error("获取支付SoaClient异常...", e);
			e.printStackTrace();
		}
		return soaClient;
	}
	
	
	//RSA加密
	public static String rsaEncrypt(String signStr) throws Exception{
		try{
			System.out.println("rsaEncrypt start");
			RSAUtil rsaUtil = new RSAUtil((RSAPublicKey)publicKey, (RSAPrivateKey)privateKey);
			return rsaUtil.encrypt(signStr);
		}catch(Exception e){
			System.out.println("rsaEncrypt error");
			e.printStackTrace();
			throw e;
		}
	}

	@Value("${sysid}")
	public void setSysid(String sysid) {
		PayClientUtil.sysid = sysid;
	}
	@Value("${cert_pwd}")
	public void setPwd(String pwd) {
		PayClientUtil.pwd = pwd;
	}
	@Value("${cert_name}")
	public void setAlias(String alias) {
		PayClientUtil.alias = alias;
	}
	@Value("${cert_path}")
	public void setPath(String path) {
		PayClientUtil.path = path;
	}
	@Value("${accountSetNo}")
	public void setAccountSetNo(String accountSetNo) {
		PayClientUtil.accountSetNo = accountSetNo;
	}
	@Value("${serverAddress}")
	public void setServerAddress(String serverAddress) {
		PayClientUtil.serverAddress = serverAddress;
	}
	@Value("${notifyAddress}")
	public void setNotifyAddress(String notifyAddress) {
		PayClientUtil.notifyAddress = notifyAddress;
	}
	@Value("${gateWayUrl}")
	public void setGateWayUrl(String gateWayUrl) {
		PayClientUtil.gateWayUrl = gateWayUrl;
	}
	@Value("${payPwdAddress}")
	public void setpayPwdAddress(String payPwdAddress) {
		PayClientUtil.payPwdAddress = payPwdAddress;
	}
	@Value("${syhNo}")
	public void setSyhNo(String syhNo) {
		PayClientUtil.syhNo = syhNo;
	}

	public static PublicKey getPublicKey() {
		return publicKey;
	}

	public static PrivateKey getPrivateKey() {
		return privateKey;
	}


	
	

}
