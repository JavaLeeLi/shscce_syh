
package com.visionet.syh_mall.web.mobile.shop;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.visionet.syh_mall.entity.marketing.GroupBuy;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.mobile.ConponsService;
import com.visionet.syh_mall.vo.CouponsVo;
import com.visionet.syh_mall.vo.GroupBuyVo;
import com.visionet.syh_mall.vo.shop.ComboPatchVo;
import com.visionet.syh_mall.vo.shop.DiscountTimeVo;
import com.visionet.syh_mall.vo.shop.FulfilRemitVo;
import com.visionet.syh_mall.web.BaseController;

@RestController
@RequestMapping(value = "api/marketing")
public class CouponsController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(CouponsController.class);

	@Autowired
	private ConponsService conponService;

	/**
	 * @Title: searchCoupons @Description: 搜索优惠券 @param @param
	 *         conponsVo @param @param result @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */	
	@RequestMapping(value = "searchCoupons", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchCoupons(@RequestBody Map<String, Object> param) {
		logger.info("搜索优惠券的参数:{}", param);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = conponService.searchCoupons(param, getPageInfo(param, Direction.DESC,"isAvailable"));
		} catch (RestException e) {
			logger.error("搜索优惠券异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索优惠券异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}
	
	@RequiresAuthentication
	@RequestMapping(value = "searchCoupon", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchCoupon(@RequestBody Map<String, Object> param) {
		logger.info("搜索优惠券的参数:{}", param);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			param.put("userId", getCurrentUserId());
			result = conponService.searchCoupon(param, getPageInfo(param, Direction.DESC,"updateTime"));
		} catch (RestException e) {
			logger.error("搜索优惠券异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索优惠券异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: addCoupon @Description: 添加编辑优惠券 @param @param
	 *         couponsVo @param @param result @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "addCoupon", method = RequestMethod.POST)
	@Log(name="添加/编辑优惠券",model="优惠券模块")
	public BaseReturnVo<Object> addCoupon(@RequestBody @Valid CouponsVo couponsVo, BindingResult result) {
		logger.info("添加编辑优惠券的参数:{}", couponsVo.toString());
		try {
			conponService.addCoupon(couponsVo);
		} catch (RestException e) {
			logger.error("添加编辑优惠券异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加编辑优惠券异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: disableCoupon @Description: 删除优惠券 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "disableCoupon", method = RequestMethod.POST)
	@Log(name="删除优惠券",model="优惠券模块")
	public BaseReturnVo<Object> disableCoupon(@RequestBody Map<String, String> param) {
		logger.info("删除优惠券的参数:{}", param);
		try {
			conponService.disableCoupon(param.get("couponID"));
		} catch (RestException e) {
			logger.error("删除优惠券异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除优惠券异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: getFulfils @Description: 满减满送列表 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "getFulfils", method = RequestMethod.POST)
	public BaseReturnVo<Object> getFulfils(@RequestBody Map<String, String> param) {
		logger.info("获取满减满送列表的参数:{}", param);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			result = conponService.getFulfils(param);
		} catch (RestException e) {
			logger.error("获取满减满送列表异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取满减满送列表异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: addFulfil @Description: 添加编辑满减满送 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "addFulfil", method = RequestMethod.POST)
	@Log(name="添加/编辑满减满送",model="满减满送模块")
	public BaseReturnVo<Object> addFulfil(@RequestBody @Valid FulfilRemitVo fulfilRemitVo, BindingResult result) {
		logger.info("添加编辑满减满送的参数:{}", fulfilRemitVo.toString());
		try {
			conponService.addFulfil(fulfilRemitVo);
		} catch (RestException e) {
			logger.error("添加编辑满减满送异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加编辑满减满送异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: delFulfil @Description: 删除满减满送 @param @param param @param @return
	 *         设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "delFulfil", method = RequestMethod.POST)
	@Log(name="删除满减满送",model="满减满送模块")
	public BaseReturnVo<Object> delFulfil(@RequestBody Map<String, String> param) {
		logger.info("删除满减满送的参数:{}", param);
		try {
			conponService.delFulfil(param.get("fulfilID"));
		} catch (RestException e) {
			logger.error("删除满减满送异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除满减满送异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: searchDiscounts @Description: 搜索限时折扣 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "searchDiscounts", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchDiscounts(@RequestBody Map<String, Object> param) {
		logger.info("搜索限时折扣的参数:{}", param);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = conponService.searchDiscounts(param, getPageInfo(param, Direction.DESC,"updateTime"));
		} catch (RestException e) {
			logger.error("搜索限时折扣的异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索限时折扣的异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: addDiscount @Description: 添加限时折扣 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "addDiscount", method = RequestMethod.POST)
	@Log(name="添加限时折扣",model="限时折扣模块")
	public BaseReturnVo<Object> addDiscount(@RequestBody @Valid DiscountTimeVo discountTimeVo, BindingResult result) {
		logger.info("添加限时折扣的参数:{}", discountTimeVo.toString());
		try {
			conponService.addDiscount(discountTimeVo);
		} catch (RestException e) {
			logger.error("添加限时折扣的异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加限时折扣的异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: delDiscount @Description: 删除限时折扣 @param @param
	 *         discountTimeVo @param @param result @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "delDiscount", method = RequestMethod.POST)
	@Log(name="删除限时折扣",model="限时折扣模块")
	public BaseReturnVo<Object> delDiscount(@RequestBody Map<String, String> param) {
		logger.info("删除限时折扣的参数:{}", param);
		try {
			conponService.delDiscount(param.get("discountID"));
		} catch (RestException e) {
			logger.error("删除限时折扣的异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除限时折扣的异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: searchCombos @Description: 搜索套餐搭配 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "searchCombos", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchCombos(@RequestBody Map<String, Object> param) {
		logger.info("搜索套餐搭配的参数:{}", param);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = conponService.searchCombos(param, getPageInfo(param, Direction.DESC,"updateTime"));
		} catch (RestException e) {
			logger.error("搜索套餐搭配的异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索套餐搭配的异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * 删除套餐单配内的商品 @param @return List<Map<String,Object>> @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "delGoodsCombos", method = RequestMethod.POST)
	public BaseReturnVo<Object> delGoodsCombos(@RequestBody Map<String, Object> param){
		logger.info("删除的套餐搭配商品id:{}", param.get("comboId"));
		try {
			conponService.delGoodsCombos((String) param.get("comboId"));
		} catch (RestException e) {
			logger.error("删除套餐搭配商品异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索套餐搭配的异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 搜索商品套餐搭配
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequestMapping(value = "searchGoodsCombos", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchGoodsCombos(@RequestBody Map<String,Object> param) {
		logger.info("搜索商品套餐搭配:{}", param);
		List<Map<String, Object>> result = null;
		try {
			result = conponService.searchGoodsCombos(param);
		} catch (RestException e) {
			logger.error("搜索商品套餐搭配异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索商品套餐搭配异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: addCombo @Description: 添加套餐搭配 @param @param
	 *         comboPatchVo @param @param result @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "addCombo", method = RequestMethod.POST)
	@Log(name="添加套餐搭配",model="套餐搭配模块")
	public BaseReturnVo<Object> addCombo(@RequestBody @Valid ComboPatchVo comboPatchVo, BindingResult result) {
		logger.info("添加限时折扣的参数:{}", comboPatchVo.toString());
		try {
			conponService.addCombo(comboPatchVo);
		} catch (RestException e) {
			logger.error("添加限时折扣的异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加限时折扣的异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: delCombo @Description: 删除套餐搭配 @param @param
	 *         discountTimeVo @param @param result @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "delCombo", method = RequestMethod.POST)
	@Log(name="删除套餐搭配",model="套餐搭配模块")
	public BaseReturnVo<Object> delCombo(@RequestBody Map<String, String> param) {
		logger.info("删除套餐搭配的参数:{}", param);
		try {
			conponService.delCombo(param.get("comboID"));
		} catch (RestException e) {
			logger.error("删除套餐搭配的异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除套餐搭配的异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: searchGroups @Description: 搜索团购 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "searchGroups", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchGroups(@RequestBody Map<String, Object> param) {
		logger.info("搜索团购的参数:{}", param);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = conponService.searchGroups(param, getPageInfo(param, Direction.DESC,"updateTime"));
		} catch (RestException e) {
			logger.error("搜索团购的异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索团购的异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: addGroup @Description: 添加团购活动 @param @param
	 *         groupBuyVo @param @param result @param @return 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "addGroup", method = RequestMethod.POST)
	@Log(name="添加团购活动",model="团购模块")
	public BaseReturnVo<Object> addGroup(@RequestBody @Valid GroupBuyVo groupBuyVo, BindingResult result) {
		logger.info("添加团购活动的参数:{}", groupBuyVo.toString());
		try {
			conponService.addGroup(groupBuyVo);
		} catch (RestException e) {
			logger.error("添加团购活动的异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加团购活动的异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
	
	@RequestMapping(value = "/groupDetil", method = RequestMethod.POST)
	public BaseReturnVo<Object> groupDetil(@RequestBody Map<String,Object> param) {
		logger.info("团购活动详情:{}", param);
		GroupBuyVo groupBuyVo = null;
		try {
			groupBuyVo = conponService.groupDetil(param);
		} catch (RestException e) {
			logger.error("团购活动详情:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("团购活动详情:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(groupBuyVo);
	}

	/**
	 * @Title: disableCoupon @Description: 删除团购 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "delGroup", method = RequestMethod.POST)
	public BaseReturnVo<Object> delGroup(@RequestBody Map<String, String> param) {
		logger.info("删除团购的参数:{}", param);
		try {
			conponService.delGroup(param.get("groupID"));
		} catch (RestException e) {
			logger.error("删除团购异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除团购异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: searchGroupDetails @Description: 搜索组团信息 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "searchGroupDetails", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchGroupDetails(@RequestBody Map<String, Object> param) {
		logger.info("搜索组团信息的参数:{}", param);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = conponService.searchGroupDetails(param, getPageInfo(param, Direction.DESC, "updateTime"));
		} catch (RestException e) {
			logger.error("搜索组团信息的异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索组团信息的异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}
}
