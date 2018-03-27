package com.visionet.syh_mall.service.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.FileManage;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.cart.ShopCart;
import com.visionet.syh_mall.entity.goods.Goods;
import com.visionet.syh_mall.entity.goods.GoodsPicLink;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.cart.ShopCartRepository;
import com.visionet.syh_mall.repository.marketing.CouponRepository;
import com.visionet.syh_mall.repository.mobile.GoodsPicLinkRepository;
import com.visionet.syh_mall.repository.mobile.GoodsRepository;
import com.visionet.syh_mall.repository.mobile.ShopRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.fileManage.FilePathUtils;
/**
 * 购物车service层
 * @author mulongfei
 * @date 2017年8月31日上午10:22:58
 */
@Service
public class ShopCartService extends BaseService{
	@Autowired
	private ShopCartRepository shopCartDao;
	@Autowired
	private GoodsRepository goodsDao;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private FileManageRepostory fileManageDao;
	@Autowired
	private GoodsPicLinkRepository goodsPicLinkDao;
	@Autowired
	private KeyMappingRepository keyMappingDao;
	@Autowired
	private ShopRepository shopDao;
	@Autowired
	private CouponRepository couponDao;
	/**
	 * 获取购物车列表
	 * @param 
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getShopCartList(String UserId){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		//查询用户购物车中的所有商品
		List<ShopCart> shopCarts = (List<ShopCart>) shopCartDao.findAll(UserId);
		//对商品进行遍历
		for (ShopCart shopCart : shopCarts) {
			Goods good = goodsDao.findOne(shopCart.getGoodsId());
			if(StringUtils.isEmpty(good)){
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
			}
			//得到卖家
			User seller = userDao.findOne(good.getOwnerId());
			//list中的数据随着循环次数进行逐步增加
			Iterator<Map<String, Object>> iterator = list.iterator();
			//卖家id集合，用以将同店铺的商品进行合并显示
			List<String> sellerIds=new ArrayList<String>();
			while(iterator.hasNext()){
				Map<String, Object> next = iterator.next();
				sellerIds.add((String) next.get("sellerID"));
			}
			Map<String,Object> map = null;
			List<Map<String,Object>> goodlist = null;
			//如果卖家id集合中不包含新的卖家id就创建一个新的map对象放该店铺的商品
			if(!sellerIds.contains(seller.getId())){
				map=new HashMap<String,Object>();
				map.put("sellerID", seller.getId());
				Shop shop = shopDao.findIdByUserId(seller.getId());
				map.put("shopID", shop.getId());
				int i = couponDao.hasCoupon(shop.getId(),DateUtil.getCurrentDate());
				map.put("hasCoupon", i>0 ? true : false);
				map.put("shopName", shop.getShopName());
				map.put("sellerName", seller.getAliasName());
				if(!StringUtils.isEmpty(seller.getImgFileId())) {					
					FileManage fileManage = fileManageDao.findOne(seller.getImgFileId());
					map.put("sellerImgUrl", FilePathUtils.fileUrl(fileManage.getFilePath()));
				}
				map.put("sellerLevel", seller.getChannelLevel());
				map.put("sellerTypeCode", seller.getUserTypeCode());
				KeyMapping keyMapping = keyMappingDao.findByKeyCode(seller.getUserTypeCode());
				if(keyMapping!=null){
					map.put("sellerTypeDesc", keyMapping.getValueDesc());
				}else{
					map.put("sellerTypeDesc", null);					
				}
				//创建存放该店铺中商品的集合
				goodlist=new ArrayList<Map<String,Object>>();
			}
			for (int i=0;i<sellerIds.size();i++) {
				if(sellerIds.get(i).equals(seller.getId())){
					map=list.get(i);
					goodlist=(List<Map<String, Object>>) map.get("goodsInfos");
				}
			}
			//获取商品信息
			Map<String, Object> goodsMap = getGoods(shopCart, good);
			goodlist.add(goodsMap);
			map.put("goodsInfos", goodlist);
			if(!sellerIds.contains(seller.getId())){				
				list.add(map);
			}
		}
		return list;
	}
	private Map<String, Object> getGoods(ShopCart shopCart, Goods good) {
		Map<String,Object> goodsMap=new HashMap<String,Object>();
		goodsMap.put("goodsID", good.getId());
		goodsMap.put("goodsName", good.getGoodsName());
		goodsMap.put("goodsNum", shopCart.getGoodsNum());
		goodsMap.put("goodsOriginalPrice", good.getGoodsPrice());
		goodsMap.put("goodsDesc", good.getGoodsDescription());
		goodsMap.put("goodsStockNum", good.getStockNum());
		goodsMap.put("goodsQualityCode", good.getGoodsQualityCode());
		KeyMapping keyMapping = keyMappingDao.findByKeyCode(good.getGoodsQualityCode());
		if(keyMapping!=null){
			goodsMap.put("goodsQualityDesc", keyMapping.getValueDesc());
		}else{
			goodsMap.put("goodsQualityDesc", null);
		}
		goodsMap.put("goodsQualityScore", good.getGoodsQualityScore());
		goodsMap.put("goodsRecognizeCode", good.getRecognizedCode());
		goodsMap.put("goodsExpressFee", good.getExpressFee());
		//得到商品图片
		List<Map<String, Object>> picList = getGoodsPic(good);
		goodsMap.put("goodsImgUrls", picList);
		Map<String,Object> expressMap=new HashMap<String,Object>();
		expressMap.put("id", good.getExpressTempletId());
		goodsMap.put("expressTemplet", expressMap);
		return goodsMap;
	}
	
	private List<Map<String, Object>> getGoodsPic(Goods good) {
		List<GoodsPicLink> goodsPicList = goodsPicLinkDao.getGoodsPicList(good.getId());
		List<Map<String,Object>> picList=new ArrayList<Map<String,Object>>();
		for (GoodsPicLink goodsPicLink : goodsPicList) {
			Map<String,Object> picMap=new HashMap<String,Object>();
			String maxImgId = goodsPicLink.getMaxImgId();
			FileManage MaxFile = fileManageDao.findOne(maxImgId);
			if(!StringUtils.isEmpty(MaxFile)){					
				picMap.put("maxImgID", maxImgId);
				picMap.put("maxImgUrl", FilePathUtils.fileUrl(MaxFile.getFilePath()));
			}
			String midImgId = goodsPicLink.getMidImgId();
			FileManage MidFile = fileManageDao.findOne(midImgId);
			if(!StringUtils.isEmpty(MidFile)){					
				picMap.put("midImgID", midImgId);
				picMap.put("midImgUrl", FilePathUtils.fileUrl(MidFile.getFilePath()));
			}
			String minImgId = goodsPicLink.getMinImgId();
			FileManage MinFile = fileManageDao.findOne(minImgId);
			if(!StringUtils.isEmpty(MinFile)){					
				picMap.put("minImgID", minImgId);
				picMap.put("minImgUrl", FilePathUtils.fileUrl(MinFile.getFilePath()));
			}
			picList.add(picMap);
		}
		return picList;
	}
	/**
	 * 加入/编辑购物车
	 * @param Goods,changeType
	 * @return void
	 * @throws
	 */
	public void addGoodsCart(List<ShopCart> Goods,Integer changeType){
		changeType = StringUtils.isEmpty(changeType) ? 0 : changeType;
		for (ShopCart shopCart : Goods) {
			List<ShopCart> goods = shopCartDao.findOne(shopCart.getUserId(),shopCart.getGoodsId());
			for (ShopCart good : goods) {
				if(good!=null&&changeType==0){
					good.setGoodsNum(good.getGoodsNum()+shopCart.getGoodsNum());
					good.setUpdateTime(DateUtil.getCurrentDate());
					shopCartDao.save(good);
				}
				if(good!=null&&changeType==1){
					good.setGoodsNum(shopCart.getGoodsNum());
					good.setUpdateTime(DateUtil.getCurrentDate());
					shopCartDao.save(good);
				}
			}
			if(goods.size()==0){
				shopCart.setCreateTime(DateUtil.getCurrentDate());
				shopCart.setUpdateTime(DateUtil.getCurrentDate());
				shopCartDao.save(shopCart);
			}
		}
	}
	/**
	 * 购物车中删除
	 * @param List
	 * @return void
	 * @throws
	 */
	public void delGoodsCart(List<String> goodIds,String userId){
		for (String id : goodIds) {
			List<ShopCart> cartGood = shopCartDao.findOne(userId,id);
			if(StringUtils.isEmpty(cartGood)){
				throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
			}
			for (ShopCart shopCart : cartGood) {
				shopCart.setIsDeleted(1);
				shopCart.setUpdateTime(DateUtil.getCurrentDate());
				shopCartDao.save(shopCart);
			}
		}
	}
}
