package com.chengqiang.record.entity;

import java.util.Date;

public class LoginLog {
	private static final long serialVersionUID = 1L;
	
	//用户Id
	private String userId;
	
	//用户名称
	private String username;
	
	//用户状态
	private String userStatus;
	
	//事件类型
	private String logType;
	
	//事件时间
	private String logTime;
	
	//设备id
	private String deviceId;
	
	//设备类型
	private String deviceType;
	
	//设备系统版本
	private String deviceVersion;
	
	//访问Ip
	private String loginIp;
	
	//备注
	private String remarks;
	
	//开始日期
	private Date beginDate;
	
	//结束日期
	private Date endDate;
	
	// 1、登录成功
	public static final String LOGTYPE_LOGIN_SUCCESS = "1";
	
	// 2、登录失败
	public static final String LOGTYPE_LOGIN_FAIL = "2";
		
	// 3、退出成功
	public static final String LOGTYPE_LOGOUT_SUCCESS = "1";
	
	// 4、退出失败
	public static final String LOGTYPE_LOGOUT_FAIL = "1";

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
