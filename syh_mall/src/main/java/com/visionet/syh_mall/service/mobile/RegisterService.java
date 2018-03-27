package com.visionet.syh_mall.service.mobile;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.MessageUtils;
import com.visionet.syh_mall.entity.VerifyCode;
import com.visionet.syh_mall.repository.VerifyCodeRepository;
import com.visionet.syh_mall.vo.SMSVO;

/**
 * @Author DM
 * @version ：2017年8月22日下午5:59:10 获取验证码service
 */
@Service
public class RegisterService {
	@Autowired
	private VerifyCodeRepository verifyCodeDao;

	/**
	 * 验证码获取
	 * 
	 * @return
	 * @author dm
	 * @throws Exception
	 */
	public SMSVO sendMessage(SMSVO smsvo) throws Exception {
		String contentTemp = "用户注册验证码为：{verifyCode} (30分钟内有效)";
		String code = MessageUtils.sendMessage(smsvo.getTelephone(), contentTemp);
		addCode(smsvo.getTelephone(), code);
		smsvo.setCode(code);
		return smsvo;
	}

	/**
	 * 短信通知获取
	 * 
	 * @return
	 * @author dm
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public SMSVO sendText(SMSVO smsvo) throws Exception {
		String contentTemp = "您的认证申请已被拒绝，拒绝理由为：{text}";
		String text = smsvo.getCode();
		String code = MessageUtils.sendText(smsvo.getTelephone(), contentTemp, text);
		return smsvo;
	}

	public void addCode(String phone, String verifyCode) throws Exception {
		VerifyCode code = new VerifyCode();
		code.setPhone(phone);
		code.setVerifyCode(verifyCode);
		code.setHasSent(1);
		code.setHasValidated("unValidated");
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, 30);
		SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdft.format(now.getTimeInMillis());
		code.setExpireTime(sdft.parse(dateStr));// 过期时间
		code = verifyCodeDao.save(code);
	}


}
