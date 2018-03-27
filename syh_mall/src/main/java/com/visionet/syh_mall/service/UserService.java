package com.visionet.syh_mall.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.visionet.syh_mall.entity.account.UserBank;
import com.visionet.syh_mall.repository.mbr.UserBankRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.persistence.DynamicParamConvert;
import com.visionet.syh_mall.common.persistence.DynamicSpecifications;
import com.visionet.syh_mall.common.persistence.SearchFilter;
import com.visionet.syh_mall.common.utils.DateUtil;
import com.visionet.syh_mall.common.utils.Id18;
import com.visionet.syh_mall.common.utils.MessageUtils;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.common.utils.Validator;
import com.visionet.syh_mall.entity.FileManage;
import com.visionet.syh_mall.entity.KeyMapping;
import com.visionet.syh_mall.entity.Tag;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.UserAccountFlow;
import com.visionet.syh_mall.entity.UserAuthentication;
import com.visionet.syh_mall.entity.UserAuthentification;
import com.visionet.syh_mall.entity.UserHierarchy;
import com.visionet.syh_mall.entity.UserTagLink;
import com.visionet.syh_mall.entity.WeixinOauth2Token;
import com.visionet.syh_mall.entity.WeixinUserInfo;
import com.visionet.syh_mall.entity.account.UserAccount;
import com.visionet.syh_mall.entity.finance.Bond;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.entity.shop.ShopSetting;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.KeyMappingRepository;
import com.visionet.syh_mall.repository.NoteRepository;
import com.visionet.syh_mall.repository.UserAuthenticationRepository;
import com.visionet.syh_mall.repository.UserRepository;
import com.visionet.syh_mall.repository.UserTagLinkRepository;
import com.visionet.syh_mall.repository.cart.ShopCartRepository;
import com.visionet.syh_mall.repository.finance.BondRepository;
import com.visionet.syh_mall.repository.mbr.UserAccountFlowRepository;
import com.visionet.syh_mall.repository.mbr.UserAuthenticationDaolmpl;
import com.visionet.syh_mall.repository.mobile.MessageRepository;
import com.visionet.syh_mall.repository.mobile.OrderRepository;
import com.visionet.syh_mall.repository.mobile.ShopRepository;
import com.visionet.syh_mall.repository.mobile.ShopSettingRepository;
import com.visionet.syh_mall.repository.mobile.TagRepository;
import com.visionet.syh_mall.repository.mobile.channel.UserHierarchyRepository;
import com.visionet.syh_mall.repository.userAttention.GoodsFavoriteRepository;
import com.visionet.syh_mall.repository.userAttention.UserAttentionRepository;
import com.visionet.syh_mall.service.account.UserAccountService;
import com.visionet.syh_mall.service.mobile.RegisterService;
import com.visionet.syh_mall.service.thridAccount.SoaMemberService;
import com.visionet.syh_mall.vo.CompanyUserAuthenticationVo;
import com.visionet.syh_mall.vo.SMSVO;
import com.visionet.syh_mall.vo.UserAuthenticationQo;
import com.visionet.syh_mall.vo.UserAuthenticationVo;
import com.visionet.syh_mall.vo.UserVo;

@Service
public class UserService extends BaseService {
	@Autowired
	private UserRepository userRep;
	@Autowired
	private ShopRepository shopDao;
	@Autowired
	private TagRepository tagDao;
	@Autowired
	private FileManageRepostory fManageRepostory;
	@Autowired
	private UserTagLinkRepository linkDao;
	@Autowired
	private KeyMappingRepository keyDao;
	@Autowired
	private UserAttentionRepository attentionDao;
	@Autowired
	private GoodsFavoriteRepository favoriteDao;
	@Autowired
	private UserAuthenticationRepository authenticationDao;
	@Autowired
	private UserAuthenticationDaolmpl authenticationDaolmpl;
	@Autowired
	private ShopSettingRepository shopSetDao;
	@Autowired
	private SoaMemberService memberService;
	@Autowired
	private RegisterService registerService;
	@Autowired
	private MessageRepository messageDao;
	@Autowired
	private OrderRepository orderDao;
	@Autowired
	private UserAccountFlowRepository userAccountFlowDao;
	@Autowired
	private UserHierarchyRepository userHierDao;
	@Autowired
	private WeiXinService weiXinService;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private NoteRepository noteDao;
	@Autowired
	private BondRepository bondDao;
	@Autowired
	private ShopCartRepository shopCartDao;
	@Autowired
	private UserAccountService accountService;
    @Autowired
    private UserBankRepository userBankDao;

	public User findUserByPhone(String phone) {
		User user = userRep.findUserByphone(phone);
		return user;
	}

	public User findUserByLoginName(String loginName) {
		User user = userRep.findUserByLoginName(loginName);
		return user;
	}

	public User findUserById(String id) {
		User user = userRep.findOne(id);
		return user;
	}

	public User login(String userName, String pwd) {
		User user = userRep.findUserByloginName(userName);
		// Pageable page = new PageRequest(1-1, 10, new
		// Sort(Direction.ASC,"id"));
		// Page<User> pageUser = userRep.findAll(page);
		return user;
	}

	public User findByloginName(String userName) {
		User user = userRep.findUserByloginName(userName);
		return user;
	}

	/**
	 * 修改密码
	 * 
	 * @param phone
	 * @param newPwd
	 * @param confirmNewPwd
	 */
	public void editPwd(String phone, String newPwd, String confirmNewPwd) {
		User user = userRep.findUserByphone(phone);
		// if(!user.getLoginPassword().equals(oldPwd)){
		// throw new RestException("旧密码错误，请重新输入！");
		// }
		// if(oldPwd.equals(newPwd)){
		// throw new RestException("新密码和旧密码不能相同，请重新输入！");
		// }
		if (!newPwd.equals(confirmNewPwd)) {
			throw new RestException("重复密码和新密码不一致，请重新输入！");
		}
		user.setLoginPassword(newPwd);
		userRep.save(user);
	}

	/**
	 * 保存用户图片
	 * 
	 * @param userId
	 * @param imgFileId
	 */
	public void saveUserImages(String userId, String imgFileId) {
		User user = userRep.findOne(userId);
		user.setImgFileId(imgFileId);
		user.setUpdateTime(DateUtil.getCurrentDate());
		userRep.save(user);
	}

	/**
	 * 生成随机邀请码
	 * 
	 * @param
	 * @return
	 */
	private static String randomNumber() {
		StringBuffer code = new StringBuffer("");
		for (int i = 0; i < 6; i++) {
			code.append((int) (10 * (Math.random())));
		}
		return code.toString();
	}

	/**
	 * 创建会员 @param @return void @throws
	 */
	// @Transactional(rollbackOn={RestException.class})
	public void createMember(String password, String phone, String parentInvitationCode, String weiCode,
			String source) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String today = sdf.format(date).replace(":", "");
		WeixinOauth2Token weixinOauth2Token = null;
		WeixinUserInfo weixinUserInfo = null;
		User user = new User();
		user.setLoginPassword(password);
		user.setUserStatusCode("user_normal");
		user.setUserTypeCode("authentication_unknown");
		user.setUserTypeOngoingCode("authentication_unknown");
		user.setChannelLevel(0);
		user.setLoginName(phone);
		user.setPhone(phone);
		user.setAliasName("***" + phone.substring(phone.length() - 4, phone.length()));
		user.setCreateTime(DateUtil.getCurrentDate());
		user.setIsDeleted(0);
		user.setInvitationCode(today + randomNumber());
		user.setMemberType(Integer.valueOf(source));
		if (weiCode != null) {
			String accessToken = CommonUtil.getToken(" ", "1cb4b7eae167f1190d6f3b8f94db5a79").getAccessToken();
			weixinOauth2Token = weiXinService.getOauth2AccessToken(weiCode);
			if (null != weixinOauth2Token && null != weixinOauth2Token.getOpenId()) {
				weixinUserInfo = weiXinService.getUserInfo(accessToken, weixinOauth2Token.getOpenId());
				User userByOpenId = userRep.findByWechatOpenIdAndIsDeleted(weixinOauth2Token.getOpenId(), 0);
				if (!StringUtils.isEmpty(userByOpenId)) {
					throw new RestException("该微信已经被绑定，请更换微信注册！");
				}
				user.setWechatOpenId(weixinUserInfo.getOpenId());
				user.setWechat(weixinUserInfo.getNickname());
			}
		}
		user = userRep.save(user);
		UserHierarchy userHierarchy = new UserHierarchy();
		if (Validator.isNotNull(parentInvitationCode)) {
			User u = userRep.findByInvitationCode(parentInvitationCode);
			if (u == null) {
				throw new RestException("邀请码不存在！");
			}
			userHierarchy.setUserId(user.getId());
			userHierarchy.setParentUserId(u.getId());
			userHierarchy.setCreateTime(DateUtil.getCurrentDate());
			userHierarchy.setIsDeleted(0);
			userHierarchy = userHierDao.save(userHierarchy);
		} else {
			userHierarchy.setUserId(user.getId());
			userHierarchy.setCreateTime(DateUtil.getCurrentDate());
			userHierarchy.setIsDeleted(0);
			userHierarchy = userHierDao.save(userHierarchy);
		}
		String status = null;
		try {
			JSONObject jsonObject = memberService.createMember(user.getId(), source, "2");
			status = (String) jsonObject.get("status");
		} catch (RestException e) {
			e.getMessage();
			throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.CREATE_MBR_ERROR.getDesc());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			if ("error".equals(status) || null == status) {
				userRep.delete(user.getId());
			}
		}
		try {
			if ("3".equals(source)) {
				JSONObject json = memberService.bindPhoneWithoutConfirm(user.getId(), phone);
				status = (String) json.get("status");
				if ("OK".equals(status)) {
					UserAccount userAccount = new UserAccount();
					userAccount.setUserId(user.getId());
					userAccount.setCreateTime(DateUtil.getCurrentDate());
					userAccount.setUpdateTime(DateUtil.getCurrentDate());
					userAccount.setBalance(new BigDecimal(0));
					userAccount.setWithdrawal(new BigDecimal(0));
					userAccount.setFrozenAmt(new BigDecimal(0));
					accountService.saveUserAccount(userAccount);
				}
			}
		} catch (RestException e) {
			e.getMessage();
			throw new RestException("第三方绑定手机失败");
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			if ("error".equals(status) || null == status) {
				userRep.delete(user.getId());
			}
		}
	}

	/**
	 * @author DM
	 * @param:
	 * @return:
	 * @Description:获取会员信息
	 * @date
	 */
	public UserVo getMbrInfo(UserVo qo) throws Exception {
		UserVo returnVo = new UserVo();
		User user = userRep.findOne(qo.getMbrId());
		returnVo.setMbrId(user.getId());
		returnVo.setMbrLoginName(user.getLoginName());
		returnVo.setMbrName(user.getAliasName());
		returnVo.setMbrPhone(user.getPhone());
		returnVo.setMbrImgId(user.getImgFileId());
		if (Validator.isNotNull(user.getImgFileId())) {
			FileManage file = fManageRepostory.findOne(user.getImgFileId());
			if (null != file && null != file.getId()) {
				returnVo.setMbrImgUrl(file.getAbsolutePath());
			}
		}
		if (Validator.isNotNull(user.getUserTypeCode())) {
			returnVo.setMbrTypeCode(user.getUserTypeCode());
			KeyMapping key = keyDao.findByKeyCode(user.getUserTypeCode());
			if (null != key && null != key.getId()) {
				returnVo.setMbrTypeDesc(key.getValueDesc());
			}
		}
		if (Validator.isNotNull(user.getUserTypeOngoingCode())) {
			returnVo.setUserTypeOngoingCode(user.getUserTypeOngoingCode());
			KeyMapping key = keyDao.findByKeyCode(user.getUserTypeOngoingCode());
			if (null != key && null != key.getId()) {
				returnVo.setUserTypeOngoingDesc(key.getValueDesc());
			}
		}
		if (Validator.isNotNull(user.getUserStatusCode())) {
			returnVo.setMbrStatusCode(user.getUserStatusCode());
			KeyMapping key1 = keyDao.findByKeyCode(user.getUserTypeOngoingCode());
			if (null != key1 && null != key1.getId()) {
				returnVo.setMbrStatusDesc(key1.getValueDesc());
			}
		}
		returnVo.setMemberType(user.getMemberType());
		returnVo.setMbrSignature(user.getSignature());
		returnVo.setMbrAddress(user.getAddress());
		returnVo.setMbrEmail(user.getMail());
		returnVo.setMbrWechat(user.getWechat());
		returnVo.setMbrQQ(user.getQq());

		if (Validator.isNotNull(user.getChannelLevel())) {
			returnVo.setChannelLevel(user.getChannelLevel());
		}
		if (Validator.isNotNull(user.getLastLoginTime())) {
			returnVo.setMbrLastLoginTime(user.getLastLoginTime());
		}
		if (Validator.isNotNull(user.getUserValidIntegral())) {
			returnVo.setMbrValidIntegral(user.getUserValidIntegral());
		}
		if (Validator.isNotNull(user.getUserTotalIntegral())) {
			returnVo.setMbrTotalIntegral(user.getUserTotalIntegral());
		}
		if (Validator.isNotNull(user.getCreateTime())) {
			returnVo.setMbrRegisterTime(user.getCreateTime());
		}
		UserAuthentication userAuthentication = authenticationDao.findByUserId(qo.getMbrId());
		if (null != userAuthentication && null != userAuthentication.getId()) {
			returnVo.setUserRealName(userAuthentication.getUserRealName());
		}
		if (user.getMemberType()==3){
            UserAuthentication byUserID = authenticationDao.findUserAuthentication(user.getId());
            List<UserBank> bankCard = userBankDao.findByUserId(user.getId());
            if (StringUtils.isEmpty(byUserID)){
            	return returnVo;
			}
            returnVo.setMbrIdCode(byUserID.getIdCode());
            returnVo.setUserAccount(bankCard);
		}
		if (user.getMemberType()==2){
            UserAuthentication byUserID = authenticationDao.findUserAuthentication(user.getId());
            List<UserBank> bankCard = userBankDao.findByUserId(user.getId());
			if (StringUtils.isEmpty(byUserID)){
				return returnVo;
			}
            returnVo.setMbrIdCode(byUserID.getIdCode());
            returnVo.setUserAccount(bankCard);
            returnVo.setParentBankName(byUserID.getParentBankName());
            returnVo.setAccountNo(byUserID.getAccountNo());
		}
		returnVo.setMbrWechatOpenId(null);
		returnVo.setBindWeiXin(user.getWechatOpenId() != null);
		returnVo.setMbrQQOpenId(user.getQqOpenId());
		returnVo.setPayPwd(user.getPayPwd());
		int attentionNum = attentionDao.findNumById(qo.getMbrId());
		returnVo.setMbrAttentionCount(attentionNum);
		int favoriteNum = favoriteDao.findNumById(qo.getMbrId());
		returnVo.setMbrFavoriteCount(favoriteNum);
		return returnVo;
	}

	/**
	 * @author DM
	 * @Description:修改会员信息
	 */
	public void editMbrInfo(UserVo qo) {
		// 修改操作
		logger.info("用户昵称为:{}", qo.getMbrName());
		User user = userRep.findOne(qo.getMbrId());
		List<User> user1 = userRep.findByAliasName(qo.getMbrName());
		if (user1.size() == 0) {
			userRep.save(settUser(user, qo));
			return;
		}
		for (int i = 0; i < user1.size(); i++) {
			logger.info("用户id为:{}", user1.get(i).getId());
			if (user1.get(i).getId().equals(qo.getMbrId())) {
				logger.info("用户id为:{}", qo.getMbrId());
				userRep.save(settUser(user, qo));
				return;
			}
		}
		logger.info("用户id为:{}", qo.getMbrId());
		throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.USER_NAME.getDesc());
	}

	public static User settUser(User user, UserVo qo) {
		user.setAliasName(qo.getMbrName());
		user.setPhone(qo.getMbrPhone());
		user.setImgFileId(qo.getMbrImgId());
		user.setSignature(qo.getMbrSignature());
		user.setMail(qo.getMbrEmail());
		user.setQq(qo.getMbrQQ());
		user.setUpdateTime(DateUtil.getCurrentDate());
		return user;
	}

	/**
	 * @author DM
	 * @Description:个人会员认证申请
	 */
	@Transactional
	public void authenticateMbr(UserAuthenticationVo authenticationVo) {
		UserAuthentication userAuthentication = authenticationDao.findOneByUserId(authenticationVo.getMbrId());
        List<UserAuthentication> byIdCode = authenticationDao.findByIdCode(authenticationVo.getMbrIDCode());
        if (byIdCode.size()>0){
            throw new RestException("该身份证已注册");
        }
        char end = Id18.getValidateCode(authenticationVo.getMbrIDCode().substring(0, 17));
		char trueend = authenticationVo.getMbrIDCode().charAt(17);
		// 验证身份证
		if ((end + 0) != (trueend + 0)) {
			throw new RestException("身份证校验失败");
		}
		if (null != userAuthentication) {
			System.out.println(authenticationVo.getMbrId());
			System.out.println(userAuthentication.getAccountNo());
			UserAuthentication authentication = authenticationVo.coverPo(authenticationVo, userAuthentication);
			authentication.setUpdateTime(new Date());
			authenticationDao.save(authentication);
		} else {
			userAuthentication = new UserAuthentication();
			userAuthentication = authenticationVo.coverPo(authenticationVo, userAuthentication);
			authenticationDao.save(userAuthentication);
		}
		User user = userRep.findOne(authenticationVo.getMbrId());
		user.setUserTypeOngoingCode("authentication_personal_ongoing");
		userRep.save(user);
	}

	/**
	 * @Title: enterpriseMbr @Description: 添加企业会员认证信息 @param @param qo
	 *         设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void enterpriseMbr(CompanyUserAuthenticationVo companyUserAuthenticationVo) {
		UserAuthentication userAuthentication = authenticationDao
				.findOneByUserId(companyUserAuthenticationVo.getBizUserId());
		if (companyUserAuthenticationVo.getIdentityType() == 1) {
			char end = Id18.getValidateCode(companyUserAuthenticationVo.getLegalIds().substring(0, 17));
			char trueend = companyUserAuthenticationVo.getLegalIds().charAt(17);
			if ((end + 0) != (trueend + 0)) {
				throw new RestException("身份证校验失败");
			}
		}
		if (null != userAuthentication) {
			authenticationDao
					.save(companyUserAuthenticationVo.converPo(companyUserAuthenticationVo, userAuthentication));
		} else {
			userAuthentication = new UserAuthentication();
			logger.info(String.valueOf(companyUserAuthenticationVo));
            logger.info(keyDao.findByKeyCode(companyUserAuthenticationVo.getDdKeyCode()).toString());
            userAuthentication.setParentBankName(keyDao.findByKeyCode(companyUserAuthenticationVo.getDdKeyCode()).getValueDesc());
			authenticationDao
					.save(companyUserAuthenticationVo.converPo(companyUserAuthenticationVo, userAuthentication));
		}
		User user = userRep.findOne(companyUserAuthenticationVo.getBizUserId());
		user.setUserTypeOngoingCode("authentication_tenant_ongoing");
		userRep.save(user);
	}

	/**
	 * @author DM
	 * @throws Exception
	 * @Description:会员认证审核
	 */
	@Transactional
	public void reviewAuthentication(UserAuthenticationVo authenticationVo) throws Exception {
		UserAuthentication authentication = authenticationDao.findByUserID(authenticationVo.getMbrId());
		User user = userRep.findOne(authenticationVo.getMbrId());
		Integer memberType = user.getMemberType();
		if (0 == authenticationVo.getReviewResult()) {
			authentication.setStatus(2);
			authentication.setRefusalReason(authenticationVo.getRefusalReason());
			authentication = authenticationDao.save(authentication);
			user.setUserTypeOngoingCode("authentication_failed");
			user.setUserTypeCode("authentication_failed");
			userRep.save(user);
			SMSVO smsvo = new SMSVO();
			smsvo.setCode(authenticationVo.getRefusalReason());
			smsvo.setTelephone(user.getPhone());
			registerService.sendText(smsvo);// 拒绝理由短信发送
			return;
		}
		if (1 == authenticationVo.getReviewResult()) {
			SoaMemberService memberService = new SoaMemberService();
			if (memberType == 2) {
				// 企业实名认证
				JSONObject realName = memberService.setCompanyInfo(authentication);
				if ("error".equals(realName.get("status"))) {
					throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
				}
				if ("OK".equals(realName.get("status"))) {
					JSONObject jsonObject = new JSONObject(String.valueOf(realName.get("signedValue")));
					logger.info("响应内容:{}", jsonObject.getMap());
					user.setUserTypeOngoingCode("authentication_ongoing");
					user.setUserTypeCode("authentication_ongoing");
					userRep.save(user);
				}
			}
			if (memberType == 3) {
				// 个人实名认证
				JSONObject realName = memberService.setRealName(user.getId(), true, authentication.getUserRealName(),
						authentication.getIdCode());
				if ("error".equals(realName.get("status"))) {
					throw new RestException(HttpStatus.ACCEPTED, BusinessStatus.DATE_ERROR.getDesc());
				}
				user.setUserTypeOngoingCode("authentication_personal");
				user.setUserTypeCode("authentication_personal");
			}
			// 设置个人会员信息
			if (memberType == 3) {
				setUserInfo(authentication, user);
			}
		}
	}

	/**
	 * @throws JSONException
	 * @Title: setUserInfo @Description: 添加个人设置,创建店铺,添加店铺设置,修改认证状态 @param @param
	 *         authentication @param @param user 设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void setUserInfo(UserAuthentication authentication, User user) throws JSONException {
		if (user.getMemberType() == 3) {
			UserAuthentification userAuthenfication = new UserAuthentification();
			userAuthenfication.setName(authentication.getUserRealName());
			userAuthenfication.setCountry("中国");
			userAuthenfication.setProvince(authentication.getUserProvince());
			userAuthenfication.setArea(authentication.getUserArea());
			userAuthenfication.setAddress(authentication.getUserStreet());
			memberService.setMemberInfo(user.getId(), userAuthenfication);
		}

		ShopSetting shopSet = new ShopSetting();
		shopSet.setCreateTime(DateUtil.getCurrentDate());
		shopSetDao.save(shopSet);
		Shop shop = new Shop();
		shop.setShopName(user.getAliasName() + "的店铺");// 店铺名称
		shop.setUserId(authentication.getUserId());// 创建人id
		shop.setUserName(authentication.getUserRealName());// 店主姓名
		shop.setShopLevel(0);// 店铺等级
		shop.setSettingId(shopSet.getId());
		shop.setShopAddress(authentication.getUserProvince() + authentication.getUserCity()
				+ authentication.getUserArea() + authentication.getUserStreet());// 店铺地址
		shop.setStatusCode("shop_normal");// 店铺状态编码
		shop.setShopIsFrozen(0);// 店铺是否冻结
		shop.setShopIsOfficial(0);// 是否官方店铺
		shop.setIsDeleted(0);// 是否删除
		shop = shopDao.save(shop);
		Bond bond = new Bond();
		bond.setBondAmt(new BigDecimal(0));
		bond.setShopBalance(new BigDecimal(0));
		bond.setBondStatus(0);
		bond.setShopId(shop.getId());
		bondDao.save(bond);

		userRep.save(user);
		authentication.setStatus(1);
		authentication.setIsDeleted(1);
		authentication = authenticationDao.save(authentication);
	}

	/**
	 * @author DM
	 * @param:
	 * @return:
	 * @Description:搜索会员认证
	 * @date
	 */
	public Page<UserVo> getAuthenticationList(UserAuthenticationQo qo) throws Exception {
		PageRequest pr = getPageRequest(qo.getPageIndex(), qo.getItemCount(), qo.getOrderConditions());
		return authenticationDaolmpl.queryCondition(qo, pr);
	}

	/**
	 * @author DM
	 * @param:
	 * @return:
	 * @Description:获取认证会员信息
	 * @date
	 */
	public UserAuthenticationVo getAuthenticationDetail(UserAuthenticationQo qo) throws Exception {
		UserAuthenticationVo returnVo = new UserAuthenticationVo();
		UserAuthentication userAuth = authenticationDao.findOne(qo.getId());
		User user = userRep.findOne(userAuth.getUserId());
		returnVo.setMbrId(user.getId());
		returnVo.setMbrLoginName(user.getLoginName());
		returnVo.setMbrName(user.getAliasName());
		returnVo.setMbrPhone(user.getPhone());
		returnVo.setMbrImgId(user.getImgFileId());
		if (Validator.isNotNull(user.getImgFileId())) {
			FileManage file = fManageRepostory.findOne(user.getImgFileId());
			if (null != file && null != file.getId()) {
				returnVo.setMbrImgUrl(file.getAbsolutePath());
			}
		}
		if (Validator.isNotNull(user.getUserTypeCode())) {
			returnVo.setMbrTypeCode(user.getUserTypeCode());
			KeyMapping key = keyDao.findByKeyCode(user.getUserTypeCode());
			if (null != key && null != key.getId()) {
				returnVo.setMbrTypeDesc(key.getValueDesc());
			}
		}
		if (Validator.isNotNull(user.getUserTypeOngoingCode())) {
			returnVo.setUserTypeOngoingCode(user.getUserTypeOngoingCode());
			KeyMapping key = keyDao.findByKeyCode(user.getUserTypeOngoingCode());
			if (null != key && null != key.getId()) {
				returnVo.setUserTypeOngoingDesc(key.getValueDesc());
			}
		}
		if (Validator.isNotNull(user.getUserStatusCode())) {
			returnVo.setMbrStatusCode(user.getUserStatusCode());
			KeyMapping key1 = keyDao.findByKeyCode(user.getUserStatusCode());
			if (null != key1 && null != key1.getId()) {
				returnVo.setMbrStatusDesc(key1.getValueDesc());
			}
		}
		returnVo.setMbrSignature(user.getSignature());
		returnVo.setMbrAddress(user.getAddress());
		returnVo.setMbrEmail(user.getMail());
		returnVo.setMbrWechat(user.getWechat());
		returnVo.setMbrQQ(user.getQq());
		if (Validator.isNotNull(user.getChannelLevel())) {
			returnVo.setChannelLevel(user.getChannelLevel());
		}
		if (Validator.isNotNull(user.getLastLoginTime())) {
			returnVo.setMbrLastLoginTime(user.getLastLoginTime());
		}
		returnVo.setMbrRealName(userAuth.getUserRealName());
		returnVo.setMbrProvince(userAuth.getUserProvince());
		returnVo.setMbrCity(userAuth.getUserCity());
		returnVo.setMbrArea(userAuth.getUserArea());
		returnVo.setMbrStreet(userAuth.getUserArea());
		returnVo.setMbrAddress(userAuth.getUserProvince() + userAuth.getUserCity() + userAuth.getUserArea()
				+ userAuth.getUserStreet());
		returnVo.setMbrIDFImgId(userAuth.getId_f_fileId());
		if (Validator.isNotNull(userAuth.getId_f_fileId())) {
			FileManage file = fManageRepostory.findOne(userAuth.getId_f_fileId());
			if (null != file && null != file.getId()) {
				returnVo.setMbrIDFImgUrl(file.getAbsolutePath());
			}
		}
		returnVo.setMbrIDBImgId(userAuth.getId_b_fileId());
		if (Validator.isNotNull(userAuth.getId_b_fileId())) {
			FileManage file = fManageRepostory.findOne(userAuth.getId_b_fileId());
			if (null != file && null != file.getId()) {
				returnVo.setMbrIDBImgUrl(file.getAbsolutePath());
			}
		}
		returnVo.setMbrIDCode(userAuth.getIdCode());
		returnVo.setMbrLicenseCode(userAuth.getLicenseNo());
		returnVo.setMbrOrganizationCode(userAuth.getOrganizationCode());
		returnVo.setMbrLicenseImgId(userAuth.getLicenseCertFileId());
		if (Validator.isNotNull(userAuth.getLicenseCertFileId())) {
			FileManage file = fManageRepostory.findOne(userAuth.getLicenseCertFileId());
			if (null != file && null != file.getId()) {
				returnVo.setMbrLicenseImgUrl(file.getAbsolutePath());
			}
		}
		return returnVo;
	}

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	/**
	 *
	 * @Title: defriendMbr @Description: 拉黑用户的方法 @param @param mbrID
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void defriendMbr(String mbrID) {
		User user = userRep.findOne(mbrID);
		if (StringUtils.isEmpty(user)) {
			throw new RestException("没有该用户");
		}
		user.setUpdateTime(DateUtil.getCurrentDate());
		user.setUserStatusCode("user_blacklist");
		userRep.save(user);
		Collection<Session> sessions = sessionDao.getActiveSessions();
		logger.info("在线用户人数:{}", sessions.size() + "----------------");
		for (Session session : sessions) {
			logger.info("用户IP:{}", session.getHost() + "----------------");
			logger.info("用户名:{}",
					session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) + "----------------");
			if (user.getLoginName()
					.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
				session.stop();
				break;
			}
		}
		String content = noteDao.findOne("19").getContent();
		String phone = user.getPhone();
		MessageUtils.sendText(phone, "{text}", content);
	}

	/**
	 * @Title: recoverMbr @Description: 重申的会员 @param @param mbrID 设定文件 @return
	 *         void 返回类型 @throws
	 */
	public void recoverMbr(String mbrID) {
		User user = userRep.findOne(mbrID);
		if (StringUtils.isEmpty(user)) {
			throw new RestException("没有该用户");
		}
		user.setUpdateTime(DateUtil.getCurrentDate());
		user.setUserStatusCode("user_normal");
		userRep.save(user);
		String content = noteDao.findOne("20").getContent();
		String phone = user.getPhone();
		MessageUtils.sendText(phone, "{text}", content);

	}

	/**
	 * 
	 * @Title: searchMbr @Description: 按条件查询会员的方法 @param @param
	 *         param @param @param pageInfo @param @return @param @throws
	 *         Exception 设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public Map<String, Object> searchMbr(Map<String, Object> param, PageInfo pageInfo) throws Exception {
		// 返回的用户信息Map
		Map<String, Object> result = new HashMap<String, Object>();
		// 用户信息的List
		List<Map<String, Object>> mbrList = new ArrayList<Map<String, Object>>();
		// 五搜索信息立刻终止返回的分页信息
		result = getReturnMap(result, null, pageInfo);
		result.put("mbrInfos", mbrList);
		// 通过会员标签检索
		if (!StringUtils.isEmpty(param.get("mbrTag"))) {
			String tagId = tagDao.findTagIdIdByTagName(String.valueOf(param.get("mbrTag")));
			List<String> tags = linkDao.findUserIdByTagId(tagId);
			if (tags.size() <= 0) {
				return result;
			}
			// 该标签的会员Ids
			param.put("ids", tags);
		}
		// 检索条件的Map
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchMbr(param)));
		// 检索出来的所有用户
		Page<User> list = userRep.findAll(DynamicSpecifications.bySearchFilter(filter.values(), User.class),
				buildPageRequest(pageInfo));

		for (User user : list) {
			if (!StringUtils.isEmpty(user)) {
				Map<String, Object> mbrMap = new HashMap<String, Object>();
				mbrMap.put("mbrID", user.getId());
				mbrMap.put("mbrLoginName", user.getLoginName());
				mbrMap.put("mbrName", user.getAliasName());
				mbrMap.put("mbrPhone", user.getPhone());
				mbrMap.put("mbrTypeCode", user.getUserTypeCode());
				KeyMapping keyCode = keyDao.findByKeyCode(user.getUserTypeCode());
				mbrMap.put("mbrTypeDesc", keyCode.getValueDesc());
				mbrMap.put("mbrStatus", user.getUserStatusCode());
				mbrMap.put("mbrLastLoginTime", user.getLastLoginTime());
				mbrMap.put("mbrValidIntegral", user.getUserValidIntegral());
				mbrMap.put("mbrRegisterTime", user.getCreateTime());
				List<BigDecimal> amts = userRep.findMbrAmtById(user.getId());
				mbrMap.put("mbrConsumeSum", 0);
				if (amts.size() > 0) {
					BigDecimal amt = new BigDecimal(0);
					for (BigDecimal bigDecimal : amts) {
						amt.add(bigDecimal);
					}
					mbrMap.put("mbrConsumeSum", amt);
				}
				mbrList.add(mbrMap);
			}
		}
		result = getReturnMap(result, list, pageInfo);
		result.put("mbrInfos", mbrList);
		return result;
	}

	/**
	 * 
	 * @Title: addTag @Description: 添加标签的方法 @param @param tagType @param @param
	 *         tagID @param @param tagName 设定文件 @return void 返回类型 @throws
	 */
	public void addTag(Tag tag, String currentUserId) {
		Integer isDeleted = 0;
		Iterable<Tag> tags = tagDao.findByIsDeleted(isDeleted);
		if (StringUtils.isEmpty(tag.getId())) {
			for (Tag tag2 : tags) {
				if (tag2.getTagName().equals(tag.getTagName())) {
					throw new RestException("不能保存重复标签");
				}
			}
			tag.setUpdateBy(currentUserId);
			tag.setCreateBy(currentUserId);
			tagDao.save(tag);
			return;
		}
		Tag tag2 = tagDao.findById(tag.getId());
		if (StringUtils.isEmpty(tag2)) {
			throw new RestException("没有该标签");
		}
		tag2.setUpdateTime(DateUtil.getCurrentDate());
		tag2.setUpdateBy(currentUserId);
		tag2.setTagType(tag.getTagType());
		tag2.setTagName(tag.getTagName());
		tagDao.save(tag2);
	}

	/**
	 * @Title: delTag @Description: 删除标签 @param @param tag @param @param
	 *         currentUserId 设定文件 @return void 返回类型 @throws
	 */
	public void delTag(String tagID) {
		Tag tag = tagDao.findById(tagID);
		if (StringUtils.isEmpty(tag)) {
			throw new RestException("没有该标签");
		}
		tag.setIsDeleted(1);
		tagDao.save(tag);
	}

	/**
	 * 登陆后操作
	 * 
	 * @param userId
	 */
	public void loginOperta(String userId) {
		// 修改登录时间
		User user = userRep.findOne(userId);
		user.setLastLoginTime(DateUtil.getCurrentDate());
		userRep.save(user);
	}

	/**
	 * 
	 * @Title: searchTags @Description: 搜索标签的方法 @param @param
	 *         tagType @param @param tagName @param @return 设定文件 @return
	 *         List<Map<String,Object>> 返回类型 @throws
	 */
	public Map<String, Object> searchTags(Map<String, Object> param, PageInfo pageInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, SearchFilter> filter = SearchFilter.parse((DynamicParamConvert.searchTags(param)));
		Page<Tag> tags = tagDao.findAll(DynamicSpecifications.bySearchFilter(filter.values(), Tag.class),
				buildPageRequest(pageInfo));
		List<Map<String, Object>> tagList = new ArrayList<Map<String, Object>>();
		for (Tag tag : tags) {
			if (!StringUtils.isEmpty(tag)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tagID", tag.getId());
				map.put("tagType", tag.getTagType());
				map.put("tagName", tag.getTagName());
				map.put("tagMbrNum", userRep.counttagId(tag.getId()));
				tagList.add(map);
			}
		}
		result = getReturnMap(result, tags, pageInfo);
		result.put("tagInfos", tagList);
		return result;
	}

	/**
	 * 
	 * @Title: addMbrTags @Description: 为用户添加标签的方法 @param @param
	 *         mbrIDs @param @param mbrTags @param @param tagID @param @param
	 *         tagName 设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void addMbrTags(List<String> mbrIDs, List<Map<String, Object>> mbrTags, String adminID) {
		for (String mbrID : mbrIDs) {
			for (Map<String, Object> mbrTag : mbrTags) {
				String tagID = (String) mbrTag.get("tagID");
				if (StringUtils.isEmpty(mbrTag.get("tagID"))) {
					Tag tag = new Tag();
					tag.setTagType(1);
					tag.setCreateBy(adminID);
					tag.setUpdateBy(adminID);
					tag.setTagName((String) mbrTag.get("tagName"));
					tag = tagDao.save(tag);
					tagID = tag.getId();
				}
				UserTagLink link = new UserTagLink();
				link.setTagId(tagID);
				link.setUserId(mbrID);
				linkDao.save(link);
			}
		}
	}

	/**
	 * @Title: getTag @Description: 获取标签详情 @param @param TagId @param @return
	 *         设定文件 @return Map<String,Object> 返回类型 @throws
	 */
	public Map<String, Object> getTag(String tagId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Tag tag = tagDao.findById(tagId);
		if (StringUtils.isEmpty(tag)) {
			throw new RestException("没有该标签ID");
		}
		result.put("tagID", tag.getId());
		result.put("tagName", tag.getTagName());
		result.put("tagType", tag.getTagType());
		return result;
	}

	/**
	 * 
	 * @Title: editMbrTags @Description: 修改用户的标签 @param @param
	 *         mbrID @param @param mbrTags @param @param tagID @param @param
	 *         tagName 设定文件 @return void 返回类型 @throws
	 */
	@Transactional
	public void editMbrTags(String mbrID, List<Map<String, Object>> mbrTags) {
		// 编辑时先删除原始标签关联
		linkDao.deleteByUserId(mbrID);
		// 添加新的标签
		for (Map<String, Object> map : mbrTags) {
			UserTagLink link = new UserTagLink();
			link.setUserId(mbrID);
			link.setTagId((String) map.get("tagID"));
			linkDao.save(link);
		}
	}

	/**
	 * 获得会员提醒信息 @param @return Map<String,Object> @throws
	 */
	public Map<String, Object> getMbrRemindInfo(String currentUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(currentUserId)) {
			Integer newsNum = messageDao.findNewsNum(currentUserId);
			Integer incompleteOrderNum = orderDao.getIncompleteOrderNum(currentUserId);
			Integer orderNum = orderDao.getOrderNum(currentUserId);
			BigDecimal totalIncomeAmt = new BigDecimal(0);
			List<UserAccountFlow> inForMoney = userAccountFlowDao.findInUserId(currentUserId);
			for (UserAccountFlow userAccountFlow : inForMoney) {
				totalIncomeAmt = totalIncomeAmt.add(userAccountFlow.getAmt());
			}
			BigDecimal totalCostAmt = new BigDecimal(0);
			List<UserAccountFlow> outForMoney = userAccountFlowDao.findOutUserId(currentUserId);
			for (UserAccountFlow userAccountFlow : outForMoney) {
				totalCostAmt = totalCostAmt.add(userAccountFlow.getAmt());
			}
			int goodsNum = shopCartDao.findByUserId(currentUserId);
			map.put("cartGoodsNum", goodsNum);
			map.put("mbrID", currentUserId);
			map.put("unreadMsgCount", newsNum);
			map.put("unreadNoticeCount", 0);
			map.put("incompleteOrderCount", incompleteOrderNum);
			map.put("totalOrderCount", orderNum);
			map.put("totalIncomeAmt", inForMoney);
			map.put("cnn", outForMoney);
		}
		return map;
	}

	/**
	 * 获取会员标签 @param @return Map<String,Object> @throws
	 */
	public List<Tag> getMbrTags(String userId) {
		List<Tag> tagList = new ArrayList<Tag>();
		List<String> allTags = linkDao.findAllByUserId(userId);
		for (String tagId : allTags) {
			Tag tag = tagDao.findByTagId(tagId);
			tagList.add(tag);
		}
		return tagList;
	}

	/**
	 * 绑定微信 @param @return void @throws
	 */
	public void bindWeiXin(Map<String, Object> param) {
		String weiCode = (String) param.get("code");
		String userId = (String) param.get("userId");
		User userByOpenId = userRep.findByWechatOpenIdAndIsDeleted(weiCode, 0);
		if (!StringUtils.isEmpty(userByOpenId)) {
			throw new RestException("该微信已绑定用户，请尝试更换微信号进行绑定");
		}
		if (StringUtils.isEmpty(weiCode)) {
			throw new RestException("微信授权失败");
		}
		User user = userRep.findOne(userId);
		WeixinOauth2Token weixinOauth2Token = null;
		WeixinUserInfo weixinUserInfo = null;
		if (weiCode != null) {
			String accessToken = CommonUtil.getToken(" ", "1cb4b7eae167f1190d6f3b8f94db5a79").getAccessToken();
			weixinOauth2Token = weiXinService.getOauth2AccessToken(weiCode);
			if (null != weixinOauth2Token && null != weixinOauth2Token.getOpenId()) {
				weixinUserInfo = weiXinService.getUserInfo(accessToken, weixinOauth2Token.getOpenId());
				user.setWechatOpenId(weixinUserInfo.getOpenId());
				user.setWechat(weixinUserInfo.getNickname());
			}
		}
		userRep.save(user);
	}
}
