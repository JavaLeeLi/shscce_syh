package com.visionet.syh_mall.web.adminManager.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.interceptor.Log;
import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.admin.AdminUser;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.UserService;
import com.visionet.syh_mall.service.adminUser.AdminUserService;
import com.visionet.syh_mall.service.adminUser.RoleService;
import com.visionet.syh_mall.service.mobile.ShopService;
import com.visionet.syh_mall.vo.AdminUserVo;
import com.visionet.syh_mall.vo.ModuleVo;
import com.visionet.syh_mall.web.BaseController;

/**
 * 
 * @author xiaofb
 * @time 2017年8月17日
 */
@RestController
@RequestMapping("/api/web/")
public class AdminUserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	protected ShopService shopService;

	/**
	 * @Title: merchantLogin @Description: 商家管理端的登录验证s @param @param
	 *         mapUser @param @return 设定文件 @return BaseReturnVo
	 *         <Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/merchantLogin", method = RequestMethod.POST)
	public BaseReturnVo<Object> merchantLogin(@RequestBody Map<String, String> mapUser) {
		logger.info("用户登陆:{}", mapUser);
		Map<String, Object> result = new HashMap<String, Object>();
		String userName = mapUser.get("loginName");
		String password = mapUser.get("loginPwd");
		User user = userService.findUserByLoginName(userName);
		if (StringUtils.isEmpty(user)) {
			throw new RestException(BusinessStatus.USER_NOTEXIST.getCode(), BusinessStatus.USER_NOTEXIST.getDesc());
		}
		if (user.getUserTypeCode().equals("authentication_unknown")) {
			throw new RestException(BusinessStatus.SHOP_NO_RENZHENG.getCode(),
					BusinessStatus.SHOP_NO_RENZHENG.getDesc());
		}
		if (user.getUserStatusCode().equals("user_blacklist")) {
			throw new RestException(BusinessStatus.USER_LAHEI.getCode(), BusinessStatus.USER_LAHEI.getDesc());
		}
		Shop shop = shopService.findIdByUserId(user.getId());
		if (StringUtils.isEmpty(shop)) {
			throw new RestException(BusinessStatus.USER_NO_SHOP.getCode(), BusinessStatus.USER_NO_SHOP.getDesc());
		}
		if (!shop.getStatusCode().equals("shop_normal")) {
			throw new RestException(BusinessStatus.SHOP_NO_REVIEW.getCode(), BusinessStatus.SHOP_NO_REVIEW.getDesc());
		}
		if (shop.getShopIsFrozen() == 1) {
			throw new RestException(BusinessStatus.SHOP_DONGJIE.getCode(), BusinessStatus.SHOP_DONGJIE.getDesc());
		}
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password, "shop");
		Subject currentUser = SecurityUtils.getSubject();
		try {
			logger.info("对用户[" + userName + "]进行登录验证..验证开始");
			currentUser.login(token);
			logger.info("对用户[" + userName + "]进行登录验证..验证通过");
		} catch (UnknownAccountException uae) {
			logger.info("对用户[" + userName + "]进行登录验证..验证未通过,未知账户");
			throw new RestException(BusinessStatus.USER_NOTEXIST.getCode(), BusinessStatus.USER_NOTEXIST.getDesc());
		} catch (IncorrectCredentialsException ice) {
			logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误的凭证");
			throw new RestException(BusinessStatus.PWD_ERROR.getCode(), BusinessStatus.PWD_ERROR.getDesc());
		} catch (LockedAccountException lae) {
			logger.info("对用户[" + userName + "]进行登录验证..验证未通过,账户已锁定");
			throw new RestException("账户已锁定");
		} catch (AuthenticationException ae) {
			logger.info("对用户[" + userName + "]进行登录验证..验证未通过,堆栈轨迹如下");
			throw new RestException(BusinessStatus.PWD_ERROR.getCode(), BusinessStatus.PWD_ERROR.getDesc());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}
		// 验证是否登录成功
		if (!currentUser.isAuthenticated()) {
			token.clear();
		}
		// 登陆成功后操作
		userService.loginOperta(getCurrentUserId());
		// 登录后返回的用户数据
		result.put("adminID", getCurrentUserId());
		result.put("adminName", getCurrentLoginName());
		// result.put("adminRoleID", getCurrentUserRoleId());
		// result.put("adminImgUrl", getCurrentUserPicUrl());
		result.put("memberType",getCurrentMemberType());
		result.put("adminToken", null);
		result.put("shopID", getCurrentUserShop());
		return BaseReturnVo.success(result);
	}

	/**
	 * 管理员登陆
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public BaseReturnVo<Object> adminLogin(@RequestBody Map<String, String> mapUser) {
		logger.info("用户登陆:{}", mapUser);
		Map<String, Object> result = new HashMap<String, Object>();
		String userName = mapUser.get("loginName");
		String password = mapUser.get("loginPwd");
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password, "web");
		Subject currentUser = SecurityUtils.getSubject();
		try {
			logger.info("对用户[" + userName + "]进行登录验证..验证开始");
			currentUser.login(token);
			logger.info("对用户[" + userName + "]进行登录验证..验证通过");
		} catch (UnknownAccountException uae) {
			logger.info("对用户[" + userName + "]进行登录验证..验证未通过,未知账户");
			throw new RestException(BusinessStatus.USER_NOTEXIST.getCode(), BusinessStatus.USER_NOTEXIST.getDesc());
		} catch (IncorrectCredentialsException ice) {
			ice.printStackTrace();
			logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误的凭证");
			throw new RestException(BusinessStatus.PWD_ERROR.getCode(), BusinessStatus.PWD_ERROR.getDesc());
		} catch (LockedAccountException lae) {
			logger.info("对用户[" + userName + "]进行登录验证..验证未通过,账户已锁定");
			throw new RestException("账户已锁定");
		} catch (AuthenticationException ae) {
			logger.info("对用户[" + userName + "]进行登录验证..验证未通过,堆栈轨迹如下");
			throw new RestException(BusinessStatus.PWD_ERROR.getCode(), BusinessStatus.PWD_ERROR.getDesc());
		} catch (Exception e) {
			e.printStackTrace();
			sysException();
		}

		Subject aa = SecurityUtils.getSubject();
		Session session = aa.getSession();
		String id = session.getId().toString();
		long time = session.getTimeout();
		Date start = session.getStartTimestamp();
		Date last = session.getLastAccessTime();

		// 验证是否登录成功
		if (!currentUser.isAuthenticated()) {
			token.clear();
		}
		// 登陆成功后操作
		adminUserService.loginOperta(getCurrentUserId());
		// 权限列表
		List<ModuleVo> permissionsList = roleService.getUserPermissions(getCurrentUserRoleId());
		result.put("adminID", getCurrentUserId());
		result.put("adminName", getCurrentLoginName());
		// result.put("adminImgUrl", getCurrentUserPicUrl());
		result.put("adminToken", null);
		result.put("adminRoleID", getCurrentUserRoleId());
		result.put("roleModules", permissionsList);
		return BaseReturnVo.success(result);
	}

	/**
	 * 用户退出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@Log(name="用户退出",model="会员模块")
	public BaseReturnVo<Object> logout() {
		// 使用权限管理工具进行用户的退出，跳出登录，给出提示信息
		SecurityUtils.getSubject().logout();
		return BaseReturnVo.success("退出成功");
	}

	/**
	 * 删除管理员
	 * 
	 * @param //id
	 */
	@RequestMapping(value = "/delAdminUser", method = RequestMethod.POST)
	@Log(name="删除管理员",model="权限模块")
	public BaseReturnVo<Object> delAdminUser(@RequestBody Map<String, Object> param) {
		logger.info("删除管理员:{}", param);
		adminUserService.delAdminUser((String) param.get("adminUserID"));
		return BaseReturnVo.success("删除成功");
	}

	/**
	 * 重置管理员密码
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/restPwd", method = RequestMethod.POST)
	@Log(name="重置管理员密码",model="权限模块")
	public BaseReturnVo<Object> restPwd(@RequestBody Map<String, String> param) {
		adminUserService.restPassword(param.get("adminUserID"));
		return BaseReturnVo.success("重置成功");
	}

	/**
	 * 获取管理员列表接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryAdminList", method = RequestMethod.POST)
	public BaseReturnVo<Object> queryAdminList() {
		List<AdminUserVo> list = null;
		try {
			list = adminUserService.getAdminUserList();
		} catch (RestException e) {
			e.printStackTrace();
			throw new RestException(HttpStatus.ACCEPTED,e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常");
		}
		return BaseReturnVo.success(list);
	}

	/**
	 * 修改管理员信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/modifyAdminInfo", method = RequestMethod.POST)
	@Log(name="修改管理员信息",model="权限模块")
	public BaseReturnVo<Object> modifyAdminInfo(@RequestBody Map<String, String> param) {
		logger.info("修改管理员请求参数:{}", param);
		try {
			AdminUser adminuser = new AdminUser();
			adminuser.setAliasName(param.get("adminUserAliasName"));
			// adminuser.setImageUrl(param.get("adminUserEmail"));
			adminuser.setMail(param.get("adminUserEmail"));
			adminuser.setPhone(param.get("adminUserPhone"));
			adminUserService.updateAdminUserInfo(adminuser);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常,修改失败");
		}
		return BaseReturnVo.success("修改成功");
	}

	/**
	 * 修改密码
	 * 
	 * @param param
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
	@Log(name="修改密码",model="权限模块")
	public BaseReturnVo<Object> modifyPwd(@RequestBody Map<String, String> param) {
		String oldPwd = param.get("oldPwd");
		String newPwd = param.get("newPwd");
		String confirmNewPwd = param.get("confirmNewPwd");
		adminUserService.modifyPassword(getCurrentUserId(), oldPwd, newPwd, confirmNewPwd);
		return BaseReturnVo.success("修改成功");
	}

	/**
	 * 编辑或添加管理员
	 * 
	 * @param //param
	 * @return
	 */
	@RequestMapping(value = "/addSystemAdmin", method = RequestMethod.POST)
	@Log(name="编辑/添加管理员",model="权限模块")
	public BaseReturnVo<Object> addSystemAdmin(@RequestBody AdminUserVo vo) {
		logger.info("编辑或添加管理员请求参数:{}", vo);
		try {
			adminUserService.addUser(vo.convertDto(vo));
		} catch (RestException e) {
			throw new RestException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestException("未知异常,修改失败");
		}
		return BaseReturnVo.success("操作成功");
	}

}
