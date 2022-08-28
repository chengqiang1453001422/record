package com.chengqiang.record.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chengqiang.record.api.RecordServiceImpl;
import com.chengqiang.record.entity.RecordEntity;
import com.chengqiang.record.mapper.RecordMapper;

@Service
public class RecordService implements RecordServiceImpl {
	@Resource
	RecordMapper recordMapper;
	
	public String saveRecord(RecordEntity recordEntity) {
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		Date dNow = new Date( );
		String time = ft.format(dNow);
		Date dateTime = null;
		try {
			dateTime = ft.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recordEntity.setCreateTime(dateTime);
		recordEntity.setCreateBy("system");
		recordEntity.setDelFlag(0);
		int result = recordMapper.insertRecord(recordEntity);
		String str = "";
		if(result == 1) {
			str = "成功";
		}else {
			str = "失败";
		}
		return str;
	}
	
	public List<RecordEntity> recordList(String userId, String startDate, String endDate) {
		List<RecordEntity> result;
		result = recordMapper.recordByUserId(userId, startDate, endDate);
		return result;
	}
}
