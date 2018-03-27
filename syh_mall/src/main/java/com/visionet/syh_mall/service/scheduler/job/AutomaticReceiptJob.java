package com.visionet.syh_mall.service.scheduler.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.service.order.OrderPayService;
import com.visionet.syh_mall.web.syhservice.SyhserviceController;

public class AutomaticReceiptJob implements Job{
	private static final Logger logger = LoggerFactory.getLogger(SyhserviceController.class);
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private OrderPayService orderPayService;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String orderNo = context.getJobDetail().getJobDataMap().getString("orderNo");
		String flowNO = context.getJobDetail().getJobDataMap().getString("flowNO");
		logger.info("订单自动收货任务【{}】", orderNo);
		try {
			Order order = orderDao.findByOrderSn(orderNo);
			//确认收货
			orderPayService.agentPay(orderNo, order.getBuyerId(),1,flowNO);
			
		} catch (Exception e) {
			logger.error("未支付订单,订单关闭任务异常", e);
			e.printStackTrace();
		}
	}

}
