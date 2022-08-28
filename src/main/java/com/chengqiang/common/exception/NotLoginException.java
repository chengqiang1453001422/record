package com.chengqiang.common.exception;

public class NotLoginException extends RuntimeException {
	/**
	 * 异常处理
	 */
	private static final long serialVersionUID = 1L;

	public NotLoginException() {
        super("未登录");
    }
    public NotLoginException(Throwable cause) {
        super("未登录", cause);
    }

    public NotLoginException(String message) {
        super(message);
    }

    public NotLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
