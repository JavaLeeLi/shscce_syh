package com.visionet.syh_mall.service.scheduler;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.service.mobile.channel.ChannelService;
import com.visionet.syh_mall.web.mobile.channel.ChannelController;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
@ComponentScan("com.visionet.syh_mall.service")
@EnableScheduling // 通过@EnableScheduling注解开启对计划任务的支持
public class DynamicScheduledTask implements SchedulingConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
	private static final String DEFAULT_CRON = "0 00 00 01 * ?";
	private String cron = DEFAULT_CRON;

	@Autowired
	private ChannelService channelService;
	@Autowired
	private KeyMappingRepository mappingDao;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				final KeyMapping closedCircle = mappingDao.findByKeyCode("closed_circle");
				logger.info("计算佣金开始");
				System.out.println("在指定时间 " + new Date() + " 执行");
				String valueDesc = closedCircle.getValueDesc();
				String ddRemark = closedCircle.getDdRemark();
				Date startTime = null;
				Date endTime = null;
				if ("week".equals(ddRemark)) {
					System.out.println(ddRemark);
					Calendar startCalendar = Calendar.getInstance();
					startCalendar.add(Calendar.DATE, -Integer.valueOf(valueDesc) * 7);
					startCalendar.set(Calendar.HOUR_OF_DAY, 0);
					startCalendar.set(Calendar.SECOND, 0);
					startCalendar.set(Calendar.MINUTE, 0);
					startCalendar.set(Calendar.MILLISECOND, 0);
					startTime = startCalendar.getTime();
					
					Calendar endCalendar = Calendar.getInstance();
					endTime = endCalendar.getTime();

				}
				if ("month".equals(ddRemark)) {
					Calendar startCalendar = Calendar.getInstance();
					startCalendar.add(Calendar.MONTH, -Integer.valueOf(valueDesc));
					startCalendar.set(Calendar.DAY_OF_MONTH, 1);
					startCalendar.set(Calendar.HOUR_OF_DAY, 0);
					startCalendar.set(Calendar.SECOND, 0);
					startCalendar.set(Calendar.MINUTE, 0);
					startCalendar.set(Calendar.MILLISECOND, 0);
					startTime = startCalendar.getTime();

					Calendar endCalendar = Calendar.getInstance();
					endCalendar.add(Calendar.MONTH, -1);
					endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
					endCalendar.set(Calendar.HOUR_OF_DAY, 0);
					endCalendar.set(Calendar.SECOND, 0);
					endCalendar.set(Calendar.MINUTE, 0);
					endCalendar.set(Calendar.MILLISECOND, 0);
					endTime = endCalendar.getTime();
				}
				// 定时任务的业务逻辑
				channelService.getCommissionUser(startTime.getTime(), endTime.getTime());
				logger.info("计算佣金结束");
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				// 定时任务触发，可修改定时任务的执行周期
				CronTrigger trigger = new CronTrigger(cron);
				Date nextExecDate = trigger.nextExecutionTime(triggerContext);
				return nextExecDate;
			}
		});
	}
	
	public static void main(String[] args) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.MONTH, -Integer.valueOf(1));
		startCalendar.set(Calendar.DAY_OF_MONTH, 1);
		startCalendar.set(Calendar.HOUR_OF_DAY, 0);
		startCalendar.set(Calendar.SECOND, 0);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.MILLISECOND, 0);
		System.out.println(startCalendar.getTime());

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.MONTH, -1);
		endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		endCalendar.set(Calendar.HOUR_OF_DAY, 0);
		endCalendar.set(Calendar.SECOND, 0);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.MILLISECOND, 0);
		System.out.println(endCalendar.getTime());
		
	}
	
	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getCron() {
		return cron;
	}

}