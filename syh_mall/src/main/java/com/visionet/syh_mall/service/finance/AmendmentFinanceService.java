package com.visionet.syh_mall.service.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.entity.order.OrderGoods;
import com.visionet.syh_mall.entity.order.OrderRefund;
import com.visionet.syh_mall.entity.order.PaymentFlow;
import com.visionet.syh_mall.repository.mbr.UserAccountFlowRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderGoodsRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.repository.order.OrderRefundRepository;
import com.visionet.syh_mall.repository.order.PaymentFlowRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.account.UserAccountFlowService;
import com.visionet.syh_mall.service.account.UserAccountService;
import com.visionet.syh_mall.service.order.PaymentFlowService;

@Service
public class AmendmentFinanceService extends BaseService{

	@Autowired
	private UserAccountFlowRepository userAccountFlowDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserAccountFlowService accountFlowService;
	@Autowired
	private OrderRefundRepository orderRefundDao;
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private OrderGoodsRepository orderGoodsDao;
	@Autowired
	private GoodsRepository goodsDao;
	@Autowired
	private PaymentFlowService paymentFlowService;
	
	/**
	 * 修改异常流水
	 * @param 
	 * @return void
	 * @throws
	 */
	public void amendmentFinance(String bizOrderNo) {
		UserAccountFlow accountFlow = userAccountFlowDao.findByOrderNo(bizOrderNo);
		//退款处理异常
		if("货款运费退款入账".equals(accountFlow.getContent())||"货款退款入账".equals(accountFlow.getContent())){
			OrderRefund orderRefund = orderRefundDao.findByRefundOrderNo(bizOrderNo);
			if (!StringUtils.isEmpty(orderRefund)) {
				orderRefund.setRefundStatusCode("refund_accepted");
				orderRefund.setUpdateTime(DateUtil.getCurrentDate());
				orderRefundDao.save(orderRefund);
			}
			Order order = orderDao.findByOrderId(orderRefund.getOrderId());
			if(!"order_closed".equals(order.getOrderStatusCode())){				
				List<OrderGoods> orderGoods = orderGoodsDao.findByOrderId(order.getId());
				for (OrderGoods orderGood : orderGoods) {
					Goods goods = goodsDao.findOne(orderGood.getGoodsId());
					goods.setStockNum(goods.getStockNum() + orderGood.getGoodsNum());
					goods.setStatusCode("goods_grounding");
					goodsDao.save(goods);
				}
				order.setUpdateTime(DateUtil.getCurrentDate());
				order.setOrderStatusCode("order_closed");
				order.setIsRefund("refund_accepted");
				orderDao.save(order);
			}
			accountFlow.setStatus("success");
			accountFlow.setUpdateTime(DateUtil.getCurrentDate());
			accountFlowService.addUserAccountFlow(accountFlow);
			userAccountService.addAccountAmt(order.getBuyerId(), accountFlow.getAmt(), accountFlow.getAmt(), null);
		}
		//运费异常
		if("邮费入账".equals(accountFlow.getContent())){
			PaymentFlow payFlow = paymentFlowService.getPaymentFlowRecord(bizOrderNo);
			accountFlow.setStatus("success");
			accountFlow.setUpdateTime(DateUtil.getCurrentDate());
			accountFlowService.addUserAccountFlow(accountFlow);
			//修改用户账户余额
			userAccountService.addAccountAmt(payFlow.getGatheringUserId(), accountFlow.getAmt(), accountFlow.getAmt(), null);
			payFlow.setStatus("代付交易成功");
			payFlow.setUpdateTime(DateUtil.getCurrentDate());
			paymentFlowService.insertPaymentFlow(payFlow);
		}
		//充值异常
		if("余额充值".equals(accountFlow.getContent())){
			accountFlowService.modifyFlowStatus(accountFlow.getUserId(), bizOrderNo);
			// 修改账户余额
			userAccountService.addAccountAmt(accountFlow.getUserId(), accountFlow.getAmt(), accountFlow.getAmt(), null);
		}
		//提现异常
		if("提现".equals(accountFlow.getContent())){
			UserAccountFlow flow = userAccountFlowDao.findByOrderNo(bizOrderNo);
			flow.setStatus("success");
			flow.setUpdateTime(DateUtil.getCurrentDate());
			accountFlowService.addUserAccountFlow(flow);
		}
		//托管代付异常
		if("".equals(accountFlow.getContent())){
			
		}
		//托管代收异常
		if("".equals(accountFlow.getContent())){
			
		}
		//营销支付异常
		if("营销服务支出".equals(accountFlow.getContent())){
			
		}
	}

}
