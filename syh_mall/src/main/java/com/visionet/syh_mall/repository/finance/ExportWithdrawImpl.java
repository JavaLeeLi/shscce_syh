package com.visionet.syh_mall.repository.finance;

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

@Repository
public class ExportWithdrawImpl implements ExportWithdrawRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private final static String HQL = "SELECT u.loginName,d.drawAmt,d.drawAccount,d.accountName,d.statusCode,d.createTime FROM DrawCash d,User u WHERE d.userId=u.id AND u.isDeleted=0";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getDrawCashList(Map<String,Object> param) {
		Map<String,Object> hqlMap = new HashMap<String,Object>();
		StringBuilder hqlQuery = new StringBuilder(HQL);
		if (!StringUtils.isEmpty(param.get("userLoginName"))) {
			hqlQuery.append(" AND u.loginName LIKE :userLoginName");
			hqlMap.put("userLoginName", "%"+param.get("userLoginName")+"%");
		}
		if (!StringUtils.isEmpty(param.get("drawCashState"))) {
			hqlQuery.append(" AND d.statusCode LIKE :drawCashState");
			hqlMap.put("drawCashState", "%"+param.get("drawCashState")+"%");
		}
		if (!StringUtils.isEmpty(param.get("cardNo"))) {
			hqlQuery.append(" AND d.drawAccount LIKE :cardNo");
			hqlMap.put("cardNo", "%"+param.get("cardNo")+"%");
		}
		if (!StringUtils.isEmpty(param.get("startTime"))) {
			hqlQuery.append(" AND d.createTime >= :startTime");
			hqlMap.put("startTime", param.get("startTime"));
		}
		if (!StringUtils.isEmpty(param.get("endTime"))) {
			hqlQuery.append(" AND d.createTime <= :endTime");
			hqlMap.put("endTime", param.get("endTime"));
		}
		hqlQuery.append(" ORDER BY d.updateTime DESC");
		String lastHql = hqlQuery.toString();
		EntityManager manager  = null;
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
