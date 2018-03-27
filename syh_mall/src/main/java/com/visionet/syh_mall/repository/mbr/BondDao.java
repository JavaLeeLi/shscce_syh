package com.visionet.syh_mall.repository.mbr;

import java.util.List;
import java.util.Map;

public interface BondDao {
	public List<Object[]> searchBond(Map<String,Object> param);
}
