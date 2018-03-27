package com.visionet.syh_mall.repository.marketing;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.marketing.Coupon;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @Author DM
 * @version ：2017年9月11日上午11:23:46 优惠券类
 */
public interface CouponRepository extends BaseRepository<Coupon, String> {
	@Query(value = "FROM Coupon c WHERE c.shopId=?1 AND ?2 BETWEEN c.effectiveTime AND c.expirationTime AND c.isDeleted=0")
	List<Coupon> findAll(String shopID, Date Date);

	@Query("FROM Coupon c WHERE c.id=?1 AND c.isDeleted=0")
	Coupon findById(String Id);

	@Query(value="SELECT COUNT(1) FROM Coupon c WHERE c.shopId = ?1 AND c.isAvailable = 1 AND c.isDeleted = 0 AND c.issueNum > c.claimNum AND ?2 BETWEEN c.effectiveTime AND c.expirationTime")
	int hasCoupon(String shopId, Date currentDate);

}
