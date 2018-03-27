package com.visionet.syh_mall.repository.mobile;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;

import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.order.OrderCanc;
import com.visionet.syh_mall.repository.BaseRepository;

/**
 * @ClassName: OrderCanRepository
 * @Description: 订单退货
 * @author chenghongzhan
 * @date 2017年9月13日 下午12:46:06
 *
 */
public interface OrderCanRepository extends BaseRepository<OrderCanc, String> {

	public List<OrderCanc> getOrderCancList(Map<String, Object> param, PageInfo pageInfo);
	
	
	//通过订单编号查询退货订单
	@Query("SELECT o FROM OrderCanc o WHERE o.orderId=?1 AND o.isDeleted=0")
	OrderCanc findByorderId(String orderId);
	
	@Query("SELECT o.orderId FROM OrderCanc o WHERE o.goodsId=?1 AND o.isDeleted=0")
	List<String> findBygoodsId(String goodsIds);
	
}
