package com.visionet.syh_mall.repository.marketing;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.marketing.UserCoupon;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 *@Author DM
 *@version ：2017年9月11日下午1:57:47
 *实体类
 */
public interface UserCouponRepository extends BaseRepository<UserCoupon, String> {
	
	@Query("from UserCoupon c where c.userId=?1")
	List<UserCoupon> findByUserId(String userId);
	
	@Query(value="FROM UserCoupon u WHERE u.userId=?1 AND u.couponId=?2 AND u.isUsed=0")
	UserCoupon findOne(String userId,String couponId);

	@Query(value="FROM UserCoupon u WHERE u.userId=?1 AND u.couponId=?2")
	UserCoupon findCouponId(String userId,String couponId);
}
