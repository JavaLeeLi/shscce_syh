package com.visionet.syh_mall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.channel.CommissionFlow;

public interface CommissionFlowRepository extends BaseRepository<CommissionFlow, String>{
	
	@Query(value="SELECT DISTINCT rd.retail_user_id FROM	tbl_retail_detail rd",nativeQuery=true)
	List<String> getUsers();
	
	
}
