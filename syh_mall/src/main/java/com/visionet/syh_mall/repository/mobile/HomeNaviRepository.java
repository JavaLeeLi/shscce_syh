package com.visionet.syh_mall.repository.mobile;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.visionet.syh_mall.entity.HomeNavi;

/**
 *@Author DM
 *@version ：2017年8月21日下午2:05:33
 *实体类
 */
public interface HomeNaviRepository extends PagingAndSortingRepository<HomeNavi, String>, JpaSpecificationExecutor<HomeNavi>  {
	
	
	
}
