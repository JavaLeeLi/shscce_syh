package com.visionet.syh_mall.web.account;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.service.account.UserAccountFlowService;
import com.visionet.syh_mall.vo.AccountFlowVo;
import com.visionet.syh_mall.web.BaseController;

/**
 * 用户账户流水
 * 
 * @author xiaofb
 * @time 2017年9月20日
 */
@RestController
@RequestMapping(value = "api/account")
public class UserAccountFlowController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(UserAccountFlowController.class);

	@Autowired
	private UserAccountFlowService accountFlowService;

	/**
	 * 获取用户账户收支明细
	 * 
	 * @param param
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/getUserAccountJournal", method = RequestMethod.POST)
	public BaseReturnVo<Object> getUserAccountJournal(@RequestBody Map<String, Object> param) {
		logger.info("获取用户账户收支明细请求参数{}", param);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Page<UserAccountFlow> page = accountFlowService.getAccountFlow(getCurrentLoginName(),
					getPageInfo(param, Direction.DESC, "updateTime"));
			result.put("itemCount", page.getTotalElements());
			result.put("pageCount", page.getTotalPages());
			result.put("curPageIndex", page.getNumber() + 1);
			result.put("hasNext", page.hasNext() ? true : false);
			result.put("journalInfos", AccountFlowVo.convert(page.getContent()));
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: getUserAccountDetails @Description: 获取用户账户流水详情 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/getUserAccountDetails", method = RequestMethod.POST)
	public BaseReturnVo<Object> getUserAccountDetails(@RequestBody Map<String, Object> param) {
		logger.info("获取用户账户流水详情请求参数{}", param);
		UserAccountFlow flow = null;
		try {
			flow = accountFlowService.getUserAccountDetails(getCurrentUserId(), (String) param.get("orderNo"));
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(flow);
	}

}
