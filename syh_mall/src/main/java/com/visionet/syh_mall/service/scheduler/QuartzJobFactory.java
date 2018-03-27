package com.visionet.syh_mall.service.scheduler;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * spring 对  quartz job的依赖注入
 * @author xiaofb
 * @time 2017年10月26日
 */
@Component
public class QuartzJobFactory extends AdaptableJobFactory {
	@Autowired
    private AutowireCapableBeanFactory capableBeanFactory;
	
	@Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        // 进行注入
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
