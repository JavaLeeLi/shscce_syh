package com.visionet.syh_mall;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.visionet.syh_mall.repository.BaseRepositoryFactoryBean;

@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@SpringBootApplication
public class Application {
	/**
	 * 主程序"Too many authentication failures for git"
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		// 关闭横幅
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
