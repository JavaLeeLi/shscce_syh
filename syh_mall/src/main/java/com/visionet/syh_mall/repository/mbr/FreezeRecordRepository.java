package com.visionet.syh_mall.repository.mbr;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.account.FreezeRecord;
import com.visionet.syh_mall.repository.BaseRepository;

public interface FreezeRecordRepository extends BaseRepository<FreezeRecord, String> {
	
	@Query("from FreezeRecord where bizId = ?1 and userId = ?2 and  freezeStatus = ?3")
	public FreezeRecord findByBizIdAndUserId(String bizId,String userId,String status);
}
