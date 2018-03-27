package com.visionet.syh_mall.service.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.visionet.syh_mall.entity.order.Order;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.service.order.OrderCancService;
import com.visionet.syh_mall.web.syhservice.SyhserviceController;

public class ByApplication implements Job {
	private static final Logger logger = LoggerFactory.getLogger(SyhserviceController.class);
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private OrderCancService orderCancService;

	@Override
	public void execute(JobExecutionContext context) {
		String orderId = context.getJobDetail().getJobDataMap().getString("orderId");
		logger.info("商家自动审核通过任务【{}】", orderId);
		try {
			Order order = orderDao.findByOrderId(orderId);
			// 商家审核退款申请
			orderCancService.reviewRefundApply("", orderId, "refund_syh_confirm", order.getShopOwnerId(),1);
		} catch (Exception e) {
			logger.error("商家自动审核通过任务异常", e);
			e.printStackTrace();
		}
	}
}
