package com.visionet.syh_mall.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.visionet.syh_mall.entity.WeixinConfig;
import com.visionet.syh_mall.entity.WeixinOauth2Token;
import com.visionet.syh_mall.entity.WeixinUserInfo;
import com.visionet.syh_mall.exception.RestException;

import net.sf.json.JSONObject;

/**
 * @Author DM
 * @version ：2017年10月17日下午4:53:53 实体类
 */
@Service
public class WeiXinService {
	private static Logger log = LoggerFactory.getLogger(WeiXinService.class);
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	public final static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi";
	public final static String AppId = "wxc480ed072f7e31a0";
	public final static String AppSecret = "1cb4b7eae167f1190d6f3b8f94db5a79";

	// public static void main(String args[]) {
	// // 获取接口访问凭证
	// String accessToken = CommonUtil.getToken("wxc480ed072f7e31a0",
	// "1cb4b7eae167f1190d6f3b8f94db5a79").getAccessToken();
	// /**
	// * 获取用户信息
	// */
	// WeixinUserInfo user = getUserInfo(accessToken,
	// "olBC2wiX8cxeo3sOeIgITjMsex4c");
	// //做这个测试的时候可以先关注，或者取消关注，控制台会打印出来此用户的openid
	// System.out.println("OpenID：" + user.getOpenId());
	// System.out.println("关注状态：" + user.getSubscribe());
	// System.out.println("关注时间：" + user.getSubscribeTime());
	// System.out.println("昵称：" + user.getNickname());
	// System.out.println("性别：" + user.getSex());
	// System.out.println("国家：" + user.getCountry());
	// System.out.println("省份：" + user.getProvince());
	// System.out.println("城市：" + user.getCity());
	// System.out.println("语言：" + user.getLanguage());
	// System.out.println("头像：" + user.getHeadImgUrl());
	// }

	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param openId
	 *            用户标识
	 * @return WeixinUserInfo
	 */
	public WeixinUserInfo getUserInfo(String accessToken, String openId) {
		WeixinUserInfo weixinUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeixinUserInfo();
				// 用户的标识
				weixinUserInfo.setOpenId(jsonObject.getString("openid"));
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
				// 用户关注时间
				weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
				// 昵称
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfo.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				weixinUserInfo.setCity(jsonObject.getString("city"));
				// 用户的语言，简体中文为zh_CN
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				// 用户头像
				weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
			} catch (Exception e) {
				if (0 == weixinUserInfo.getSubscribe()) {
					System.err.printf("用户{}已取消关注", weixinUserInfo.getOpenId());
				} else {
					int errorCode = jsonObject.getInt("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					System.err.printf("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
				}
			}
		}
		return weixinUserInfo;
	}

	/**
	 * 获取网页授权凭证
	 * 
	 * @param /appId
	 *            公众账号的唯一标识
	 * @param /appSecret
	 *            公众账号的密钥
	 * @param /code
	 * @return WeixinAouth2Token
	 */
	public WeixinOauth2Token getOauth2AccessToken(String code) {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", AppId);
		requestUrl = requestUrl.replace("SECRET", AppSecret);
		requestUrl = requestUrl.replace("CODE", code);
		// 获取网页授权凭证
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.err.printf("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return wat;
	}

	public static String accesstokenRequest(String appId, String secret) throws RestException {
		if (appId == null) {
			appId = AppId;
		}
		if (secret == null) {
			secret = AppSecret;
		}
		//
		String url = ACCESS_TOKEN_URL + "&appid=" + appId + "&secret=" + secret;
		JSONObject json = null;
		try {
			String access_token = GuavaCacheUtil.getToken(AppSecret);
			if(access_token==null){
				json= httpRequest(url, "GET", null);
				GuavaCacheUtil.setToken(AppSecret, json.getString("access_token"));
				access_token = json.getString("access_token");
			}
			return access_token;
		} catch (Exception e) {
			log.error(e.toString());
			log.error(json.toString());
			if (json.containsKey("errmsg")) {
				throw new RestException(json.getString("errmsg"));
			} else {
				throw new RestException(e.toString());
			}
		}

	}

	private static WeixinConfig getSignature(String ticket, String url) {
		// String nonceStr = Encodes.encodeHex(salt);
		String nonceStr = create_nonce_str();
		// "Wm3WZYTPz0wzccnW";
		long timestamp = new Date().getTime() / 1000;
		log.info("ticket:{}",ticket);
		String jsapi_ticket = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url="
				+ url;
		MessageDigest crypt;
		String signature = null;
		try {
			crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(jsapi_ticket.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// byte[] hashPassword = Digests.sha1(jsapi_ticket.getBytes());
		// String signature = Encodes.encodeHex(hashPassword);
		// String signature = CommonUtil.Sha1(jsapi_ticket);
		WeixinConfig weixinConfig = new WeixinConfig();
		weixinConfig.setAppId(AppId);
		weixinConfig.setNonceStr(nonceStr);
		weixinConfig.setTimestamp(timestamp);
		weixinConfig.setSignature(signature);
		weixinConfig.setJsApiList(Lists.newArrayList("checkJsApi", "onMenuShareTimeline", "onMenuShareAppMessage",
				"onMenuShareQQ", "onMenuShareWeibo", "hideMenuItems", "chooseImage"));
		log.info(weixinConfig.toString());
		return weixinConfig;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/*
	 * wx.config({ debug: true, //
	 * 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开
	 * ，参数信息会通过log打出，仅在pc端时才会打印。 appId: '', // 必填，公众号的唯一标识 timestamp: , //
	 * 必填，生成签名的时间戳 nonceStr: '', // 必填，生成签名的随机串 signature: '',// 必填，签名，见附录1
	 * jsApiList: [] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2 });
	 */
	public synchronized WeixinConfig getSignature(String url) throws Exception {
		log.info("微信分享签名:{}");
		String ticket = GuavaCacheUtil.get(AppId);
		if (ticket == null) {
			ticket = getTicket(accesstokenRequest(null, null));
			GuavaCacheUtil.set(AppId, ticket);
		}
		try {
			return getSignature(ticket, url);
		} catch (Exception e) {
			log.error(e.toString());
			
			ticket = getTicket(accesstokenRequest(null, null));
			GuavaCacheUtil.set(AppId, ticket);
			return getSignature(ticket, url);
		}
	}

	// 获取jsapi票据
	private static String getTicket(String token) {
		String url = TICKET_URL + "&access_token=" + token;
		log.info("----wx_url:" + url);
		JSONObject json = httpRequest(url, "GET", null);
		try {
			return json.getString("ticket");
		} catch (Exception e) {
			log.error(e.toString());
			log.error(json.toString());
			if (json.containsKey("errmsg")) {
				throw new RestException(json.getString("errmsg"));
			} else {
				throw new RestException(e.toString());
			}
		}
	}

	/**
	 * 发起https请求并获取结果
	 *
	 * @author 李晓健
	 * @date 2014年7月12日 下午12:17:36
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 * @return
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		BufferedReader bufferedReader = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		HttpsURLConnection httpUrlConn = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if (requestMethod.equalsIgnoreCase("GET")) {
				httpUrlConn.connect();
			}
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			httpUrlConn.connect();
			inputStream = httpUrlConn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			jsonObject = JSONObject.fromObject(buffer.toString());

		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		} finally {
			// 释放资源
			try {
				if (null != bufferedReader) {
					bufferedReader.close();
				}
				if (null != inputStreamReader) {
					inputStreamReader.close();
				}
				if (null != inputStream) {
					inputStream.close();
					inputStream = null;
				}
				if (null != httpUrlConn) {
					httpUrlConn.disconnect();
				}
			} catch (Exception e2) {
			}
		}
		return jsonObject;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}
}