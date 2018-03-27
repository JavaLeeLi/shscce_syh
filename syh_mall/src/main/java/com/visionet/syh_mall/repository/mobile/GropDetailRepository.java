package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.marketing.GroupDetail;
import com.visionet.syh_mall.repository.BaseRepository;

public interface GropDetailRepository extends BaseRepository<GroupDetail, String>{
		
	
	@Query("SELECT g FROM GroupDetail g WHERE g.groupId=?1 AND g.isDeleted=0")
	List<GroupDetail> findByGroupIds(String groupId);

}
