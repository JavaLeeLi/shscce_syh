package com.visionet.syh_mall.repository.mobile.channel;

import java.util.List;
import java.util.Map;

public interface RetailDetailDao {
	public List<Object[]> getExportChannel(Map<String,Object> param);
}
