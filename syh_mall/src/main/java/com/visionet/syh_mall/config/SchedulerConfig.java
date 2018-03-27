package com.visionet.syh_mall.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.visionet.syh_mall.service.scheduler.QuartzJobFactory;

/**
 * spring管理quartz配置
 * @author xiaofb
 * @time 2017年10月26日
 */
@Configuration
public class SchedulerConfig {

	@Autowired
	private QuartzJobFactory jobFactory;


	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setJobFactory(jobFactory);
		schedulerFactoryBean.setAutoStartup(true);
		schedulerFactoryBean.setQuartzProperties(quartzProperties());
		return schedulerFactoryBean;
	}
	
	@Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/schedule/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

	@Bean
	public Scheduler scheduler() throws IOException {
		return schedulerFactoryBean().getScheduler();
	}
}
