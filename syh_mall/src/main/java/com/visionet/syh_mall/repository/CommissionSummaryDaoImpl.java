package com.visionet.syh_mall.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Repository;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MathUtils;
import com.visionet.syh_mall.common.utils.Validator;
import com.visionet.syh_mall.vo.ChannelCollctVo;
import com.visionet.syh_mall.vo.GoodsQo;

@Repository
public class CommissionSummaryDaoImpl {

	@PersistenceContext
	private EntityManager entityManager;

	public static String FromTable = "SELECT tbl1.hierarchy,u.login_name AS loginName,u.alias_name AS aliasName,tbl1.atm AS amount,tbl1.userRate AS commissionRate,tbl1.createTime,tbl1.retailType FROM (SELECT d.retail_user_id,d.hierarchy ,d.retail_user_rate as userRate,SUM(d.commission_amt) AS atm,d.retail_user_rate , d.create_time  as createTime,d.retail_type as retailType FROM tbl_retail_detail d  where 1=1";

	@SuppressWarnings("unchecked")
	public List<ChannelCollctVo> queryRecommendGoods(GoodsQo qo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder(FromTable);

		if (Validator.isNotNull(qo.getCommissionRate())) {// 佣金比例
			query.append(" and d.retail_user_rate = :commissionRate");
			map.put("commissionRate", qo.getCommissionRate());
		}
		if (Validator.isNotNull(qo.getStartTime())) { // 开始时间检索
			query.append(" and d.create_time >= :startTime");
			map.put("startTime", qo.getStartTime());
		}
		if (Validator.isNotNull(qo.getStartTime())) { // 结束时间检索
			query.append(" and d.create_time <= :endTime");
			map.put("endTime", qo.getEndTime());
		}

		query.append(
				" GROUP BY  d.retail_user_id,d.hierarchy,d.commission_rate) tbl1  LEFT JOIN  tbl_user u ON tbl1.retail_user_id=u.id where 1=1");
		// 用户登录名
		if (qo.getLoginName() != null) {
			query.append(" and u.login_name like :loginName");
			map.put("loginName", "%" + qo.getLoginName() + "%");
		}
		// 用户昵称
		if (Validator.isNotNull(qo.getUserName())) {// 用户名字
			query.append(" and u.alias_name like :userName");
			map.put("userName", "%" + qo.getUserName() + "%");
		}
		query.append(" ORDER BY u.login_name DESC");
		String sql = query.toString();
		EntityManager em = null;
		List<Object[]> lists1 = null;
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		try {
			em = entityManager.getEntityManagerFactory().createEntityManager();
			Query queryString = em.createNativeQuery(sql);
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				queryString.setParameter(entry.getKey(), entry.getValue());
			}
			lists1 = queryString.getResultList();
			queryString.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Object> rows = queryString.getResultList();
			for (Object obj : rows) {
				Map<String, Object> row = (Map<String, Object>) obj;
				lists.add(row);
			}

		} catch (RuntimeException e) {
			throw e instanceof IllegalArgumentException ? e : new IllegalArgumentException(e);
		} finally {
			if (em != null) {
				EntityManagerFactoryUtils.closeEntityManager(em);
			}
		}
		return resultToVoPage(lists, lists1, qo);
	}

	private List<ChannelCollctVo> resultToVoPage(List<Map<String, Object>> lists, List<Object[]> lists1, GoodsQo qo) {
		if (lists != null) {
			List<ChannelCollctVo> channelCollctVos = new ArrayList<ChannelCollctVo>();
			for (Map<String, Object> mp : lists) {
				ChannelCollctVo channelCollctVo = new ChannelCollctVo();
				channelCollctVo.setLoginName((String) mp.get("loginName"));
				channelCollctVo.setAliasName((String) mp.get("aliasName"));
				channelCollctVo.setAmount(MathUtils.getBigDecimal(mp.get("amount")));
				channelCollctVo.setCommissionRate(
						MathUtils.getBigDecimal(mp.get("commissionRate")).divide(new BigDecimal(100)));
				if (mp.get("hierarchy") != null) {
					if (mp.get("hierarchy").equals("buyFather")) {
						channelCollctVo.setHierarchy("买家父级");
					}
					if (mp.get("hierarchy").equals("buyGrandFather")) {
						channelCollctVo.setHierarchy("买家祖父级");
					}
					if (mp.get("hierarchy").equals("sellFather")) {
						channelCollctVo.setHierarchy("卖家父级");
					}
					if (mp.get("hierarchy").equals("sellGrandFather")) {
						channelCollctVo.setHierarchy("卖家祖父级");
					}
				}
				channelCollctVo.setTime(DateUtil.convertToString((Date) mp.get("createTime")) + "-"
						+ DateUtil.convertToString(new Date()));

				// 如果查询的开始时间不为空,结束时间为空
				if (qo.getStartTime() != null && qo.getEndTime() == null) {
					channelCollctVo.setTime(
							DateUtil.convertToString(qo.getStartTime()) + "-" + DateUtil.convertToString(new Date()));
				}

				// 如果查询的结束时间不为空，开始时间为空
				if (qo.getEndTime() != null && qo.getStartTime() == null) {
					channelCollctVo.setTime(DateUtil.convertToString((Date) mp.get("createTime")) + "-"
							+ DateUtil.convertToString(qo.getEndTime()));
				}
				// 如果查询的结束时间不为空，开始时间不为空
				if (qo.getEndTime() != null && qo.getStartTime() != null) {
					channelCollctVo.setTime(DateUtil.convertToString(qo.getStartTime()) + "-"
							+ DateUtil.convertToString(qo.getEndTime()));
				}
				channelCollctVos.add(channelCollctVo);

			}
			return channelCollctVos;
		} else {

			return null;
		}
	}

}
