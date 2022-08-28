package com.chengqiang.record.api;

import java.util.List;

import com.chengqiang.record.entity.DepositEntity;

public interface DepositServiceImpl {

	List<DepositEntity> list();

	DepositEntity getDetailById(String depositId);

	String saveDeposit(DepositEntity depositEntity);

}
