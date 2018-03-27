package com.visionet.syh_mall.service.thridAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.common.utils.MathUtils;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.channel.RetailDetail;
import com.visionet.syh_mall.entity.goods.AuctionJob;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.goods.AuctionJobRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.repository.mobile.channel.RetailDetailRepository;
import com.visionet.syh_mall.service.mobile.GoodsChannelRuleService;
import com.visionet.syh_mall.service.mobile.channel.ChannelService;
import com.visionet.syh_mall.service.scheduler.QuartzManager;

/**
 * 托管代付（确认收货）
 * 
 * @author xiaofb
 * @time 2017年10月14日
 */
@Service
public class SoaAgentPayService {
	private static final Logger logger = LoggerFactory.getLogger(SoaAgentPayService.class);

	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private RetailDetailRepository retailDetailRepo;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private KeyMappingRepository mappingDao;
	@Autowired
	private GoodsChannelRuleService channelRuleService;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private AuctionJobRepository auctionJobRepo;
	@Autowired
	private QuartzManager quartzManager;

	/**
	 * 营销服务单笔托管代付
	 * 
	 * @throws Exception
	 */
	/*
	 * @SuppressWarnings("unchecked") public Map<String, String>
	 * marketSignalAgentPay(String bussOrderNo, String sourceOrderNo, String
	 * userId) throws Exception { logger.info("单笔托管代付请求订单号：{}", bussOrderNo);
	 * Map<String, String> returnMap = null; JSONObject param =
	 * signalAgentPayParam(bussOrderNo, sourceOrderNo);
	 * logger.info("单笔托管代付请求参数：{}", param); JSONObject res =
	 * PayClientUtil.getSOAClient().request("OrderService", "signalAgentPay",
	 * param); logger.info("单笔托管代付请求响应：{}", res); if
	 * ("error".equals(res.get("status"))) {
	 * logger.info("单笔托管代付失败bizOrderNo:{}", bussOrderNo); throw new
	 * RestException(String.valueOf(res.get("message"))); } if
	 * ("OK".equals(res.get("status"))) { JSONObject returnValue = new
	 * JSONObject(String.valueOf(res.get("returnValue"))); returnMap =
	 * returnValue.getMap(); } return returnMap; }
	 */

	/**
	 * 单笔托管代付
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> signalAgentPay(String bussOrderNo, String sourceOrderNo, String flowNo)
			throws Exception {
		logger.info("单笔托管代付请求订单号：{}", bussOrderNo);
		Map<String, String> returnMap = null;
		JSONObject param = signalAgentPayParam(bussOrderNo, sourceOrderNo, flowNo);
		logger.info("单笔托管代付请求参数：{}", param);
		JSONObject res = PayClientUtil.getSOAClient().request("OrderService", "signalAgentPay", param);
		logger.info("单笔托管代付请求响应：{}", res);
		if ("error".equals(res.get("status"))) {
			logger.info("单笔托管代付失败bizOrderNo:{}", bussOrderNo);
			throw new RestException(String.valueOf(res.get("message")));
		}
		if ("OK".equals(res.get("status"))) {
			// 取消定时任务的确认收货
			AuctionJob auctionJob = auctionJobRepo.findByJobName("job_automatic_receipt_" + sourceOrderNo);
			quartzManager.removeJob(auctionJob.getJobName(), auctionJob.getJobGroupName(), auctionJob.getTriggerName(),
					auctionJob.getTriggerGroupName());
			auctionJob.setJobStatus(1);
			auctionJobRepo.save(auctionJob);
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
	private JSONObject signalAgentPayParam(String bussOrderNo, String sourceOrderNo, String flowNo)
			throws JSONException {
		// 源订单信息
		Order order = orderRepo.findByOrderSn(sourceOrderNo);
		if (null == order || StringUtils.isEmpty(order.getShopOwnerId())) {
			throw new RestException(HttpStatus.ACCEPTED, "代付订单信息有误");
		}
		String userId = order.getShopOwnerId(); // 收款人
		String amt = null;
		if (null != order.getIsRefund() && "order_closed".equals(order.getOrderStatusCode())) {
			amt = order.getExpressFee().toString(); // 邮费
		} else {
			amt = MathUtils.add(order.getTotalPrice(), order.getExpressFee()).toString(); // 总金额
		}
		// 订单金额
		Long amount = Long.valueOf(AmountUtils.changeY2F(amt)); // 支付金额 元转分

		// 商品分销金额
		BigDecimal commissionAmount = channelRuleService.getGoodChannelAmount(order);

		// 商品分销用户
		String shareUserNo = channelRuleService.getGoodChannelUser(order);

		// 手续费
		Long totalFeeAmount = Long.valueOf(AmountUtils
				.changeY2F(String.valueOf(channelService.getServiceCharge(order.getOrderSn()).doubleValue())));
		// 分账规则
		JSONArray splitRuleList = getSplitRuleList(order, userId, amt, commissionAmount);

		JSONArray collectPayList = new JSONArray();
		JSONObject collectPay2 = new JSONObject();
		collectPay2.put("bizOrderNo", flowNo);
		collectPay2.put("amount", amount);
		collectPayList.put(collectPay2);

		JSONObject param = new JSONObject();
		param.put("bizOrderNo", bussOrderNo); // 托管代付订单号
		// 源托管代收订单付款信息

		JSONObject collectPay1 = new JSONObject();
		// 商品分销用户
		collectPay1.put("shareUserNo", shareUserNo);
		// 商品分销的金额
		collectPay1.put("commissionAmount",
				Long.valueOf(AmountUtils.changeY2F(String.valueOf(commissionAmount.doubleValue()))));
		// 订单金额
		collectPay1.put("orderFee", Long.valueOf(AmountUtils.changeY2F(String.valueOf(new BigDecimal(amt)))));
		// 手续费
		collectPay1.put("fee", totalFeeAmount);
		param.put("extendInfo", collectPay1.toString());

		param.put("bizUserId", userId);
		param.put("accountSetNo", PayClientUtil.accountSetNo);
		param.put("collectPayList", collectPayList);
		param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/hostCollectBackNofify");
		param.put("amount", amount);
		param.put("fee", 0);
		param.put("splitRuleList", splitRuleList);
		param.put("tradeCode", "4001");
		return param;
	}

	/**
	 * 分账
	 * 
	 * @param
	 * @return
	 * @throws JSONException
	 */
	public JSONArray getSplitRuleList(Order order, String userId, String amount, BigDecimal commissionAmount)
			throws JSONException {
		logger.info("订单金额为:{}", amount);
		// 订单总金额
		BigDecimal totalAmt = new BigDecimal(amount);
		// 订单交易总手续费
		BigDecimal totalFee = channelService.getServiceCharge(order.getOrderSn());
		logger.info("订单交易总手续费金额为:{}", totalFee);

		KeyMapping mapping = mappingDao.findByKeyCode("costFee");
		// 扣除平台成本的金额
		BigDecimal costAmount = MathUtils.mul(totalFee, new BigDecimal(mapping.getValueDesc()));
		logger.info("扣除平台成本的分账金额金额为:{}", costAmount);

		// 分账金额为
		BigDecimal fashionableAmount = MathUtils.sub(totalFee, costAmount);
		logger.info("分账金额金额为:{}", fashionableAmount);

		// 会员奖励总金额
		BigDecimal totalReward = new BigDecimal(0);
		List<RetailDetail> list = retailDetailRepo.findByRetailObjId(order.getOrderSn());
		for (RetailDetail retailDetail : list) {
			totalReward = MathUtils.add(retailDetail.getCommissionAmt(), totalReward);
		}
		logger.info("会员奖励总金额为:{}", totalReward);

		// 商家应得 元转分
		Long userAmt = Long.valueOf(AmountUtils.changeY2F(
				String.valueOf(MathUtils.sub(MathUtils.sub(totalAmt, totalFee), commissionAmount).doubleValue())));

		// 奖励金额（分）
		Long totalRewardAmt = Long.valueOf(AmountUtils.changeY2F(String.valueOf(totalReward.doubleValue())));

		// 平台分账金额
		Long platformAmt = Long
				.valueOf(AmountUtils.changeY2F(String.valueOf(MathUtils.sub(totalFee, totalReward).doubleValue())));

		// 商品分销金额
		Long channelAmount = Long.valueOf(AmountUtils.changeY2F(String.valueOf(commissionAmount.doubleValue())));

		JSONArray splitRuleList = new JSONArray();
		// 用户分账
		if (userAmt.compareTo(new Long(0)) > 0) {
			
			JSONObject splistRule1 = new JSONObject();
			splistRule1.put("bizUserId", userId);
			splistRule1.put("accountSetNo", PayClientUtil.accountSetNo);
			splistRule1.put("amount", userAmt);
			splistRule1.put("fee", 0);
			splistRule1.put("remark", "");
			splitRuleList.put(splistRule1);
		}

		// 平台分账
		JSONObject splistRule2 = new JSONObject();
		splistRule2.put("bizUserId", "#yunBizUserId_application#");
		splistRule2.put("accountSetNo", "100001");
		splistRule2.put("amount", platformAmt);
		splistRule2.put("fee", 0);
		splistRule2.put("remark", "");
		splitRuleList.put(splistRule2);

		// 中间账户分账
		if (totalRewardAmt.compareTo(new Long(0)) > 0) {
			JSONObject splistRule3 = new JSONObject();
			splistRule3.put("bizUserId", "#yunBizUserId_application#");
			splistRule3.put("accountSetNo", "2000000"); // 标准营销账户
			splistRule3.put("amount", totalRewardAmt);
			splistRule3.put("fee", 0);
			splistRule3.put("remark", "");
			splitRuleList.put(splistRule3);
		}

		// 商品分销分享码
		String code = order.getSharingCode();
		if (null != code) {
			User user = userDao.findByInvitationCode(code);
			// 商品分销分账
			JSONObject splistRule4 = new JSONObject();
			splistRule4.put("bizUserId", user.getId());
			splistRule4.put("accountSetNo", PayClientUtil.accountSetNo);
			splistRule4.put("amount", channelAmount);
			splistRule4.put("fee", 0);
			splistRule4.put("remark", "");
			splitRuleList.put(splistRule4);
		}

		return splitRuleList;
	}
}
