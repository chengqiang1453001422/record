package com.chengqiang.record.controller;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	protected static final String SPLIT_STR = ",";
	protected static final String UNKNOWN = "unknown";
	
	protected String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("X-forwarded-for");
		if(ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if(ipAddress.equals("0:0:0:0:0:0:0:1")) {
				ipAddress = "127.0.0.1";
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
		if(ipAddress.length() > 15 && ipAddress.indexOf(SPLIT_STR) > -1 && ipAddress.indexOf(SPLIT_STR) !=0) {
			ipAddress = ipAddress.substring(0, ipAddress.indexOf(SPLIT_STR));
		}
		return ipAddress;
	}
}
