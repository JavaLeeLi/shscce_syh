package com.visionet.syh_mall.repository.mbr;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Repository;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.Validator;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.vo.UserAuthenticationQo;
import com.visionet.syh_mall.vo.UserVo;

/**
 * @Author DM
 * @version ：2017年9月29日上午11:25:16 实体类
 */
@Repository
public class UserAuthenticationDaolmpl {
	@PersistenceContext
	private EntityManager entityManager;
	@SuppressWarnings("unused")
	@Autowired
	private FileManageRepostory fManageRepostory;
	@Autowired
	private KeyMappingRepository keyDao;
	public static String FromTable = "SELECT ua.id,ua.user_id as mbrId,u.login_name as mbrLoginName,u.alias_name as mbrName,u.phone as mbrPhone,u.user_type_code as mbrTypeCode,ua.create_time as mbrAuthenDate,ua.status,ua.refusal_reason as refusalReason,u.user_type_ongoing_code as userTypeOngoingCode FROM tbl_user_authentication ua LEFT JOIN tbl_user u ON ua.user_id = u.id WHERE ua.is_deleted = '0'";

	/**
	 * 分页查询
	 * 
	 * @param tQo
	 * @param pr
	 * @param isBack
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page<UserVo> queryCondition(UserAuthenticationQo qo, PageRequest pr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder(FromTable);
		if (Validator.isNotNull(qo.getMbrLoginName())) {// 会员登录名
			query.append(" and u.login_name like :mbrLoginName");
			map.put("mbrLoginName", "%" + qo.getMbrLoginName() + "%");
		}
		if (Validator.isNotNull(qo.getMbrName())) {// 会员昵称
			query.append(" and u.alias_name like :mbrName");
			map.put("mbrName", "%" + qo.getMbrName() + "%");
		}
		if (Validator.isNotNull(qo.getMbrPhone())) {// 会员手机号
			query.append(" and u.phone like :mbrPhone");
			map.put("mbrPhone", "%" + qo.getMbrPhone() + "%");
		}
//		if (Validator.isNotNull(qo.getStatus())) {//会员认证状态
//			query.append(" and ua.status = :status");
//			map.put("status",qo.getStatus());
//		}
		if (Validator.isNotNull(qo.getMbrTypeCode())) {// 会员认证类型编码
			query.append(" and u.user_type_code = :mbrTypeCode");
			map.put("mbrTypeCode", qo.getMbrTypeCode());
		}
		if (Validator.isNotNull(qo.getUserTypeOngoingCode())) {//会员认证过程类型编码
			query.append(" and u.user_type_ongoing_code = :userTypeOngoingCode");
			map.put("userTypeOngoingCode",qo.getUserTypeOngoingCode());
		}
		if (Validator.isNotNull(qo.getStartTime())) {// 开始时间
			query.append(" and DATE_FORMAT(ua.create_time,'%Y-%m-%d') >= :startTime");
			map.put("startTime", DateUtil.convertToString(qo.getStartTime(), "yyyy-MM-dd"));
		}
		if (Validator.isNotNull(qo.getEndTime())) {// 结束时间
			query.append(" and DATE_FORMAT(ua.create_time,'%Y-%m-%d') <= :endTime");
			map.put("endTime", DateUtil.convertToString(qo.getEndTime(), "yyyy-MM-dd"));
		}
		query.append(" order by ua.create_time desc");
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

	private Page<UserVo> resultToVoPage(List<Map<String, Object>> lists, PageRequest pr, List<Object[]> lists1) {
		if (lists != null) {
			List<UserVo> voList = new ArrayList<UserVo>();
			for (Map<String, Object> mp : lists) {
				UserVo tVo = new UserVo();
				if (Validator.isNotNull(mp.get("id"))) {
					tVo.setId(mp.get("id").toString());
				}
				if (Validator.isNotNull(mp.get("mbrId"))) {
					tVo.setMbrId(mp.get("mbrId").toString());
				}
				if (Validator.isNotNull(mp.get("mbrLoginName"))) {
					tVo.setMbrLoginName(mp.get("mbrLoginName").toString());
				}
				if (Validator.isNotNull(mp.get("mbrName"))) {
					tVo.setMbrName(mp.get("mbrName").toString());
				}
				if (Validator.isNotNull(mp.get("mbrPhone"))) {
					tVo.setMbrPhone(mp.get("mbrPhone").toString());
				}
				if (Validator.isNotNull(mp.get("mbrTypeCode"))) {
					tVo.setMbrTypeCode(mp.get("mbrTypeCode").toString());
					KeyMapping key = keyDao.findByKeyCode(mp.get("mbrTypeCode").toString());
					tVo.setMbrTypeDesc(key.getValueDesc());
				}
				if(Validator.isNotNull(mp.get("userTypeOngoingCode"))){
					tVo.setUserTypeOngoingCode(mp.get("userTypeOngoingCode").toString());
					KeyMapping key = keyDao.findByKeyCode(mp.get("userTypeOngoingCode").toString());
					tVo.setUserTypeOngoingDesc(key.getValueDesc());
				}
                if(Validator.isNotNull(mp.get("mbrAuthenDate"))){
				 	tVo.setMbrAuthenDate((Date)mp.get("mbrAuthenDate"));
				}
				if (Validator.isNotNull(mp.get("status"))) {
					tVo.setStatus((Integer) mp.get("status"));
				}
                if(Validator.isNotNull(mp.get("refusalReason"))){
					tVo.setRefusalReason(mp.get("refusalReason").toString());
				}
				voList.add(tVo);

			}
			return new PageImpl<UserVo>(voList, pr, lists1.size());
		} else {

			return null;
		}
	}
}
