package com.visionet.syh_mall.service.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MathUtils;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.account.UserAccount;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.mbr.UserAccountImpl;
import com.visionet.syh_mall.repository.mbr.UserAccountRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.UserService;
import com.visionet.syh_mall.service.order.OrderService;
import com.visionet.syh_mall.vo.user.UserAccountVo;

/**
 * 用户账户service
 * @author xiaofb
 * @time 2017年10月13日
 */
@Service
public class UserAccountService extends BaseService {
	@Autowired
	private UserAccountRepository userAccountRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private UserAccountImpl userAccountImpl;
	
	/**
	 * 获取用户账户信息
	 * @param userId
	 * @return
	 */
	public UserAccount getUserAccountInfo(String userId){
		UserAccount userAccount = userAccountRepo.findByUserId(userId);
		return userAccount;
	}
	
	/**
	 * 新增账户金额(元)
	 * @param userId
	 * @param balance 余额
	 * @param withdrawal 可提现金额
	 * @param frozenAmt 冻结金额
	 * @return
	 */
	public void addAccountAmt(String userId,BigDecimal balance,BigDecimal withdrawal,BigDecimal frozenAmt){
		User user = userService.findUserById(userId);
		if(null == user){
			throw new RestException("用户不存在");
		}
		UserAccount account = userAccountRepo.findByUserId(userId);
		
		if(null != balance && null != account){	//增加余额
			BigDecimal amt = MathUtils.add(account.getBalance(), balance);
			account.setBalance(amt);
		}
		if(null != withdrawal && null != account){	//增加可提现金额
			BigDecimal amt = MathUtils.add(account.getWithdrawal(), withdrawal);
			account.setWithdrawal(amt);
		}
		if(null != frozenAmt && null != account){	//增加冻结金额
			BigDecimal amt = MathUtils.add(account.getFrozenAmt(), frozenAmt);
			account.setFrozenAmt(amt);
		}
		account.setUpdateTime(DateUtil.getCurrentDate());
		userAccountRepo.save(account);
	}
	
	/**
	 * 扣减账户余额
	 * @param userId
	 * @param balance
	 */
	public void deductionAccountAmt(String userId,BigDecimal amount){
		User user = userService.findUserById(userId);
		if(null == user){
			throw new RestException("用户不存在");
		}
		UserAccount account = userAccountRepo.findByUserId(userId);
		account.setBalance(MathUtils.sub(account.getBalance(), amount));
		account.setWithdrawal(MathUtils.sub(account.getWithdrawal(), amount));
		account.setUpdateTime(DateUtil.getCurrentDate());
		userAccountRepo.save(account);
	}
	
	/**
	 * 创建用户账户
	 * @param account
	 */
	public void saveUserAccount(UserAccount account){
		userAccountRepo.save(account);
	}

	public List<UserAccountVo> searchUserAccount(Map<String, String> param) {
		List<Object[]> userAccount = userAccountImpl.getUserAccount(param);
		List<UserAccountVo> result = new ArrayList<UserAccountVo>();
		for (Object[] objects : userAccount) {
			UserAccountVo userAccountVo = new UserAccountVo();
			userAccountVo.setBalance(OrderService.countAmt((BigDecimal) objects[2]));
			userAccountVo.setFrozenAmt(OrderService.countAmt((BigDecimal) objects[4]));
			userAccountVo.setLoginName((String) objects[0]);
			userAccountVo.setUserName((String) objects[1]);
			userAccountVo.setWithdrawal(OrderService.countAmt((BigDecimal) objects[3]));
			result.add(userAccountVo);
		}
		return result;
	}
}
