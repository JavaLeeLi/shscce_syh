package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.goods.ExpressTemplet;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @Author DM
 * @version ：2017年9月5日下午4:44:07 实体类
 */
public interface ExpressTempletRepository extends BaseRepository<ExpressTemplet, String> {

	// 搜索店铺的运送模块
	@Query("SELECT et FROM ExpressTemplet et WHERE et.shopId=?1 AND et.isDeleted=0")
	List<ExpressTemplet> findByshopId(String shopId);

	@Query("SELECT et FROM ExpressTemplet et WHERE et.shopId=?1 AND et.isDeleted=0 AND et.id=?2")
	List<ExpressTemplet> findByIdAndShopId(String shopId, String templetId);
	
	@Query("SELECT et FROM ExpressTemplet et WHERE et.isDeleted=0 AND et.id=?1")
	ExpressTemplet findById(String id);

}
