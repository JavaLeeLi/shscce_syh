package com.visionet.syh_mall.common.utils;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.touch.sysif.sms.api.SmsClient;
import com.touch.sysif.sms.api.client.HttpSmsClient;
import com.touch.sysif.sms.api.model.SmsMessage;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.exception.RestException;

/**
 * 第三方短信工具类
 * @author xiaofb
 * @time 2017年9月26日
 */
@Component
public class MessageUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageUtils.class);
	
	private static String configPath;

	@Value("${configPath}")
	public void setConfigPath(String configPath) {
		MessageUtils.configPath = configPath;
	}

	/**
	 * 发送短信
	 * @param phone 手机号
	 * @param contentTemp 短信内容模板  必须包含 {verifyCode}
	 * return 验证码 code
	 * @throws IOException 
	 */
	public static String sendMessage(String phone,String contentTemp) throws IOException{
		SmsClient client = new HttpSmsClient();
		client.init(MessageUtils.configPath);
		//生成验证码
		String verifyCode = randomNumber();
		try {
			//设置发送消息属性
			SmsMessage message = new SmsMessage();
			//短信接收号码必填多个号号码用‘；’或者','隔开，群发时建议不超过100个号码
			message.setDestAddr(phone);
			//消息内容
			String content = StringUtils.replace(contentTemp, "{verifyCode}", verifyCode);
			message.setContent(content);
			message.setReqReport(true);
			//定时发送时间可选，默认立即发送
			message.setSendTime(new Date());
			String result = client.send(message);
			logger.info("短信发送返回结果：{}",result);
			if(StringUtils.isEmpty(result)){
				throw new RestException(BusinessStatus.SMS_FAIL.getDesc());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			client.shutdown();
		}
		return verifyCode;
	}
	/**
	 * 短信通知
	 * @param phone 手机号
	 * @param contentTemp 短信内容模板  必须包含 {text}
	 * @throws IOException 
	 */
	public static String sendText(String phone,String contentTemp,String text){
		SmsClient client = new HttpSmsClient();
		client.init(MessageUtils.configPath);
		String content = null;
		try {
			//设置发送消息属性
			SmsMessage message = new SmsMessage();
			//短信接收号码必填多个号号码用‘；’或者','隔开，群发时建议不超过100个号码
			message.setDestAddr(phone);
			//消息内容
			content = StringUtils.replace(contentTemp, "{text}", text);
			message.setContent(content);
			message.setReqReport(true);
			//定时发送时间可选，默认立即发送
			message.setSendTime(new Date());
			String result = client.send(message);
			logger.info("短信发送返回结果：{}",result);
			if(StringUtils.isEmpty(result)){
				logger.info("短信发送失败【{}】",phone);
//				throw new RestException(HttpStatus.OK,BusinessStatus.SMS_FAIL.getDesc());
			}
		} catch (Exception e) {
			logger.error("短信发送异常：{}",e);
			e.printStackTrace();
		}finally{
			client.shutdown();
		}
		return content;
	}
	
	
	/**
	 * 6位随机数
	 * @param 
	 * @return
	 */
	private static String randomNumber() {
		StringBuffer code = new StringBuffer("");
		for (int i = 0; i < 6; i++) {
			code.append((int) (10 * (Math.random())));
		}
		return code.toString();
	}
}
