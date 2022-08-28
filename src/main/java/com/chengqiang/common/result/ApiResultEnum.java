package com.chengqiang.common.result;

public enum ApiResultEnum {
	SUCCESS(2000, "操作成功"),
	ERROR(3000, "系统异常");
	
	/**
	 * code码
	 */
	private int code;
	
	/**
	 * 返回消息
	 */
	private String message;
	
	ApiResultEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

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
}
