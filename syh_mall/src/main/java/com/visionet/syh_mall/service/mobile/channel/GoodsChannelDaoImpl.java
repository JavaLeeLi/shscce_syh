package com.visionet.syh_mall.service.mobile.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Repository;

import com.visionet.syh_mall.common.utils.Validator;
import com.visionet.syh_mall.vo.GoodsQo;

@Repository
public class GoodsChannelDaoImpl {

	@PersistenceContext
	private EntityManager entityManager;

	public static String FromTable = "SELECT g.id FROM tbl_goods g  WHERE g.goods_channel_rule IS NOT NULL";

	@SuppressWarnings("unchecked")
	public Page<String> queryRecommendGoods(GoodsQo qo, PageRequest pr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder(FromTable);
		if (Validator.isNotNull(qo.getGoodsName())) {// 商品名称
			query.append(" and g.goods_name = :goodsName");
			map.put("goodsName", qo.getGoodsName());
		}
		if (Validator.isNotNull(qo.getUserId())) {// 用户ID
			query.append(" and g.owner_id = :ownerId");
			map.put("ownerId", qo.getUserId());
		}
		query.append(" ORDER BY g.update_time DESC");
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
			queryString.setFirstResult(qo.getCurrentPageNumber());
			queryString.setMaxResults(qo.getItemCount());
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
		return resultToVoPage(lists, pr, lists1);
	}

	private Page<String> resultToVoPage(List<Map<String, Object>> lists, PageRequest pr, List<Object[]> lists1) {
		if (lists != null) {
			List<String> voList = new ArrayList<String>();
			for (Map<String, Object> mp : lists) {
				if(Validator.isNotNull(mp.get("id"))){
					voList.add((String)mp.get("id"));
				}
				
			}
			return new PageImpl<String>(voList, pr, lists1.size());
		} else {

			return null;
		}
	}
}
