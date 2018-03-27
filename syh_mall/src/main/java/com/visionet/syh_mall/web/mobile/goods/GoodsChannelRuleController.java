package com.visionet.syh_mall.web.mobile.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.visionet.syh_mall.vo.GoodsVo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.mobile.GoodsChannelRuleService;
import com.visionet.syh_mall.service.mobile.channel.GoodsChannelVo;
import com.visionet.syh_mall.vo.GoodsChannalVo;
import com.visionet.syh_mall.vo.GoodsQo;
import com.visionet.syh_mall.web.BaseController;

/**
 * 商品分销的Controller
 * 
 * @author chenghongzhan
 * @version 2017年8月23日 下午6:14:01
 * 
 */
@RestController
@RequestMapping(value = "api/channel")
public class GoodsChannelRuleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(GoodsChannelRuleController.class);

	@Autowired
	private GoodsChannelRuleService channelRuleService;
	
	

	/**
	 * @Title: delGoodsChannelRule @Description: 商品分销模块的删除 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "delGoodsChannelRule", method = RequestMethod.POST)
	@Log(name = "删除商品分销模板", model = "商品分销模块")
	public BaseReturnVo<Object> delGoodsChannelRule(@RequestBody Map<String, String> param) {
		logger.info("删除商品分销模块的方法{}", param);
		try {
			channelRuleService.delGoodsChannelRule(param.get("goodsChannelRuleID"), getCurrentUserId());
		} catch (RestException e) {
			logger.error("删除商品分销模块异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除商品分销模块异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: goodsChannelRule @Description: 商品分销模板详情 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/goodsChannelRule", method = RequestMethod.POST)
	@Log(name = "商品分销模板详情", model = "商品分销模块")
	public BaseReturnVo<Object> goodsChannelRule(@RequestBody Map<String, String> param) {
		logger.info("商品分销模块详情的方法{}", param);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = channelRuleService.goodsChannelRule(param.get("goodsChannelRuleID"));
		} catch (RestException e) {
			logger.error("商品分销模块详情异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("商品分销模块详情异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: getGoodsChannelRule @Description: 配置商品分销模板列表 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/getGoodsChannelRule", method = RequestMethod.POST)
	public BaseReturnVo<Object> getGoodsChannelRule(@RequestBody Map<String, Object> param) {
		logger.info("配置商品分销:{}", param);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = channelRuleService.getGoodsChannelRule((String) param.get("shopID"),
					getPageInfo(param, Direction.DESC, "updateTime"));
		} catch (RestException e) {
			logger.error("配置商品分销异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("配置商品分销异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: configGoodsChannel @Description: 商家用户管理配置商品分销模板 @param @param
	 *         channelRuleVo @param @param result @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "configGoodsChannel", method = RequestMethod.POST)
	@Log(name = "商品分销模板配置", model = "商品分销模块")
	public BaseReturnVo<Object> configGoodsChannel(@RequestBody @Valid GoodsChannalVo channelRuleVo,
			BindingResult result) {
		logger.info("商家用户管理配置商品分销的方法{}", channelRuleVo.toString());
		try {
			channelRuleService.configGoodsChannel(channelRuleVo, getCurrentUserId());
		} catch (RestException e) {
			logger.error("商家用户管理配置商品分销异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("商家用户管理配置商品分销异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: selectFormwork @Description: 商品选择模板 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "selectFormwork", method = RequestMethod.POST)
	@Log(name = "商品选择模板", model = "商品分销模块")
	public BaseReturnVo<Object> selectFormwork(@RequestBody Map<String, String> param) {
		logger.info("商品选择模板的方法{}", param);
		try {
			String goodsID = param.get("goodId");
			String formworklId = param.get("formworklId");
			channelRuleService.selectFormwork(goodsID, formworklId);
		} catch (RestException e) {
			logger.error("商品选择模板异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("商品选择模板异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: commodityDistribution @Description: 分享商品 @param @param
	 *         channelRuleVo @param @param result @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "commodityDistribution", method = RequestMethod.POST)
	@Log(name = "分享商品", model = "分享商品模块")
	public BaseReturnVo<Object> commodityDistribution(@RequestBody Map<String, String> param) {
		logger.info("分享商品的方法{}", param);
		try {
			String goodsID = param.get("goodsID");
			String shareCode = param.get("sharingCode");
			channelRuleService.commodityDistribution(goodsID, shareCode);
		} catch (RestException e) {
			logger.error("分享商品异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("分享商品异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("分享成功");
	}

	/**
	 * @Title: getGoodsPromotion @Description: 店铺商品分销 @param @param
	 * param @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "getGoodsChannel", method = RequestMethod.POST)
	@Log(name = "商品分销", model = "店铺商品分销")
	public BaseReturnVo<Object> getGoodsChannel(@RequestBody Map<String, Object> param){
		logger.info("店铺商品分销的方法{}", param);
		Map<String,Object> result = null;
		try {
			channelRuleService.getGoodsChannel(param, getPageInfo(param, Direction.DESC, "updateTime"));
		} catch (RestException e) {
			logger.error("店铺商品分销异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("店铺商品分销异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);

	}



	/**
	 * @Title: getGoodsPromotion @Description: 商品分销汇总 @param @param
	 * param @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "getGoodsPromotion", method = RequestMethod.POST)
	@Log(name = "商品推广汇总", model = "商品推广汇总")
	public BaseReturnVo<Object> getGoodsPromotion(@RequestBody GoodsQo qo) {
		logger.info("商品推广汇总的方法{}", qo);
		GoodsChannelVo result = null;
		try {
			qo.setUserId(getCurrentUserId());
			Page<String> page = channelRuleService.searchChannelGoods(qo);
			List<Map<String,Object>> goodsPromotion = channelRuleService.getGoodsPromotion(page);
			result = new GoodsChannelVo();
			result.setCurPageIndex(qo.getPageIndex());
			result.setHasNext(page.hasNext());
			result.setItemCount(page.getTotalElements());
			result.setPageCount(page.getTotalPages());
			result.setResult(goodsPromotion);
			return BaseReturnVo.success(result);
		} catch (RestException e) {
			logger.error("商品推广汇总异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("商品推广汇总异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

}
