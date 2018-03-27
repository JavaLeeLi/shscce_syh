package com.visionet.syh_mall.service.account;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.SequenceUUID;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.thridAccount.SoaAccountService;

/**
 * 订单支付
 * 
 * @author xiaofb
 * @time 2017年10月11日
 */
@Service
public class RechargePayService extends BaseService {

	@Autowired
	private SoaAccountService soaAccountService;
	@Autowired
	private UserAccountFlowService userAccountFlowService;

	/**
	 * 充值支付
	 * 
	 * @param userId
	 *            用户id
	 * @param payMethod
	 *            充值方式
	 * @param amount
	 *            交易金额(元)
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> rechargePay(String userId, String payMethod, String amount, String bankCode,
			String frontUrl) throws Exception {
		Long amt = Long.valueOf(AmountUtils.changeY2F(amount));
		System.out.println(payMethod);
		String orderNo = SequenceUUID.getOrderIdByUUId("B"); // 充值订单号
		// 添加账户流水记录
		UserAccountFlow flow = addAccountFlow(orderNo, amount, userId, payMethod);
		// 提交支付申请
		Map<String, String> result = soaAccountService.applyDeposit(userId, amt, payMethod, orderNo, bankCode,
				frontUrl);
		// 扫码支付返回二维码字符串
		if ("SCAN_WEIXIN".equals(payMethod) || "SCAN_ALIPAY".equals(payMethod)) {
			result.put("payUrl", result.get("payInfo"));
			result.put("orderNo", orderNo);
			return result;
		}
		if ("WECHAT_PUBLIC".equals((payMethod))){
			result.put("payUrl", result.get("payInfo"));
			result.put("orderNo", orderNo);
			return result;
		}
		// 网关确认支付url
		String payUrl = soaAccountService.confirmPay(userId, result.get("bizOrderNo"));
		result.put("payUrl", payUrl);
		result.put("orderNo", flow.getOrderNo());
		return result;
	}

	/**
	 * 新增账户流水记录
	 * 
	 * @param orderNo
	 *            订单号
	 * @param amount
	 *            金额
	 * @param userId
	 *            用户id
	 */
	private UserAccountFlow addAccountFlow(String orderNo, String amount, String userId, String payMethod) {
		UserAccountFlow accountFlow = new UserAccountFlow();
		accountFlow.setOrderNo(orderNo);
		accountFlow.setCreateTime(DateUtil.getCurrentDate());
		accountFlow.setUpdateTime(DateUtil.getCurrentDate());
		accountFlow.setContent("余额充值");
		accountFlow.setAmt(new BigDecimal(amount));
		accountFlow.setUserId(userId);
		accountFlow.setPayMethod(payMethod);
		accountFlow.setStatus("等待确认");
		accountFlow.setBusinessType(null);
		accountFlow.setFlowType("充值");
		accountFlow.setType(0); // 0 收入 1.支出
		return userAccountFlowService.addUserAccountFlow(accountFlow);

	}
}
