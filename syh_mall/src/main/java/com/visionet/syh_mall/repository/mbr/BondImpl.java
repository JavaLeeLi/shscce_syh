package com.visionet.syh_mall.repository.mbr;

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
public class BondImpl implements BondDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	private final static String HQL = "SELECT s.shopName,b.bondStatus,b.bondAmt,b.shopBalance,b.updateTime FROM Shop s,Bond b WHERE s.id = b.shopId AND s.isDeleted = 0";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> searchBond(Map<String, Object> param) {
		Map<String,Object> hqlMap = new HashMap<String,Object>();
		StringBuilder hqlQuery = new StringBuilder(HQL);
		if (!StringUtils.isEmpty(param.get("shopName"))) {
			hqlQuery.append(" AND s.shopName LIKE :shopName");
			hqlMap.put("shopName", "%"+param.get("shopName")+"%");
		}
		if (!StringUtils.isEmpty(param.get("status"))) {
			hqlQuery.append(" AND b.bondStatus = :status");
			hqlMap.put("status", param.get("status"));
		}
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
