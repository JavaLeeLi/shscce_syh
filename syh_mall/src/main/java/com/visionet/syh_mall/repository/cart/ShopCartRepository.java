package com.visionet.syh_mall.repository.cart;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.cart.ShopCart;
import com.visionet.syh_mall.repository.BaseRepository;
/**
 * 购物车Dao层
 * @author mulongfei
 * @date 2017年8月31日上午10:16:50
 */
public interface ShopCartRepository extends BaseRepository<ShopCart, String> {
	@Query(value="FROM ShopCart s WHERE s.userId=?1 AND s.goodsId=?2 AND s.isDeleted=0")
	List<ShopCart> findOne(String UserId,String GoodId);
	
	@Query(value="FROM ShopCart s WHERE s.userId=?1 AND s.isDeleted=0")
	List<ShopCart> findAll(String UserId);
	
	@Query(value="FROM ShopCart s WHERE s.userId=?1 AND s.goodsId=?2 AND s.isDeleted=0")
	ShopCart findByUserIdAndGoodId(String userId,String goodId);
	
	@Query(value="SELECT COUNT(1) FROM ShopCart s WHERE s.userId=?1 AND s.isDeleted=0")
	int findByUserId(String userId);
}
