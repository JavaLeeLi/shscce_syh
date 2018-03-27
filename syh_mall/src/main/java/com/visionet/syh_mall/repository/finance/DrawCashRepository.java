package com.visionet.syh_mall.repository.finance;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.account.DrawCash;
import com.visionet.syh_mall.repository.BaseRepository;

public interface DrawCashRepository extends BaseRepository<DrawCash, String> {
	// 通过订单号获取提现记录
	public DrawCash findByDrawOrderNo(String drawOrderNo);

	@Query(value = "SELECT * FROM tbl_draw_cash d WHERE d.user_id=?1 AND (d.status_code='withdrawal_accepted' OR d.status_code='withdrawal_review') AND d.update_time>=?2 AND d.update_time<=?3 ", nativeQuery = true)
	public List<DrawCash> findByUserIdAndStatusCode(String userId, Date startTime, Date endTime);
}
