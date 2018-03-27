package com.visionet.syh_mall.repository.mobile;


import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * 商品鉴评模块Dao层接口
 * 
 * @author mulongfei
 * @date 2017年8月25日上午10:14:42
 */
public interface GoodsEvaluationRepository extends BaseRepository<Goods, String> {

	@Query("FROM Goods g WHERE g.id=?1 AND isDeleted = 0")
	Goods findById(String id);
}
