package com.visionet.syh_mall.service.mbr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.entity.FileManage;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.GoodsPicLink;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.entity.userAttention.GoodsFavorite;
import com.visionet.syh_mall.entity.userAttention.UserAttention;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mobile.GoodsPicLinkRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.repository.mobile.ShopRepository;
import com.visionet.syh_mall.repository.userAttention.GoodsFavoriteRepository;
import com.visionet.syh_mall.repository.userAttention.UserAttentionRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
/**
 * 收藏列表
 * @author mulongfei
 * @date 2017年9月5日上午11:47:19
 */
@Service
public class GoodsFavoritesService extends BaseService{
	@Autowired
	private GoodsFavoriteRepository goodsFavoriteDao;
	@Autowired
	private GoodsRepository goodsDao;
	@Autowired
	private GoodsPicLinkRepository goodsPicLinkDao;
	@Autowired
	private FileManageRepostory fileManageDao;
	@Autowired
	private UserAttentionRepository userAttentionDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private KeyMappingRepository keyMappingDao;
	@Autowired
	private ShopRepository shopDao;
	/**
	 * 获取收藏列表
	 * @param String
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String,Object>> getFavorites(String UserId){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<GoodsFavorite> goodsFavorites = goodsFavoriteDao.findByUserId(UserId);
		for (GoodsFavorite goodsFavorite : goodsFavorites) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("favoriteID", goodsFavorite.getId());
			map.put("goodsID", goodsFavorite.getGoodsId());
			Goods good = goodsDao.findOne(goodsFavorite.getGoodsId());
			if(StringUtils.isEmpty(good)){
				throw new RestException(HttpStatus.ACCEPTED,BusinessStatus.DATE_ERROR.getDesc());
			}
			map.put("goodsTypeCode", good.getGoodsTypeCode());
			map.put("goodsName", good.getGoodsName());
			map.put("goodsPrice", good.getGoodsPrice());
			map.put("goodsDesc", good.getGoodsDescription());
			List<GoodsPicLink> goodsPicList = goodsPicLinkDao.getGoodsPicList(good.getId());
			List<Map<String,Object>> picList = new ArrayList<Map<String,Object>>();
			for (GoodsPicLink goodsPicLink : goodsPicList) {
				Map<String,Object> picMap = new HashMap<String, Object>();
				FileManage MaxImg = fileManageDao.findOne(goodsPicLink.getMaxImgId());
				FileManage MidImg = fileManageDao.findOne(goodsPicLink.getMidImgId());
				FileManage MinImg = fileManageDao.findOne(goodsPicLink.getMinImgId());
				if(!StringUtils.isEmpty(MaxImg)){					
					picMap.put("maxImgUrl", FilePathUtils.fileUrl(MaxImg.getFilePath()));
				}
				if(!StringUtils.isEmpty(MidImg)){					
					picMap.put("midImgUrl", FilePathUtils.fileUrl(MidImg.getFilePath()));
				}
				if(!StringUtils.isEmpty(MinImg)){					
					picMap.put("minImgUrl", FilePathUtils.fileUrl(MinImg.getFilePath()));
				}
				picList.add(picMap);
			}
			map.put("goodsImgs", picList);
			list.add(map);
		}
		return list;
	}
	/**
	 * 获取关注列表
	 * @param String
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String,Object>> getAttentions(String UserId){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<UserAttention> userAttentions = userAttentionDao.findByUserId(UserId);
		for (UserAttention userAttention : userAttentions) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("attentionID", userAttention.getId());
			map.put("concernUserID", userAttention.getConcernedUserId());
			User user = userDao.findOne(userAttention.getConcernedUserId());
			if(StringUtils.isEmpty(user)){
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
			}
			map.put("concernUserName", user.getAliasName());
			if(!StringUtils.isEmpty(user.getImgFileId())){
				FileManage fileManage = fileManageDao.findOne(user.getImgFileId());
				map.put("concernUserImgUrl", FilePathUtils.fileUrl(fileManage.getFilePath()));
			}else{
				map.put("concernUserImgUrl", null);
			}
			Shop shop = shopDao.findByUserId(user.getId());
			map.put("concernShopId", shop.getId());
			map.put("concernUserLevel", user.getChannelLevel());
			map.put("userTypeCode", user.getUserTypeCode());
			KeyMapping keyMapping = keyMappingDao.findByKeyCode(user.getUserTypeCode());
			map.put("userTypeDesc", keyMapping.getValueDesc());
			list.add(map);
		}
		return list;
	}
}
