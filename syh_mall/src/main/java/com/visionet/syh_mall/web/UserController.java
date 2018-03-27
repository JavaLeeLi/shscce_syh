package com.visionet.syh_mall.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.visionet.syh_mall.entity.*;
import com.visionet.syh_mall.service.CommonUtil;
import com.visionet.syh_mall.service.WeiXinService;
import com.visionet.syh_mall.service.account.UserAccountFlowService;
import com.visionet.syh_mall.vo.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.expression.ParseException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.common.utils.Validator;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.repository.FileManageRepostory;
import com.visionet.syh_mall.repository.VerifyCodeRepository;
import com.visionet.syh_mall.service.UserService;

@RestController
@RequestMapping("/api/mbr")
public class UserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private VerifyCodeRepository verifyCodeDao;
	@Autowired
	private FileManageRepostory fManageRepostory;
	@Autowired
	private UserAccountFlowService accountFlowService;
	@Autowired
    private WeiXinService weiXinService;


	/**
	 * 绑定微信
	 * @param 
	 * @return BaseReturnVo<Object>
	 * @throws
	 */
	@RequestMapping(value="bindWeiXin",method=RequestMethod.POST)
	public BaseReturnVo<Object> bindWeiXin(@RequestBody Map<String,Object> param){
		logger.info("绑定微信：{}",param);
		try {
			userService.bindWeiXin(param);
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(e.getMessage());
		} catch (Exception e) {
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	@RequestMapping(value = "/verifyCodeLogin", method = RequestMethod.POST)
	public BaseReturnVo<Object> login(@RequestBody Map<String, String> mapUser, HttpServletRequest request) {
		logger.info("用户登陆:{}", mapUser);
		String phone = mapUser.get("phone");
		String smsCode = mapUser.get("smsCode");
		String weiCode = mapUser.get("code");
		String verifyCode = verifyCodeDao.findCodeByTime(phone);
		if (verifyCode == null) {
			throw new RestException("验证码不正确");
		}
		if (!verifyCode.equals(smsCode)) {
			throw new RestException("验证码不正确");
		}
		List<VerifyCode> list = verifyCodeDao.findVerifyCodeByTime(phone);
		VerifyCode code = list.get(0);
		if (null != code && null != code.getId()) {
			code.setHasValidated("validated");
			code = verifyCodeDao.save(code);
		}
		User user = userService.findUserByPhone(phone);
		//判断当前微信openid是否有对应的用户
		weiXinValidate(user,weiCode);
		if (user == null) {
			throw new RestException("账号不存在！");
		}
		if (user.getUserStatusCode().equals("user_blacklist")) {
			throw new RestException(BusinessStatus.USER_LAHEI.getCode(), BusinessStatus.USER_LAHEI.getDesc());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		String userName = user.getPhone();
		String pwd = user.getLoginPassword();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, pwd, "mobile");
		Subject currentUser = SecurityUtils.getSubject();
		try {
			logger.info("对用户[" + userName + "]进行登录验证..验证开始");
			currentUser.login(token);
			logger.info("对用户[" + userName + "]进行登录验证..验证通过");
		} catch (IncorrectCredentialsException ice) {
			logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误的凭证");
			throw new RestException("密码不正确");
		} catch (AuthenticationException ae) {
			logger.info("对用户[" + userName + "]进行登录验证..验证未通过,堆栈轨迹如下");
			throw new RestException("用户名或密码不正确");
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		// 验证是否登录成功
		if (!currentUser.isAuthenticated()) {
			token.clear();
			throw new RestException("登录失败");
		}

		// 登陆成功后操作
		userService.loginOperta(getCurrentUserId());
		User u = userService.findUserById(getCurrentUserId());
		result.put("id", getCurrentUserId());
		result.put("userPhone", u.getPhone());
		result.put("userName", u.getAliasName());
		result.put("adminToken", null);
		result.put("adminRoleID", getCurrentUserRoleId());
		result.put("userWechat", u.getWechatOpenId());
		result.put("userQQ", u.getQqOpenId());
		result.put("userEmail", u.getMail());
		result.put("userType", u.getUserTypeCode());
		if (Validator.isNotNull(u.getImgFileId())) {
			FileManage pic = fManageRepostory.findOne(u.getImgFileId());
			if (null != pic && null != pic.getId()) {
				result.put("userImgUrl", pic.getAbsolutePath());
			}
		}
		result.put("userSign", u.getSignature());
		result.put("userAddr", u.getAddress());
		result.put("invitationCode", u.getInvitationCode());
		// String sessionId = request.getHeader("Cookie");
		String sessionId = (String) currentUser.getSession().getId();
		result.put("sessionId", sessionId);
		return BaseReturnVo.success(result);
	}

	@RequestMapping(value = "/pwdLogin", method = RequestMethod.POST)
	public BaseReturnVo<Object> pwdLogin(@RequestBody Map<String, String> mapUser, HttpServletRequest request) {
		logger.info("用户登陆:{}", mapUser);
		String weiCode = mapUser.get("code");
		String userName = mapUser.get("loginName");
		String password = mapUser.get("loginPwd");
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userService.findUserByLoginName(userName);
        if (user == null) {
			throw new RestException("账号不存在！");
		}
		//判断当前微信openid是否有对应的用户
		weiXinValidate(user,weiCode);
		if (user.getUserStatusCode().equals("user_blacklist")) {
			throw new RestException(BusinessStatus.USER_LAHEI.getCode(), BusinessStatus.USER_LAHEI.getDesc());
		}
		UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), password, "mobile");
		Subject currentUser = SecurityUtils.getSubject();
		try {
			logger.info("对用户[" + userName + "]进行登录验证..验证开始");
			currentUser.login(token);
			logger.info("对用户[" + userName + "]进行登录验证..验证通过");
		} catch (IncorrectCredentialsException ice) {
			logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误的  ");
			throw new RestException("密码不正确");
		} catch (AuthenticationException ae) {
			logger.info("对用户[" + userName + "]进行登录验证..验证未通过,堆栈轨迹如下");
			throw new RestException("用户名或密码不正确");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException();
		}
		// 验证是否登录成功
		if (!currentUser.isAuthenticated()) {
			token.clear();
			throw new RestException("登录失败");
		}

		// 登陆成功后操作
		userService.loginOperta(getCurrentUserId());
		User u = userService.findUserById(getCurrentUserId());
		result.put("id", getCurrentUserId());
		result.put("userPhone", u.getPhone());
		result.put("userName", u.getAliasName());
		result.put("adminToken", null);
		result.put("adminRoleID", getCurrentUserRoleId());
		result.put("userWechat", u.getWechatOpenId());
		result.put("userQQ", u.getQqOpenId());
		result.put("userEmail", u.getMail());
		result.put("userType", u.getUserTypeCode());
		if (Validator.isNotNull(u.getImgFileId())) {
			FileManage pic = fManageRepostory.findOne(u.getImgFileId());
			if (null != pic && null != pic.getId()) {
				result.put("userImgUrl", pic.getAbsolutePath());
			}
		}
		result.put("userSign", u.getSignature());
		result.put("userAddr", u.getAddress());
		result.put("invitationCode", u.getInvitationCode());
		String sessionId = (String) currentUser.getSession().getId();
		// String sessionId = request.getHeader("Cookie");
		result.put("sessionId", sessionId);
		return BaseReturnVo.success(result);
	}


	private void weiXinValidate(User user,String weiCode){
        if (!StringUtils.isEmpty(user.getWechatOpenId())) {
			logger.info("微信openid不为空"+user.getWechatOpenId());
            WeixinOauth2Token weixinOauth2Token = null;
            WeixinUserInfo weixinUserInfo = null;
            if (!StringUtils.isEmpty(weiCode)) {
				logger.info("前端传来的微信code不为空"+weiCode);
                String accessToken = CommonUtil.getToken(" ", "1cb4b7eae167f1190d6f3b8f94db5a79").getAccessToken();
				weixinOauth2Token = weiXinService.getOauth2AccessToken(weiCode);
				if(null == weixinOauth2Token){
					throw new RestException("该账号已绑定对应微信，请去绑定的微信中登录");
				}
				if (null != weixinOauth2Token && null != weixinOauth2Token.getOpenId()) {
					weixinUserInfo = weiXinService.getUserInfo(accessToken, weixinOauth2Token.getOpenId());					
					if (StringUtils.isEmpty(weixinUserInfo)) {
						throw new RestException("该账号已绑定对应微信，请去绑定的微信中登录");
					}
					logger.info("对微信[" + weixinUserInfo.getOpenId() + "]进行登录验证..验证开始");
					logger.info("下面判断的两个对象"+weixinUserInfo.getOpenId()+"======="+user.getWechatOpenId());
					if (!weixinUserInfo.getOpenId().equals(user.getWechatOpenId())) {
						throw new RestException("该账号已绑定对应微信，请去绑定的微信中登录");
					}
				}
			}
		}
	}




	/**
	 * @Title: register @Description: 用户注册 @param @param mapUser @param @return
	 *         设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public BaseReturnVo<Object> register(@RequestBody Map<String, String> mapUser) {
		logger.info("用户注册:{}", mapUser);
		String phone = mapUser.get("phone");
		String smsCode = mapUser.get("smsCode");
		String password = mapUser.get("password");
		String parentInvitationCode = mapUser.get("parentInvitationCode");
		String weiCode = mapUser.get("code");
		String source = mapUser.get("source");
		String verifyCode = verifyCodeDao.findCodeByTime(phone);
		if (verifyCode == null) {
			throw new RestException("验证码不正确");
		}
		if (!verifyCode.equals(smsCode)) {
			throw new RestException("验证码不正确");
		}
		List<VerifyCode> list = verifyCodeDao.findVerifyCodeByTime(phone);
		VerifyCode code = list.get(0);
		if (null != code && null != code.getId()) {
			code.setHasValidated("validated");
			code = verifyCodeDao.save(code);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userService.findUserByPhone(phone);
		if (user != null) {
			throw new RestException("该手机号已注册！");
		}
		try {
			userService.createMember(password, phone, parentInvitationCode, weiCode, source);
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(e.getMessage());
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * 修改密码
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/editPwd", method = RequestMethod.POST)
	public BaseReturnVo<Object> editPwd(@RequestBody Map<String, String> param) {
		String phone = param.get("phone");
		String smsCode = param.get("smsCode");
		String newPwd = param.get("newPwd");
		String confirmNewPwd = param.get("confirmNewPwd");
		/*
		 * String userid = userDao.findByPhone(phone); if (null==userid){ throw
		 * new RestException("该手机号不存在"); } String currentUserId =
		 * getCurrentUserId(); if (currentUserId!=userid){ throw new
		 * RestException("请输入预留的手机号"); }
		 */
		String verifyCode = verifyCodeDao.findCodeByTime(phone);
		if (verifyCode == null) {
			throw new RestException("验证码不正确");
		}
		if (!verifyCode.equals(smsCode)) {
			throw new RestException("验证码不正确");
		}
		List<VerifyCode> list = verifyCodeDao.findVerifyCodeByTime(phone);
		VerifyCode code = list.get(0);
		if (null != code && null != code.getId()) {
			code.setHasValidated("validated");
			code = verifyCodeDao.save(code);
		}
		userService.editPwd(phone, newPwd, confirmNewPwd);
		return BaseReturnVo.success("修改成功");
	}

	/**
	 * 
	 * @Title: searchMbr @Description: (查询用户的方法) @param @param
	 *         param @param @return @param @throws ParseException 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "searchMbr", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchMbr(@RequestBody Map<String, Object> param) throws ParseException {
		logger.info("查询用户会员的参数{}", param);
		Map<String, Object> result = null;
		try {
			result = userService.searchMbr(param, getPageInfo(param, Direction.DESC, "createTime"));
		} catch (RestException e) {
			logger.error("搜索会员异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索会员异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 *
	 * @Title: defriendMbr @Description: 拉黑会员的方法 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "defriendMbr", method = RequestMethod.POST)
	@Log(name = "拉黑会员", model = "会员模块")
	public BaseReturnVo<Object> defriendMbr(@RequestBody Map<String, String> param) {
		logger.info("拉黑用户会员的参数{}", param);
		try {
			userService.defriendMbr(param.get("mbrID"));
		} catch (RestException e) {
			logger.error("拉黑会员异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("拉黑会员异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: recoverMbr @Description: 重申会员 @param @param param @param @return
	 *         设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "recoverMbr", method = RequestMethod.POST)
	@Log(name = "重申会员", model = "会员模块")
	public BaseReturnVo<Object> recoverMbr(@RequestBody Map<String, String> param) {
		logger.info("重申会员 的参数{}", param);
		try {
			userService.recoverMbr(param.get("mbrID"));
		} catch (RestException e) {
			logger.error("重申会员 异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("重申会员 异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 获取会员信息
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMbrInfo")
	public BaseReturnVo<Object> getMbrInfo(@RequestBody UserVo qo) throws Exception {
		logger.info("获取会员信息:{}", qo);
		UserVo user = null;
		try {
			user = userService.getMbrInfo(qo);
		} catch (RestException e) {
			e.printStackTrace();
			logger.info("获取会员信息异常:{}", e.getMessage());
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(user);
	}

	/**
	 * 修改会员信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editMbrInfo", method = RequestMethod.POST)
	@RequiresAuthentication
	@Log(name = "修改会员信息", model = "会员模块")
	public BaseReturnVo<Object> editMbrInfo(@RequestBody UserVo qo) throws Exception {
		logger.info(" 修改会员信息:{}", qo);
		try {
			userService.editMbrInfo(qo);
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException("昵称已存在，修改失败");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常,修改失败");
		}
		return BaseReturnVo.success("修改成功");
	}

	/**
	 * 添加个人认证会员
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/authenticateMbr", method = RequestMethod.POST)
	@Log(name = "添加个人认证会员信息", model = "会员模块")
	public BaseReturnVo<Object> authenticateMbr(@RequestBody UserAuthenticationVo authenticationVo) throws Exception {
		logger.info("添加个人认证会员参数:{}", authenticationVo.toString());
		try {
			userService.authenticateMbr(authenticationVo);
		} catch (RestException e) {
			logger.error("添加个人认证会员异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常,修改失败");
		}
		return BaseReturnVo.success("操作成功");
	}

	/**
	 * @Title: enterpriseMbr @Description: 添加企业认证会员信息 @param @param
	 *         vo @param @return @param @throws Exception 设定文件 @return
	 *         BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/enterpriseMbr", method = RequestMethod.POST)
	@Log(name = "添加企业认证会员信息", model = "会员模块")
	public BaseReturnVo<Object> enterpriseMbr(@RequestBody CompanyUserAuthenticationVo companyUserAuthenticationVo)
			throws Exception {
		logger.info(" 添加企业认证会员信息参数:{}", companyUserAuthenticationVo);
		try {
			userService.enterpriseMbr(companyUserAuthenticationVo);
		} catch (RestException e) {
			logger.error("添加企业认证会员信息异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常,修改失败");
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: reviewAuthentication @Description: 审核会员认证 @param @param
	 *         authenticationVo @param @return @param @throws Exception
	 *         设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/reviewAuthentication", method = RequestMethod.POST)
	@Log(name = "认证审核会员", model = "会员模块")
	public BaseReturnVo<Object> reviewAuthentication(@RequestBody UserAuthenticationVo authenticationVo)
			throws Exception {
		logger.info(" 审核会员认证:{}", authenticationVo);
		try {
			userService.reviewAuthentication(authenticationVo);
		} catch (RestException e) {
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常,修改失败");
		}
		return BaseReturnVo.success("操作成功");
	}

	/**
	 * 搜索认证会员
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAuthenticationList", method = RequestMethod.POST)
	public BaseReturnVo<Object> getAuthenticationList(@RequestBody UserAuthenticationQo authenticationQo)
			throws Exception {
		logger.info("搜索会员认证的方法:{}", authenticationQo);
		Page<UserVo> page = userService.getAuthenticationList(authenticationQo);
		MbrAuthenVo result = new MbrAuthenVo();
		result.setCurPageIndex(authenticationQo.getPageIndex());
		result.setHasNext(page.hasNext());
		result.setItemCount(page.getTotalElements());
		result.setPageCount(page.getTotalPages());
		result.setMbrAuthenList(page.getContent());
		return BaseReturnVo.success(result);
	}

	/**
	 * 获取会员认证详细信息
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAuthenticationDetail")
	public BaseReturnVo<Object> getAuthenticationDetail(@RequestBody UserAuthenticationQo authenticationQo)
			throws Exception {
		logger.info("获取会员认证详细信息:{}", authenticationQo);
		UserAuthenticationVo user = null;
		try {
			user = userService.getAuthenticationDetail(authenticationQo);
		} catch (RestException e) {
			e.printStackTrace();
			logger.info("获取会员认证详细信息:{}", e.getMessage());
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(user);
	}

	/**
	 * 
	 * @Title: addMbrTags @Description: 为一批用户添加标签 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */

	@RequiresAuthentication
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "addMbrTags", method = RequestMethod.POST)
	@Log(name = "为一批会员添加标签", model = "会员模块")
	public BaseReturnVo<Object> addMbrTags(@RequestBody Map<String, Object> param) {
		logger.info("添加会员标签的参数{}", param);
		List<String> mbrIDs = (List<String>) param.get("mbrIDs");
		List<Map<String, Object>> mbrTags = (List<Map<String, Object>>) param.get("mbrTags");
		try {
			userService.addMbrTags(mbrIDs, mbrTags, getCurrentUserId());
		} catch (RestException e) {
			logger.error("为一批会员添加标签异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("为一批会员添加标签异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 
	 * @Title: editMbrTags @Description: 修改会员标签的方法 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "editMbrTags", method = RequestMethod.POST)
	@Log(name = "修改会员标签", model = "会员模块")
	public BaseReturnVo<Object> editMbrTags(@RequestBody Map<String, Object> param) {
		logger.info("修改会员标签的参数{}", param);
		String mbrID = String.valueOf(param.get("mbrID"));
		List<Map<String, Object>> mbrTags = (List<Map<String, Object>>) param.get("mbrTags");
		try {
			userService.editMbrTags(mbrID, mbrTags);
		} catch (RestException e) {
			logger.error("修改会员标签异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("修改会员标签异常:{}", e);
			e.printStackTrace();
			sysException();
		}

		return BaseReturnVo.success("成功");
	}

	/**
	 * 获得会员标签 @param @return BaseReturnVo<Object> @throws
	 */
	@RequestMapping(value = "/getMbrTags", method = RequestMethod.POST)
	public BaseReturnVo<Object> getMbrTags(@RequestBody Map<String, Object> map) {
		logger.info("获得会员标签:{}", map);
		List<Tag> mbrTags = null;
		try {
			mbrTags = userService.getMbrTags((String) map.get("mbrId"));
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(mbrTags);
	}

	/**
	 * 
	 * @Title: searchTags @Description: 搜索标签的方法 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "searchTags", method = RequestMethod.POST)
	public BaseReturnVo<Object> searchTags(@RequestBody Map<String, Object> param) {
		logger.info("搜索标签的参数{}", param);
		Map<String, Object> result = null;
		try {
			result = userService.searchTags(param, getPageInfo(param, Direction.DESC, "updateTime"));
		} catch (RestException e) {
			logger.error("搜索标签异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("搜索标签异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(result);
	}

	/**
	 * @Title: getTag @Description: 获取标签详情的方法 @param @param param @param @return
	 *         设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "getTag", method = RequestMethod.POST)
	public BaseReturnVo<Object> getTag(@RequestBody Map<String, String> param) {
		logger.info("获取标签详情的参数{}", param);
		Map<String, Object> tag = null;
		try {
			tag = userService.getTag(param.get("tagID"));
		} catch (RestException e) {
			logger.error("获取标签详情异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("获取标签详情异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(tag);
	}

	/**
	 * 
	 * @Title: addTag @Description: 添加/编辑标签的方法 @param @param
	 *         param @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "addTag", method = RequestMethod.POST)
	@Log(name = "添加/编辑标签", model = "会员模块")
	public BaseReturnVo<Object> addTag(@RequestBody Map<String, Object> param) {
		logger.info("添加/编辑标签的参数{}", param);
		try {
			Tag tag = new Tag();
			if (!StringUtils.isEmpty(param.get("tagID"))) {
				tag.setId(String.valueOf(param.get("tagID")));
			}
			tag.setTagType((Integer) param.get("tagType"));
			tag.setTagName(String.valueOf(param.get("tagName")));
			userService.addTag(tag, getCurrentUserId());
		} catch (RestException e) {
			logger.error("添加/编辑标签异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("添加/编辑标签异常:{}", e);
			e.printStackTrace();
			sysException();
		}

		return BaseReturnVo.success("成功");
	}

	/**
	 * @Title: delTag @Description: 删除标签 @param @param param @param @return
	 *         设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "delTag", method = RequestMethod.POST)
	@Log(name = "删除会员标签", model = "会员模块")
	public BaseReturnVo<Object> delTag(@RequestBody Map<String, Object> param) {
		logger.info("删除标签的参数{}", param);
		try {
			userService.delTag(String.valueOf(param.get("tagID")));
		} catch (RestException e) {
			logger.error("删除标签异常:{}", e);
			e.printStackTrace();
			throw new RestException(e.baseReturnVo.getCode(), e.baseReturnVo.getMsg());
		} catch (Exception e) {
			logger.error("删除标签异常:{}", e);
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success("成功");
	}

	/**
	 * 会员提醒记录 @param @return BaseReturnVo<Object> @throws
	 */
	@RequestMapping(value = "/getMbrRemindInfo", method = RequestMethod.POST)
	public BaseReturnVo<Object> getMbrRemindInfo() {
		logger.info("会员提醒记录:{}");
		Map<String, Object> mbrRemindInfo = null;
		try {
			mbrRemindInfo = userService.getMbrRemindInfo(getCurrentUserId());
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(BusinessStatus.getCodeByDesc(e.getMessage()), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		return BaseReturnVo.success(mbrRemindInfo);
	}

	/**
	 * @Title: getUserAccount @Description: 获取会员账户收支明细 @param @param
	 * param @param @return 设定文件 @return BaseReturnVo<Object> 返回类型 @throws
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/getUserAccount", method = RequestMethod.POST)
	public BaseReturnVo<Object> getUserAccount(@RequestBody Map<String, Object> param) {
		logger.info("获取用户账户收支明细请求参数{}", param);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Page<UserAccountFlow> page = accountFlowService.getAccountFlow((String) param.get("userLoginName"),
					getPageInfo(param, Sort.Direction.DESC, "updateTime"));
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
	 * 统计 @param @return BaseReturnVo<Object> @throws
	 */
	@RequestMapping(value = "/statistics", method = RequestMethod.POST)
	public BaseReturnVo<Object> getStatistics() {
		return BaseReturnVo.success("成功！");
	}
}
