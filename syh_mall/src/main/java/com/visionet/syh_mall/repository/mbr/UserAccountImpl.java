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
public class UserAccountImpl implements UserAccountDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	private final static String HQL = "SELECT u.loginName,u.aliasName,ua.balance,ua.withdrawal,ua.frozenAmt,u.memberType,u.userStatusCode FROM UserAccount ua,User u WHERE u.id = ua.userId AND u.isDeleted = 0";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getUserAccount(Map<String, String> param) {
		Map<String,Object> hqlMap = new HashMap<String,Object>();
		StringBuilder hqlQuery = new StringBuilder(HQL);
		if (!StringUtils.isEmpty(param.get("userLoginName"))) {
			hqlQuery.append(" AND u.loginName LIKE :userLoginName");
			hqlMap.put("userLoginName", "%"+param.get("userLoginName")+"%");
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
