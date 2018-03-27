package com.visionet.syh_mall.repository.mobile;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.entity.marketing.GroupDetail;
import com.visionet.syh_mall.repository.BaseRepository;

public interface GroupDetailRepository extends BaseRepository<GroupDetail, String>{
	
	@Query(value="SELECT count(1) FROM GroupDetail g WHERE g.groupId=?1 AND g.isDeleted=0")
	int findGroupNumberByGroupId(String GroupId);
	
	@Query(value="SELECT count(1) FROM GroupDetail g WHERE g.groupId=?1 AND g.isGroupSuccess=1 AND g.isDeleted=0")
	int findSuccessGroupByGroupId(String GroupId);

	@Query(value="SELECT t3.goods_num FROM tbl_group_detail t1, tbl_group_user t2, tbl_order_goods t3 WHERE t1.group_id = ?1 AND t1.id = t2.group_detail_id AND t2.order_id = t3.order_id AND t2.user_id = ?2",nativeQuery=true)
	List<Integer> findNum(String groupBuyId, String userId);
}
