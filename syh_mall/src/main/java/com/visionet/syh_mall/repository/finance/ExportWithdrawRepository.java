package com.visionet.syh_mall.repository.finance;

import java.util.List;
import java.util.Map;

public interface ExportWithdrawRepository {
	public List<Object[]> getDrawCashList(Map<String,Object> param);
}
