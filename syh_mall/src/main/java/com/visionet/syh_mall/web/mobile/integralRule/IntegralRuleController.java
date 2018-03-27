package com.visionet.syh_mall.web.mobile.integralRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.entity.integralRule.IntegralRule;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.integralRule.IntegralRuleService;
import com.visionet.syh_mall.vo.IntegralRuleQo;
import com.visionet.syh_mall.web.BaseController;

/**
 * 积分规则模块
 * 
 * @author mulongfei
 * @date 2017年8月28日下午1:53:37
 */
@RestController
@RequestMapping(value = "/api/mbr")
public class IntegralRuleController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(IntegralRuleController.class);
	@Autowired
	private IntegralRuleService integralRuleService;

	/**
	 * 获取积分规则列表 @param IntegralRuleQo @return BaseReturnVo<Object> @throws
	 */
	@RequestMapping(value = "/getIntegralRule", method = RequestMethod.POST)
	public BaseReturnVo<Object> getIntegralRule(@RequestBody IntegralRuleQo qo) {
		logger.info("获取积分规则列表:{}", qo);
		List<Map<String, Object>> integralRule = null;
		try {
			integralRule = integralRuleService.getIntegralRule(qo);
		} catch (RestException e) {
			e.printStackTrace();
			logger.info("获取积分规则列表异常:{}", e.getMessage());
			throw new RestException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(integralRule);
	}

	/**
	 * 编辑积分规则 @param Map<String,Object> @return BaseReturnVo<Object> @throws
	 */
	@SuppressWarnings("unchecked")
	@RequiresAuthentication
	@RequestMapping(value = "/editIntegralRule", method = RequestMethod.POST)
	@Log(name="编辑积分规则",model="积分模块")
	public BaseReturnVo<Object> editIntegralRule(@RequestBody Map<String, Object> map) {
		logger.info("编辑积分规则:{}", map);
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("ruleInfos");
		if (StringUtils.isEmpty(list)) {
			throw new RestException(BusinessStatus.NULL_LIMIT.getCode(), BusinessStatus.NULL_LIMIT.getDesc());
		}
		List<IntegralRule> arr = new ArrayList<IntegralRule>();
		for (Map<String, Object> map2 : list) {
			IntegralRule integralRule = new IntegralRule();
			integralRule.setId((String) map2.get("ruleID"));
			integralRule.setIntegralNum((Integer) map2.get("integralNum"));
			integralRule.setMinSumForIntegral((Integer) map2.get("minSunForIntegral"));
			arr.add(integralRule);
		}
		try {
			integralRuleService.editIntegralRule(arr,getCurrentUserId());
		} catch (RestException e) {
			e.printStackTrace();
			logger.info("编辑积分规则异常:{}", e.getMessage());
			throw new RestException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}
}
