package com.visionet.syh_mall.service.account;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.utils.AmountUtils;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.MathUtils;
import com.visionet.syh_mall.common.utils.SequenceUUID;
import com.visionet.syh_mall.entity.account.FreezeRecord;
import com.visionet.syh_mall.entity.account.UserAccount;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.OperateLogRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.mbr.FreezeRecordRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.thridAccount.SoaFundService;

/**
 * 冻结记录service
 * @author xiaofb
 * @time 2017年10月30日
 */
@Service
public class FreezeRecordService extends BaseService {
	
	@Autowired
	private FreezeRecordRepository freezdRecordRepo;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private SoaFundService soaFundService;
	@Autowired
	private UserRepository userDao;
	@Autowired
	private OperateLogRepository operateLogDao;
	
//	/**
//	 * 通过业务id获取冻结记录信息
//	 * @param bizId
//	 * @return
//	 */
//	public FreezeRecord findByFreezdRecord(String bizId){
//		FreezeRecord  freezeRecord = freezdRecordRepo.findByBizIdAndUserId(bizId,userId);
//		return freezeRecord;
//	}
	
	/**
	 *  保存冻结记录   资金冻结
	 * @param userId 用户id
	 * @param bizId 业务id
	 * @param amount 冻结金额
	 * @throws Exception
	 */
	public void saveFreezdRecord(String userId,String bizId,BigDecimal amount) throws Exception{
		//账户余额验证
		UserAccount userAccount = userAccountService.getUserAccountInfo(userId);
		//可用金额（余额-冻结金额）
		BigDecimal useAmt = MathUtils.sub(userAccount.getBalance(), userAccount.getFrozenAmt());
		if(useAmt.compareTo(amount)<0){
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.AUCTION2_ERROR.getDesc());
		}
		String bizFreezenNo = SequenceUUID.getOrderIdByUUId("B");
		Long amt = Long.valueOf(AmountUtils.changeY2F(amount.toString()));	//元转分
		//soa资金冻结
		soaFundService.freezeMoney(userId, bizFreezenNo, amt);
		//账户资金冻结
		BigDecimal withdrawal = MathUtils.sub(userAccount.getWithdrawal(), amount);
		userAccount.setWithdrawal(withdrawal);
		userAccount.setFrozenAmt(MathUtils.add(userAccount.getFrozenAmt(), amount));
		userAccountService.saveUserAccount(userAccount);
		//冻结记录保存
		FreezeRecord record = new FreezeRecord();
		record.setBizId(bizId);
		record.setCreateTime(DateUtil.getCurrentDate());
		record.setFreezeAmt(amount);
		record.setFreezeStatus("freeze");
		record.setOrderNo(bizFreezenNo);
		record.setUserId(userId);
		freezdRecordRepo.save(record);
		operateLogDao.save(addLog(null, "adminId",userId+"用户的账户资金被冻结"));
	}
	
	
	/**
	 * 竞价资金解冻
	 * @param bizId
	 * @throws Exception
	 */
	@Transactional
	public void unfreezeAmt(String bizId,String userId) throws Exception{
		FreezeRecord record = freezdRecordRepo.findByBizIdAndUserId(bizId,userId,"freeze");
		Long amt = Long.valueOf(AmountUtils.changeY2F(record.getFreezeAmt().toString()));	//元转分
		//soa资金解冻
		soaFundService.unfreezeMoney(record.getUserId(), record.getOrderNo(), amt);
		//平台账户资金解冻
		UserAccount account = userAccountService.getUserAccountInfo(record.getUserId());
		BigDecimal withdrawal = MathUtils.add(account.getWithdrawal(),record.getFreezeAmt());
		account.setWithdrawal(withdrawal);
		account.setFrozenAmt(MathUtils.sub(account.getFrozenAmt(), record.getFreezeAmt()));
		userAccountService.saveUserAccount(account);
		record.setFreezeStatus("unfreeze");
		freezdRecordRepo.save(record);
		operateLogDao.save(addLog(null, "adminId",userId+"用户账户资金解冻"));		
	}
	
	public String getUserId(String userLoginName){
		List<String> findByloginName = userDao.findByloginName(userLoginName);
		if(findByloginName.size()==0){
			throw new RestException("该用户不存在");
		}
		String userId = findByloginName.get(0);
		return userId;
	}
	
}
