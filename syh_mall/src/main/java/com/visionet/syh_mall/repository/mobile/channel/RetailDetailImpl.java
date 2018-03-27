package com.visionet.syh_mall.repository.mobile.channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class RetailDetailImpl implements RetailDetailDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	private final static String HQL = "SELECT u.loginName,ua.userRealName,r.buyRetailAmt,r.sellRetailAmt,o.orderSn,r.commissionRate,r.commissionAmt FROM RetailDetail r,Order o,User u,UserAuthentication ua WHERE r.retailObjId = o.id AND r.retailUserId = u.id AND u.id = ua.userId AND r.retailType = 0";

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getExportChannel(Map<String, Object> param) {
		Map<String,Object> hqlMap = new HashMap<String,Object>();
		StringBuilder hqlQuery = new StringBuilder(HQL);
		if (!StringUtils.isEmpty(param.get("LoginName"))) {
			hqlQuery.append(" AND u.loginName LIKE :userLoginName");
			hqlMap.put("userLoginName", "%"+param.get("LoginName")+"%");
		}
		if (!StringUtils.isEmpty(param.get("orderNo"))) {
			hqlQuery.append(" AND o.orderSn LIKE :orderNo");
			hqlMap.put("orderNo", "%"+param.get("orderNo")+"%");
		}
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			hqlQuery.append(" AND r.createTime >= :startTime");
			hqlMap.put("startTime", param.get("startTime"));
		}
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			hqlQuery.append(" AND r.createTime <= :endTime");
			hqlMap.put("endTime", param.get("endTime"));
		}
		hqlQuery.append(" ORDER BY r.updateTime DESC");
		String lastHql = hqlQuery.toString();
		EntityManager manager = entityManager.getEntityManagerFactory().createEntityManager();
		Query createQuery = manager.createQuery(lastHql);
		Set<Entry<String,Object>> entrySet = hqlMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {			
			createQuery.setParameter(entry.getKey(), entry.getValue());
		}
		return createQuery.getResultList();
	}

}
