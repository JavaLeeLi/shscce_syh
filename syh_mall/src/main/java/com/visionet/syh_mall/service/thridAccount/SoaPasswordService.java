package com.visionet.syh_mall.service.thridAccount;
import java.net.URLEncoder;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.web.BaseController;
import ime.service.util.RSAUtil;

@Service
public class SoaPasswordService {

	private final static Logger logger = LoggerFactory.getLogger(SoaPasswordService.class);

	/**
	 * 设置支付密码 @param @return JSONObject @throws
	 */
	public Map<String, Object> setPayPwd(String bizUserId, String phone, String name, Long identityType,
			String identityNo, String jumpUrl) {
		logger.info("设置支付密码:{}", bizUserId);
		Map<String, Object> result = new HashMap<String, Object>();
		String jumpUrls = null;
		try {
			logger.info("设置支付密码开始请求参数");
			logger.info("【bizUserId】：" + bizUserId + "【phone】：" + phone + "【name】：" + name + "【identityType】"
					+ identityType + "【identityNo】" + identityNo + "【jumpUrl】" + jumpUrl);
			jumpUrls = confirmPay("setPayPwd.html", bizUserId, phone, name, identityType, identityNo, jumpUrl,
					"MemberPwdService", "setPayPwd", "/api/notify/setPayPwd");
			logger.info("设置支付密码结束...");
		} catch (Exception e) {
			logger.error("设置支付密码异常", e);
			e.printStackTrace();
			BaseController.sysException();
		}
		result.put("jumpUrl", jumpUrls);
		return result;
	}

	/**
	 * 修改密码
	 */
	public Map<String, Object> updatePayPwd(String bizUserId, String phone, String name, Long identityType,
			String identityNo, String jumpUrl) {
		logger.info("修改支付密码:{}", bizUserId);
		Map<String, Object> result = new HashMap<String, Object>();
		String jumpUrls = null;
		try {
			logger.info("修改支付密码开始{}");
			jumpUrls = confirmPay("updatePayPwd.html", bizUserId, phone, name, identityType, identityNo, jumpUrl,
					"MemberPwdService", "updatePayPwd", "/api/notify/updatePayPwd");
			logger.info("修改支付密码结束");
		} catch (Exception e) {
			logger.error("修改支付密码异常", e);
			e.printStackTrace();
			BaseController.sysException();
		}
		result.put("jumpUrl", jumpUrls);
		return result;
	}

	/**
	 * @Title: changePayPwdParam @Description: 修改密码的传参 @param @param
	 *         bizUserId @param @param name @param @param
	 *         identityType @param @param identityNo @param @param
	 *         jumpUrl @param @return @param @throws Exception 设定文件 @return
	 *         JSONObject 返回类型 @throws
	 */
	public static JSONObject changePayPwdParam(String bizUserId, String name, Long identityType, String identityNo,
			String jumpUrl) throws Exception {
		JSONObject param = new JSONObject();
		param.put("bizUserId", bizUserId);
		param.put("name", name);
		param.put("identityType", identityType);
		RSAUtil rsaUtil = new RSAUtil((RSAPublicKey) PayClientUtil.getPublicKey(),
				(RSAPrivateKey) PayClientUtil.getPrivateKey());
		param.put("identityNo", rsaUtil.encrypt(identityNo));
		param.put("jumpUrl", PayClientUtil.notifyAddress + jumpUrl);
		param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/updatePayPwd");
		return param;
	}

	/**
	 * 重置支付密码 @param @return JSONObject @throws
	 */
	public Map<String, Object> resetPayPwd(String bizUserId, String phone, String name, Long identityType,
			String identityNo, String jumpUrl) {
		logger.info("重置支付密码:{}", bizUserId);
		Map<String, Object> result = new HashMap<String, Object>();
		String jumpUrls = null;
		try {
			logger.info("重置支付密码开始请求参数...{}");
			jumpUrls = confirmPay("resetPayPwd.html", bizUserId, phone, name, identityType, identityNo, jumpUrl,
					"MemberPwdService", "resetPayPwd", "/api/notify/resetPayPwd");
			logger.info("重置支付密码结束");
		} catch (Exception e) {
			logger.error("重置支付密码异常", e);
			e.printStackTrace();
			BaseController.sysException();
		}
		result.put("jumpUrl", jumpUrls);
		return result;
	}

	/**
	 * @Title: setPayPwdParam @Description: 设置支付密码 @param @param
	 *         bizUserId @param @param phone @param @param name @param @param
	 *         identityType @param @param identityNo @param @param
	 *         jumpUrl @param @param backUrl @param @return @param @throws
	 *         Exception 设定文件 @return JSONObject 返回类型 @throws
	 */
	public static JSONObject setPayPwdParam(String bizUserId, String phone, String name, Long identityType, String identityNo, String jumpUrl, String backUrl) throws Exception {
		JSONObject param = new JSONObject();
		param.put("bizUserId", bizUserId);
		param.put("phone", phone);
		param.put("name", name);
		param.put("identityType", identityType);
		RSAUtil rsaUtil = new RSAUtil((RSAPublicKey) PayClientUtil.getPublicKey(),
				(RSAPrivateKey) PayClientUtil.getPrivateKey());
		param.put("identityNo", rsaUtil.encrypt(identityNo));
		param.put("jumpUrl", PayClientUtil.notifyAddress + jumpUrl);
		param.put("backUrl", PayClientUtil.notifyAddress + backUrl);
		return param;
	}

	@SuppressWarnings("rawtypes")
	public String confirmPay(String url, String bizUserId, String phone, String name, Long identityType,
			String identityNo, String jumpUrl, String service, String method, String backUrl) {
		String resUrl = null;
		try {
			JSONObject param = null;
			if (backUrl.equals("/api/notify/updatePayPwd")) {
				param = changePayPwdParam(bizUserId, name, identityType, identityNo, jumpUrl);
			} else {
				param = setPayPwdParam(bizUserId, phone, name, identityType, identityNo, jumpUrl, backUrl);
			}
			logger.info("支付密码开始请求参数...{}", param);
			String sysid = PayClientUtil.sysid;
			String timestamp = DateUtil.convertToString(new Date()); // 时间戳

			JSONObject req = new JSONObject();
			req.put("service", service);
			req.put("method", method);
			req.put("param", param);
			// 签名
			StringBuilder sb = new StringBuilder();
			String reqStr1 = req.toString();
			sb.append(sysid).append(reqStr1).append(timestamp);
			String sign = RSAUtil.sign(PayClientUtil.getPrivateKey(), sb.toString());
			sb.setLength(0);
			Map<String, String> map = new HashMap<String, String>();
			map.put("sysid", sysid);
			map.put("sign", sign);
			map.put("timestamp", timestamp);
			map.put("v", "1.0");
			map.put("req", req.toString());
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (java.util.Map.Entry) iterator.next();
				sb.append((String) entry.getKey()).append("=")
						.append(URLEncoder.encode(URLEncoder.encode((String) entry.getValue(), "UTF-8"),"UTF-8")).append("&");
			}
			String hrefStr = sb.toString().substring(0, sb.length() - 1);
			resUrl = PayClientUtil.payPwdAddress + url + "?" + hrefStr;
			logger.info("密码end.........resUrl：{}", resUrl);
		} catch (Exception e) {
			logger.error("密码", e);
			e.printStackTrace();
		}
		return resUrl;
	}


}
