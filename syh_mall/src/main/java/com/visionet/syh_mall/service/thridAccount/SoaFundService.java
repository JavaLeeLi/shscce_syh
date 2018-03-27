package com.visionet.syh_mall.service.thridAccount;

import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.exception.RestException;

/**
 * soa资金冻结、解冻
 * 
 * @author xiaofb
 * @time 2017年10月22日
 */
@Service
public class SoaFundService {
	private static final Logger logger = LoggerFactory.getLogger(SoaFundService.class);
	
	/**
	 * soa资金冻结
	 * @param bizUserId
	 * @param bizFreezenNo
	 * @param amount
	 * @return	bizFreezenNo   订单号
	 * 			amount	金额
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> freezeMoney(String bizUserId,String bizFreezenNo,Long amount) throws Exception {
		Map<String,String> returnMap = null;
		JSONObject param = new JSONObject();
		param.put("bizUserId", bizUserId);
		param.put("bizFreezenNo", bizFreezenNo);
		param.put("accountSetNo", PayClientUtil.accountSetNo);
		param.put("amount", amount);
		logger.info("soa资金冻结请求参数：{}",param);
		JSONObject response = PayClientUtil.getSOAClient().request("OrderService", "freezeMoney", param);
		logger.info("soa资金冻结响应：{}",response);
		if("error".equals(response.get("status"))){
			logger.info("资金冻结失败bizFreezenNo:{}",bizFreezenNo);
			throw new RestException(String.valueOf(response.get("errorCode")),String.valueOf(response.get("message")));
		}
		if("OK".equals(response.get("status"))){
			JSONObject returnValue = new JSONObject(String.valueOf(response.get("returnValue")));
			returnMap = returnValue.getMap();
		}
		return returnMap;
	}
	
	/**
	 * soa资金解冻
	 * @param bizUserId
	 * @param bizFreezenNo
	 * @param amount
	 * @return	bizFreezenNo   订单号
	 * 			amount	金额
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> unfreezeMoney(String bizUserId,String bizFreezenNo,Long amount) throws Exception {
		Map<String,String> returnMap = null;
		JSONObject param = new JSONObject();
		param.put("bizUserId", bizUserId);
		param.put("bizFreezenNo", bizFreezenNo);
		param.put("accountSetNo", PayClientUtil.accountSetNo);
		param.put("amount", amount);
		logger.info("soa资金解冻请求参数：{}",param);
		JSONObject response = PayClientUtil.getSOAClient().request("OrderService", "unfreezeMoney", param);
		logger.info("soa资金解冻响应：{}",response);
		if("error".equals(response.get("status"))){
			logger.info("soa资金解冻失败bizFreezenNo:{}",bizFreezenNo);
			throw new RestException(String.valueOf(response.get("errorCode")),String.valueOf(response.get("message")));
		}
		if("OK".equals(response.get("status"))){
			
			JSONObject returnValue = new JSONObject(String.valueOf(response.get("returnValue")));
			returnMap = returnValue.getMap();
		}
		return returnMap;
	}

}
