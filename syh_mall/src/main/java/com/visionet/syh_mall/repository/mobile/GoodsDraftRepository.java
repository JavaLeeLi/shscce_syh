package com.visionet.syh_mall.repository.mobile;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.goods.GoodsDraft;
import com.visionet.syh_mall.repository.BaseRepository;
/**
 * 商品草稿Dao层
 * @author mulongfei
 * @date 2017年9月5日下午2:48:43
 */
public interface GoodsDraftRepository extends BaseRepository<GoodsDraft, String> {
	@Query(value="FROM GoodsDraft g WHERE g.id=?1 AND g.isDeleted=0")
	GoodsDraft findOne(String goodsDraftId);
}
