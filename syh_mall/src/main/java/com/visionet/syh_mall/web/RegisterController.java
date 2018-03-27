package com.visionet.syh_mall.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.service.mobile.RegisterService;
import com.visionet.syh_mall.vo.SMSVO;

/**
 *@Author DM
 *@version ：2017年8月22日下午5:46:27
 *实体类
 */

@RestController
@RequestMapping("/api/register")
public class RegisterController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private RegisterService registerService;
	/**
	 * 发送短信
	* @author dm
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendCode", method = RequestMethod.POST)
	@ResponseBody
	public Object queryList(@RequestBody @Valid SMSVO vo, BindingResult result) throws Exception {
		logger.info("短信发送请求参数：{}",vo);
		SMSVO sendMessage = registerService.sendMessage(vo);
		logger.info("发送短信返回验证码：{}",sendMessage.getCode());
		BaseReturnVo<SMSVO> returnVo = new BaseReturnVo<SMSVO>(sendMessage);
		return returnVo;
	}

}
