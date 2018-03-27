package com.visionet.syh_mall.web.userAttention;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.userAttention.UserAttentionService;
import com.visionet.syh_mall.web.BaseController;

/**
 * 用户管理模块
 * 
 * @author mulongfei
 * @date 2017年9月4日上午11:29:16
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserAttentionController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserAttentionController.class);
	@Autowired
	private UserAttentionService userAttentionService;

	/**
	 * 关注卖家/店铺 @param Map @return BaseReturnVo<Object> @throws
	 */
	@SuppressWarnings("static-access")
	@RequiresAuthentication
	@RequestMapping(value = "/addAttention", method = RequestMethod.POST)
	@Log(name="关注店铺",model="关注模块")
	public BaseReturnVo<Object> addAttention(@RequestBody Map<String, Object> map) {
		logger.info("关注卖家/店铺:{}", map);
		try {
			userAttentionService.addAttention(this.getCurrentUserId(), (String) map.get("sellerID"));
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 取消关注卖家/店铺 @param Map @return BaseReturnVo<Object> @throws
	 */
	@SuppressWarnings("static-access")
	@RequiresAuthentication
	@RequestMapping(value = "/removeAttention", method = RequestMethod.POST)
	@Log(name="取消关注店铺",model="关注模块")
	public BaseReturnVo<Object> delAttention(@RequestBody Map<String, Object> map) {
		logger.info("取消关注卖家/店铺:{}", map);
		try {
			userAttentionService.delAttention(this.getCurrentUserId(), (String) map.get("sellerID"));
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 收藏商品 @param Map @return BaseReturnVo<Object> @throws
	 */
	@SuppressWarnings("static-access")
	@RequiresAuthentication
	@RequestMapping(value = "/addFavorite", method = RequestMethod.POST)
	@Log(name="收藏商品",model="收藏模块")
	public BaseReturnVo<Object> addFavorite(@RequestBody Map<String, Object> map) {
		logger.info("收藏商品:{}", map);
		try {
			userAttentionService.addFavorite(this.getCurrentUserId(), (String) map.get("goodsID"));
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 取消收藏商品 @param Map @return BaseReturnVo<Object> @throws
	 */
	@SuppressWarnings("static-access")
	@RequiresAuthentication
	@RequestMapping(value = "/removeFavorite", method = RequestMethod.POST)
	@Log(name="取消收藏商品",model="收藏模块")
	public BaseReturnVo<Object> removeFavorite(@RequestBody Map<String, Object> map) {
		logger.info("取消收藏商品:{}", map);
		try {
			userAttentionService.removeFavorite(this.getCurrentUserId(), (String) map.get("goodsID"));
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 商品是否被收藏 @param Map @return BaseReturnVo<Object> @throws
	 */
	@RequestMapping(value = "/favoriteForGoods", method = RequestMethod.POST)
	public BaseReturnVo<Object> isFavorite(@RequestBody Map<String, Object> map) {
		logger.info("商品是否被收藏:{}", map);
		boolean favoriteType = userAttentionService.isFavorite(getCurrentUserId(), (String) map.get("goodsID"));
		return BaseReturnVo.success(favoriteType);
	}
}
