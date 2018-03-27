package com.visionet.syh_mall.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.exception.RestException;
import com.visionet.syh_mall.web.BaseController;

/**
 * 平台端验证请求控制登录拦截
 * @author xiaofb
 * @time 2017年11月6日
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		String referer = request.getHeader("referer");
		if(!StringUtils.isEmpty(referer) && referer.contains("/admin_platform/")){
			if(!uri.contains("/adminLogin") && !uri.contains("/logout") && !uri.contains("/getGoodsEvaluationDetail")){
				String userId = BaseController.getCurrentUserId();
				if(StringUtils.isEmpty(userId)){
					throw new RestException(BusinessStatus.UNLOGIN.getCode(),BusinessStatus.UNLOGIN.getDesc());
				}
			}
		}
		return super.preHandle(request, response, handler);
	}
}
