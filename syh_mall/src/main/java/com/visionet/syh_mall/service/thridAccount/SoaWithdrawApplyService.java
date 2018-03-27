package com.visionet.syh_mall.service.thridAccount;

import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.account.DrawCash;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.finance.DrawCashRepository;

@Service
@EnableAsync
public class SoaWithdrawApplyService {
	private static final Logger logger = LoggerFactory.getLogger(SoaWithdrawApplyService.class);
	
	@Autowired
	private DrawCashRepository drawCashRepo;
	@Autowired
	private UserRepository userDao;
	
	/**
	 * 提现申请
	 * 
	 * @param bizOrderNo
	 * @param bizUserId
	 * @param amount
	 * @param bankCardNo
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Async
	public Map<String, String> withdrawApply(String bizOrderNo, String bizUserId, Long amount, String bankCardNo,
			DrawCash cash,String personal) throws Exception {
		Map<String, String> returnMap = null;
		JSONObject param = withdrawApplyParam(bizOrderNo, bizUserId, amount, bankCardNo,personal);
		logger.info("thridPay提现申请请求参数：{}", param);
		JSONObject response = PayClientUtil.getSOAClient().request("OrderService", "withdrawApply", param);
		logger.info("thridPay提现申请响应：{}", response);
		Map result = response.getMap();
		if ("error".equals(result.get("status"))) {
			logger.info("thridPay托管代收申请（支付订单）失败bizOrderNo:{}", bizOrderNo);
			throw new RestException(String.valueOf(result.get("message")));
		}
		if ("OK".equals(result.get("status"))) {
			cash.setStatusCode("withdrawal_accepted");
			cash.setReviewBy(bizUserId);
			drawCashRepo.save(cash);
			JSONObject returnValue = new JSONObject(String.valueOf(result.get("returnValue")));
			returnMap = returnValue.getMap();
		}
		return returnMap;
	}
	
	/**
	 * 提现申请请求参数
	 * 
	 * @param bizOrderNo
	 * @param bizUserId
	 * @param amount
	 * @param bankCardNo
	 * @return
	 * @throws Exception
	 */
	public JSONObject withdrawApplyParam(String bizOrderNo, String bizUserId, Long amount, String bankCardNo,String personal)
			throws Exception {
		User user = userDao.findById(bizUserId);
		JSONObject param = new JSONObject();
		param.put("bizUserId", bizUserId);
		param.put("bizOrderNo", bizOrderNo);
		param.put("accountSetNo", PayClientUtil.accountSetNo);
		param.put("amount", amount);
		param.put("fee", 0);
		param.put("backCardPro", 0);
		param.put("validateType", 0);
		if(user.getMemberType()==2){
			param.put("bankCardPro", 1);
			param.put("validateType", 1);
			if(personal!=null){
				param.put("bankCardPro", 0);
			}
		}
		param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/withdrawApplyBackNotify");
		param.put("withdrawType", "T1");
		param.put("bankCardNo", PayClientUtil.rsaEncrypt(bankCardNo));
		param.put("industryCode", "1914");
		param.put("industryName", "交易市场");
		param.put("source", 1L);
		param.put("summary", "withdraw");
		param.put("extendInfo", "");
		return param;
	}
}
