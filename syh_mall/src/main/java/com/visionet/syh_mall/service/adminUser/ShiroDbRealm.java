/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.visionet.syh_mall.service.adminUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.visionet.syh_mall.entity.User;
import com.visionet.syh_mall.entity.admin.AdminUser;
import com.visionet.syh_mall.entity.admin.Role;
import com.visionet.syh_mall.entity.admin.SystemModule;
import com.visionet.syh_mall.entity.shop.Shop;
import com.visionet.syh_mall.service.UserService;
import com.visionet.syh_mall.service.mobile.ShopService;

public class ShiroDbRealm extends AuthorizingRealm {
	protected AdminUserService adminUserService;
	protected UserService userService;
	protected ShopService shopService;

	

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// web管理端认证
		if ("web".equals(token.getHost())) {
			AdminUser adminUser = adminUserService.findUserByLoginName(token.getUsername());
			System.out.println("user name:" + adminUser.getLoginName());
			if (adminUser != null) {
				return new SimpleAuthenticationInfo(new ShiroUser(adminUser.getId(), adminUser.getLoginName(),
						adminUser.getAliasName(), adminUser.getRoleId()), adminUser.getPassword(), getName());
			}
		}
		// mobile玩家端验证码登录
		if ("mobile".equals(token.getHost())) {
			User user = null;
			try {
				user = userService.findUserByPhone(token.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("user name:" + user.getLoginName());
			if (user != null) {
				return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getLoginName(), user.getAliasName(), null),
						user.getLoginPassword(), getName());
			}
		}
		// 商家登录获取用户信息
		if ("shop".equals(token.getHost())) {
			User user = userService.findUserByLoginName(token.getUsername());
			Shop shop = shopService.findIdByUserId(user.getId());
			System.out.println("user name:" + user.getLoginName());
			if (user != null) {
				return new SimpleAuthenticationInfo(
						new ShiroUser(user.getId(), user.getLoginName(), user.getAliasName(), null, shop.getId(),user.getMemberType()),
						user.getLoginPassword(), getName());
			}
		}
		return null;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@SuppressWarnings("null")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		AdminUser user = adminUserService.findUserByLoginName(shiroUser.loginName);
		// 用户角色
		Role role = adminUserService.getUserRole(user.getRoleId());
		info.addRole(role.getRoleName());
		// 用户权限
		List<SystemModule> systemModuleList = null;
		List<String> permissions = new ArrayList<String>();
		for (SystemModule systemModule : systemModuleList) {
			permissions.add(systemModule.getModuleCode());
		}
		info.addStringPermissions(permissions);
		return info;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public String id;
		public String loginName;
		public String aliasName;
		public String roleId;
		public String shopID;
		public Integer memberType;

		public ShiroUser(String id, String loginName, String aliasName, String roleId) {
			this.id = id;
			this.loginName = loginName;
			this.aliasName = aliasName;
			this.roleId = roleId;
		}

		public ShiroUser(String id, String loginName, String aliasName, String roleId, String shopID,Integer memberType) {
			this.id = id;
			this.loginName = loginName;
			this.aliasName = aliasName;
			this.roleId = roleId;
			this.shopID = shopID;
			this.memberType = memberType;
		}

		public String getAliasName() {
			return aliasName;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null) {
					return false;
				}
			} else if (!loginName.equals(other.loginName)) {
				return false;
			}
			return true;
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setAccountService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}


}
