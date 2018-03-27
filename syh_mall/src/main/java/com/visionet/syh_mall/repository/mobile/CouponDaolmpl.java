package com.visionet.syh_mall.repository.mobile;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.visionet.syh_mall.entity.marketing.UserCoupon;
import com.visionet.syh_mall.repository.marketing.UserCouponRepository;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Repository;

import com.visionet.syh_mall.common.utils.Validator;
import com.visionet.syh_mall.entity.marketing.Coupon;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.repository.marketing.CouponRepository;
import com.visionet.syh_mall.vo.message.CouponQo;
import com.visionet.syh_mall.vo.message.CouponVo;

/**
 * @Author DM
 * @version ：2017年9月21日下午4:40:00 实体类
 */
@Repository
public class CouponDaolmpl {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private CouponRepository couponDao;
	@Autowired
	private UserCouponRepository UserCouponDao;
	@Autowired
	private ShopRepository shopDao;
	public static String FromTable = "SELECT u.id AS userCouponId,u.coupon_id AS couponId,u.coupon_num AS couponNum,u.is_used AS couponIsUsed,u.user_id AS userId,c.shop_id AS shopId,u.is_used AS isUsed FROM tbl_user_coupon u LEFT JOIN tbl_coupon c ON c.id = u.coupon_id WHERE 1=1";

	/**
	 * 分页查询
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page<CouponVo> queryCondition(CouponQo qo, PageRequest pr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder(FromTable);

		if (Validator.isNotNull(qo.getUserId())) {// 用户ID
			query.append(" and u.user_id = :userId");
			map.put("userId", qo.getUserId());
		}
		if (Validator.isNotNull(qo.getShopId())) {// 店铺ID
			query.append(" and c.shop_id = :shopId");
			map.put("shopId", qo.getShopId());
		}
		if (Validator.isNotNull(qo.getCouponStatus())) {// 优惠劵状态
			if(qo.getCouponStatus()==0){
				query.append(" and c.expiration_time >= :expirationTime");
				map.put("expirationTime",new Date());
			}
			query.append(" and u.is_used = :couponIsUsed");
			map.put("couponIsUsed", qo.getCouponStatus());
		}
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
			queryString.setFirstResult(qo.getPageNumber());
			queryString.setMaxResults(qo.getPageSize());
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

	private Page<CouponVo> resultToVoPage(List<Map<String, Object>> lists, PageRequest pr, List<Object[]> lists1) {
		if (lists != null) {
			List<CouponVo> voList = new ArrayList<CouponVo>();
			for (Map<String, Object> mp : lists) {
				CouponVo tVo = new CouponVo();
				if (Validator.isNotNull(mp.get("userCouponId"))) {
					tVo.setUserCouponId(mp.get("userCouponId").toString());
					UserCoupon userCoupon = UserCouponDao.findOne(mp.get("userCouponId").toString());
					tVo.setCouponhaveNum(userCoupon.getCouponHaveNum());
				}
				int couponNum = (Integer) mp.get("couponNum");
				if (Validator.isNotNull(mp.get("couponId"))) {
					tVo.setCouponId(mp.get("couponId").toString());
					Coupon coupon = couponDao.findOne(mp.get("couponId").toString());
					if (null != coupon && null != coupon.getId()) {
						tVo.setCouponName(coupon.getCouponName());
						tVo.setCouponValue(coupon.getCouponValue());
						tVo.setCouponLimitAmt(coupon.getLimitMoney());
						tVo.setShopId(coupon.getShopId());
						tVo.setCouponNum(couponNum);
						Shop shop = shopDao.findOne(coupon.getShopId());
						if (null != shop && null != shop.getId()) {
							tVo.setShopName(shop.getShopName());
						}
						tVo.setEffectiveTime(coupon.getEffectiveTime());
						tVo.setExpireTime(coupon.getExpirationTime());
						tVo.setIsAvailable(coupon.getIsAvailable());
					}
				}
				voList.add(tVo);
			}
			return new PageImpl<CouponVo>(voList, pr, lists1.size());
		} else {

			return null;
		}
	}
}
