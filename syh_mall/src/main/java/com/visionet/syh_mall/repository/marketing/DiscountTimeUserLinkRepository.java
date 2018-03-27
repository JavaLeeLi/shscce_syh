package com.visionet.syh_mall.repository.marketing;

import com.visionet.syh_mall.entity.marketing.DiscountTimeUserLink;
import com.visionet.syh_mall.repository.BaseRepository;

public interface DiscountTimeUserLinkRepository extends BaseRepository<DiscountTimeUserLink, String> {
	
	DiscountTimeUserLink findByDiscountTimeIdAndUserId(String DiscountTimeId,String UserId);
}
