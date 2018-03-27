package com.visionet.syh_mall.repository.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.order.OrderCanc;

/**
 * OrderCanRepository接口实现
 * @author xiaofb
 * @time 2017年9月7日
 */
@Repository
public class OrderCanRepositoryImpl {
	
	@Autowired
	private EntityManager em;
	
	/**
	 * 搜索订单退货列表
	 * @param param
	 * @return
	 */
	public List<OrderCanc> getOrderCancList(Map<String, Object> param,PageInfo pageInfo){
		String FromTable = "select * from tbl_order_canc toc,tbl_order tbo,tbl_goods tg,tbl_user tu,tbl_shop ts "
				+ "where toc.order_id = tbo.id and toc.goods_id = tg.id and toc.buyer_id = tu.id and toc.seller_id = ts.id ";
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder querySql= new StringBuilder(FromTable);
		String buyerPhone =String.valueOf(param.get("goodsName")	);
		String loginName =String.valueOf(param.get("buyerName"));
		String shopName =String.valueOf(param.get("shopName"));
		String orderSN =String.valueOf(param.get("orderSN"));
		String cancStatusCode =String.valueOf(param.get("cancStatusCode"));
		String startTime =String.valueOf(param.get("startTime"));
		String endTime =String.valueOf(param.get("endTime"));
		if(StringUtils.isEmpty(buyerPhone) && StringUtils.isEmpty(loginName)){	//买家手机号和登录名
			querySql.append(" and (toc.buyer_phone like :buyerPhone or tu.login_name like :loginName)");
			map.put("buyerPhone", "%"+buyerPhone+"%");
			map.put("loginName", "%"+loginName+"%");
		}else if(StringUtils.isEmpty(buyerPhone)){	//买家手机号
			querySql.append(" and toc.buyer_phone like :buyerPhone");
			map.put("buyerPhone", "%"+buyerPhone+"%");
		}else if(StringUtils.isEmpty(loginName)){	//买家登录名
			querySql.append(" and tu.login_name like :loginName");
			map.put("loginName", "%"+loginName+"%");
		}
		if(StringUtils.isEmpty(shopName)){	//店铺名称
			querySql.append(" and ts.shop_name like :shopName");
			map.put("shopName", "%"+shopName+"%");
		}
		if(StringUtils.isEmpty(orderSN)){	//订单编号
			querySql.append(" and tbo.order_sn like :orderSN");
			map.put("orderSN", "%"+orderSN+"%");
		}
		if(StringUtils.isEmpty(cancStatusCode)){	//退货申请状态编码
			querySql.append(" and tbo.canc_status_code = :cancStatusCode");
			map.put("orderSN", cancStatusCode);
		}
		if(StringUtils.isEmpty(startTime)){	//创建开始时间
			querySql.append(" and tbo.create_time >= :startTime");
			map.put("startTime", startTime);
		}
		if(StringUtils.isEmpty(endTime)){	//创建结束时间
			querySql.append(" and tbo.create_time <= :endTime");
			map.put("orderSN", endTime);
		}
		String sql = querySql.toString();
		Query query = em.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(pageInfo.getPageIndex());
		query.setMaxResults(pageInfo.getItemCount());
		@SuppressWarnings({ "unused", "unchecked" })
		List<Object> rows = query.getResultList();
		return null;
	}
	
}
