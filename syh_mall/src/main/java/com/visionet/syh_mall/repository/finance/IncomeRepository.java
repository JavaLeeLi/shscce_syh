package com.visionet.syh_mall.repository.finance;

import java.util.List;
import java.util.Map;

public interface IncomeRepository {
	
	public List<Object[]> searchIncome(Map<String,Object> param);
	
}
