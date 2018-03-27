package com.visionet.syh_mall.service.scheduler;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;  
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.visionet.syh_mall.entity.goods.AuctionJob;
import com.visionet.syh_mall.entity.goods.GroupJob;

@Component
public class QuartzManager {

	private static final Logger logger = LoggerFactory.getLogger(QuartzManager.class);
	
	@Autowired
	private Scheduler scheduler;

	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory(); 
	 
	/**
	 * 创建一个定时任务
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @param jobClass
	 * @param cron
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addJob(AuctionJob auctionJob, Map<String, String> paramMap) {
		try {
			Class jobClass = Class.forName(auctionJob.getJobClass());
			// 任务名，任务组，任务执行类
			JobDetail jobDetail = JobBuilder.newJob(jobClass)
					.withIdentity(auctionJob.getJobName(), auctionJob.getJobGroupName()).build();
			if (null != paramMap) {
				jobDetail.getJobDataMap().putAll(paramMap);
			}
			// 触发器
			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
			triggerBuilder.withIdentity(auctionJob.getTriggerName(), auctionJob.getTriggerGroupName());
			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(auctionJob.getCronExpression()));
			triggerBuilder.startNow();
			CronTrigger trigger = (CronTrigger) triggerBuilder.build();
			scheduler.scheduleJob(jobDetail, trigger);
			// scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 创建一个团购定时任务 @param @return void @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addJob(GroupJob groupJob, Map<String, String> paramMap) {
		try {
			Class jobClass = Class.forName(groupJob.getJobClass());
			// 任务名，任务组，任务执行类
			JobDetail jobDetail = JobBuilder.newJob(jobClass)
					.withIdentity(groupJob.getJobName(), groupJob.getJobGroupName()).build();
			if (null != paramMap) {
				jobDetail.getJobDataMap().putAll(paramMap);
			}
			// 触发器
			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
			triggerBuilder.withIdentity(groupJob.getTriggerName(), groupJob.getTriggerGroupName());
			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(groupJob.getCronExpression()));
			triggerBuilder.startNow();
			CronTrigger trigger = (CronTrigger) triggerBuilder.build();
			scheduler.scheduleJob(jobDetail, trigger);
			// scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 移除一个任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 */
	public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
			if (null != triggerKey) {
				scheduler.pauseTrigger(triggerKey);// 停止触发器
				scheduler.unscheduleJob(triggerKey);// 移除触发器
				scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改一个定时任务的执行时间
	 * @param 
	 * @return void
	 * @throws
	 */
	public static void modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String cron) {    
        try {    
        	logger.info("=====================修改定时任务执行开始==================");
            Scheduler scheduler = schedulerFactory.getScheduler();    
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);  
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);    
            if (trigger == null) {    
                return;    
            }    
            String oldTime = trigger.getCronExpression();    
            if (!oldTime.equalsIgnoreCase(cron)) {   
                // 触发器    
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();  
                // 触发器名,触发器组    
                triggerBuilder.withIdentity(triggerName, triggerGroupName);  
                triggerBuilder.startNow();  
                // 触发器时间设定    
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));  
                // 创建Trigger对象  
                trigger = (CronTrigger) triggerBuilder.build();  
                // 方式一 ：修改一个任务的触发时间  
                scheduler.rescheduleJob(triggerKey, trigger);  
            }    
            logger.info("=====================修改定时任务执行结束==================");
        } catch (Exception e) {    
            throw new RuntimeException(e);    
        }    
    }    
}
