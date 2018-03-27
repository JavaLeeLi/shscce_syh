package com.visionet.syh_mall.repository.userAttention;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.userAttention.GoodsFavorite;
import com.visionet.syh_mall.repository.BaseRepository;
/**
 * 商品收藏接口Dao
 * @author mulongfei
 * @date 2017年9月4日下午2:02:07
 */
public interface GoodsFavoriteRepository extends BaseRepository<GoodsFavorite, String> {
	@Query(value="FROM GoodsFavorite g WHERE g.userId=?1 AND g.goodsId=?2 AND g.isDeleted=0")
	GoodsFavorite findByUserIdAndGoodId(String UserId,String GoodId);
	@Query(value="FROM GoodsFavorite g WHERE g.userId=?1 AND g.isDeleted=0")
	List<GoodsFavorite> findByUserId(String UserId);
	@Query(value="select count(*) as num from tbl_goods_favorite where user_id=?1 and is_deleted=0",nativeQuery=true)
	Integer findNumById(String userId);
}
