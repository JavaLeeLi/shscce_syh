package com.visionet.syh_mall.web.mobile.shop;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.mobile.ShopService;
import com.visionet.syh_mall.vo.shop.MarketVo;
import com.visionet.syh_mall.vo.shop.ShopVo;
import com.visionet.syh_mall.web.BaseController;

/**
 * @Author DM
 * @version ：2017年8月16日下午6:13:54 店铺业务控制层
 */
@RestController
@RequestMapping(value = "api/shop")
public class ShopController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(ShopController.class);

	@Autowired
	private ShopService shopService;

	/**
	 * 
	 * @Title: queryList @Description: 搜索店铺的方法 @param @param
	 *         qo @param @return @param @throws Exception 设定文件 @return Object
	 *         返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/searchShops", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchShops(@RequestBody Map<String, Object> param) {
		logger.info("搜索店铺的参数:{}", param);
		Map<String, Object> result = null;
		try {
			result = shopService.searchShops(param, getPageInfo(param, Direction.DESC, "updateTime"));
		} catch (RestException e) {
			logger.error("搜索店铺异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索店铺异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}


	/**
	 * 
	 * @Title: getShopDetail @Description:查询店铺详情 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/getShopDetail", method = RequestMethod.POST)
	public BaseReturnVo<Object> getShopDetail(@RequestBody Map<String, String> param) {
		logger.info("店铺详情的参数:{}", param);
		Map<String, Object> result = null;
		try {
			result = shopService.getShopDetail(param.get("shopID"));
		} catch (RestException e) {
			logger.error("查询店铺详情异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("查询店铺详情异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * 
	 * @Title: frozeShop @Description: 冻结店铺 @param @param param @param @return
	 *         设定文件 @return BaseReturnVo <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/frozeShop", method = RequestMethod.POST)
	@Log(name = "冻结店铺", model = "店铺管理模块")
	public BaseReturnVo<Object> frozeShop(@RequestBody Map<String, Object> param) {
		logger.info("冻结店铺的参数:{}", param);
		try {
			shopService.frozeShop((String) param.get("shopID"));
		} catch (RestException e) {
			logger.error("冻结店铺异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("冻结店铺异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: unfrozeShop @Description: 解冻店铺 @param @param param @param @return
	 *         设定文件 @return BaseReturnVo <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/unfrozeShop", method = RequestMethod.POST)
	@Log(name = "解冻店铺", model = "店铺管理模块")
	public BaseReturnVo<Object> unfrozeShop(@RequestBody Map<String, Object> param) {
		logger.info("解冻店铺的参数:{}", param);
		try {
			shopService.unfrozeShop((String) param.get("shopID"));
		} catch (RestException e) {
			logger.error("解冻店铺异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("解冻店铺异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: editShopDetail @Description: 店铺的编辑 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/editShopDetail", method = RequestMethod.POST)
	public BaseReturnVo<Object> editShopDetail(@RequestBody @Valid ShopVo shopVo, BindingResult result) {
		logger.info("编辑店铺的参数:{}", shopVo.toString());
		try {
			shopService.editShopDetail(shopVo);
		} catch (RestException e) {
			logger.error("编辑店铺异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("编辑店铺异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 
	 * @Title: reviewShop @Description: 审核店铺 @param @param param @param @return
	 *         设定文件 @return BaseReturnVo <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/reviewShop", method = RequestMethod.POST)
	@Log(name = "审核店铺", model = "店铺管理模块")
	public BaseReturnVo<Object> reviewShop(@RequestBody Map<String, Object> param) {
		logger.info("审核店铺的参数:{}", param);
		try {
			shopService.reviewShop((String) param.get("shopID"), (Integer) param.get("isApproved"));
		} catch (RestException e) {
			logger.error("审核店铺异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("审核店铺异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 
	 * @Title: getAddonPkgs @Description: 搜索增值服务 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/getAddonPkgs", method = RequestMethod.POST)
	public BaseReturnVo<Object> getAddonPkgs(@RequestBody Map<String, Object> param) {
		logger.info("搜索增值服务的参数:{}", param);
		List<Map<String, Object>> result = null;
		try {
			result = shopService.getAddonPkgs(param);
		} catch (RestException e) {
			logger.error("搜索增值服务异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索增值服务异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * 
	 * @Title: editAddonPkg @Description: 编辑增值服务 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/editAddonPkg", method = RequestMethod.POST)
	@Log(name = "编辑增值服务", model = "店铺管理模块")
	public BaseReturnVo<Object> editAddonPkg(@RequestBody @Valid MarketVo marketVo, BindingResult result) {
		logger.info("编辑增值服务的参数:{}", marketVo.toString());
		try {
			shopService.editAddonPkg(marketVo);
		} catch (RestException e) {
			logger.error("编辑增值服务异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("编辑增值服务异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: getShopBanner @Description:获取店铺banner @param @param
	 *         map @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/getShopInfo", method = RequestMethod.POST)
	public BaseReturnVo<Object> getShopInfo(@RequestBody Map<String, String> param) {
		logger.info("店铺banner的参数:{}", param);
		Map<String, Object> result = null;
		try {
			result = shopService.getShopInfo(param.get("shopId"));
		} catch (RestException e) {
			logger.error("获取店铺banner异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取店铺banner异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: getSaleroom @Description: 获取店铺的营销额 @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/getSaleroom", method = RequestMethod.POST)
	public BaseReturnVo<Object> getSaleroom() {
		logger.info("得到店铺销售额的参数:{}");
		Map<String, Object> result = null;
		try {
			result = shopService.getSaleroom(getCurrentUserId());
		} catch (RestException e) {
			logger.error("得到店铺销售额异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("得到店铺销售额异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: getShopSetting @Description: 获取店铺设置 @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/getShopSetting", method = RequestMethod.POST)
	public BaseReturnVo<Object> getShopSetting(@RequestBody Map<String, String> param) {
		logger.info("获取店铺设置的参数:{}", param);
		Map<String, Object> result = null;
		try {
			result = shopService.getShopSetting(param.get("shopID"));
		} catch (RestException e) {
			logger.error("获取店铺设置异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取店铺设置异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: editShopSetting @Description: 编辑店铺设置 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/editShopSetting", method = RequestMethod.POST)
	@Log(name = "编辑店铺设置", model = "店铺管理模块")
	public BaseReturnVo<Object> editShopSetting(@RequestBody Map<String, String> param) {
		logger.info("编辑店铺设置的参数:{}", param);
		try {
			shopService.editShopSetting(param);
		} catch (RestException e) {
			logger.error("编辑店铺设置异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("编辑店铺设置异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

}
