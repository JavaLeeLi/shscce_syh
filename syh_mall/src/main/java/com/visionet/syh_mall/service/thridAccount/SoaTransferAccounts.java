package com.visionet.syh_mall.service.thridAccount;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.exception.RestException;

@Service
public class SoaTransferAccounts {
	private static final Logger logger = LoggerFactory.getLogger(SoaTransferAccounts.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String> marketTransferAccounts(String bizTransferNo, String sourceAccountSetNo, String targetBizUserId, String targetAccountSetNo,
			Long amount)throws Exception {
		logger.info("平台转账 申请start.........{}");
		Map<String, String> returnMap = null;
		// 参数组装
		JSONObject param = marketTransferAccountsParam(bizTransferNo,sourceAccountSetNo,targetBizUserId,targetAccountSetNo,amount);
		logger.info("平台转账  请求参数：{}", param);
		JSONObject response = PayClientUtil.getSOAClient().request("OrderService", "applicationTransfer", param);
		logger.info("平台转账申请响应：{}", response);
		Map result = response.getMap();
		if ("error".equals(result.get("status"))) {
			logger.info("平台转账申请失败");
			throw new RestException(String.valueOf(result.get("errorCode")), String.valueOf(result.get("message")));
		}
		if ("OK".equals(result.get("status"))) {
			JSONObject returnValue = new JSONObject(String.valueOf(result.get("returnValue")));
			returnMap = returnValue.getMap();
		}
		return returnMap;
	}
	
	/**
	 * 充值申请参数组装
	 * 
	 * @param bizUserId
	 * @param bizOrderNo
	 * @param accountSetNo
	 * @param amount
	 * @param payMethodName
	 * @return
	 * @throws JSONException
	 */
	private JSONObject marketTransferAccountsParam(String bizTransferNo, String sourceAccountSetNo, String targetBizUserId, String targetAccountSetNo,
			Long amount) throws JSONException {
		JSONObject param = new JSONObject();
		param.put("bizTransferNo", bizTransferNo);
		param.put("sourceAccountSetNo", sourceAccountSetNo);
		param.put("targetBizUserId", targetBizUserId);
		param.put("targetAccountSetNo", targetAccountSetNo);
		param.put("amount", amount);
		return param;
	}
}
