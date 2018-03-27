package com.visionet.syh_mall.service.thridAccount;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.entity.goods.AuctionJob;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.goods.AuctionJobRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.service.scheduler.QuartzManager;

/**
 * 托管代付（确认收货）
 * 
 * @author xiaofb
 * @time 2017年10月14日
 */
@Service
public class SoaPostagePayService {
	private static final Logger logger = LoggerFactory.getLogger(SoaPostagePayService.class);

	@Autowired
	private OrderRepository orderRepo;


	/**
	 * 单笔托管代付
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> signalAgentPay(String bussOrderNo, String sourceOrderNo) throws Exception {
		logger.info("单笔托管代付请求订单号：{}", bussOrderNo);
		logger.info("单笔托管代付请求源订单号：{}", sourceOrderNo);
		Map<String, String> returnMap = null;
		JSONObject param = signalAgentPayParam(bussOrderNo, sourceOrderNo);
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

	/**
	 * 单笔托管代付参数组装
	 * 
	 * @return
	 * @throws JSONException
	 */
	private JSONObject signalAgentPayParam(String bussOrderNo, String sourceOrderNo) throws JSONException {
		// 源订单信息
		Order order = orderRepo.findByOrderSn(sourceOrderNo);
		if (null == order || StringUtils.isEmpty(order.getShopOwnerId())) {
			throw new RestException(HttpStatus.ACCEPTED, "代付订单信息有误");
		}
		String userId = order.getShopOwnerId(); // 收款人
		String amt = order.getExpressFee().toString();
		// 订单金额
		Long amount = Long.valueOf(AmountUtils.changeY2F(amt)); // 支付金额 元转分

		JSONObject param = new JSONObject();
		param.put("bizOrderNo", bussOrderNo); // 托管代付订单号
		// 源托管代收订单付款信息

		param.put("bizUserId", userId);
		param.put("accountSetNo", PayClientUtil.accountSetNo);
		param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/postagePayBackNofify");
		param.put("amount", amount);
		param.put("fee", 0);
		param.put("tradeCode", "4001");
		return param;
	}
}
