package com.visionet.syh_mall.repository.finance;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.finance.StatementOrder;
import com.visionet.syh_mall.repository.BaseRepository;

public interface StatementOrderRepository extends BaseRepository<StatementOrder, String> {
	
	@Query("from StatementOrder s where s.allinpayTradeDate >= ?1 and  s.allinpayTradeDate <= ?2")
	List<StatementOrder> findByDate(Date startDate,Date endDate);
	
	@Query("from StatementOrder s where s.allinpayTradeDate >= ?1 and  s.allinpayTradeDate <= ?2 order by comporeResult desc")
	Page<StatementOrder> findByDateOrderByComporeResult(Date startDate,Date endDate,Pageable page);
}
