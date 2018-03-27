package com.visionet.syh_mall.repository.finance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.vo.finance.FundSummaryPo;

@Repository
public class FundSummaryImpl implements FundSummaryRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	private final static String HQL = "SELECT "+
"us.login_name,us.alias_name,u.create_time,t1.amt1,t1_1.amt1_1,t1_2.amt1_2,t1_3.amt1_3,t_freeze.freeze_amt,t2.amt2,t3.amt3,t4.amt4,t5.amt5,t6.amt6,t7.amt7,t8.amt8,t9.amt9,t10.amt10,t11.amt11,t12.amt12,t13.amt13,t14.amt14,t15.amt15,t16.amt16,t17.amt17,t18.amt18,t19.amt19,us.member_type,t20.amt20 "+
"FROM tbl_user_account u LEFT JOIN tbl_user us ON u.user_id = us.id LEFT JOIN (SELECT uf.user_id AS id,SUM(uf.amt) AS amt1 FROM tbl_user_account_flow uf WHERE uf.type=0 AND uf.status='success' ";
	private final static String HQL2 = " GROUP BY user_id) t1 ON u.user_id = t1.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt1_1 FROM tbl_user_account_flow uf WHERE uf.type=1 AND uf.status='success' AND (uf.pay_method='BALANCE' OR uf.flow_type='提现') ";
	private final static String HQL3 = " GROUP BY user_id) t1_1 ON u.user_id = t1_1.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt1_2 FROM tbl_user_account_flow uf WHERE uf.type=0 AND uf.status='success' ";
	private final static String HQL4 = " GROUP BY user_id) t1_2 ON u.user_id = t1_2.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt1_3 FROM tbl_user_account_flow uf WHERE uf.type=1 AND uf.status='success' AND (uf.pay_method='BALANCE' OR uf.flow_type='提现') ";
	private final static String HQL5 = " GROUP BY user_id) t1_3 ON u.user_id = t1_3.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.freeze_amt) AS freeze_amt FROM tbl_freeze_record uf WHERE uf.freeze_status='freeze' ";
	private final static String HQL6 = " GROUP BY uf.user_id) t_freeze ON u.user_id = t_freeze.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt2 FROM tbl_user_account_flow uf WHERE uf.flow_type='提现' AND uf.status='success' ";
	private final static String HQL7 = " GROUP BY user_id) t2 ON u.user_id = t2.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt3 FROM tbl_user_account_flow uf WHERE uf.business_type='交易' AND uf.flow_type='收入' AND uf.content='店铺交易入账' AND uf.status='success' ";
	private final static String HQL8 = " GROUP BY user_id) t3 ON u.user_id = t3.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt4 FROM tbl_user_account_flow uf WHERE uf.business_type='交易' AND uf.flow_type='支出' AND uf.pay_method='BALANCE' AND uf.content='商品支付交易' AND uf.status='success' ";
	private final static String HQL9 = " GROUP BY user_id) t4 ON u.user_id = t4.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt5 FROM tbl_user_account_flow uf WHERE uf.business_type='竞拍' AND uf.flow_type='收入' AND uf.status='success' ";
	private final static String HQL10 = " GROUP BY user_id) t5 ON u.user_id = t5.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt6 FROM tbl_user_account_flow uf WHERE uf.business_type='竞拍' AND uf.flow_type='支出' AND uf.pay_method='BALANCE' AND uf.status='success' ";
	private final static String HQL11 = " GROUP BY user_id) t6 ON u.user_id = t6.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt7 FROM tbl_user_account_flow uf WHERE uf.business_type='求购' AND uf.flow_type='收入' AND uf.status='success' ";
	private final static String HQL12 = " GROUP BY user_id) t7 ON u.user_id = t7.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt8 FROM tbl_user_account_flow uf WHERE uf.business_type='求购' AND uf.flow_type='支出' AND uf.pay_method='BALANCE' AND uf.status='success' ";
	private final static String HQL13 = " GROUP BY user_id) t8 ON u.user_id = t8.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt9 FROM tbl_user_account_flow uf WHERE uf.business_type='违约金' AND uf.flow_type='收入' AND uf.status='success' ";
	private final static String HQL14 = " GROUP BY user_id) t9 ON u.user_id = t9.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt10 FROM tbl_user_account_flow uf WHERE uf.business_type='违约金' AND uf.flow_type='支出' AND uf.pay_method='BALANCE' AND uf.status='success' ";
	private final static String HQL15 = " GROUP BY user_id) t10 ON u.user_id = t10.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt11 FROM tbl_user_account_flow uf WHERE uf.business_type='交易' AND uf.flow_type='收入' AND uf.content='营销服务收入' AND uf.status='success' ";
	private final static String HQL16 = " GROUP BY user_id) t11 ON u.user_id = t11.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt12 FROM tbl_user_account_flow uf WHERE uf.business_type='交易' AND uf.flow_type='支出' AND uf.pay_method='BALANCE' AND uf.content='营销服务支出' AND uf.status='success' ";
	private final static String HQL17 = " GROUP BY user_id) t12 ON u.user_id = t12.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt13 FROM tbl_user_account_flow uf WHERE uf.business_type='交易' AND uf.flow_type='收入' AND uf.content='邮费入账' AND uf.status='success' ";
	private final static String HQL18 = " GROUP BY user_id) t13 ON u.user_id = t13.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt14 FROM tbl_user_account_flow uf WHERE uf.business_type='交易' AND uf.flow_type='支出' AND uf.pay_method='BALANCE' AND uf.content='保险费支出' AND uf.status='success' ";
	private final static String HQL19 = " GROUP BY user_id) t14 ON u.user_id = t14.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt15 FROM tbl_user_account_flow uf WHERE uf.business_type='交易' AND uf.flow_type='收入' AND uf.content='保险费收入' AND uf.status='success' ";
	private final static String HQL20 = " GROUP BY user_id) t15 ON u.user_id = t15.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt16 FROM tbl_user_account_flow uf WHERE uf.business_type='交易' AND uf.flow_type='支出' AND uf.pay_method='BALANCE' AND uf.content='平台手续费' AND uf.status='success' ";
	private final static String HQL21 = " GROUP BY user_id) t16 ON u.user_id = t16.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt17 FROM tbl_user_account_flow uf WHERE uf.business_type='交易' AND uf.flow_type='收入' AND uf.content='平台手续费' AND uf.status='success' ";
	private final static String HQL22 = " GROUP BY user_id) t17 ON u.user_id = t17.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt18 FROM tbl_user_account_flow uf WHERE uf.business_type='交易' AND uf.flow_type='收入' AND (uf.content='佣金结算' OR uf.content='商品分销入账') AND uf.status='success' ";
	private final static String HQL23 = " GROUP BY user_id) t18 ON u.user_id = t18.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt19 FROM tbl_user_account_flow uf WHERE uf.business_type='交易' AND uf.flow_type='支出' AND uf.content='商品分销出账' AND uf.status='success' ";
	private final static String HQL24 = " GROUP BY user_id) t19 ON u.user_id = t19.id LEFT JOIN "+
"(SELECT uf.user_id AS id,SUM(uf.amt) AS amt20 FROM tbl_user_account_flow uf WHERE uf.flow_type='收入' AND uf.content='余额充值' AND uf.status='success' ";
	private final static String HQL25 = " GROUP BY user_id) t20 ON u.user_id = t20.id WHERE 1=1 ";
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> searchFundSummary(FundSummaryPo vo) {
		Map<String,Object> hqlMap = new HashMap<String,Object>();
		StringBuilder hqlQuery = new StringBuilder(HQL);
		String hql  = "";
		String fHql = "";
		String lHql = "";
		if (!StringUtils.isEmpty(vo.getStartTime())&&!StringUtils.isEmpty(vo.getEndTime())) {
			hqlMap.put("endTime", vo.getEndTime());
			hqlMap.put("startTime", vo.getStartTime());
			fHql = " AND uf.create_time <= :startTime";
			lHql = " AND uf.create_time <= :endTime";
			hql = " AND uf.create_time BETWEEN :startTime AND :endTime";
		}
		if (!StringUtils.isEmpty(vo.getEndTime())&&StringUtils.isEmpty(vo.getStartTime())) {
			hqlMap.put("endTime", vo.getEndTime());
			fHql = " AND uf.create_time <= 0";
			lHql = " AND uf.create_time <= :endTime";
			hql = " AND uf.create_time <= :endTime";
		}
		if (StringUtils.isEmpty(vo.getEndTime())&&!StringUtils.isEmpty(vo.getStartTime())) {
			hqlMap.put("startTime", vo.getStartTime());
			fHql = " AND uf.create_time <= :startTime";
			hql = " AND uf.create_time >= :startTime";
		}
		if (StringUtils.isEmpty(vo.getEndTime())&&StringUtils.isEmpty(vo.getStartTime())) {
			fHql = " AND uf.create_time <= 0";
		}
		hqlQuery.append(fHql);//期初收入
		hqlQuery.append(HQL2);
		hqlQuery.append(fHql);//期初支出
		hqlQuery.append(HQL3);
		hqlQuery.append(lHql);//期末收入
		hqlQuery.append(HQL4);
		hqlQuery.append(lHql);//期末支出
		hqlQuery.append(HQL5);
		hqlQuery.append(lHql);//冻结金额
		hqlQuery.append(HQL6);
		hqlQuery.append(hql);
		hqlQuery.append(HQL7);
		hqlQuery.append(hql);
		hqlQuery.append(HQL8);
		hqlQuery.append(hql);
		hqlQuery.append(HQL9);
		hqlQuery.append(hql);
		hqlQuery.append(HQL10);
		hqlQuery.append(hql);
		hqlQuery.append(HQL11);
		hqlQuery.append(hql);
		hqlQuery.append(HQL12);
		hqlQuery.append(hql);
		hqlQuery.append(HQL13);
		hqlQuery.append(hql);
		hqlQuery.append(HQL14);
		hqlQuery.append(hql);
		hqlQuery.append(HQL15);
		hqlQuery.append(hql);
		hqlQuery.append(HQL16);
		hqlQuery.append(hql);
		hqlQuery.append(HQL17);
		hqlQuery.append(hql);
		hqlQuery.append(HQL18);
		hqlQuery.append(hql);
		hqlQuery.append(HQL19);
		hqlQuery.append(hql);
		hqlQuery.append(HQL20);
		hqlQuery.append(hql);
		hqlQuery.append(HQL21);
		hqlQuery.append(hql);
		hqlQuery.append(HQL22);
		hqlQuery.append(hql);
		hqlQuery.append(HQL23);
		hqlQuery.append(hql);
		hqlQuery.append(HQL24);
		hqlQuery.append(hql);
		hqlQuery.append(HQL25);
		if (!StringUtils.isEmpty(vo.getUserLoginName())) {
			hqlQuery.append(" AND us.login_name LIKE :userLoginName");
			hqlMap.put("userLoginName", "%"+vo.getUserLoginName()+"%");
		}
		if (!StringUtils.isEmpty(vo.getUserName())) {
			hqlQuery.append(" AND us.alias_name LIKE :userName");
			hqlMap.put("userName", "%"+vo.getUserName()+"%");
		}
		if (!StringUtils.isEmpty(vo.getLittleAmt())) {
			hqlQuery.append(" AND t1_2.amt1_2-t1_3.amt1_3 >= :littleAmt");
			hqlMap.put("littleAmt", vo.getLittleAmt());
		}
		if (!StringUtils.isEmpty(vo.getHeightAmt())) {
			hqlQuery.append(" AND t1_2.amt1_2-t1_3.amt1_3 <= :heightAmt");
			hqlMap.put("heightAmt", vo.getHeightAmt());
		}
		String lastHql = hqlQuery.toString();
		EntityManager manager = null;
		List<Object[]> resultList = null;
		try {			
			manager = entityManager.getEntityManagerFactory().createEntityManager();
			Query createQuery = manager.createNativeQuery(lastHql);
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
