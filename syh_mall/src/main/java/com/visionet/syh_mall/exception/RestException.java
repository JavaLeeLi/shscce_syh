package com.visionet.syh_mall.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSONObject;
import com.visionet.syh_mall.common.BaseReturnVo;
import com.visionet.syh_mall.common.constant.BusinessStatus;
import com.visionet.syh_mall.common.constant.MobileKey;

public class RestException extends RuntimeException {

	private static final long serialVersionUID = -1401593546385403720L;

	private static JSONObject mapper = new JSONObject();

	public HttpStatus status;
	
	public BaseReturnVo<Object> baseReturnVo;

	public RestException() {
	}

	public RestException(HttpStatus status) {
		this.status = status;
	}

	public RestException(HttpStatus status, String message) {
		super(message);
		this.status = status;
		
	}

	/**
	 * messageMap.put("code","202"); messageMap.put("msg","XXXXXXXXXX"); status
	 * 此处禁止用200，否则手机端不错异常处理
	 * 
	 * @param status
	 * @param messageMap
	 */
	@SuppressWarnings("static-access")
	public RestException(HttpStatus status, Map<String, String> messageMap) {
		super(mapper.toJSONString(messageMap));
		this.status = status;
	}

	/**
	 * 默认stauts:202, 表示服务器已接受请求，但尚未成功处理
	 * 
	 * @param messageMap
	 */

	@SuppressWarnings("static-access")
	public RestException(Map<String, String> messageMap) {
		super(mapper.toJSONString(chkMessageMap(messageMap)));
		this.status = HttpStatus.ACCEPTED;
	}

	@SuppressWarnings("static-access")
	public RestException(String message) {
		super(mapper.toJSONString(getMessageMap(message)));
		this.baseReturnVo=getMessageMap(message);
		this.status = HttpStatus.ACCEPTED;
	}

	@SuppressWarnings("static-access")
	public RestException(String code, String message) {
		super(mapper.toJSONString(getMessageMap(code, message)));
		this.baseReturnVo=getMessageMap(code, message);
		this.status = HttpStatus.ACCEPTED;
	}

	private static Map<String, String> chkMessageMap(Map<String, String> messageMap) {
		if (messageMap.size() == 1 && messageMap.get(MobileKey.CODE) == null) {
			messageMap.put(MobileKey.CODE, BusinessStatus.UNKNOWN_ERROR.getCode());
		}
		return messageMap;
	}

	private static BaseReturnVo<Object> getMessageMap(String statusCode, String message) {
		return BaseReturnVo.fail(message, statusCode);
	}

	private static BaseReturnVo<Object> getMessageMap(String message) {
		return BaseReturnVo.fail(message, BusinessStatus.UNKNOWN_ERROR.getCode());
	}
}
