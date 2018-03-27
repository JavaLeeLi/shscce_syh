package com.visionet.syh_mall.service.thridAccount;

import com.visionet.syh_mall.entity.UserAuthentication;
import com.visionet.syh_mall.entity.UserAuthentification;
import ime.service.client.SOAClient;
import ime.service.util.RSAUtil;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.BusinessStatus;

import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.vo.user.BindBankCard;
import com.visionet.syh_mall.web.BaseController;

/**
 * 4
 * @author xiaofb
 * @time 2017年10月10日
 */
@Service
public class SoaMemberService{
	private static final Logger logger = LoggerFactory.getLogger(SoaMemberService.class);
	/**
	 * @throws JSONException
	 * 会员注册
	 * @param String
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject createMember(String bizUserId,String memberType,String source){
		logger.info("会员注册:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("memberType", memberType);//会员类型(2:企业会员,3:个人会员)
			param.put("source", source);//访问终端类型(1:Mobile,2:PC)
			param.put("extendParam", new JSONObject());
			logger.info("会员注册开始请求参数...{}",param);
			response = soaClient.request("MemberService", "createMember", param);
			logger.info("会员注册响应:{}",response);
		}catch(Exception e){
			logger.error("第三方会员注册异常", e);
			e.printStackTrace();
			BaseController.sysException();
		}
		callOfSuccess(response);
		logger.info("会员注册结束...");
		return response;
	}


	/**
	 * @throws JSONException
	 * 发送验证码
	 * @param String
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject sendVerificationCode(String bizUserId,String phone,int verificationCodeType){
		logger.info("发送验证码:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject extendParams = new JSONObject();
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("phone", phone);
			param.put("verificationCodeType", verificationCodeType);//(6为解绑,9为绑卡)
			param.put("extendParams", extendParams);
			response = soaClient.request("MemberService", "sendVerificationCode", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		return response;
	}
	/**
	 * @throws JSONException
	 * 短信验证码验证
	 * @param String
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject validateVerificationCode(String bizUserId,String phone,String verificationCode){
		logger.info("短信验证码验证:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("phone", phone);
			param.put("verificationCodeType", 9);
			param.put("verificationCode", verificationCode);//验证码内容
			response = soaClient.request("MemberService", "validateVerificationCode", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		return response;
	}
	/**
	 * @throws JSONException
	 * 实名认证
	 * @param String
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject setRealName(String bizUserId,Boolean isAuth,String name,String identityNo){
		logger.info("实名认证:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("name", name);
			param.put("isAuth", isAuth);
			param.put("identityType", 1);
			param.put("identityNo", rsaEncrypt(identityNo));
			logger.debug("实名认证开始...");
			response = soaClient.request("MemberService", "setRealName", param);
			logger.info("实名认证:{}",response);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		logger.debug("实名认证结束...");
		return response;
	}
	/**
	 * @Title: setCompanyInfo @Description: 企业认证 @param @param
	 *         bizUserId @param @param isAuth @param @param name @param @param
	 *         identityNo @param @return 设定文件 @return JSONObject 返回类型 @throws
	 */
	public JSONObject setCompanyInfo(UserAuthentication authentication) {
		logger.info("企业认证:{}", authentication);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try {  
			JSONObject companyBasicInfo = new JSONObject();
			companyBasicInfo.put("companyName", authentication.getCompanyName());
//			companyBasicInfo.put("companyAddress", authentication.getUserProvince());
			companyBasicInfo.put("businessLicense", authentication.getLicenseNo());
//			companyBasicInfo.put("telephone", authentication.getLegelPhone());
			companyBasicInfo.put("organizationCode", authentication.getOrganizationCode());
			companyBasicInfo.put("legalName", authentication.getUserRealName());
			companyBasicInfo.put("identityType", authentication.getIdentityType());
			companyBasicInfo.put("legalIds", rsaEncrypt(authentication.getIdCode()));
			companyBasicInfo.put("legalPhone", authentication.getLegelPhone());
			companyBasicInfo.put("accountNo", rsaEncrypt(authentication.getAccountNo()));
			companyBasicInfo.put("parentBankName", authentication.getParentBankName());
//			companyBasicInfo.put("bankCityNo", "11");
			
			JSONObject param = new JSONObject();
			param.put("bizUserId", authentication.getUserId());
			param.put("backUrl", PayClientUtil.notifyAddress + "/api/notify/setCompanyInfo");
			param.put("companyBasicInfo", companyBasicInfo);
			
			logger.info("企业认证参数：{}",param);
			response = soaClient.request("MemberService", "setCompanyInfo", param);
			logger.info("企业认证:{}", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		callOfSuccess(response);
		logger.debug("企业认证结束...");
		return response;
	}

	/**
	 * @throws JSONException
	 * 手机绑定
	 * @param String
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject bindPhone(String bizUserId,String phone,String verificationCode){
		logger.info("手机绑定:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("phone", phone);
			param.put("verificationCode", verificationCode);
			response = soaClient.request("MemberService", "bindPhone", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		return response;
	}
	/**
	 * @throws JSONException
	 * 无验证手机绑定
	 * @param String
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject bindPhoneWithoutConfirm(String bizUserId,String phone){
		logger.info("无验证手机绑定:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("phone", phone);
			logger.debug("无验证手机绑定开始...");
			response = soaClient.request("MemberServiceWithoutConfirm", "bindPhoneWithoutConfirm", param);
			logger.info("绑定手机响应:{}",response);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		logger.debug("绑定手机结束...");
		return response;
	}
	/**
	 * @throws JSONException
	 * 设置企业信息
	 * @param String
	 * @return void
	 * @throws
	 */
	/*public JSONObject setCompanyInfo(CompanyAuthentification company, String bizUserId) {
		logger.info("设置企业信息:{}", bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();

			JSONObject companyBasicInfo = new JSONObject();
			companyBasicInfo.put("companyName", company.getCompanyName());
			companyBasicInfo.put("companyAddress", company.getCompanyAddress());
			companyBasicInfo.put("businessLicense", company.getBusinessLicense());
			companyBasicInfo.put("organizationCode", company.getOrganizationCode());
			companyBasicInfo.put("telephone", company.getTelephone());
			companyBasicInfo.put("legalName", company.getLegalName());
			companyBasicInfo.put("identityType", company.getIdentityType());
			companyBasicInfo.put("legalIds", rsaEncrypt(company.getLegalIds()));
			companyBasicInfo.put("legalPhone", company.getLegalPhone());
			companyBasicInfo.put("accountNo", rsaEncrypt(company.getAccountNo()));
			companyBasicInfo.put("parentBankName", company.getParentBankName());
			companyBasicInfo.put("bankCityNo", company.getBankCityNo());
			companyBasicInfo.put("bankName", company.getBankName());

			param.put("bizUserId", bizUserId);
			param.put("companyBasicInfo", companyBasicInfo);
			logger.debug("设置企业信息开始...");
			response = soaClient.request("MemberService", "setCompanyInfo", param);
			logger.info("设置企业信息返回:{}",response);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		logger.debug("设置企业信息结束...");
		return response;
	}*/
	/**
	 * @throws JSONException
	 * 设置会员信息
	 * @param String
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject setMemberInfo(String bizUserId,UserAuthentification user){
		logger.info("设置会员信息:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();
			JSONObject userInfo = new JSONObject();
			userInfo.put("name", user.getName());
			userInfo.put("country", user.getCountry());
			userInfo.put("province", user.getProvince());
			userInfo.put("area", user.getArea());
			userInfo.put("address", user.getAddress());

			param.put("bizUserId", bizUserId);
			param.put("userInfo", userInfo);

			response = soaClient.request("MemberService", "setMemberInfo", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		return response;
	}
	/**
	 * @throws JSONException
	 * 获取会员信息
	 * @param String
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject getMemberInfo(String bizUserId){
		logger.info("获取会员信息:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			response = soaClient.request("MemberService", "getMemberInfo", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		return response;
	}
	/**
	 * @throws JSONException
	 * 更改手机号
	 * @param String
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject changeBindPhone(String bizUserId,String phone,String changePhone,String newVerificationCode){
		logger.info("更改手机号:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("oldPhone", phone);
			param.put("newPhone", changePhone);
			param.put("newVerificationCode", newVerificationCode);
			response = soaClient.request("MemberService", "changeBindPhone", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		return response;
	}
	/**
	 * 查询卡bin
	 * @param String
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject getBankCardBin(String jjBankCardNo){
		logger.info("查询卡bin:{}",jjBankCardNo);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();
			param.put("cardNo", rsaEncrypt(jjBankCardNo));
			response = soaClient.request("MemberService", "getBankCardBin", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		JSONObject json = null;
		try {
			JSONObject jsonObject = (JSONObject) response.get("returnValue");
			json = (JSONObject) jsonObject.get("cardBinInfo");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	/**
	 * @throws Exception
	 * 请求绑定银行卡（三要素绑卡）
	 * @param BindBankCard
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject applyBindBankCard(BindBankCard bindBankCard){
		logger.info("请求绑定银行卡");
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			//借记卡
			JSONObject param = new JSONObject();
			param.put("bizUserId", bindBankCard.getBizUserId());
			param.put("cardNo",rsaEncrypt(bindBankCard.getCardNo()));
			param.put("phone", bindBankCard.getPhone());
			param.put("name", bindBankCard.getName());
			param.put("cardCheck", bindBankCard.getCardCheck());
			param.put("identityType", bindBankCard.getIdentityType());
			param.put("identityNo",rsaEncrypt(bindBankCard.getIdentityNo()));
//			param.put("isSafeCard", bindBankCard.getIsSafeCard());
//			param.put("unionBank", bindBankCard.getUnionBank());
//			param.put("validate", rsaEncrypt(""));
//			param.put("cvv2", rsaEncrypt(""));
			logger.info("请求绑定银行卡：{}",param);
			response = soaClient.request("MemberService", "applyBindBankCard", param);
			logger.info("请求绑定银行卡响应:{}",response);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		return response;
	}

	/**
	 * 确认绑定银行卡
	 * @param
	 * @return void
	 * @throws
	 */
	public JSONObject bindBankCard(String bizUserId,String tranceNum,String transDate,String phone,String verificationCode){
		logger.info("确认绑定银行卡:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("tranceNum", tranceNum);
			param.put("transDate", transDate);
			param.put("phone", phone);
			param.put("verificationCode", verificationCode);

			response = soaClient.request("MemberService", "bindBankCard", param);
			logger.info("确认绑定银行卡响应参数：{}",response);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		return response;
	}
	/**
	 * 设置安全卡
	 * @param
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject setSafeCard(String bizUserId,String cardNo) {
		logger.info("设置安全卡:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try {
			JSONObject parm = new JSONObject();
			parm.put("bizUserId", bizUserId);
			parm.put("cardNo", rsaEncrypt(cardNo));
			parm.put("setSafeCard", true);

			response = soaClient.request("MemberService", "setSafeCard", parm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		callOfSuccess(response);
		return response;
	}
	/**
	 * 查询绑定银行卡
	 * @param
	 * @return void
	 * @throws
	 */
	public JSONObject queryBankCard(String bizUserId,String jjBankCardNo){
		logger.info("查询绑定银行卡:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			//查询单张卡
			JSONObject param = new JSONObject();
			if(!StringUtils.isEmpty(jjBankCardNo)){
				param.put("bizUserId", bizUserId);
				param.put("cardNo", rsaEncrypt(jjBankCardNo));
			}

			//查询全部
			if(StringUtils.isEmpty(jjBankCardNo))
				param.put("bizUserId", bizUserId);

			response = soaClient.request("MemberService", "queryBankCard", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		return response;
	}
	/**
	 * 解绑银行卡
	 * @param String
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject unbindBankCard(String bizUserId,String jjBankCardNo){
		logger.info("解绑银行卡:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			param.put("cardNo", rsaEncrypt(jjBankCardNo));

			response = soaClient.request("MemberService", "unbindBankCard", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		return response;
	}

	/**
	 * @throws JSONException
	 * 锁定用户
	 * @param String
	 * @return void
	 * @throws
	 */
	public JSONObject lockMember(String bizUserId){
		logger.info("锁定用户:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			response = soaClient.request("MemberService", "lockMember", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		return response;
	}
	/**
	 * @throws JSONException
	 * 解锁用户
	 * @param String
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject unlockMember(String bizUserId){
		logger.info("解锁用户:{}",bizUserId);
		SOAClient soaClient = PayClientUtil.getSOAClient();
		JSONObject response = null;
		try{
			JSONObject param = new JSONObject();
			param.put("bizUserId", bizUserId);
			response = soaClient.request("MemberService", "unlockMember", param);
		}catch(Exception e){
			e.printStackTrace();
		}
		callOfSuccess(response);
		return response;
	}
	/**
	 * 验证是否调用成功
	 */
	public void callOfSuccess(JSONObject response) {
		try {
			if("error".equals(response.get("status"))){
				logger.debug("接口调用失败");
				throw new RestException(BusinessStatus.INTERFACE_ERROR.getDesc()+"_"+response.get("message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	//RSA加密
	public String rsaEncrypt(String str) throws Exception{
		try{
			RSAUtil rsaUtil = new RSAUtil((RSAPublicKey)PayClientUtil.getPublicKey(), (RSAPrivateKey)PayClientUtil.getPrivateKey());
			String encryptStr = rsaUtil.encrypt(str);
			return encryptStr;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	//RSA解密
	public String rsaDecrypt(String str) throws Exception{
		try{
			RSAUtil rsaUtil = new RSAUtil((RSAPublicKey)PayClientUtil.getPublicKey(), (RSAPrivateKey)PayClientUtil.getPrivateKey());
			String dencryptStr = rsaUtil.dencrypt(str);
			return dencryptStr;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
