package com.visionet.syh_mall.service.scheduler.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.visionet.syh_mall.common.utils.MessageUtils;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.GroupJob;
import com.visionet.syh_mall.entity.marketing.GroupBuy;
import com.visionet.syh_mall.entity.marketing.GroupDetail;
import com.visionet.syh_mall.entity.marketing.GroupUser;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.goods.GroupJobRepository;
import com.visionet.syh_mall.repository.marketing.GroupBuyRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.repository.mobile.GroupDetailRepository;
import com.visionet.syh_mall.repository.mobile.GroupUserRepository;
import com.visionet.syh_mall.service.mobile.ConponsService;

/**
 * 组团定时任务结束
 * @author mulongfei
 * @date 2017年11月17日下午2:34:25
 */
public class GroupEndJob implements Job{
	@Autowired
	private GroupJobRepository groupJobDao;
	@Autowired
	private ConponsService conponsService;
	@Autowired
	private GroupDetailRepository groupDetailDao;
	@Autowired
	private GroupUserRepository groupUserDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private GroupBuyRepository groupBuyDao;
	@Autowired
	private GoodsRepository goodsDao;
	
	private static final Logger logger = LoggerFactory.getLogger(GroupEndJob.class);
	@Override
	public void execute(JobExecutionContext context) {
		String groupDetailId = context.getJobDetail().getJobDataMap().getString("groupDetailId");
		GroupJob groupJob = groupJobDao.findByGroupDetailId(groupDetailId);
		List<GroupUser> users = groupUserDao.findByGroupDetailIds(groupDetailId);
		String phones = null;
		for (int i=0;i<users.size();i++) {
			String userId = users.get(i).getUserId();
			User user = userDao.findOne(userId);
			if(i==0){
				phones = user.getPhone();				
			}else{				
				phones = phones+","+user.getPhone();
			}
		}
		logger.info("组团结束【{}】",groupDetailId);
		try {
			boolean isSuccess = conponsService.groupIsSuccess(groupDetailId);
			logger.info("组团是否成功【{}】",isSuccess);
			//成功
			if(isSuccess){
				//修改定时任务为已结束
				groupJob.setJobStatus(1);
				groupJobDao.save(groupJob);
				logger.info("团购定时任务结束！");
				logger.info("短信发送开始");
				GroupDetail groupDetail = groupDetailDao.findOne(groupDetailId);
				MessageUtils.sendText(phones, "{text}", "您的团购已成团！");
				String groupId = groupDetail.getGroupId();
				GroupBuy groupBuy = groupBuyDao.findOne(groupId);
				Goods goods = goodsDao.findOne(groupBuy.getGoodsId());
				User user = userDao.findOne(goods.getOwnerId());
				MessageUtils.sendText(user.getPhone(), "{text}", "您的团购有成团订单待发货！");				
				logger.info("短信发送结束");
			}
			//失败
			if(!isSuccess){
				//订单团购中的状态修改为团购失败
				conponsService.changeOrderGroupStatus(groupDetailId, "未成团");
				//修改定时任务为已结束
				groupJob.setJobStatus(1);
				groupJobDao.save(groupJob);
				logger.info("团购定时任务结束！");
				//退款处理
				conponsService.groupRefund(groupDetailId);
				logger.info("未成团订单退款完成！");
				//编辑团购信息为未成团
				GroupDetail groupDetail = groupDetailDao.findOne(groupDetailId);
				groupDetail.setIsGroupSuccess(0);
				groupDetailDao.save(groupDetail);
				logger.info("本团购未成团！");
				MessageUtils.sendText(phones, "{text}", "您的团购未成团，退款将在2小时内到账！");
			}
		} catch (Exception e) {
			logger.info("组团处理异常！");
			e.printStackTrace();
		}
	}

}
