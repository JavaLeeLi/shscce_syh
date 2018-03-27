package com.visionet.syh_mall.web.mobile.mbr;

import java.util.List;
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
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.mbr.GoodsFavoritesService;
import com.visionet.syh_mall.web.BaseController;
/**
 * 收藏列表
 * @author mulongfei
 * @date 2017年9月5日上午11:44:58
 */
@RestController
@RequestMapping(value="/api/mbr")
public class GoodsFavoritesController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(GoodsFavoritesController.class);
	@Autowired
	private GoodsFavoritesService goodsFavoritesService;
	/**
	 * 获取收藏列表
	 * @param Map
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="/getFavorites",method=RequestMethod.POST)
	public BaseReturnVo<Object> getFavorites(@RequestBody Map<String,Object> map){
		logger.info("获取收藏列表:{}", map);
		List<Map<String,Object>> favoritesList=null;
		try {
			favoritesList = goodsFavoritesService.getFavorites((String) map.get("userID"));			
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(favoritesList);
	}
	/**
	 * 获取关注列表
	 * @param Map
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value="/getAttentions",method=RequestMethod.POST)
	public BaseReturnVo<Object> getAttentions(@RequestBody Map<String,Object> map){
		logger.info("获取关注列表:{}",map);
		List<Map<String,Object>> attentionList=null;
		try {
			attentionList = goodsFavoritesService.getAttentions((String) map.get("userID"));
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(attentionList);
	}
}
