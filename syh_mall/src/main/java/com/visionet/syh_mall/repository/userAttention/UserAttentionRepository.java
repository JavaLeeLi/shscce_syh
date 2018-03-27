package com.visionet.syh_mall.repository.userAttention;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.userAttention.UserAttention;
import com.visionet.syh_mall.repository.BaseRepository;
/**
 * 用户关注模块Dao层
 * @author mulongfei
 * @date 2017年9月4日上午11:51:46
 */
public interface UserAttentionRepository extends BaseRepository<UserAttention, String>{
	
	@Query(value = "SELECT u.userId FROM UserAuthentication u WHERE u.userRealName like %?1%")
	List<String> finUserIdByName(String Name);
	
	@Query(value="FROM UserAttention u WHERE u.userId=?1 AND u.concernedUserId=?2 AND u.isDeleted=0")
	UserAttention findUserAttention(String userId,String attentionId);
	
	@Query(value="FROM UserAttention u WHERE u.userId=?1 AND u.isDeleted=0")
	List<UserAttention> findByUserId(String UserId);
	
	@Query(value="FROM UserAttention u WHERE u.userId=?1 AND u.concernedUserId=?2 AND u.isDeleted=0")
	UserAttention findByUserIdAndAttentionId(String UserId,String AttentionId);
	@Query(value="select count(*) as num from tbl_user_attention where user_id=?1 and is_deleted=0",nativeQuery=true)
	Integer findNumById(String userId);
}
