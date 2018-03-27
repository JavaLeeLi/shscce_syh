package com.visionet.syh_mall.web.account;

import java.util.HashMap;
import java.util.Map;

import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.service.account.UserAccountFlowService;
import com.visionet.syh_mall.vo.AccountFlowVo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.entity.account.UserAccount;
import com.visionet.syh_mall.service.account.UserAccountService;
import com.visionet.syh_mall.web.BaseController;

/**
 * 用户账户Controller
 * @author xiaofb
 * @time 2017年10月13日
 */
@RestController
@RequestMapping(value = "api/account")
public class UserAccountController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserAccountController.class);
	
	
	@Autowired
	private UserAccountService accountService;

	/**
	 * 获取用户账户详情
	 * @param
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/getUserAccount", method = RequestMethod.POST)
	public BaseReturnVo<Object> getUserAccountJournal(){
		logger.info("获取用户账户详情");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			UserAccount account = accountService.getUserAccountInfo(getCurrentUserId());
			logger.info("获取用户账户详情rps:{}",account);
			if(null == account){
				result.put("accountBalance", 0);	//账户余额
				result.put("accountWithDrawal", 0);	//可提现金额
				result.put("accountFrozenAmt", 0);	//冻结金额
				result.put("bindAlipay", null);
				result.put("bindWechat", null);
				result.put("bindUnionpay", null);
			}else{
				result.put("accountBalance", account.getBalance());	//账户余额
				result.put("accountWithDrawal", account.getWithdrawal());	//可提现金额
				result.put("accountFrozenAmt", account.getFrozenAmt() == null?0:account.getFrozenAmt());	//冻结金额
				result.put("bindAlipay", account.getAlipay());
				result.put("bindWechat", account.getWechat());
				result.put("bindUnionpay", account.getUnionpay());
			}
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}  
	
}
