package com.visionet.syh_mall.repository.mobile.channel;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.UserHierarchy;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * 
 * 用户层级关系表实体类
 * 
 * @author admin
 * @date 2017年8月24日下午4:14:31
 */
public interface UserHierarchyRepository extends BaseRepository<UserHierarchy, String> {
	@Query(value = "SELECT t.userId FROM UserHierarchy t WHERE t.parentUserId=?1 AND t.isDeleted=0")
	List<String> getSon(String Id);

	@Query(value = "SELECT t.parentUserId FROM UserHierarchy t WHERE t.userId=?1")
	public String getFather(String Id);

	@Query(value = "FROM UserHierarchy t WHERE t.userId=?1")
	public UserHierarchy findFather(String Id);
	
	@Modifying
	@Query(value = "UPDATE FROM UserHierarchy u SET u.parentUserId=?1 WHERE u.userId=?2")
	int upLevel(String newFatherId, String Id);
}
