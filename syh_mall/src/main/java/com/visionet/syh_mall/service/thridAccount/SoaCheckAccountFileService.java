package com.visionet.syh_mall.service.thridAccount;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.entity.account.DrawCash;
import com.visionet.syh_mall.entity.finance.StatementOrder;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.entity.order.OrderRefund;
import com.visionet.syh_mall.entity.order.PaymentFlow;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.finance.DrawCashRepository;
import com.visionet.syh_mall.repository.finance.StatementOrderRepository;
import com.visionet.syh_mall.repository.mbr.UserAccountFlowRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.repository.order.OrderRefundRepository;
import com.visionet.syh_mall.repository.order.PaymentFlowRepository;
import com.visionet.syh_mall.service.BaseService;

/**
 * 对账文件的获取
 * @author xiaofb
 * @time 2017年11月16日
 */
@Service
public class SoaCheckAccountFileService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(SoaCheckAccountFileService.class);
	
	@Autowired
	private UserAccountFlowRepository userFlowRepo;
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private DrawCashRepository drawCashRepo;
	@Autowired
	private OrderRefundRepository orderRefundRepo;
	@Autowired
	private PaymentFlowRepository paymentFlowRepo;
	@Autowired
	private StatementOrderRepository statementOrderRepo;
	/**
	 * 获取第三方支付对账文件
	 * @param date
	 * @throws Exception
	 * @retrun String  云账户对账文件地址
	 */
	public String getCheckAccountFile(String date) throws Exception {
		JSONObject param = new JSONObject();
		Map<String,String> resultMap = new HashMap<String, String>();
		logger.info("支付对账文件下载请求参数：{}",date);
		param.put("date", date);
		JSONObject response = PayClientUtil.getSOAClient().request("MerchantService", "getCheckAccountFile", param);
		logger.info("支付对账文件下载响应：{}",response);
		if ("error".equals(response.get("status"))) {
			throw new RestException(String.valueOf(response.get("message")));
		}
		if ("OK".equals(response.get("status"))) {
			JSONObject returnValue = new JSONObject(String.valueOf(response.get("returnValue")));
			resultMap = returnValue.getMap();
		}
		String url = resultMap.get("url");
		return url;
	}
	
	public Page<StatementOrder> getAccountFileResult(String date,PageInfo pageInfo) throws Exception{
		this.comporeAccountFile(date);
		Date startDate = DateUtil.convertFromString(date+" 00:00:00", DateUtil.YMD_FULL);
		Date endDate = DateUtil.convertFromString(date+" 24:00:00", DateUtil.YMD_FULL);
		PageRequest page = buildPageRequest(pageInfo);
		Page<StatementOrder> statementOrderList = statementOrderRepo.findByDateOrderByComporeResult(startDate, endDate, page);
		return statementOrderList;
	}
	
	/**
	 * 对账文件订单和系统订单比较
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public void comporeAccountFile(String date) throws Exception{
		Date startDate = DateUtil.convertFromString(date+" 00:00:00", DateUtil.YMD_FULL);
		Date endDate = DateUtil.convertFromString(date+" 24:00:00", DateUtil.YMD_FULL);
		List<StatementOrder> statementOrderList = statementOrderRepo.findByDate(startDate, endDate);
		if(statementOrderList.size() <= 0){
			accountFileToList(date); //对账文件信息入库
			statementOrderList = statementOrderRepo.findByDate(startDate, endDate);	//重新查询数据库
		}
		for (StatementOrder statementOrder : statementOrderList) {
			if(statementOrder.getComporeResult() != null){
				continue;
			}
			//充值订单
			if(1 == statementOrder.getOrderType()){
				comporeRechargeOrderNo(statementOrder);
			}
			//消费订单
			if(2 == statementOrder.getOrderType()){
				comporeConsumeOrderNo(statementOrder);
			}
			//提现订单
			if(3 == statementOrder.getOrderType()){
				comporeWithdrawOrderNo(statementOrder);
			}
			//托管代收
			if(4 == statementOrder.getOrderType()){
				comporeConsumeOrderNo(statementOrder);
			}
			//单笔托管代付
			if(5 == statementOrder.getOrderType()){
				comporePaymentOrder(statementOrder);
			}
			//退款订单
			if(10 == statementOrder.getOrderType()){
				comporeRefundOrderNo(statementOrder);
			}
		}
	}
	
	/**
	 * 托管代付订单比较
	 * @param accountFileOrderVo
	 */
	private void comporePaymentOrder(StatementOrder statementOrder){
		PaymentFlow  paymentFlow = paymentFlowRepo.findByBussOrderNo(statementOrder.getAllinpayBizOrderNo());
		if(null == paymentFlow){
			statementOrder.setComporeResult(1);
			return;
		}
		if(statementOrder.getAllinpayTradeAmt().compareTo(paymentFlow.getPaymentAmt()) != 0){
			statementOrder.setComporeResult(1);
		}else{
			statementOrder.setComporeResult(2);
		}
		statementOrder.setPlatformTradeAmt(paymentFlow.getPaymentAmt());
		statementOrder.setPlatformOrderNo(paymentFlow.getBussOrderNo());
		statementOrder.setUpdateTime(DateUtil.getCurrentDate());
		statementOrderRepo.save(statementOrder);
	}
	
	/**
	 * 充值订单比较
	 * @param accountFileOrderVo
	 */
	private void comporeRechargeOrderNo(StatementOrder statementOrder){
		UserAccountFlow  userAccountFlow = userFlowRepo.findByOrderNo(statementOrder.getAllinpayBizOrderNo());
		if(null == userAccountFlow){
			statementOrder.setComporeResult(1);;
			return;
		}
		if(statementOrder.getAllinpayTradeAmt().compareTo(userAccountFlow.getAmt()) != 0){
			statementOrder.setComporeResult(1);;
		}else{
			statementOrder.setComporeResult(2);;
		}
		statementOrder.setPlatformTradeAmt(userAccountFlow.getAmt());
		statementOrder.setPlatformOrderNo(userAccountFlow.getOrderNo());
		statementOrder.setUpdateTime(DateUtil.getCurrentDate());
		statementOrderRepo.save(statementOrder);
	}
	
	/**
	 *提现订单比较
	 * @param accountFileOrderVo
	 */
	private void comporeWithdrawOrderNo(StatementOrder statementOrder){
		DrawCash drawCashOrder = drawCashRepo.findByDrawOrderNo(statementOrder.getAllinpayBizOrderNo());
		if(null == drawCashOrder){
			statementOrder.setComporeResult(1);
			return;
		}
		if(statementOrder.getAllinpayTradeAmt().compareTo(drawCashOrder.getDrawAmt()) != 0){
			statementOrder.setComporeResult(1);
		}else{
			statementOrder.setComporeResult(2);
		}
		statementOrder.setPlatformTradeAmt(drawCashOrder.getDrawAmt());
		statementOrder.setPlatformOrderNo(drawCashOrder.getDrawOrderNo());
		statementOrder.setUpdateTime(DateUtil.getCurrentDate());
		statementOrderRepo.save(statementOrder);
	}
	
	/**
	 * 消费订单比较
	 * @param accountFileOrderVo
	 */
	private void comporeConsumeOrderNo(StatementOrder statementOrder){
		Order  consumeOrder = orderRepo.findByOrderSn(statementOrder.getAllinpayBizOrderNo());
		if(null == consumeOrder){
			statementOrder.setComporeResult(1);
			return;
		}
		if(statementOrder.getAllinpayTradeAmt().compareTo(consumeOrder.getTotalPrice()) != 0){
			statementOrder.setComporeResult(1);
		}else{
			statementOrder.setComporeResult(2);
		}
		statementOrder.setPlatformTradeAmt(consumeOrder.getTotalPrice());
		statementOrder.setPlatformOrderNo(consumeOrder.getOrderSn());
		statementOrder.setUpdateTime(DateUtil.getCurrentDate());
		statementOrderRepo.save(statementOrder);
	}
	
	/**
	 * 退款订单比较
	 * @param accountFileOrderVo
	 */
	private void comporeRefundOrderNo(StatementOrder statementOrder){
		OrderRefund  refundOrder = orderRefundRepo.findByRefundOrderNo(statementOrder.getAllinpayBizOrderNo());
		if(null == refundOrder){
			statementOrder.setComporeResult(1);
			return;
		}
		if(statementOrder.getAllinpayTradeAmt().compareTo(refundOrder.getRefundSum()) != 0){
			statementOrder.setComporeResult(1);
		}else{
			statementOrder.setComporeResult(2);
		}
		statementOrder.setPlatformTradeAmt(refundOrder.getRefundSum());
		statementOrder.setPlatformOrderNo(refundOrder.getRefundOrderNo());
		statementOrder.setUpdateTime(DateUtil.getCurrentDate());
		statementOrderRepo.save(statementOrder);
	}
	
	/**
	 * 对账文件  To List数据集合
	 * @param date
	 * @return
	 * @throws Exception
	 */
	private void accountFileToList(String date) throws Exception{
		Date date1 = DateUtil.convertDate(date);
		date = DateUtil.convertToString(date1, DateUtil.YMD3);
		String result = getCheckAccountFile(date);
		URL url = new URL(result);
		URLConnection urlConn = url.openConnection();
		InputStream input = urlConn.getInputStream();
		Reader reader = new InputStreamReader(input);
		BufferedReader bufferReader = new BufferedReader(reader);
		bufferReader.readLine();
		String orderText = null;
		while (!StringUtils.isEmpty(orderText = bufferReader.readLine())) {
			StatementOrder statementOrder = new StatementOrder();
			String[] orderArray = orderText.split("\\|");
			statementOrder.setAllinpayOrderNo(orderArray[0]);
			statementOrder.setOrderType(Integer.valueOf(orderArray[1]));
			statementOrder.setAllinpayTradeAmt(new BigDecimal(AmountUtils.changeF2Y(orderArray[2])));
			statementOrder.setAllinpayFee(new BigDecimal(AmountUtils.changeF2Y(orderArray[3])));
			statementOrder.setAllinpayTradeDate(DateUtil.convertFromString(orderArray[4], DateUtil.YMD_FULL));
			statementOrder.setAllinpayBizOrderNo(orderArray[5]);
			statementOrder.setComporeResult(1);
			statementOrder.setCreateTime(DateUtil.getCurrentDate());
			statementOrderRepo.save(statementOrder);
		}
	}
	
	

}
