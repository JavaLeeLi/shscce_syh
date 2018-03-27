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
import com.visionet.syh_mall.entity.goods.GroupJob;
import com.visionet.syh_mall.repository.goods.GroupJobRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.scheduler.QuartzManager;

/**
 * 组团定时任务service
 * @author mulongfei
 * @date 2017年11月17日上午10:54:40
 */
@Service
public class GroupJobService extends BaseService{
private static final Logger logger = LoggerFactory.getLogger(GroupJobService.class);
	
	@Autowired
	private QuartzManager quartzManager;
	@Autowired
	private GroupJobRepository groupJobDao;
	
	/**
	 * 添加组团任务调度
	 * @param goodsId
	 * @param time
	 * @throws Exception
	 */
	public void saveAuctionJob(String groupDetailId,String time) throws Exception{
		Date date = DateUtil.convertFromString(time);
		String cron = DateUtil.getSchedulerCronExpression(date);
		GroupJob job = groupJobDao.findByGroupDetailId(groupDetailId);
		if(null != job ){
			job.setCronExpression(cron);
		}else{
			job = new GroupJob();
			job.setGroupDetailId(groupDetailId);
			job.setJobName("job_"+groupDetailId);
			job.setJobGroupName("job_goods_group");
			job.setTriggerName("trigger_"+groupDetailId);
			job.setTriggerGroupName("trigger_goods_group");
			job.setJobStatus(0);
			job.setCronExpression(cron);
			job.setJobClass("com.visionet.syh_mall.service.scheduler.job.GroupEndJob");// 定时任务的实现了类
			job.setCreateTime(DateUtil.getCurrentDate());
			job.setUpdateTime(DateUtil.getCurrentDate());
		}
		Map<String,String> map = new HashMap<String, String>();
		map.put("groupDetailId", groupDetailId);
		logger.info("商品团购任务调度 groupDetailId:{}",groupDetailId);
		groupJobDao.save(job);
		//任务调度
		quartzManager.addJob(job,map);
	}
	
	/**
	 * 获取调度任务列表
	 * @param jobStatus 调度任务状态
	 * @return
	 */
	public List<GroupJob> getGroupJobList(int jobStatus){
		List<GroupJob>  list = groupJobDao.findAllByJobStatus(jobStatus);
		return list;
	}
}
