package com.visionet.syh_mall.service.thridAccount;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mbr.UserAccountFlowRepository;

@Service
public class SoaDepositService {
	private final static Logger logger = LoggerFactory.getLogger(SoaDepositService.class);
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserAccountFlowRepository userAccountFlowDao;
	
	/**
	 * 保证金订单支付
	 * @param 
	 * @return Map<String,String>
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String> applyHostCollect(String bizUserId, Long amount, String payMethodName, String bizOrderNo,
			String bankCode, String frontUrl,String shopId) throws Exception {
		logger.info("保证金订单支付申请（支付订单）申请start.........bizOrderNo:{}", bizOrderNo);
		Map<String, String> returnMap = null;
		// 参数组装
		JSONObject param = applyHostCollectParam(bizUserId, amount, bizOrderNo, payMethodName, bankCode, frontUrl,shopId);
		logger.info("thridPay托管代收申请（支付订单）请求参数：{}", param);
		JSONObject response = PayClientUtil.getSOAClient().request("OrderService", "agentCollectApply", param);
		logger.info("thridPay托管代收申请（支付订单）响应：{}", response);
		Map result = response.getMap();
		if ("error".equals(result.get("status"))) {
			logger.info("thridPay托管代收申请（支付订单）失败bizOrderNo:{}", bizOrderNo);
			throw new RestException(String.valueOf(result.get("errorCode")), String.valueOf(result.get("message")));
		}
		if ("OK".equals(result.get("status"))) {
			JSONObject returnValue = new JSONObject(String.valueOf(result.get("returnValue")));
			returnMap = returnValue.getMap();
		}
		return returnMap;
	}
	
	/**
	 * 保证金参数拼接
	 * @param 
	 * @return JSONObject
	 * @throws
	 */
	private JSONObject applyHostCollectParam(String bizUserId, Long amount, String bizOrderNo, String payMethodName,
			String bankCode, String frontUrl,String shopId) throws JSONException {
		//分账列表
		JSONArray recieverList = new JSONArray();
		JSONObject reciever = new JSONObject();
		//保证金分账数据
		reciever.put("bizUserId", PayClientUtil.syhNo);
		reciever.put("amount", amount);
		recieverList.put(reciever);
		
		
		// 托管代收订单收款列表
		JSONObject param = new JSONObject();
		JSONObject extendInfo = new JSONObject();
		extendInfo.put("payMethodName", payMethodName);
		extendInfo.put("shopId", shopId);
		// 组装请求参数
		if ("BALANCE".equals(payMethodName)) {
			param.put("bizOrderNo", bizOrderNo);
			param.put("payerId", bizUserId);
			param.put("recieverList", recieverList);
			param.put("tradeCode", "3001");
			param.put("amount", amount);
			User user = userRepo.findOne(bizUserId);
			if(user.getMemberType()==3){				
				param.put("validateType", 2);// 密码验证方式支付
			}
			param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/depositAgentPayNotify");
			param.put("payMethod", getPayMethod(payMethodName, amount, bankCode, bizUserId));
			param.put("industryCode", "1914");
			param.put("industryName", "交易市场");
			param.put("source", 1);
			param.put("summary", "订单支付"); // 交易内容
			param.put("extendInfo", extendInfo.toString());
		} else {
			param.put("bizOrderNo", bizOrderNo);
			param.put("payerId", bizUserId);
			param.put("recieverList", recieverList);
			param.put("tradeCode", "3001");
			param.put("amount", amount);
			param.put("fee", 0);
			param.put("validateType", 0);// 免密支付
			param.put("frontUrl", PayClientUtil.notifyAddress + frontUrl);
			param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/depositAgentPayNotify");
			param.put("payMethod", getPayMethod(payMethodName, amount, bankCode, bizUserId));
			param.put("industryCode", "1914");
			param.put("industryName", "交易市场");
			param.put("source", 1);
			param.put("summary", "订单支付"); // 交易内容
			param.put("extendInfo", extendInfo.toString());
		}
		return param;
	}
	
	
	/**
	 * 保证金入到保证金账户中
	 * @param 
	 * @return Map<String,String>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> signalAgentPay(String bussOrderNo, String flowNo)
			throws Exception {
		logger.info("单笔托管代付请求订单号：{}", bussOrderNo);
		Map<String, String> returnMap = null;
		JSONObject param = signalAgentPayParam(bussOrderNo, flowNo);
		logger.info("单笔托管代付请求参数：{}", param);
		JSONObject res = PayClientUtil.getSOAClient().request("OrderService", "signalAgentPay", param);
		logger.info("单笔托管代付请求响应：{}", res);
		if ("error".equals(res.get("status"))) {
			logger.info("单笔托管代付失败bizOrderNo:{}", bussOrderNo);
			throw new RestException(String.valueOf(res.get("message")));
		}
		if ("OK".equals(res.get("status"))) {
			JSONObject returnValue = new JSONObject(String.valueOf(res.get("returnValue")));
			returnMap = returnValue.getMap();
		}
		return returnMap;
	}
	
	private JSONObject signalAgentPayParam(String bussOrderNo, String flowNo)
			throws JSONException {
		UserAccountFlow accountFlow = userAccountFlowDao.findByOrderNo(flowNo);
		Long amount = Long.valueOf(AmountUtils.changeY2F(accountFlow.getAmt().toString())); // 支付金额 元转分
		
		JSONArray collectPayList = new JSONArray();
		JSONObject collectPay = new JSONObject();
		collectPay.put("bizOrderNo", flowNo);
		collectPay.put("amount", amount);
		collectPayList.put(collectPay);
		
		JSONArray splitRuleList = new JSONArray();
		// 平台分账
		JSONObject splistRule = new JSONObject();
		splistRule.put("bizUserId", "#yunBizUserId_application#");
		splistRule.put("accountSetNo", "100002");
		splistRule.put("amount", amount);
		splistRule.put("fee", 0);
		splistRule.put("remark", "");
		splitRuleList.put(splistRule);
		
		JSONObject param = new JSONObject();
		
		param.put("bizOrderNo", bussOrderNo); // 托管代付订单号
		param.put("bizUserId", PayClientUtil.syhNo);
		param.put("accountSetNo", PayClientUtil.accountSetNo);
		param.put("collectPayList", collectPayList);
		param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/depositSignalPayNotify");
		param.put("amount", amount);
		param.put("fee", 0);
		param.put("splitRuleList", splitRuleList);
		param.put("tradeCode", "4001");
		return param;
	}
	
	/**
	 * 支付方式
	 * 
	 * @param payMethodName
	 *            支付方式名称
	 * @param amount
	 *            金额 （分）
	 * @return
	 * @throws JSONException
	 */
	private JSONObject getPayMethod(String payMethodName, Long amount, String bankCode, String userId)
			throws JSONException {
		JSONObject payMethod = new JSONObject();
		if ("BALANCE".equals(payMethodName)) { // 账户余额支付
			JSONArray accountPay = new JSONArray();
			JSONObject balance = new JSONObject();
			balance.put("accountSetNo", PayClientUtil.accountSetNo);
			balance.put("amount", amount);
			accountPay.put(balance);
			payMethod.put("BALANCE", accountPay);
		}
		if ("GATEWAY".equals(payMethodName)) { // 网关支付
			JSONObject gateway = new JSONObject();
			gateway.put("bankCode", bankCode); // 银行编码
			gateway.put("payType", 1); // 借记卡
			gateway.put("amount", amount);
			payMethod.put("GATEWAY", gateway);
		}
		if ("WECHAT_PUBLIC".equals(payMethodName)) { // 微信js支付（公众号）
			User user = userRepo.findOne(userId);
			if (StringUtils.isEmpty(user.getWechatOpenId())) {
				throw new RestException("请先关注公众号");
			}
			JSONObject wechatPublic = new JSONObject();
			wechatPublic.put("payType", "");
			wechatPublic.put("acct", user.getWechatOpenId()); // 微信 JS 支付
																// openid——微信分配
			wechatPublic.put("amount", amount);
			payMethod.put("WECHAT_PUBLIC", wechatPublic);
		}
		if ("SCAN_WEIXIN".equals(payMethodName)) { // 微信正扫
			JSONObject scanWeixin = new JSONObject();
			scanWeixin.put("payType", "W01");
			scanWeixin.put("amount", amount);
			payMethod.put("SCAN_WEIXIN", scanWeixin);
		}
		if ("SCAN_ALIPAY".equals(payMethodName)) { // 支付宝正扫
			JSONObject scanAlipay = new JSONObject();
			scanAlipay.put("payType", "A01");
			scanAlipay.put("amount", amount);
			payMethod.put("SCAN_ALIPAY", scanAlipay);
		}
		return payMethod;
	}
}
