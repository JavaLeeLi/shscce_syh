package com.visionet.syh_mall.repository.finance;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.DateUtil;

@Repository
public class IncomeImpl implements IncomeRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	private final static String HQL = "SELECT us.loginName,us.aliasName,u.createTime,u.orderNo,u.amt,u.businessType,u.content FROM User us,UserAccountFlow u WHERE us.id = u.userId AND u.type=1 AND u.content IN ('平台手续费','营销服务') AND u.status = 'success'";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> searchIncome(Map<String, Object> param) {
		Map<String,Object> hqlMap = new HashMap<String,Object>();
		StringBuilder hqlQuery = new StringBuilder(HQL);
		if (!StringUtils.isEmpty(param.get("userLoginName"))) {
			hqlQuery.append(" AND us.loginName LIKE :userLoginName");
			hqlMap.put("userLoginName", "%"+param.get("userLoginName")+"%");
		}
		if (!StringUtils.isEmpty(param.get("userName"))) {
			hqlQuery.append(" AND us.aliasName LIKE :userName");
			hqlMap.put("userName", "%"+param.get("userName")+"%");
		}
		if (!StringUtils.isEmpty(param.get("businessType"))) {
			hqlQuery.append(" AND u.businessType = :businessType");
			hqlMap.put("businessType", param.get("businessType"));
		}
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			hqlQuery.append(" AND u.createTime >= :startTime");
			hqlMap.put("startTime", new Date((long) param.get("startTime")));
		}
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			hqlQuery.append(" AND u.createTime <= :endTime");
			hqlMap.put("endTime", DateUtil.seekDate(new Date((long) param.get("endTime")),1));
		}
		hqlQuery.append(" ORDER BY u.createTime DESC");
		String lastHql = hqlQuery.toString();
		EntityManager manager = null;
		List<Object[]> resultList = null;
		try {
			manager = entityManager.getEntityManagerFactory().createEntityManager();
			Query createQuery = manager.createQuery(lastHql);
			Set<Entry<String,Object>> entrySet = hqlMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				createQuery.setParameter(entry.getKey(), entry.getValue());
			}
			resultList = createQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager!=null){
				EntityManagerFactoryUtils.closeEntityManager(manager);
			}
		}
		return resultList;
	}

}
