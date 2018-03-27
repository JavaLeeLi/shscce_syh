package com.visionet.syh_mall.repository.goods;

import java.util.List;

import com.visionet.syh_mall.entity.goods.AuctionJob;
import com.visionet.syh_mall.repository.BaseRepository;

public interface AuctionJobRepository extends BaseRepository<AuctionJob, String> {
	
	//通过商品id获取拍卖任务Job
	public AuctionJob findByGoodsId(String goodsId);

	public AuctionJob findByJobName(String jobName);
	
	//通过状态获取调度任务列表
	public List<AuctionJob> findAllByJobStatus(int jobStatus);
}
