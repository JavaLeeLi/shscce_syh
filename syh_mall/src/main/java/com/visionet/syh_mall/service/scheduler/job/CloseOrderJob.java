package com.visionet.syh_mall.service.scheduler.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.visionet.syh_mall.entity.goods.AuctionJob;
import com.visionet.syh_mall.repository.goods.AuctionJobRepository;
import com.visionet.syh_mall.service.order.OrderService;

public class CloseOrderJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(CloseOrderJob.class);
	@Autowired
	private OrderService orderService;
	@Autowired
	private AuctionJobRepository auctionJobRepo;

	@Override
	public void execute(JobExecutionContext context) {
		String orderId = context.getJobDetail().getJobDataMap().getString("orderId");
		AuctionJob auctionJob = auctionJobRepo.findByJobName("job_close_order_" + orderId);
		logger.info("未支付订单,订单关闭任务【{}】", orderId);
		try {
			// 取消订单关闭的定时器任务
			auctionJob.setJobStatus(1);
			auctionJobRepo.save(auctionJob);
			// 关闭订单
			orderService.cancelOrder(orderId);
		} catch (Exception e) {
			logger.error("未支付订单,订单关闭任务异常", e);
			e.printStackTrace();
		}
	}

}
