package com.visionet.syh_mall.service.scheduler.job;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.service.order.OrderService;
import com.visionet.syh_mall.web.syhservice.SyhserviceController;

public class CloseReviewJob implements Job{
	
	private static final Logger logger = LoggerFactory.getLogger(SyhserviceController.class);
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private OrderService orderService;
	@Override
	public void execute(JobExecutionContext context) {
		String orderNo = context.getJobDetail().getJobDataMap().getString("orderNo");
		logger.info("订单评论关闭任务【{}】", orderNo);
		try {
			Order order = orderDao.findByOrderSn(orderNo);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("orderID", order.getId());
			map.put("orderScore", 1);
			map.put("shopsScore", 1);
			orderService.scoreOrder(map, order.getBuyerId(),1);
		} catch (Exception e) {
			logger.error("订单评论关闭任务异常", e);
			e.printStackTrace();
		}
	}

}
