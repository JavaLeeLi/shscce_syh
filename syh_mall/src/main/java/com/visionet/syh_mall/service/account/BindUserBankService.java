package com.visionet.syh_mall.service.account;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAuthentication;
import com.visionet.syh_mall.entity.account.UserBank;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.UserAuthenticationRepository;
import com.visionet.syh_mall.repository.mbr.UserBankRepository;
import com.visionet.syh_mall.service.BaseService;
import com.visionet.syh_mall.service.UserService;
import com.visionet.syh_mall.service.thridAccount.SoaMemberService;
import com.visionet.syh_mall.vo.user.BindBankCard;

/**
 * 绑定用户银行卡service
 * @author xiaofb
 * @time 2017年10月20日
 */
@Service
public class BindUserBankService extends BaseService {
	
	@Autowired
	private UserBankRepository userBankRepo;
	@Autowired
	private SoaMemberService soaMemberService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserAuthenticationRepository userAuthRepo;
	
	/**
	 * 添加用户银行卡
	 * @param userBank
	 * @throws JSONException 
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public void insertUserBank(String cardNo,String userId) throws JSONException{
		//验证用户信息
		User user = userService.findUserById(userId);
		if(!"authentication_personal".equals(user.getUserTypeCode()) && 
				!"authentication_tenant".equals(user.getUserTypeCode())){
			throw new RestException("请先完成个人或商家认证");
		}
		UserAuthentication userAuth = userAuthRepo.findByUserId(userId);
		BindBankCard bindBankCard = new BindBankCard();
		bindBankCard.setBizUserId(userId);
		bindBankCard.setCardNo(cardNo);
		bindBankCard.setIdentityNo(userAuth.getIdCode());
		bindBankCard.setName(userAuth.getUserRealName());
		bindBankCard.setPhone(user.getPhone());
		//请求绑定银行卡
		JSONObject jsonRes = soaMemberService.applyBindBankCard(bindBankCard);
		Map result = jsonRes.getJSONObject("returnValue").getMap();
		//保存用户银行卡信息
		UserBank userBank = new UserBank();
		userBank.setCardNo(bindBankCard.getCardNo());
		//获取卡bin
		JSONObject bankCardBin = soaMemberService.getBankCardBin(cardNo);
		String bankName = (String) bankCardBin.get("bankName");
		String bankCode = (String) bankCardBin.get("bankCode");
		userBank.setBankName(bankName);
		userBank.setBankCode(bankCode);
		userBank.setCreateTime(DateUtil.getCurrentDate());
		userBank.setIdentityNo(bindBankCard.getIdentityNo());
		userBank.setPhone(bindBankCard.getPhone());
		userBank.setStatus("success");
		userBank.setUserName(bindBankCard.getName());
		userBank.setUpdateTime(DateUtil.getCurrentDate());
		userBank.setUserId(bindBankCard.getBizUserId());
		userBankRepo.save(userBank);
	}
	
	/**
	 * 确定绑定银行卡
	 * @param bizUserId
	 * @param tranceNum
	 * @param transDate
	 * @param phone
	 * @param verificationCode
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public void commitBindCard(String bizUserId,String tranceNum,String transDate,String phone,String verificationCode ){
		JSONObject result = soaMemberService.bindBankCard(bizUserId, tranceNum, transDate, phone, verificationCode);
		
		Map map = result.getMap();
		UserBank userBank = userBankRepo.findByTranceNum(tranceNum);
		userBank.setUpdateTime(DateUtil.getCurrentDate());
		userBank.setStatus("success");
		userBankRepo.save(userBank);
	}
	
	public List<UserBank> findUserBankList(String userId){
		List<UserBank> list = userBankRepo.findByUserIdAndStatus(userId,"success");
		return list;
	}
}
