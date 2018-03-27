package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.goods.ExpressWay;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @ClassName: ExpressWayRepository
 * @Description: 运费方式
 * @author chenghongzhan
 * @date 2017年9月13日 上午11:26:41
 *
 */
public interface ExpressWayRepository extends BaseRepository<ExpressWay, String> {

	ExpressWay findById(String id);

	@Query("SELECT ew FROM ExpressWay ew WHERE ew.templetId=?1 AND ew.isDeleted=0")
	List<ExpressWay> findBytempletId(String templetId);
	
	@Query(" FROM ExpressWay ew WHERE ew.templetId=?1 AND ew.province=?2 AND ew.isDeleted=0")
	ExpressWay findBytempletIdAndProvince(String templetId,String Province);
	
}
 