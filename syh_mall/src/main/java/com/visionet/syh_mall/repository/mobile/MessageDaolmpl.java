package com.visionet.syh_mall.repository.mobile;

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

import com.visionet.syh_mall.common.utils.Validator;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.vo.message.MessageQo;
import com.visionet.syh_mall.vo.message.MessageVo;

/**
 *@Author DM
 *@version ：2017年10月20日下午2:49:42
 *实体类
 */
@Repository
public class MessageDaolmpl {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private UserRepository userDao;
	public static String FromTable = "select m.id as id,m.sender_id as senderId,m.receiver_id as receiverId,m.title as msgTitle,m.content as msgContent,m.create_time as msgTime from tbl_user_message m where 1=1";
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
		public Page<MessageVo> queryCondition(MessageQo qo, PageRequest pr) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder query = new StringBuilder(FromTable);
			if (Validator.isNotNull(qo.getSenderId())) {//发送人ID
			query.append(" and (m.receiver_id = :receiverId and m.sender_id = :senderId) or (m.receiver_id = :senderId and m.sender_id = :receiverId)");
			map.put("receiverId",qo.getReceiverId());
			map.put("senderId",qo.getSenderId());
			}
			query.append(" order by m.create_time desc");
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
			return resultToVoPage(lists, pr, lists1,qo);
		}
		private Page<MessageVo> resultToVoPage(List<Map<String,Object>> lists, PageRequest pr, List<Object[]> lists1,MessageQo qo) {
			if (lists != null) {
				List<MessageVo> voList = new ArrayList<MessageVo>();	
				for (Map<String,Object> mp : lists) {
					MessageVo tVo = new MessageVo();
					if(Validator.isNotNull(mp.get("id"))){
						tVo.setId(mp.get("id").toString());
					}
					if(Validator.isNotNull(mp.get("senderId"))){
						tVo.setSenderId(mp.get("senderId").toString());
					}
					if(Validator.isNotNull(mp.get("receiverId"))){
						tVo.setReceiverId(mp.get("receiverId").toString());
						if(qo.getReceiverId().equals(mp.get("senderId").toString())){				
							User user = userDao.findOne(mp.get("receiverId").toString());
							tVo.setSenderName(user.getAliasName());
						}else{				
						User user = userDao.findOne(mp.get("senderId").toString());
						    tVo.setSenderName(user.getAliasName());
						}
						if(mp.get("receiverId").toString().equals(qo.getReceiverId())){
							tVo.setIsSelfMsg(0);
						}else{
							tVo.setIsSelfMsg(1);
						}
					}
					if(Validator.isNotNull(mp.get("msgTitle"))){
						tVo.setMsgTitle(mp.get("msgTitle").toString());
					}
					if(Validator.isNotNull(mp.get("msgContent"))){
						tVo.setMsgContent(mp.get("msgContent").toString());
					}
					  if(Validator.isNotNull(mp.get("msgTime"))){
						 	tVo.setMsgTime((Date)mp.get("msgTime"));
						}
					voList.add(tVo);
				}
				return new PageImpl<MessageVo>(voList, pr, lists1.size());
			} else {

				return null;
			}
		}
	}
