package com.visionet.syh_mall.repository.mobile.channel;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.channel.RetailDetail;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @ClassName: RetailDetailRepository
 * @Description: 佣金结算流水表
 * @author chenghongzhan
 * @date 2017年11月13日 上午11:24:30
 *
 */
public interface RetailDetailRepository extends BaseRepository<RetailDetail, String> {

	@Query("FROM RetailDetail r WHERE r.settlementStatus='commission_waiting' AND r.isDeleted='0' AND r.retailUserId=?1 AND r.createTime>?2 AND r.createTime<?3")
	List<RetailDetail> findBySettlementStatus(String userId, Date startTime, Date endTime);

	@Query("SELECT distinct r.retailUserId FROM RetailDetail r WHERE r.settlementStatus='untreated' AND r.isDeleted='0' AND r.createTime between ?1 AND ?2")
	List<String> findUsers(Date startTime, Date endTime);

	@Query("SELECT distinct r.retailObjId FROM RetailDetail r WHERE r.settlementStatus='untreated' AND r.isDeleted='0'")
	List<String> findALl();

	@Query("SELECT min(r.createTime),r.createTime FROM RetailDetail r WHERE r.settlementStatus='commission_waiting' AND r.isDeleted='0' AND r.retailUserId=?1")
	Date findByUser(String userId);

	@Query("FROM RetailDetail r WHERE r.settlementStatus='waitNotified' AND r.isDeleted='0' AND r.retailType=1 AND r.retailObjId=?1")
	List<RetailDetail> getByRetailObjId(String retailObjId);

	@Query("FROM RetailDetail r WHERE r.settlementStatus='waitNotified' AND r.isDeleted='0' AND r.retailType=0 AND r.retailUserId=?2 AND r.retailObjId=?1")
	RetailDetail findByRetailObjIdAndUser(String retailObjId, String userId);

	@Query("FROM RetailDetail r WHERE r.settlementStatus='waitNotified' AND r.isDeleted='0' AND r.retailType=0 AND r.retailObjId=?1")
	List<RetailDetail> findByRetailObjId(String retailObjId);
	
	@Query(value="select * from tbl_user",nativeQuery=true)
	List<Object[]> findByObjId(String retailObjId);

}
