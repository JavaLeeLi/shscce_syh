package com.visionet.syh_mall.web.mobile.channel;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.visionet.syh_mall.entity.channel.Channel;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.mobile.channel.ChannelService;
import com.visionet.syh_mall.service.scheduler.DynamicScheduledTask;
import com.visionet.syh_mall.vo.ChannelCollctPo;
import com.visionet.syh_mall.vo.ChannelCollctVo;
import com.visionet.syh_mall.vo.GoodsQo;
import com.visionet.syh_mall.vo.user.UserChannelVo;
import com.visionet.syh_mall.web.BaseController;

/**
 * 会员分销模板
 * 
 * @author mulongfei
 * @date 2017年8月23日下午4:51:42
 */
@RestController
@RequestMapping(value = "/api/channel")
public class ChannelController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
	@Autowired
	private ChannelService channelService;

	@Autowired
	DynamicScheduledTask dynamicScheduledTask;

	@RequiresAuthentication
	@RequestMapping(value = "/searchTemplateFee", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchTemplateFee() {
		logger.info("搜索平台手续费模板:{}");
		Map<String, com.visionet.syh_mall.vo.KeyMapping> result = null;
		try {
			result = channelService.searchTemplateFee();
		} catch (RestException e) {
			logger.error("搜索平台手续费模板异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索平台手续费模板异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	@SuppressWarnings("unchecked")
	@RequiresAuthentication
	@RequestMapping(value = "/editTemplateFee", method = RequestMethod.POST)
	@Log(name = "编辑平台手续费模板", model = "分销模块")
	public BaseReturnVo<Object> editTemplateFee(@RequestBody Map<String, Object> param) {
		logger.info("编辑平台手续费模板:{}", param);
		try {
			for (Map.Entry<String, Object> e : param.entrySet()) {
				Map<String, String> value = (Map<String, String>) e.getValue();
				if (null == value.get("chargeID")) {
					throw new RestException("必须传Id,只能进行编辑");
				}
				String id = (String) value.get("chargeID");
				String valueDesc = new BigDecimal(String.valueOf(value.get("valueDesc"))).divide(new BigDecimal(100))
						.toString();
				channelService.editSettlement(id, valueDesc, getCurrentUserId());
			}
		} catch (RestException e) {
			logger.error("编辑平台手续费模板异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("编辑平台手续费模板异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: searchChannel @Description: 搜索分销模板 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/searchChannel", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchChannel(@RequestBody Map<String, Object> param) {
		logger.info("搜索分销模板:{}", param);
		List<Channel> searchChannel = new ArrayList<Channel>();
		try {
			searchChannel = channelService.searchChannel();
		} catch (RestException e) {
			logger.error("搜索分销模板异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索分销模板异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(searchChannel);
	}

	/**
	 * 添加/编辑会员分销模板
	 * 
	 * @author mulongfei
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/addMbrChannelRule", method = RequestMethod.POST)
	@Log(name = "添加/编辑分销模板", model = "分销模块")
	public BaseReturnVo<Object> reviceChannel(@RequestBody UserChannelVo channelVo, @Valid BindingResult result) {
		logger.info("添加/编辑会员分销模板:{}", channelVo.toString());
		try {
			channelService.reviceChannel(channelVo, getCurrentUserId());
		} catch (RestException e) {
			logger.error("添加/编辑会员分销异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加/编辑会员分销异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 删除会员分销模板
	 * 
	 * @author mulongfei
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/delMbrChannelRule", method = RequestMethod.POST)
	@Log(name = "删除会员分销模板", model = "分销模块")
	public BaseReturnVo<Object> delMbrChannelRule(@RequestBody Map<String, String> param) {
		logger.info("删除会员分销模板:{}", param);
		try {
			channelService.delChannel(param.get("mbrChannelRuleID"), getCurrentUserId());
		} catch (RestException e) {
			logger.error("删除会员分销异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除会员分销异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: searchChildMbrs @Description: 搜索下级分销会员 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/searchChildMbrs", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchChildMbrs(@RequestBody Map<String, Object> param) {
		logger.info("搜索分销会员:{}", param);
		Map<String, Object> searchChannel = null;
		try {
			searchChannel = channelService.searchChildMbrs(param, getPageInfo(param, Direction.DESC, "createTime"),
					getCurrentUserId());
		} catch (RestException e) {
			logger.error("搜索分销会员异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索分销会员异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(searchChannel);
	}

	/**
	 * 搜索分销会员
	 * 
	 * @author mulongfei
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchChannelMbr", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchChannelMbr(@RequestBody Map<String, Object> param) {
		logger.info("搜索分销会员:{}", param);
		Map<String, Object> searchChannel = null;
		try {
			searchChannel = channelService.searchChannel(param, getPageInfo(param, Direction.DESC, "createTime"));
		} catch (RestException e) {
			logger.error("搜索分销会员异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索分销会员异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(searchChannel);
	}

	/**
	 * @Title: getSettlementTemplate @Description: 获取结算模板 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/settlementTemplate", method = RequestMethod.POST)
	public BaseReturnVo<Object> getSettlementTemplate() {
		logger.info("获取结算模板 :{}");
		List<Map<String, Object>> result = null;
		try {
			result = channelService.settlementTemplate();
		} catch (RestException e) {
			logger.error("获取结算模板 异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取结算模板 异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: getSettlementTemplate @Description: 获取分销结算模板详情 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/settlementDetails", method = RequestMethod.POST)
	public BaseReturnVo<Object> getSettlementDetails(@RequestBody Map<String, String> param) {
		logger.info("获取分销结算模板详情:{}", param);
		Map<String, Object> result = null;
		try {
			result = channelService.getSettlementDetails(param.get("balanceID"));
		} catch (RestException e) {
			logger.error("获取分销结算模板详情异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取分销结算模板详情异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: saveCommission @Description: 提前结算用户的佣金结算记录 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/saveCommission", method = RequestMethod.POST)
	@Log(name = "生成会员分销结算", model = "分销模块")
	public BaseReturnVo<Object> saveCommission(@RequestBody Map<String, Object> param) {
		logger.info("提前结算用户的佣金结算记录:{}", param);
		try {
			channelService.saveCommission(param);
		} catch (RestException e) {
			logger.error("提前结算用户的佣金结算记录异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("提前结算用户的佣金结算记录异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: commissionSettlement @Description: 佣金结算 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/commissionSettlement", method = RequestMethod.POST)
	@Log(name = "佣金结算", model = "分销模块")
	public BaseReturnVo<Object> commissionSettlement(@RequestBody Map<String, Object> param) {
		logger.info("佣金结算:{}", param);
		try {
			List<String> commissionTallyIds = (List<String>) param.get("commissionTallyIds");
			String refuseReason = (String) param.get("banReason");
			String settlementState = (String) param.get("settlementState");
			for (String commissionTallyId : commissionTallyIds) {
				channelService.commissionSettlement(commissionTallyId, settlementState, refuseReason,
						getCurrentUserId());
			}
		} catch (RestException e) {
			logger.error("佣金结算异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("佣金结算异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/commissionSettlements", method = RequestMethod.POST)
	@Log(name = "批量佣金结算", model = "分销模块")
	public BaseReturnVo<Object> commissionSettlements(@RequestBody Map<String, Object> param) {
		logger.info("批量佣金结算:{}", param);
		try {
			List<String> commissionTallyIds = (List<String>) param.get("commissionTallyIds");
			String settlementState = (String) param.get("settlementState");
			for (String commissionTallyId : commissionTallyIds) {
				channelService.commissionSettlement(commissionTallyId, settlementState, "", getCurrentUserId());
			}
		} catch (RestException e) {
			logger.error("批量佣金结算异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("批量佣金结算异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: searchChannel @Description: 搜索佣金结算列表 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/commissionList", method = RequestMethod.POST)
	public BaseReturnVo<Object> commissionList(@RequestBody Map<String, Object> param) {
		logger.info("搜索佣金结算列表:{}", param);
		Map<String, Object> searchChannel = null;
		try {
			searchChannel = channelService.commissionList(param, getPageInfo(param, Direction.DESC, "tallyStartTime"));
		} catch (RestException e) {
			logger.error("搜索佣金结算列表异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索佣金结算列表异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(searchChannel);
	}

	/**
	 * @Title: getRetailDetails @Description: 分销流水 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/getRetailDetails", method = RequestMethod.POST)
	public BaseReturnVo<Object> getRetailDetails(@RequestBody Map<String, Object> param) {
		logger.info("获取分销会员明细 :{}", param);
		Map<String, Object> result = null;
		try {
			result = channelService.getChannelExports(param, getPageInfo(param, Direction.DESC, "createTime"));
		} catch (RestException e) {
			logger.error("获取分销会员明细 异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取分销会员明细  异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: getRetailDetail @Description: 佣金结算的查看详情 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/getRetailDetail", method = RequestMethod.POST)
	public BaseReturnVo<Object> getRetailDetail(@RequestBody Map<String, Object> param) {
		logger.info("获取分销流水明细 :{}", param);
		Map<String, Object> result = null;
		try {
			result = channelService.getRetailDetail(param, getPageInfo(param, Direction.DESC, "createTime"));
		} catch (RestException e) {
			logger.error("获取分销流水明细 异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取分销流水明细  异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: getRetailDetail @Description: 佣金汇总 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/commissionSummary", method = RequestMethod.POST)
	public BaseReturnVo<Object> commissionSummary(@RequestBody GoodsQo qo) {
		logger.info("佣金汇总 :{}", qo);
		ChannelCollctPo result = null;
		try {
			List<ChannelCollctVo> commissionSummaryList = channelService.commissionSummary(qo);
			result = new ChannelCollctPo();
			List<ChannelCollctVo> subList = commissionSummaryList.subList((qo.getPageIndex() - 1) * qo.getItemCount(),
					qo.getPageIndex() * qo.getItemCount() > commissionSummaryList.size() ? commissionSummaryList.size()
							: qo.getPageIndex() * qo.getItemCount());
			result.setCurPageIndex(qo.getPageIndex());
			result.setHasNext((commissionSummaryList.size() % qo.getItemCount() == 0
					? commissionSummaryList.size() / qo.getItemCount()
					: commissionSummaryList.size() / qo.getItemCount() + 1) > qo.getPageIndex() ? true : false);
			result.setItemCount(commissionSummaryList.size());
			result.setPageCount(subList.size());
			result.setResult(subList);

		} catch (RestException e) {
			logger.error("佣金汇总 异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("佣金汇总 异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}
}
