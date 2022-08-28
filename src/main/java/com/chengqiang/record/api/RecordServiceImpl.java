package com.chengqiang.record.api;

import java.util.List;

import com.chengqiang.record.entity.RecordEntity;

public interface RecordServiceImpl {
	String saveRecord(RecordEntity recordEntity);
	
	List<RecordEntity> recordList(String userId, String startDate, String endDate);
}
