package com.visionet.syh_mall.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.order.PaymentFlow;
import com.visionet.syh_mall.repository.order.PaymentFlowRepository;
import com.visionet.syh_mall.service.BaseService;

/**
 * 订单代付service
 * @author xiaofb
 * @time 2017年10月18日
 */
@Service
public class PaymentFlowService extends BaseService {
	@Autowired
	private PaymentFlowRepository paymentFlowRepo;
	
	/**
	 * 插入代付流水
	 * @param paFlow
	 */
	public void insertPaymentFlow(PaymentFlow paFlow){
		paymentFlowRepo.save(paFlow);
	}
	
	/**
	 * 获取代付流水记录
	 * @param bussOrderNo 业务订单号
	 * @return
	 */
	public PaymentFlow getPaymentFlowRecord(String bussOrderNo){
		PaymentFlow flow = paymentFlowRepo.findByBussOrderNo(bussOrderNo);
		return flow;
	}
	
	/**
	 * 修改代付流水状态
	 * @param bussOrderNo
	 * @param status
	 */
	public void modifyPaymentFlowStatus(String bussOrderNo,String status){
		PaymentFlow flow = paymentFlowRepo.findByBussOrderNo(bussOrderNo);
		flow.setStatus(status);
		flow.setUpdateTime(DateUtil.getCurrentDate());
		paymentFlowRepo.save(flow);
	}
	
	
}
