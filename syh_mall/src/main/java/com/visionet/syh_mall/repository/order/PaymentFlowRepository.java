package com.visionet.syh_mall.repository.order;

import com.visionet.syh_mall.entity.order.PaymentFlow;
import com.visionet.syh_mall.repository.BaseRepository;

public interface PaymentFlowRepository extends BaseRepository<PaymentFlow, String> {
	
	//通过订单号获取代付流水信息
	public PaymentFlow findByBussOrderNo(String bussOrderNo);
	
	public PaymentFlow findBySourceOrderNo(String sourceOrderNo);
}
