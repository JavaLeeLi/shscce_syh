package com.visionet.syh_mall.repository.mbr;

import java.util.List;
import java.util.Map;

public interface UserAccountDao {
	public List<Object[]> getUserAccount(Map<String,String> param);
}
