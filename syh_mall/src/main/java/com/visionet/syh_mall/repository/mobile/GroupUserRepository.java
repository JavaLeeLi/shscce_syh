package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.marketing.GroupUser;
import com.visionet.syh_mall.repository.BaseRepository;

public interface GroupUserRepository extends BaseRepository<GroupUser, String> {

	@Query("SELECT g FROM GroupUser g WHERE g.groupDetailId=?1 AND g.isDeleted=0 ORDER BY g.createTime ASC")
	List<GroupUser> findByGroupDetailIds(String groupDetailId);
	
	@Query("SELECT COUNT(1) AS num FROM GroupUser g  WHERE  g.groupDetailId=?1 AND g.isDeleted=0")
	Integer fingAllUser(String groupDetailId);

	GroupUser findByUserIdAndGroupDetailId(String userId,String groupDetailId);
	
}
