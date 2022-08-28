package com.chengqiang.record.mapper;

import java.util.List;

import com.chengqiang.record.entity.RecordEntity;

public interface RecordMapper extends BaseMapper<RecordEntity> {
	int insertRecord(RecordEntity reecordEntity);
	
	List<RecordEntity> recordByUserId(String userId, String startDate, String endDate);
	
	String recordDateByUserId(String userId);
	
	String recordNumberByUserId(String userId);
	
	List<RecordEntity> recordByUserIdAndYear(String userId, String currentdate);
	
	List<RecordEntity> recordByUserIdAndMounth(String userId, String currentdate);
}
