package com.visionet.syh_mall.exception;

import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alibaba.fastjson.JSONObject;
import com.visionet.syh_mall.common.constant.BusinessStatus;

/**
 * 统一异常处理
 * @author xiaofb
 * @time 2017年9月20日
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * 自定义业务异常
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { RestException.class })
	public final ResponseEntity<?> handleException(RestException ex, WebRequest request) {
		String body = ex.getMessage();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpStatus status = (ex.status != null) ? ex.status : HttpStatus.INTERNAL_SERVER_ERROR;
		return handleExceptionInternal(ex, body, headers, status, request);
	}
	
	/**
	 * shiro认证异常处理
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = {UnauthenticatedException.class})
	public final ResponseEntity<?> UnauthenticatedException(UnauthenticatedException ex, WebRequest request) {
		JSONObject json = new JSONObject();
		json.put("code", BusinessStatus.UNLOGIN.getCode());
		json.put("msg", BusinessStatus.UNLOGIN.getDesc());
		json.put("success", false);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(ex, json.toJSONString(), headers, HttpStatus.ACCEPTED, request);
	}
}
