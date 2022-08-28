package com.chengqiang.record.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chengqiang.record.api.DepositServiceImpl;
import com.chengqiang.record.entity.DepositEntity;
import com.chengqiang.record.entity.UserInfo;
import com.chengqiang.record.mapper.DepositMapper;
import com.chengqiang.record.util.SessionUtil;

@Service
public class DepositService implements DepositServiceImpl {
	@Resource
	DepositMapper depositMapper;
	
	public List<DepositEntity> list() {
		UserInfo sessionUserInfo = (UserInfo) SessionUtil.getSession();
		String userId = sessionUserInfo.getId();
		List<DepositEntity> result;
		result = depositMapper.list(userId);
		return result;
	}
	
	public DepositEntity getDetailById(String depositId) {
		DepositEntity result = depositMapper.getDetailById(depositId);
		return result;
	}
	
	public String saveDeposit(DepositEntity depositEntity) {
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
		UserInfo sessionUserInfo = (UserInfo) SessionUtil.getSession();
		String userId = sessionUserInfo.getId();
		depositEntity.setUserId(userId);
		depositEntity.setCreateTime(dateTime);
		depositEntity.setCreateBy("system");
		depositEntity.setDelFlag(0);
		int result = depositMapper.saveDeposit(depositEntity);
		String str = "";
		if(result == 1) {
			str = "成功";
		}else {
			str = "失败";
		}
		return str;
	}
}
