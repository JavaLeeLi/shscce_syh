package com.visionet.syh_mall.service;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.VerifyCode;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.VerifyCodeRepository;
import com.visionet.syh_mall.service.thridAccount.PayClientUtil;

/**
 * @ClassName: RestPhoneService
 * @Description: 重置手机号
 * @author chenghongzhan
 * @date 2018年1月19日 下午2:08:10
 *
 */
@Service
public class RestPhoneService extends BaseService {

	private static final Logger logger = LoggerFactory.getLogger(RestPhoneService.class);
	@Autowired
	private UserRepository userDao;
	@Autowired
	private VerifyCodeRepository verifyCodeDao;

	/**
	 * @Title: resetPhone @Description: 验证旧手机号的正确性 @param @param
	 *         phone @param @param code 设定文件 @return void 返回类型 @throws
	 */
	public void validationOldPhone(String oldPhone, String smsCode, String userId) {
		User user = userDao.findById(userId);
		if (!oldPhone.equals(user.getPhone())) {
			throw new RestException("旧手机号不正确");
		}
		String verifyCode = verifyCodeDao.findCodeByTime(oldPhone);
		if (verifyCode == null) {
			throw new RestException("验证码不正确");
		}
		if (!verifyCode.equals(smsCode)) {
			throw new RestException("验证码不正确");
		}
		List<VerifyCode> list = verifyCodeDao.findVerifyCodeByTime(oldPhone);
		VerifyCode code = list.get(0);
		if (null != code && null != code.getId()) {
			code.setHasValidated("validated");
			code = verifyCodeDao.save(code);
		}

	}

	/**
	 * @throws Exception
	 * @Title: restNewPhone @Description: 重置新手机号 @param @param
	 *         oldPhone @param @param smsCode @param @param userId 设定文件 @return
	 *         void 返回类型 @throws
	 */
	public void restNewPhone(String userId, String smsCode, String newPhone) throws Exception {
		logger.info("重置绑定手机号  ......... newPhone:{}", newPhone);
		// 参数组装
		User user = userDao.findById(userId);
		JSONObject param = restNewPhoneParam(userId, user.getPhone(), newPhone, smsCode);
		logger.info("重置绑定手机号穿参：{}", param);
		JSONObject response = PayClientUtil.getSOAClient().request("MemberService", "changeBindPhone", param);
		logger.info("重置绑定手机号响应：{}", response);
		Map result = response.getMap();
		if ("error".equals(result.get("status"))) {
			logger.info("重置绑定手机号失败 newPhone:{}", newPhone);
			throw new RestException(String.valueOf(result.get("errorCode")), String.valueOf(result.get("message")));
		}
		if ("OK".equals(result.get("status"))) {
			user.setPhone(newPhone);
			userDao.save(user);
		}
	}

	/**
	 * @Title: restNewPhoneParam @Description: 重置手机号传参 @param @param
	 *         bizUserId @param @param oldPhone @param @param
	 *         newPhone @param @param
	 *         newVericationCode @param @return @param @throws JSONException
	 *         设定文件 @return JSONObject 返回类型 @throws
	 */
	private JSONObject restNewPhoneParam(String bizUserId, String oldPhone, String newPhone, String newVericationCode)
			throws JSONException {
		// 组装请求参数
		JSONObject param = new JSONObject();
		param.put("bizUserId", bizUserId);
		param.put("oldPhone", oldPhone);
		param.put("newPhone", newPhone);
		param.put("newVerificationCode", newVericationCode);
		return param;
	}

	/**
	 * @throws Exception
	 * @Title: sendVerificationCode @Description: 发送短信 @param @param
	 *         bizUserId @param @param phone @param @param
	 *         verificationCodeType @param @return @param @throws JSONException
	 *         设定文件 @return String 返回类型 @throws
	 */
	@SuppressWarnings("unused")
	public void sendVerificationCode(String bizUserId, String phone) throws Exception {
		logger.info("发送短信start......... phone:{}", phone);
		JSONObject param = new JSONObject();
		param.put("bizUserId", bizUserId);
		param.put("phone", phone);
		param.put("verificationCodeType", Long.valueOf("9"));
		logger.info("发送短信参数：{}", param);
		JSONObject response = PayClientUtil.getSOAClient().request("MemberService", "sendVerificationCode", param);
		logger.info("发送短信响应：{}", response);
		Map result = response.getMap();
		if ("error".equals(result.get("status"))) {
			logger.info("发送短信失败 phone:{}", phone);
			throw new RestException(String.valueOf(result.get("errorCode")), String.valueOf(result.get("message")));
		}
		if ("OK".equals(result.get("status"))) {
			JSONObject returnValue = new JSONObject(String.valueOf(result.get("returnValue")));
		}
	}

}
