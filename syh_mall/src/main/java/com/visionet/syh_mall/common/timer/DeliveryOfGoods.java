package com.visionet.syh_mall.common.timer;

import org.springframework.stereotype.Component;


@Component
public class DeliveryOfGoods {
	@SuppressWarnings("unused")
	private final static long orderDelivery = 100;
	
	
//	@Scheduled(fixedRate = orderDelivery)
	public void deliveryOfGoods(){
//		List<Order> orderList = orderDao.findOrderListByStatus("order_unreceied");
//		for (Order order : orderList) {
//			Date lastDate = DateUtil.seekDate(order.getUpdateTime(),15);
//			boolean bool = DateUtil.compare(DateUtil.getCurrentDate(), order.getUpdateTime(), lastDate);
//			if(!bool){
//				try {
////					orderPayService.agentPay(order.getOrderSn());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
		Thread current = Thread.currentThread(); 
		int i = 0;
		System.out.println(current.getId()+"_DeliveryOfGoods.deliveryOfGoods_"+i++);
	}
}
