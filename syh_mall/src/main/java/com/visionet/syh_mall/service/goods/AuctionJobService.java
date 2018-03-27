package com.visionet.syh_mall.service.goods;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.goods.AuctionJob;
import com.visionet.syh_mall.repository.goods.AuctionJobRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.scheduler.QuartzManager;

/**
 * 定时器service
 * 
 * @author xiaofb
 * @time 2017年10月26日
 */
@Service
public class AuctionJobService extends BaseService {

	private static final Logger logger = LoggerFactory.getLogger(AuctionJobService.class);

	@Autowired
	private QuartzManager quartzManager;
	@Autowired
	private AuctionJobRepository auctionJobRepo;

	/**
	 * @Title: saveByApplicationJob @Description: 通过售后申请 @param @param
	 *         orderNo @param @param time @param @throws Exception 设定文件 @return
	 *         void 返回类型 @throws
	 */
	public void saveByApplicationJob(String orderId, String time) throws Exception {
		Date date = DateUtil.convertFromString(time);
		String cron = DateUtil.getSchedulerCronExpression(date);
		AuctionJob job = auctionJobRepo.findByJobName("job_by_application_" + orderId);
		if (null != job) { 
			job.setCronExpression(cron);
		} else {
			job = new AuctionJob();
			job.setGoodsId(orderId);
			job.setJobName("job_by_application_" + orderId);
			job.setJobGroupName("job_by_application");
			job.setTriggerName("trigger_" + orderId);
			job.setTriggerGroupName("trigger_by_application");
			job.setJobStatus(2);
			job.setCronExpression(cron);
			job.setJobClass("com.visionet.syh_mall.service.scheduler.job.ByApplication");// 定时任务的实现了类
			job.setCreateTime(DateUtil.getCurrentDate());
			job.setUpdateTime(DateUtil.getCurrentDate());
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderId", orderId);
		logger.info("商家自动通过售后申请任务调度 orderId:{}", orderId);
		auctionJobRepo.save(job);
		// 任务调度
		quartzManager.addJob(job, map);

	}


	/**
	 * @Title: saveAutomaticReceiptJob @Description: 关闭评论 @param @param
	 *         orderNo @param @param time @param @throws Exception 设定文件 @return
	 *         void 返回类型 @throws
	 */
	public void saveCloseReviewJob(String orderNo, String time) throws Exception {
		Date date = DateUtil.convertFromString(time);
		String cron = DateUtil.getSchedulerCronExpression(date);
		AuctionJob job = auctionJobRepo.findByJobName("job_close_review_" + orderNo);
		if (null != job) {
			job.setCronExpression(cron);
		} else {
			job = new AuctionJob();
			job.setGoodsId(orderNo);
			job.setJobName("job_close_review_" + orderNo);
			job.setJobGroupName("job_close_review");
			job.setTriggerName("trigger_" + orderNo);
			job.setTriggerGroupName("trigger_close_review");
			job.setJobStatus(3);
			job.setCronExpression(cron);
			job.setJobClass("com.visionet.syh_mall.service.scheduler.job.CloseReviewJob");// 定时任务的实现了类
			job.setCreateTime(DateUtil.getCurrentDate());
			job.setUpdateTime(DateUtil.getCurrentDate());
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNo", orderNo);
		logger.info("订单关闭评论任务调度 orderNo:{}", orderNo);
		auctionJobRepo.save(job);
		// 任务调度
		quartzManager.addJob(job, map);

	}

	/**
	 * 订单自动收货
	 * 
	 * @param
	 * @param time
	 * @throws Exception
	 */
	public void saveAutomaticReceiptJob(String orderNo, String time,String flowNo) throws Exception {
		Date date = DateUtil.convertFromString(time);
		String cron = DateUtil.getSchedulerCronExpression(date);
		AuctionJob job = auctionJobRepo.findByJobName("job_automatic_receipt_" + orderNo);
		if (null != job) {
			job.setCronExpression(cron);
		} else {
			job = new AuctionJob();
			job.setGoodsId(orderNo);
			job.setJobName("job_automatic_receipt_" + orderNo);
			job.setJobGroupName("job_automatic_receipt");
			job.setTriggerName("trigger_" + orderNo);
			job.setTriggerGroupName("trigger_automatic_receipt");
			job.setJobStatus(4);
			job.setCronExpression(cron);
			job.setJobClass("com.visionet.syh_mall.service.scheduler.job.AutomaticReceiptJob");// 定时任务的实现了类
			job.setCreateTime(DateUtil.getCurrentDate());
			job.setUpdateTime(DateUtil.getCurrentDate());
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNo", orderNo);
		map.put("flowNO", flowNo);
		logger.info("订单自动收货任务调度 orderNo:{}", orderNo);
		auctionJobRepo.save(job);
		// 任务调度
		quartzManager.addJob(job, map);

	}

	/**
	 * 未支付订单关闭
	 * 
	 * @param 
	 * @param time
	 * @throws Exception
	 */
	public void saveCloseOrderJob(String orderId, String time) throws Exception {
		Date date = DateUtil.convertFromString(time);
		String cron = DateUtil.getSchedulerCronExpression(date);
		AuctionJob job = auctionJobRepo.findByJobName("job_close_order_" + orderId);
		if (null != job) {
			job.setCronExpression(cron);
		} else {
			job = new AuctionJob();
			job.setGoodsId(orderId);
			job.setJobName("job_close_order_" + orderId);
			job.setJobGroupName("job_close_order");
			job.setTriggerName("trigger_" + orderId);
			job.setTriggerGroupName("trigger_close_order");
			job.setJobStatus(5);
			job.setCronExpression(cron);
			job.setJobClass("com.visionet.syh_mall.service.scheduler.job.CloseOrderJob");// 定时任务的实现了类
			job.setCreateTime(DateUtil.getCurrentDate());
			job.setUpdateTime(DateUtil.getCurrentDate());
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderId", orderId);
		logger.info("未支付订单关闭任务调度 orderId:{}", orderId);
		auctionJobRepo.save(job);
		// 任务调度
		quartzManager.addJob(job, map);
	}

	/**
	 * 商品拍卖任务调度
	 * 
	 * @param goodsId
	 * @param time
	 * @throws Exception
	 */
	public void saveAuctionJob(String goodsId, String time) throws Exception {
		Date date = DateUtil.convertFromString(time);
		String cron = DateUtil.getSchedulerCronExpression(date);
		AuctionJob job = auctionJobRepo.findByJobName("job_goods_auction_" + goodsId);
		if (null != job) {
			quartzManager.removeJob(job.getJobName(), job.getJobGroupName(), job.getTriggerName(), job.getTriggerGroupName());
			job.setCronExpression(cron);
		} else {
			job = new AuctionJob();
			job.setGoodsId(goodsId);
			job.setJobName("job_goods_auction_" + goodsId);
			job.setJobGroupName("job_goods_auction");
			job.setTriggerName("trigger_" + goodsId);
			job.setTriggerGroupName("trigger_goods_auction");
			job.setJobStatus(0);
			job.setCronExpression(cron);
			job.setJobClass("com.visionet.syh_mall.service.scheduler.job.GoodsAuctionEndJob");// 定时任务的实现了类
			job.setCreateTime(DateUtil.getCurrentDate());
			job.setUpdateTime(DateUtil.getCurrentDate());
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("goodsId", goodsId);
		logger.info("商品拍卖任务调度 goodsId:{}", goodsId);
		auctionJobRepo.save(job);
		// 任务调度
		quartzManager.addJob(job, map);
	}

	/**
	 * 获取调度任务列表
	 * 
	 * @param jobStatus
	 *            调度任务状态
	 * @return
	 */
	public List<AuctionJob> getAuctionJobList(int jobStatus) {
		List<AuctionJob> list = auctionJobRepo.findAllByJobStatus(jobStatus);
		return list;
	}
}
