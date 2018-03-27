package com.visionet.syh_mall.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.RestPhoneService;

@RestController
@RequestMapping("/api/phone")
public class ResetPhoneController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ResetPhoneController.class);
	@Autowired
	private RestPhoneService restPhoneService;

	/**
	 * @Title: resetPhone @Description: 验证旧手机号 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/validationOldPhone", method = RequestMethod.POST)
	public BaseReturnVo<Object> validationOldPhone(@RequestBody Map<String, String> param) {
		logger.info("验证旧手机号:{}", param);
		try {
			String oldPhone = param.get("oldPhone");
			String smsCode = param.get("smsCode");
			String userId = getCurrentUserId();
			restPhoneService.validationOldPhone(oldPhone, smsCode, userId);
		} catch (RestException e) {
			logger.error("验证旧手机号异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("验证旧手机号异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("重置绑定手机号成功");
	}

	/**
	 * @Title: restNewPhone @Description: 重置新手机号 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/restNewPhone", method = RequestMethod.POST)
	public BaseReturnVo<Object> restNewPhone(@RequestBody Map<String, String> param) {
		logger.info("重置新手机号:{}", param);
		try {
			String smsCode = param.get("smsCode");
			String newPhone = param.get("newPhone");
			String userId = getCurrentUserId();
			restPhoneService.restNewPhone(userId, smsCode, newPhone);
		} catch (RestException e) {
			logger.error("重置新手机号异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("重置新手机号异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("重置新手机号成功");
	}

	/**
	 * @Title: sendVerificationCode @Description: 发送验证码 @param @param
	 * param @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/sendVerificationCode", method = RequestMethod.POST)
	public BaseReturnVo<Object> sendVerificationCode(@RequestBody Map<String, String> param) {
		logger.info("发送验证码:{}", param);
		try {
			String newPhone = param.get("newPhone");
			String userId = getCurrentUserId();
			restPhoneService.sendVerificationCode(userId, newPhone);
		} catch (RestException e) {
			logger.error("发送验证码异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("发送验证码异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("发送验证码成功");
	}

}
