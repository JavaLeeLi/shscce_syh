package com.visionet.syh_mall.service.userAttention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.userAttention.GoodsFavorite;
import com.visionet.syh_mall.entity.userAttention.UserAttention;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.repository.userAttention.GoodsFavoriteRepository;
import com.visionet.syh_mall.repository.userAttention.UserAttentionRepository;
import com.visionet.syh_mall.service.BaseService;
/**
 * 用户管理service层
 * @author mulongfei
 * @date 2017年9月4日上午11:52:30
 */
@Service
public class UserAttentionService extends BaseService{
	@Autowired
	private UserAttentionRepository userAttentionDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private GoodsFavoriteRepository goodsFavoriteDao;
	@Autowired
	private GoodsRepository goodsDao;
	/**
	 * 关注卖家/店铺
	 * @param String
	 * @return void
	 * @throws
	 */
	public void addAttention(String UserId,String AttentionId){
		//UserId="1233asd21213aqwer12swe12";//测试数据
		UserAttention userAttention = new UserAttention();
		userAttention.setUserId(UserId);
		if(UserId.equals(AttentionId)){
			throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.ATTENTION_ERROR.getDesc());
		}
		if(StringUtils.isEmpty(userDao.findOne(AttentionId))){
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		if(!StringUtils.isEmpty(userAttentionDao.findByUserIdAndAttentionId(UserId,AttentionId))){
			return;
		}
		userAttention.setConcernedUserId(AttentionId);
		userAttentionDao.save(userAttention);
	}
	/**
	 * 取消关注卖家/店铺
	 * @param String
	 * @return void
	 * @throws
	 */
	public void delAttention(String UserId,String AttentionId){
		//UserId="40288099213d76e2015e2d772b020000";//测试数据
		UserAttention findUserAttention = userAttentionDao.findUserAttention(UserId, AttentionId);
		if(StringUtils.isEmpty(findUserAttention)){
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		findUserAttention.setUpdateTime(DateUtil.getCurrentDate());
		findUserAttention.setIsDeleted(1);
		userAttentionDao.save(findUserAttention);
	}
	/**
	 * 收藏商品
	 * @param String
	 * @return void
	 * @throws
	 */
	public void addFavorite(String userId,String goodId){
		//userId="1233asd21213aqwer12swe12";//测试数据
		GoodsFavorite goodsFavorite = new GoodsFavorite();
		if(userId.equals(goodsDao.findOne(goodId).getOwnerId())){
			throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.FAVORITE_ERROR.getDesc());
		}
		if(StringUtils.isEmpty(goodsDao.findOne(goodId))){
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		goodsFavorite.setUserId(userId);
		goodsFavorite.setGoodsId(goodId);
		goodsFavoriteDao.save(goodsFavorite);
	}
	/**
	 * 取消收藏商品
	 * @param 
	 * @return void
	 * @throws
	 */
	public void removeFavorite(String userId,String goodId){
		//userId="1233asd21213aqwer12swe12";//测试数据
		GoodsFavorite favorite = goodsFavoriteDao.findByUserIdAndGoodId(userId, goodId);
		if(StringUtils.isEmpty(favorite)){
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
		}
		favorite.setUpdateTime(DateUtil.getCurrentDate());
		favorite.setIsDeleted(1);
		goodsFavoriteDao.save(favorite);
	}
	/**
	 * 商品是否被收藏
	 * @param String
	 * @return boolean
	 * @throws
	 */
	public boolean isFavorite(String currentUserId, String goodsId) {
		boolean bool = false;
		GoodsFavorite goodsFavorite = goodsFavoriteDao.findByUserIdAndGoodId(currentUserId, goodsId);
		if(!StringUtils.isEmpty(goodsFavorite)){
			bool = true;
		}
		return bool;
	}
}
