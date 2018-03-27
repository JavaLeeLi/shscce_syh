package com.visionet.syh_mall.repository.mobile;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @Author DM
 * @version ：2017年8月16日下午3:55:09 实体类
 */
public interface ShopRepository extends BaseRepository<Shop, String> {
	@Query("from Shop")
	Page<Shop> findShopList(Pageable page);
	//通过用户ID查询店铺
	@Query("FROM Shop s WHERE s.userId = ?1 AND s.isDeleted=0")
	Shop findIdByUserId(String UserId);
	//查询官方店铺ID                 
	@Query(" FROM Shop s WHERE s.shopIsOfficial=1 AND s.isDeleted=0")
	Shop findShopIsOfficial();
	@Query(value="SELECT s.id FROM Shop s WHERE s.shopName LIKE ?1 AND s.isDeleted=0")
	List<String> getShopIds(String ShopName);
	
	@Query("select s.id from Shop s where s.shopName like %?1%")
	public List<String> findByShopName(String shopName);
	
	@Query("SELECT s.userId FROM Shop s WHERE s.shopName like %?1% AND s.isDeleted=0")
	List<String> findByName(String name);
	
	@Query("FROM Shop s WHERE s.id = ?1 AND s.isDeleted=0")
	Shop findById(String Id);
	@Query("FROM Shop s WHERE s.userId = ?1 AND s.isDeleted=0")
	Shop findByUserId(String Id);

	@Query("FROM Shop s where s.shopName=?1")
	List<Shop> findoneByShopName(String ShopName);
}
