package com.visionet.syh_mall.repository.finance;

import com.visionet.syh_mall.entity.finance.Bond;
import com.visionet.syh_mall.repository.BaseRepository;

public interface BondRepository extends BaseRepository<Bond, String>{
	
	Bond findByShopId(String shopId);
}
