package com.visionet.syh_mall.service.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MathUtils;
import com.visionet.syh_mall.common.utils.SequenceUUID;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.entity.UserAuthentication;
import com.visionet.syh_mall.entity.account.DrawCash;
import com.visionet.syh_mall.entity.account.UserAccount;
import com.visionet.syh_mall.entity.account.UserBank;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.OperateLogRepository;
import com.visionet.syh_mall.repository.UserAuthenticationRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.finance.DrawCashRepository;
import com.visionet.syh_mall.repository.finance.ExportWithdrawImpl;
import com.visionet.syh_mall.repository.mbr.UserBankRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.account.UserAccountFlowService;
import com.visionet.syh_mall.service.account.UserAccountService;
import com.visionet.syh_mall.service.export.ExportWithdrawVo;
import com.visionet.syh_mall.service.thridAccount.SoaAccountService;
import com.visionet.syh_mall.service.thridAccount.SoaWithdrawApplyService;

/**
 * 提现service
 * 
 * @author xiaofb
 * @time 2017年10月20日
 */
@Service
public class DrawCashService extends BaseService {
	@Autowired
	private DrawCashRepository drawCashRepo;
	@Autowired
	private UserBankRepository userBankRepo;
	@Autowired
	private UserAccountService accountService;
	@Autowired
	private UserAccountFlowService accFlowService;
	@Autowired
	private OperateLogRepository operateLogDao;
	@Autowired
	private KeyMappingRepository keyMappingDao;
	@Autowired
	private ExportWithdrawImpl exportWithdrawImpl;
	@Autowired
	private SoaWithdrawApplyService soaWithdrawApplyService;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private UserAuthenticationRepository userAuthenticationDao;
	// /**
	// * 回调修改状态
	// * @param userId
	// * @param userBankId
	// */
	// public void insertDrawCash(String userId,String userBankId,BigDecimal
	// drawAmt,String orderNo){
	// DrawCash drawCash = drawCashRepo.findByDrawOrderNo(orderNo);
	// if(!"withdrawal_review".equals(drawCash.getStatusCode())){
	// return;
	// }
	// UserBank userBank = userBankRepo.findOne(userBankId);
	// if(null == userBank){
	// throw new RestException("提现银行卡信息有误");
	// }
	// drawCash.setStatusCode("withdrawal_accepted");
	// drawCash.setUpdateTime(DateUtil.getCurrentDate());
	// drawCash.setDrawOrderNo(orderNo);
	// drawCashRepo.save(drawCash);
	// //修改账户金额
	// UserAccount userAccount = accountService.getUserAccountInfo(userId);
	// BigDecimal useWithdrawalAmt = MathUtils.sub(userAccount.getWithdrawal(),
	// drawAmt);
	// BigDecimal useBalance = MathUtils.sub(userAccount.getBalance(), drawAmt);
	// userAccount.setWithdrawal(useWithdrawalAmt);
	// userAccount.setUpdateTime(DateUtil.getCurrentDate());
	// userAccount.setBalance(useBalance);
	// accountService.saveUserAccount(userAccount);
	// }

	/**
	 * @Title: withdrawalInstall @Description: 提现设置 @param @param param
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void withdrawalInstall(Map<String, Object> param) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) param.get("cashTimes");
		for (Map<String, Object> map : list) {
			KeyMapping keyMap = keyMappingDao.findOne((String) map.get("cashId"));
			keyMap.setValueDesc((String) map.get("cashValue"));
			keyMappingDao.save(keyMap);
		}
	}

	/**
	 * @Title: getWithdrawals @Description: 得到提现管理 @param @return 设定文件 @return
	 *         List<Map<String,Object>> 返回类型 @throws
	 */
	public List<Map<String, Object>> getWithdrawals() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<KeyMapping> list = keyMappingDao.findByDdGroupCode(2000);
		for (KeyMapping keyMapping : list) {
			Map<String, Object> withdrawals = new HashMap<String, Object>();
			withdrawals.put("CashValue", keyMapping.getValueDesc());
			withdrawals.put("CashId", keyMapping.getId());
			withdrawals.put("CashCode", keyMapping.getKeyCode());
			result.add(withdrawals);
		}
		return result;
	}

	/**
	 * 提现申请
	 * 
	 * @param userId
	 * @param amount
	 * @param userBankId
	 * @throws Exception
	 */
	public void thridPayReqDrawCash(String userId, String amount, String userBankId) throws Exception {
		UserBank userBank = null;
		User user = userDao.findById(userId);
		String orderNo = SequenceUUID.getOrderIdByUUId("B");
		if (user.getMemberType() == 3) {
			if (userBankId != null) {
				userBank = userBankRepo.findOne(userBankId);
				if (null == userBank) {
					throw new RestException("提现银行卡信息有误");
				}
			} else {
				throw new RestException("未绑定银行卡");
			}
		}
		UserAccount userAccount = accountService.getUserAccountInfo(userId);
		if (userAccount.getWithdrawal().compareTo(new BigDecimal(amount)) < 0) {
			throw new RestException("可提现余额不足");
		}
		// 修改账户金额
		BigDecimal useWithdrawalAmt = MathUtils.sub(userAccount.getWithdrawal(), new BigDecimal(amount));
		BigDecimal useBalance = MathUtils.sub(userAccount.getBalance(), new BigDecimal(amount));
		userAccount.setWithdrawal(useWithdrawalAmt);
		userAccount.setUpdateTime(DateUtil.getCurrentDate());
		userAccount.setBalance(useBalance);
		accountService.saveUserAccount(userAccount);
		// 提现记录
		DrawCash drawCash = new DrawCash();
		if(null!=userBank){
			drawCash.setAccountName(userBank.getUserName());
		}else{
			drawCash.setAccountName(user.getLoginName());
		}
		drawCash.setCreateTime(DateUtil.getCurrentDate());
		if (user.getMemberType() == 2) {
			if (userBankId != null) {
				drawCash.setDrawAccount(userBankId);
			}
			UserAuthentication userAuthentication = userAuthenticationDao.findByUserId(userId);
			drawCash.setDrawAccount(userAuthentication.getAccountNo());
		} else {
			drawCash.setDrawAccount(userBank.getCardNo());
		}
		drawCash.setDrawAmt(new BigDecimal(amount));
		drawCash.setDrawWay(null);
		drawCash.setStatusCode("withdrawal_review"); // 待审批
		drawCash.setUpdateTime(DateUtil.getCurrentDate());
		drawCash.setUserId(userId);
		drawCash.setDrawOrderNo(orderNo);
		drawCashRepo.save(drawCash);

		UserAccountFlow flow = new UserAccountFlow();
		flow.setAmt(drawCash.getDrawAmt());
		flow.setContent("提现中");
		flow.setCreateTime(DateUtil.getCurrentDate());
		flow.setOrderNo(orderNo);
		flow.setStatus("success");
		flow.setBusinessType(null);
		flow.setFlowType("提现");
		flow.setType(1);
		flow.setUserId(drawCash.getUserId());
		flow.setUpdateTime(DateUtil.getCurrentDate());
		accFlowService.addUserAccountFlow(flow);
	}

	/**
	 * 提现到对公账户
	 * @param userId
	 * @param
	 * @param amount
	 */
	public String auditing(String userId, String amount,String personal) throws Exception {
		User user = userDao.findById(userId);
		String orderNo = SequenceUUID.getOrderIdByUUId("B");
		UserAccount userAccount = accountService.getUserAccountInfo(userId);
		UserAuthentication userAuthentication = userAuthenticationDao.findByUserId(userId);
		if (userAccount.getWithdrawal().compareTo(new BigDecimal(amount)) < 0) {
			throw new RestException("可提现余额不足");
		}
		Long amt = new BigDecimal(AmountUtils.changeY2F(new BigDecimal(amount).longValue())).longValue(); // 元转分

		// 修改账户金额
		BigDecimal useWithdrawalAmt = MathUtils.sub(userAccount.getWithdrawal(), new BigDecimal(amount));
		BigDecimal useBalance = MathUtils.sub(userAccount.getBalance(), new BigDecimal(amount));
		userAccount.setWithdrawal(useWithdrawalAmt);
		userAccount.setUpdateTime(DateUtil.getCurrentDate());
		userAccount.setBalance(useBalance);
		accountService.saveUserAccount(userAccount);
        String accountNo = userAuthentication.getAccountNo();
        if(personal!=null){
            List<UserBank> byUserId = userBankRepo.findByUserId(userId);
            if(byUserId.size()<=0){
                throw new RestException("用户未绑卡，请先绑卡");
            }
            accountNo=byUserId.get(0).getCardNo();
        }
		DrawCash drawCash = new DrawCash();
		drawCash.setAccountName(user.getLoginName());
		drawCash.setCreateTime(DateUtil.getCurrentDate());
		drawCash.setDrawAmt(new BigDecimal(amount));
		drawCash.setDrawWay(null);
		drawCash.setDrawAccount(accountNo);
		drawCash.setStatusCode("withdrawal_accepted");
		drawCash.setUpdateTime(DateUtil.getCurrentDate());
		drawCash.setUserId(userId);
		drawCash.setDrawOrderNo(orderNo);
		drawCash=drawCashRepo.save(drawCash);


		soaWithdrawApplyService.withdrawApply(orderNo, userId, amt,accountNo,drawCash,personal);
		UserAccountFlow flow = new UserAccountFlow();
		flow.setAmt(drawCash.getDrawAmt());
		flow.setContent("等待确定");
		flow.setCreateTime(DateUtil.getCurrentDate());
		flow.setOrderNo(orderNo);
		flow.setStatus("success");
		flow.setBusinessType(null);
		flow.setFlowType("提现");
		flow.setType(1);
		flow.setUserId(drawCash.getUserId());
		flow.setUpdateTime(DateUtil.getCurrentDate());
		accFlowService.addUserAccountFlow(flow);
		return orderNo;
	}



	/**
	 * 提现审核
	 * 
	 * @param auditingId
	 *            提现申请id
	 * @throws Exception
	 */
	@Transactional
	public void auditing(String auditingId, String userId, String reason, String statusCode, String adminId)
			throws Exception {
		DrawCash drawCash = drawCashRepo.findOne(auditingId);
		if ("platform_audit".equals(drawCash.getStatusCode()) || "withdrawal_accepted".equals(drawCash.getStatusCode())
				|| "withdrawal_refused".equals(drawCash.getStatusCode())) {
			throw new RestException("请勿重复审核");
		}
		// 拒绝
		if ("withdrawal_refused".equals(statusCode)) {
			UserAccount userAccount = accountService.getUserAccountInfo(drawCash.getUserId());
			// 修改账户金额
			BigDecimal useWithdrawalAmt = MathUtils.add(userAccount.getWithdrawal(), drawCash.getDrawAmt());
			BigDecimal useBalance = MathUtils.add(userAccount.getBalance(), drawCash.getDrawAmt());
			userAccount.setWithdrawal(useWithdrawalAmt);
			userAccount.setUpdateTime(DateUtil.getCurrentDate());
			userAccount.setBalance(useBalance);
			accountService.saveUserAccount(userAccount);
			// 修改提现状态
			drawCash.setRejectReason(reason);
			drawCash.setStatusCode(statusCode);
			drawCash.setReviewBy(userId);
			drawCashRepo.save(drawCash);
			operateLogDao.save(addLog(null, adminId, "管理员审核提现申请，结果为拒绝！"));
			return;
		}
		// 通过
		if ("withdrawal_accepted".equals(statusCode)) {

			Long amt = new BigDecimal(AmountUtils.changeY2F(drawCash.getDrawAmt().longValue())).longValue(); // 元转分
			// 修改提现状态
			drawCash.setStatusCode("platform_audit");
			drawCash.setReviewBy(userId);
			drawCash.setUpdateTime(DateUtil.getCurrentDate());
			drawCashRepo.save(drawCash);
			// thridPay提现申请
			soaWithdrawApplyService.withdrawApply(drawCash.getDrawOrderNo(), drawCash.getUserId(), amt, drawCash.getDrawAccount(),
					drawCash,null);
			operateLogDao.save(addLog(null, adminId, "管理员审核提现申请，结果为通过！"));
			// 添加流水记录

		}
	}

	public List<ExportWithdrawVo> exportWithdraw(Map<String, Object> param) {
		List<ExportWithdrawVo> list = new ArrayList<ExportWithdrawVo>();
		List<Object[]> drawCashList = exportWithdrawImpl.getDrawCashList(param);
		for (Object[] objects : drawCashList) {
			ExportWithdrawVo exportWithdrawVo = new ExportWithdrawVo();
			exportWithdrawVo.setUserLoginName((String) objects[0]);
			String amt = objects[1].toString();
			int indexOf = amt.indexOf(".");
			String lastAmt = amt.substring(0, indexOf + 3);
			exportWithdrawVo.setWithdrawAmt(new BigDecimal(lastAmt));
			exportWithdrawVo.setCardNo((String) objects[2]);
			exportWithdrawVo.setCardUserName((String) objects[3]);
			if ("withdrawal_review".equals((String) objects[4])) {
				exportWithdrawVo.setStatus("待审批");
			}
			if ("withdrawal_accepted".equals((String) objects[4])) {
				exportWithdrawVo.setStatus("已通过");
			}
			if ("withdrawal_refused".equals((String) objects[4])) {
				exportWithdrawVo.setStatus("已拒绝");
			}
			exportWithdrawVo.setApplyTime((Date) objects[5]);
			list.add(exportWithdrawVo);
		}
		return list;
	}
}
