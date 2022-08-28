package com.chengqiang.common.result;

public class Apiresponse<T> {
	/**
	 * 返回code码
	 */
	private int code;
	/**
	 * 返回处理消息
	 */
	private String message;
	/**
	 * 返回数据
	 */
	private T data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	@SuppressWarnings("rawtypes")
	public static Apiresponse success() {
		return new Apiresponse(ApiResultEnum.SUCCESS);
	}
	
	@SuppressWarnings("rawtypes")
	public static Apiresponse success(String message) {
		return new Apiresponse(ApiResultEnum.SUCCESS.getCode(), message);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static<T> Apiresponse success(T data) {
		Apiresponse apiresponse = new Apiresponse(ApiResultEnum.SUCCESS);
		apiresponse.setData(data);
		return apiresponse;
	}
	
	Apiresponse(ApiResultEnum apiEnum) {
		this.code = apiEnum.getCode();
		this.message = apiEnum.getMessage();
	}
	
	Apiresponse(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	
	
}
