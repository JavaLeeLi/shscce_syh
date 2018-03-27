package com.visionet.syh_mall.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.utils.PageInfo;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.service.adminUser.ShiroDbRealm.ShiroUser;

@Component
public class BaseController {

	// 成功
	protected static final boolean SUCCESS_TRUE = true;
	// 失败
	protected static final boolean SUCCESS_FALSE = false;
	// 消息
	protected static final String MSG = "msg";
	// 响应码
	protected static final String CODE = "";

	/**
	 * 当前用户id
	 * 
	 * @return
	 */
	public static String getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user == null)
			return null;
		return user.id;
	}

	public static Integer getCurrentMemberType() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user == null)
			return null;
		return user.memberType;
	}

	public static String getCurrentLoginName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user == null)
			return null;
		return user.loginName;
	}

	/**
	 * 当前用户name
	 * 
	 * @return
	 */
	public static String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user == null)
			return null;
		return user.aliasName;
	}

	/**
	 * @Title: getCurrentUserShop @Description: 获取当前登录人的店铺ID @param @return
	 * 设定文件 @return String 返回类型 @throws
	 */
	public static String getCurrentUserShop() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user == null)
			return null;
		return user.shopID;
	}

	/**
	 * 当前用户角色Id
	 * 
	 * @return
	 */
	public static String getCurrentUserRoleId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (user == null)
			return null;
		return user.roleId;
	}

	/**
	 * 系统异常处理 @param @return void @throws
	 */
	public static void sysException() {
		Map<String, String> messageMap = new HashMap<String, String>();
		messageMap.put("code", BusinessStatus.UNKNOWN_ERROR.getCode());
		messageMap.put("msg", BusinessStatus.UNKNOWN_ERROR.getDesc());
		throw new RestException(HttpStatus.ACCEPTED, messageMap);
	}

	public static PageInfo getPageInfo(Map<String, Object> param, Direction direction,String sortColumn) {
		PageInfo pageInfo = new PageInfo((Integer) param.get("pageIndex"), (Integer) param.get("itemCount"), sortColumn,direction);
		return pageInfo;
	}

}
