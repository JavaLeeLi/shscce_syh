package com.visionet.syh_mall.repository.integralRule;

import java.util.List;

import com.visionet.syh_mall.entity.integralRule.IntegralRule;
import com.visionet.syh_mall.repository.BaseRepository;
/**
 * 积分规则管理Dao层
 * @author mulongfei
 * @date 2017年8月28日下午1:51:45
 */
public interface IntegralRuleRepository extends BaseRepository<IntegralRule, String>{

	List<IntegralRule> findByIntegralType(int type);

}
