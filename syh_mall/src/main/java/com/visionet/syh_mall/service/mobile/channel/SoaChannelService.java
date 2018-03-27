package com.visionet.syh_mall.service.mobile.channel;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.thridAccount.PayClientUtil;

/**
 * @ClassName: SoaChannelService
 * @Description: 佣金结算
 * @author chenghongzhan
 * @date 2017年11月10日 上午10:36:19
 *
 */
@Service
public class SoaChannelService {

	private static final Logger logger = LoggerFactory.getLogger(SoaChannelService.class);

	/**
	 * @Title: rebate @Description: 分销的平台结算 @param @param
	 *         rebateUserId @param @param targetAccountSetNo @param @param
	 *         newOrderNo @param @param amt @param @return 设定文件 @return
	 *         JSONObject 返回类型 @throws
	 */
	public JSONObject rebate(String rebateUserId, String newOrderNo, Long amt) {
		JSONObject rebate = null;
		try {
			logger.info("佣金结算start");
			// 参数组装
			JSONObject param = signalChannelParam(rebateUserId, newOrderNo, amt);
			logger.info("佣金结算参数:{}", param);
			rebate = PayClientUtil.getSOAClient().request("OrderService", "applicationTransfer", param);
			logger.info("佣金结算返回参数:{}", rebate);
			logger.info("佣金结算end");
		} catch (Exception e) {
			logger.info("佣金结算 Exception");
			e.printStackTrace();
		}
		try {
			if ("error".equals(rebate.get("status"))) {
				logger.info("接口调用失败");
				throw new RestException(HttpStatus.ACCEPTED,
						BusinessStatus.INTERFACE_ERROR.getDesc() + "_" + rebate.get("message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rebate;
	}

	/**
	 * 参数拼接 @param @return JSONObject @throws
	 */
	public static JSONObject signalChannelParam(String rebateUserId, String bizTransferNo, Long amt)
			throws JSONException {

		JSONObject param = new JSONObject();
		param.put("bizTransferNo", bizTransferNo);
		param.put("sourceAccountSetNo", "2000000");
		param.put("targetBizUserId", rebateUserId);
		param.put("targetAccountSetNo", PayClientUtil.accountSetNo);
		param.put("amount", amt);
		return param;

	}

}
