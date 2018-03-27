package com.visionet.syh_mall.repository.mbr;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * 用户流水Dao层
 * 
 * @author mulongfei
 * @date 2017年8月29日下午4:28:18
 */
public interface UserAccountFlowRepository extends BaseRepository<UserAccountFlow, String> {

	public Page<UserAccountFlow> findByUserIdAndStatus(String userId, String status, Pageable page);

	public UserAccountFlow findByUserIdAndOrderNo(String userId, String orderNo);
	
	public UserAccountFlow findByUserIdAndOrderNoAndContent(String userId, String orderNo,String content);

	@Query(value = "SELECT * FROM tbl_user_account_flow f WHERE f.type=0 AND f.status='success' AND f.user_id=?1", nativeQuery = true)
	public List<UserAccountFlow> findInUserId(String userId);

	@Query(value = "SELECT * FROM tbl_user_account_flow f WHERE f.type=0 AND f.status='success' AND f.content='余额充值' AND f.user_id=?1", nativeQuery = true)
	public List<UserAccountFlow> findInUserIdAndContent(String userId);

	@Query(value = "SELECT * FROM tbl_user_account_flow f WHERE f.type=1 AND f.status='success' AND f.user_id=?1", nativeQuery = true)
	public List<UserAccountFlow> findOutUserId(String userId);

	@Query(value = "SELECT * FROM tbl_user_account_flow f WHERE f.type=0 AND f.content='店铺交易入账' AND f.user_id=?1 AND f.create_time >= ?2  AND f.create_time <= ?3", nativeQuery = true)
	public List<UserAccountFlow> findByUserId(String userId, Date startTime, Date endTime);
	
	public UserAccountFlow findByOrderNo(String orderNo);

	@Query(value="SELECT u.login_name,a.user_real_name,f.order_no,f.business_type,f.flow_type,f.type,f.amt,f.content,f.create_time,f.remark,f.pay_method,u.alias_name FROM tbl_user u LEFT JOIN tbl_user_authentication a ON u.id = a.user_id LEFT JOIN tbl_user_account_flow f ON u.id = f.user_id WHERE f.status = 'success' ORDER BY f.create_time DESC",nativeQuery=true)
	public List<Object[]> getExportFinanceList();
}
