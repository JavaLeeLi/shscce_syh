package com.visionet.syh_mall.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mbr.UserAccountFlowRepository;
import com.visionet.syh_mall.service.BaseService;

/**
 * 用户账户流水
 * 
 * @author xiaofb
 * @time 2017年9月20日
 */
@Service
public class UserAccountFlowService extends BaseService {

	@Autowired
	private UserAccountFlowRepository accountFlowRepo;
	@Autowired
	private UserRepository userRepo;

	/**
	 * 查询用户账户流水记录
	 * 
	 * @param @userId
	 * @param pageInfo
	 * @return
	 */
	public Page<UserAccountFlow> getAccountFlow(String userLoginName, PageInfo pageInfo) {
		User user = userRepo.findByLoginNameAndIsDeleted(userLoginName, 0);
		if(StringUtils.isEmpty(user)){
			throw new RestException("用户不存在");
		}
		Page<UserAccountFlow> page = accountFlowRepo.findByUserIdAndStatus(user.getId(), "success",
				this.buildPageRequest(pageInfo));
		return page;
	}

	/**
	 * @Title: getUserAccountDetails @Description: 查看用户账户详情 @param @return
	 * 设定文件 @return UserAccountFlow 返回类型 @throws
	 */
	public UserAccountFlow getUserAccountDetails(String userId,String orderNo) {
		return accountFlowRepo.findByUserIdAndOrderNo(userId, orderNo);
	}

	/**
	 * 插入账户流水记录
	 * 
	 * @param userAccountFlow
	 */
	public UserAccountFlow addUserAccountFlow(UserAccountFlow userAccountFlow) {
		User user = userRepo.findOne(userAccountFlow.getUserId());
		if (null == user) {
			throw new RestException("用户信息有误");
		}
		return accountFlowRepo.save(userAccountFlow);
	}

	/**
	 * 修改支付流水状态为 支付成功
	 * 
	 * @param userId
	 * @param orderNo
	 */
	public void modifyFlowStatus(String userId, String orderNo) {
		UserAccountFlow accountFlow = accountFlowRepo.findByUserIdAndOrderNo(userId, orderNo);
		accountFlow.setUpdateTime(DateUtil.getCurrentDate());
		accountFlow.setStatus("success");
		accountFlowRepo.save(accountFlow);
	}
}
