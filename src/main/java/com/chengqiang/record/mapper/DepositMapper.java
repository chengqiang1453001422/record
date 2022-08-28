package com.chengqiang.record.mapper;

import java.util.List;

import com.chengqiang.record.entity.DepositEntity;

public interface DepositMapper extends BaseMapper<DepositEntity> {

	List<DepositEntity> list(String userId);

	DepositEntity getDetailById(String depositId);

	int saveDeposit(DepositEntity depositEntity);

}
