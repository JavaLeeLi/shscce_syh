package com.visionet.syh_mall.service.goods;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.goods.AuctionJob;
import com.visionet.syh_mall.repository.goods.AuctionJobRepository;
import com.visionet.syh_mall.service.scheduler.QuartzManager;

@Service
public class PurchaseJobService {
	@Autowired
	private AuctionJobRepository auctionJobRepo;
	@Autowired
	private QuartzManager quartzManager;

	
	/**
	 * 团购任务调度
	 * 
	 * @param goodsId
	 * @param time
	 * @throws Exception
	 */
	public void savePurchaseJob(String goodsId, String time) throws Exception {
		Date date = DateUtil.convertFromString(time);
		String cron = DateUtil.getSchedulerCronExpression(date);
		AuctionJob job = auctionJobRepo.findByJobName("job_goods_purchase_" + goodsId);
		if (null != job) {
			quartzManager.removeJob(job.getJobName(), job.getJobGroupName(), job.getTriggerName(), job.getTriggerGroupName());
			job.setCronExpression(cron);
		} else {
			job = new AuctionJob();
			job.setGoodsId(goodsId);
			job.setJobName("job_goods_purchase_" + goodsId);
			job.setJobGroupName("job_goods_purchase");
			job.setTriggerName("trigger_" + goodsId);
			job.setTriggerGroupName("trigger_goods_purchase");
			job.setJobStatus(8);
			job.setCronExpression(cron);
			job.setJobClass("com.visionet.syh_mall.service.scheduler.job.PurchaseGoodsEndJob");// 定时任务的实现了类
			job.setCreateTime(DateUtil.getCurrentDate());
			job.setUpdateTime(DateUtil.getCurrentDate());
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("goodsId", goodsId);
		auctionJobRepo.save(job);
		// 任务调度
		quartzManager.addJob(job, map);
	}
}
