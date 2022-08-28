package com.chengqiang.common.result;

import java.io.Serializable;

/**
 * 接口返回数据格式
 * @author chengqiang
 * @email 1453001422@qq.com
 * @date 2022年5月3
 * @param <T>
 */
public class ApiResult<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 返回处理消息
	 */
	String message = "操作成功";
	/**
	 * 返回code码
	 */
	int code;
	/**
	 * 返回数据
	 */
	T data;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ApiResult() {
		
	};
	
	public static ApiResult<Object> success() {
		ApiResult<Object> result = new ApiResult<Object>();
		result.setMessage("成功");
		result.setCode(Constant.SUCCESS_CODE);
		return result;
	}
	
	public static ApiResult<Object> success(String message) {
		ApiResult<Object> result = new ApiResult<Object>();
		result.setMessage(message);
		result.setCode(Constant.SUCCESS_CODE);
		return result;
	}
	
	public static ApiResult<Object> success(Object data) {
		ApiResult<Object> result = new ApiResult<Object>();
		result.setData(data);
		result.setCode(Constant.SUCCESS_CODE);
		return result;
	}
	
	public static<T> ApiResult<T> Success() {
		ApiResult<T> result = new ApiResult<T>();
		result.setMessage("成功");
		result.setCode(Constant.SUCCESS_CODE);
		return result;
	}
	
	public static<T> ApiResult<T> Success(T data) {
		ApiResult<T> result = new ApiResult<T>();
		result.setData(data);
		result.setCode(Constant.SUCCESS_CODE);
		return result;
	}
	
	public static<T> ApiResult<T> Success(String message, T data) {
		ApiResult<T> result = new ApiResult<T>();
		result.setData(data);
		result.setMessage(message);
		result.setCode(Constant.SUCCESS_CODE);
		return result;
	}
	
	public static ApiResult<Object> error(String message) {
		return error(message, Constant.ERROR_CODE);
	}
	
	public static ApiResult<Object> error(String message, int code) {
		ApiResult<Object> result = new ApiResult<Object>();
		result.setMessage(message);
		result.setCode(code);
		return result;
	}
	

}