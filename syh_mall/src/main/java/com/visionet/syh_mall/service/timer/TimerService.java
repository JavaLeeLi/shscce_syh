package com.visionet.syh_mall.service.timer;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.service.order.OrderPayService;

@Service
public class TimerService {
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private OrderPayService orderPayService;
	
	/**
	 * 超过15天自动收货
	 * @param 
	 * @return void
	 * @throws
	 */
//	@Scheduled(fixedRate = 10000)
	public void deliveryOfGoods(){
		List<Order> orderList = orderDao.findOrderListByStatus("order_unreceied");
		for (Order order : orderList) {
			Date lastDate = DateUtil.seekDate(order.getUpdateTime(),15);
			boolean bool = DateUtil.compare(DateUtil.getCurrentDate(), order.getUpdateTime(), lastDate);
			if(!bool){
				try {
//					orderPayService.agentPay(order.getOrderSn(), order.getBuyerId(),0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
