package com.visionet.syh_mall.repository.mobile.channel;


import org.springframework.transaction.annotation.Transactional;

import com.visionet.syh_mall.entity.channel.CommissionTally;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @ClassName: CommissionTallyRepository
 * @Description: 分销佣金结算
 * @author chenghongzhan
 * @date 2017年11月13日 上午11:21:44
 *
 */
public interface CommissionTallyRepository extends BaseRepository<CommissionTally, String> {

	@Transactional
	int deleteById(String id);
	
	
}
