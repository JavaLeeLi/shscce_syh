package com.visionet.syh_mall.repository.finance;

import com.visionet.syh_mall.vo.finance.FundSummaryPo;

import java.util.List;

public interface FundSummaryRepository {

	List<Object[]> searchFundSummary(FundSummaryPo vo);
}
