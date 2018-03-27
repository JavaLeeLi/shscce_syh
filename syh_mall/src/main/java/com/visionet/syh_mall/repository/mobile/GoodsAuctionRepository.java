package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.goods.BidRecord;
import com.visionet.syh_mall.repository.BaseRepository;
/**
 * 竞价记录
 * @author mulongfei
 * @date 2017年8月31日下午2:42:44
 */
public interface GoodsAuctionRepository extends BaseRepository<BidRecord, String>{
	@Query(value="FROM BidRecord b WHERE b.goodsId=?1 order by b.lastBidPrice Desc")
	List<BidRecord> findAll(String GoodId);
	@Query(value="FROM BidRecord b WHERE b.goodsId=?1 AND b.isBidWinner=1 AND b.isDeleted=0")
	BidRecord findByGoodsId(String goodsId);
	//根据商品id获取未退款记录
	@Query(value="FROM BidRecord b WHERE b.goodsId=?1 AND b.hasRefunded=?2 AND b.isDeleted=0")
	public List<BidRecord> findByHasRefundedAndGoodsId(String goodsId,int hasRefunded);
}
