package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.goods.SearchHistory;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 *@Author DM
 *@version ：2017年9月12日下午1:55:46
 *实体类
 */
public interface SearchHistoryRepository extends BaseRepository<SearchHistory, String> {
	@Query(value = "from SearchHistory s where s.userId=?1 and s.keywords=?2 and s.isDeleted=0")
	public SearchHistory findById(String userId,String keywords);
	@Query(value = "from SearchHistory s where s.userId=?1 and s.goodsId=?2 and s.isDeleted=0")
	public SearchHistory findByGoodsId(String userId,String goodId);	
	@Query(value = "from SearchHistory s where s.userId=?1 and s.isDeleted=0 order by s.sourchCount ASC")
	public List<SearchHistory> findAll(String userId);

}
