package com.visionet.syh_mall.service.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.visionet.syh_mall.entity.goods.AuctionJob;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.repository.goods.AuctionJobRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;

public class PurchaseGoodsEndJob implements Job{
	@Autowired
	private AuctionJobRepository auctionJobRepo;
	@Autowired
	private GoodsRepository goodsDao;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String goodsId = context.getJobDetail().getJobDataMap().getString("goodsId");
		AuctionJob auctionJob = auctionJobRepo.findByJobName("job_goods_purchase_" + goodsId);
		try {
			Goods goods = goodsDao.findOne(goodsId);
			goods.setStatusCode("goods_undercarriage");
			goodsDao.save(goods);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		auctionJob.setJobStatus(1);
		auctionJobRepo.save(auctionJob);
	}

}
