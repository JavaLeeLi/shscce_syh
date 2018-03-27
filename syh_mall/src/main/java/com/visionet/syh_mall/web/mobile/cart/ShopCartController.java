package com.visionet.syh_mall.web.mobile.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.decorate.ShopCartEnhance;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.entity.cart.ShopCart;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.cart.ShopCartService;
import com.visionet.syh_mall.vo.cart.CartGoodsInfo;
import com.visionet.syh_mall.vo.cart.CartGoodsVo;
import com.visionet.syh_mall.web.BaseController;
/**
 * 购物车模块
 * @author mulongfei
 * @date 2017年8月31日上午11:53:17
 */
@RestController
@RequestMapping(value="/api/cart")
public class ShopCartController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(ShopCartController.class);
	@Autowired
	private ShopCartService shopCartService;
	@Autowired
	private ShopCartEnhance cartEnhance;
	
	/**
	 * 获取购物车列表
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="/getCartGoods",method=RequestMethod.POST)
	public BaseReturnVo<Object> getShopCartList(){
		logger.info("获取购物车列表:{}");
		List<Map<String,Object>> shopCartList=null;
		try {
			shopCartList = cartEnhance.getShopCartList(getCurrentUserId());
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}		
		return BaseReturnVo.success(shopCartList);
	}
	/**
	 * 加入/编辑购物车
	 * @param Map
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@SuppressWarnings("static-access")
	@RequiresAuthentication
	@RequestMapping(value="/addGoodsIntoCart",method=RequestMethod.POST)
	@Log(name="加入/编辑购物车",model="购物车模块")
	public BaseReturnVo<Object> addGoodsCart(@RequestBody @Valid CartGoodsVo cartGoodsVo,BindingResult result){
		logger.info("加入/编辑购物车:{}", cartGoodsVo);
		List<CartGoodsInfo> goodsInfos = cartGoodsVo.getGoodsInfos();
		String userId=this.getCurrentUserId();
		List<ShopCart> Goods=new ArrayList<ShopCart>();
		for (CartGoodsInfo shopCart : goodsInfos) {
			ShopCart shCart=new ShopCart();
			shCart.setUserId(userId);
			shCart.setGoodsId(shopCart.getGoodsID());
			shCart.setGoodsNum(shopCart.getGoodsNum());
			Goods.add(shCart);
		}
		try {
			shopCartService.addGoodsCart(Goods,cartGoodsVo.getOperateType());			
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
	/**
	 * 购物车中删除
	 * @param Map
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequiresAuthentication
	@RequestMapping(value="/delGoodsFromCart",method=RequestMethod.POST)
	@Log(name="购物车中删除",model="购物车模块")
	public BaseReturnVo<Object> delGoodsCart(@RequestBody Map<String,Object> map){
		logger.info("购物车中删除:{}",map);
		List<Map<String,Object>> list=(List<Map<String, Object>>) map.get("cartInfos");
		List<String> ids=new ArrayList<String>();
		for (Map<String, Object> map2 : list) {
			String id = (String) map2.get("cartGoodsID");
			ids.add(id);
		}
		try {
			shopCartService.delGoodsCart(ids,getCurrentUserId());
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
}
