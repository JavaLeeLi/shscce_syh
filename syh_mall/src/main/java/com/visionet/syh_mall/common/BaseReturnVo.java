package com.visionet.syh_mall.common;

import java.io.Serializable;

import com.visionet.syh_mall.common.constant.BusinessStatus;

public class BaseReturnVo<T> implements Serializable {
	protected static final long serialVersionUID = -1373760761780840081L;

	protected boolean success = true;
	protected String code = BusinessStatus.OK.getCode();
	protected String msg = BusinessStatus.SUCCESS;

	T data;

	public BaseReturnVo(boolean success, String code, String msg) {
		this.success = success;
		this.code = code;
		this.msg = msg;
	}

	public BaseReturnVo(boolean success, String code, String msg, T data) {
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public BaseReturnVo(T data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 成功返回
	 * 
	 * @param data
	 * @return
	 */
	public static BaseReturnVo<Object> success(Object data) {
		boolean success = true;
		String code = BusinessStatus.OK.getCode();
		String msg = BusinessStatus.OK.getDesc();
		return new BaseReturnVo<Object>(success, code, msg, data);
	}

	/**
	 * 成功返回
	 * 
	 * @param data
	 * @return
	 */
	public static BaseReturnVo<Object> success(String msg) {
		boolean success = true;
		String code = BusinessStatus.OK.getCode();
		msg = BusinessStatus.OK.getDesc();
		return new BaseReturnVo<Object>(success, code, msg, null);
	}

	/**
	 * 失败返回
	 * 
	 * @param data
	 * @return
	 */
	public static BaseReturnVo<Object> fail(String msg, String code) {
		boolean success = false;
		return new BaseReturnVo<Object>(success, code, msg);
	}

}
