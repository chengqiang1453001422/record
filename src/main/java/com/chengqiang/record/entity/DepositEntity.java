package com.chengqiang.record.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DepositEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	//存钱数量
	private Integer depositCount;
	
	//存钱方式
	private String depositStyle;
	
	//存钱日期
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date depositTime;
	
	//备注
	private String remarks;
	
	private String userId;

	public Integer getDepositCount() {
		return depositCount;
	}

	public void setDepositCount(Integer depositCount) {
		this.depositCount = depositCount;
	}

	public String getDepositStyle() {
		return depositStyle;
	}

	public void setDepositStyle(String depositStyle) {
		this.depositStyle = depositStyle;
	}

	public Date getDepositTime() {
		return depositTime;
	}

	public void setDepositTime(Date depositTime) {
		this.depositTime = depositTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
