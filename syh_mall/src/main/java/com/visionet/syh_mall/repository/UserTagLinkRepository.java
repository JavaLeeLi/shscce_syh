package com.visionet.syh_mall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.visionet.syh_mall.entity.UserTagLink;

public interface UserTagLinkRepository extends BaseRepository<UserTagLink, String>{

	//查询用该标签名字的用户ID
	@Query(value="SELECT a.userId FROM UserTagLink a WHERE a.tagId=?1 AND a.isDeleted=0")
	List<String> findUserIdByTagId(String tagId);
	//删除原有标签状态
	@Query(value="SELECT a FROM UserTagLink a WHERE a.userId=?1 AND a.tagId=?2 AND a.isDeleted=0")
	UserTagLink delete(String mbrID,String tagID);
	
	@Transactional
	public int deleteByUserId(String userId);
	
	@Query(value="SELECT u.tagId FROM UserTagLink u WHERE u.userId=?1 AND u.isDeleted=0")
	List<String> findAllByUserId(String userId);
}
