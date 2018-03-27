package com.visionet.syh_mall.service.scheduler.startup;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.goods.AuctionJob;
import com.visionet.syh_mall.entity.goods.GroupJob;
import com.visionet.syh_mall.service.goods.AuctionJobService;
import com.visionet.syh_mall.service.goods.GroupJobService;
import com.visionet.syh_mall.service.scheduler.QuartzManager;

/**
 * spring boot启动服务 加载拍卖任务
 * @author xiaofb
 * @time 2017年10月26日
 */
@Component
@Order(value = 1)	//执行顺序
public class StartupScheduler implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(StartupScheduler.class);
	
	@Autowired
	private AuctionJobService auctionJobService;
	@Autowired
	private QuartzManager QuartzManager;
	@Autowired
	private GroupJobService GroupJobService;
	
	@Override
	public void run(String... args) throws Exception {
		//获取调度中的任务
		logger.info("======启动加载拍卖QuartzJob  start=========");
		List<AuctionJob> list = auctionJobService.getAuctionJobList(0);
		logger.info("待加载拍卖任务【{}】个",list.size());
		for (AuctionJob auctionJob : list) {
			logger.info("加载拍卖商品id：【{}】",auctionJob.getGoodsId());
			auctionJob = isCanExecute(auctionJob);
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("goodsId", auctionJob.getGoodsId());
			QuartzManager.addJob(auctionJob, paramMap);
		}
		logger.info("======启动加载拍卖QuartzJob  end===========");

		
		//获取调度中的求购任务
		logger.info("======启动加载求购  start=========");
		List<AuctionJob> purchaseGoods = auctionJobService.getAuctionJobList(8);
		logger.info("待加载求购任务【{}】个",purchaseGoods.size());
		for (AuctionJob auctionJob : purchaseGoods) {
			logger.info("加载求购商品id：【{}】",auctionJob.getGoodsId());
			auctionJob = isCanExecute(auctionJob);
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("goodsId", auctionJob.getGoodsId());
			QuartzManager.addJob(auctionJob, paramMap);
		}
		logger.info("======启动加载求购  end===========");
		
		
		//通过售后W
		logger.info("------------启动审核退款申请任务-----------");
		List<AuctionJob> ApplicationList = auctionJobService.getAuctionJobList(2);
		logger.info("待商家审核退款申请任务【{}】个",ApplicationList.size());
		for (AuctionJob auctionJob : ApplicationList) {
			logger.info("审核退款申请orderId：【{}】",auctionJob.getGoodsId());
			auctionJob = isCanExecute(auctionJob);
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("orderId", auctionJob.getGoodsId());
			QuartzManager.addJob(auctionJob, paramMap);
		}
		logger.info("======启动审核退款申请QuartzJob  end===========");
		

		//关闭评论
		logger.info("------------启动关闭订单评论任务-----------");
		List<AuctionJob> closeReviewList = auctionJobService.getAuctionJobList(3);
		logger.info("关闭订单评论任务【{}】个",closeReviewList.size());
		for (AuctionJob auctionJob : closeReviewList) {
			logger.info("关闭订单评论orderNO：【{}】",auctionJob.getGoodsId());
			auctionJob = isCanExecute(auctionJob);
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("orderNo", auctionJob.getGoodsId());
			QuartzManager.addJob(auctionJob, paramMap);
		}
		logger.info("======启动关闭订单评论申请QuartzJob  end===========");
		

		//自动收货
		logger.info("------------启动订单自动收货任务-----------");
		List<AuctionJob> automaticReceiptList = auctionJobService.getAuctionJobList(4);
		logger.info("订单自动收货任务【{}】个",automaticReceiptList.size());
		for (AuctionJob auctionJob : automaticReceiptList) {
			logger.info("订单自动收货orderNO：【{}】",auctionJob.getGoodsId());
			auctionJob = isCanExecute(auctionJob);
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("orderNo", auctionJob.getGoodsId());
			QuartzManager.addJob(auctionJob, paramMap);
		}
		logger.info("======启动订单自动收货QuartzJob  end===========");
		

		//订单关闭
		logger.info("------------启动订单关闭任务-----------");
		List<AuctionJob> closeOrderList = auctionJobService.getAuctionJobList(5);
		logger.info("订单关闭任务【{}】个",closeOrderList.size());
		for (AuctionJob auctionJob : closeOrderList) {
			logger.info("订单关闭orderId：【{}】",auctionJob.getGoodsId());
			auctionJob = isCanExecute(auctionJob);
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("orderId", auctionJob.getGoodsId());
			QuartzManager.addJob(auctionJob, paramMap);
		}
		logger.info("======启动订单关闭QuartzJob  end===========");
		
		
		
		//获取任务类表
		List<GroupJob> groupJobList = GroupJobService.getGroupJobList(0);
		logger.info("待加载的组团任务{}个",groupJobList.size());
		for (GroupJob groupJob : groupJobList) {
			logger.info("加载团购组团id:{}",groupJob.getGroupDetailId());
			groupJob = isCanExecute(groupJob);
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("groupDetailId", groupJob.getGroupDetailId());
			QuartzManager.addJob(groupJob, paramMap);
		}
		logger.info("------------加载组团任务结束-----------");
	}

	private AuctionJob isCanExecute(AuctionJob auctionJob) {
		String cronExpression = auctionJob.getCronExpression();
		Date date = DateUtil.schedulerCronExpression(cronExpression);
		if(date.before(DateUtil.getCurrentDate())&&auctionJob.getJobStatus()!=1){
			auctionJob.setCronExpression(DateUtil.getSchedulerCronExpression(DateUtil.getCurrentDate()));
		}
		return auctionJob;
	}
	
	private GroupJob isCanExecute(GroupJob auctionJob) {
		String cronExpression = auctionJob.getCronExpression();
		Date date = DateUtil.schedulerCronExpression(cronExpression);
		if(date.before(DateUtil.getCurrentDate())&&auctionJob.getJobStatus()!=1){
			auctionJob.setCronExpression(DateUtil.getSchedulerCronExpression(DateUtil.getCurrentDate()));
		}
		return auctionJob;
	}

}
