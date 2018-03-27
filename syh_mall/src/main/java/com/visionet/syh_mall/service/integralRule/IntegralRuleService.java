package com.visionet.syh_mall.service.integralRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.entity.integralRule.IntegralRule;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.integralRule.IntegralRuleRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.vo.IntegralRuleQo;

/**
 * 积分规则模块service层
 * 
 * @author mulongfei
 * @date 2017年8月28日下午1:56:47
 */
@Service
public class IntegralRuleService extends BaseService{
	@Autowired
	private IntegralRuleRepository integralRuleDao;

	/**
	 * 获取积分规则列表 @param @return List<IntegralRule> @throws
	 */
	public List<Map<String, Object>> getIntegralRule(IntegralRuleQo Qo) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//PageRequest page = new PageRequest(Qo.getPageNumber() - 1, Qo.getPageSize());//可能会用到
		Iterable<IntegralRule> findAll = integralRuleDao.findAll();
		for (IntegralRule integralRule : findAll) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ruleID", integralRule.getId());
			map.put("ruleItemName", integralRule.getRuleItemName());
			map.put("ruleItemDesc", integralRule.getRuleItemDesc());
			map.put("ruleItemType", integralRule.getIntegralType());
			map.put("integralNum", integralRule.getIntegralNum());
			map.put("minSumForIntegral", integralRule.getMinSumForIntegral());
			list.add(map);
		}
		return list;
	}

	/**
	 * 编辑积分规则 @param IntegralRule @return @throws
	 */
	public void editIntegralRule(List<IntegralRule> integralRules,String adminId) {
		for (IntegralRule integralRule : integralRules) {
			IntegralRule rule = integralRuleDao.findOne(integralRule.getId());
			if (StringUtils.isEmpty(rule)) {
				throw new RestException("该规则不存在");
			}
			if (!StringUtils.isEmpty(integralRule.getIntegralNum())) {
				rule.setIntegralNum(integralRule.getIntegralNum());
			}
			if (!StringUtils.isEmpty(integralRule.getMinSumForIntegral())) {
				rule.setMinSumForIntegral(integralRule.getMinSumForIntegral());
			}
			integralRuleDao.save(rule);
			
		}
		addLog(null, adminId, "编辑积分规则!");
	}
}
