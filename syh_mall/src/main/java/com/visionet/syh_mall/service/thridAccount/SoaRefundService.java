package com.visionet.syh_mall.service.thridAccount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.exception.RestException;
/**
 * 退款申请
 * @author mulongfei
 * @date 2017年11月1日下午5:20:25
 */
@Service
public class SoaRefundService {
	private static final Logger logger = LoggerFactory.getLogger(SoaRefundService.class);
	public JSONObject Refund(String refundUserId,String bizUserId,String newOrderNo,String oldOrderNo,Long amt){
		JSONObject refound = null;
		try{
			logger.info("退款处理 start");
			//新订单编号
			JSONObject param = signalRefundParam(refundUserId, bizUserId, newOrderNo, oldOrderNo, amt);

			logger.info("退款请求参数:{}",param);
			refound = PayClientUtil.getSOAClient().request("OrderService", "refund", param);
			logger.info("退款响应返回参数:{}",refound);
			
			logger.info("退款处理 end");
		}catch(Exception e){
			logger.info("退款处理 Exception");
			e.printStackTrace();
		}
		try {
			if("error".equals(refound.get("status"))){
				logger.info("接口调用失败");
				throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.INTERFACE_ERROR.getDesc()+"_"+refound.get("message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return refound;
	}
	/**
	 * 参数拼接
	 * @param 
	 * @return JSONObject
	 * @throws
	 */
	public static JSONObject signalRefundParam(String refundUserId,String bizUserId,String newOrderNo,String oldOrderNo,Long amt) throws JSONException {
		String bizOrderNo = newOrderNo;
		
		JSONObject refund1 = new JSONObject();
		refund1.put("bizUserId", refundUserId);
		refund1.put("amount", amt);
		
		JSONArray refundList = new JSONArray();
		refundList.put(refund1);
		
		JSONObject param = new JSONObject();
		param.put("bizOrderNo", bizOrderNo);
		param.put("oriBizOrderNo", oldOrderNo);
		param.put("bizUserId", bizUserId);
		param.put("refundList", refundList);
		param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/getRefund");
		param.put("amount", amt);
		param.put("couponAmount", 0);
		param.put("feeAmount", 0);
		return param;
	}
}
