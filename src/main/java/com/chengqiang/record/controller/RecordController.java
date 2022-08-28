package com.chengqiang.record.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chengqiang.common.result.ApiResult;
import com.chengqiang.record.api.RecordServiceImpl;
import com.chengqiang.record.entity.RecordEntity;
import com.chengqiang.record.entity.UserInfo;
import com.chengqiang.record.util.SessionUtil;

@Controller
@RequestMapping(value="/record")
public class RecordController {
	
	@Resource
	private RecordServiceImpl recordServiceImpl;
	
	@RequestMapping(value="/saveRecord", method=RequestMethod.POST)
	@ResponseBody
	public ApiResult<?> saveRecord(@RequestBody RecordEntity recordEntity) {
		String result = recordServiceImpl.saveRecord(recordEntity);
		return ApiResult.success(result);
	}
	
	@RequestMapping(value="/recordList", method=RequestMethod.POST)
	@ResponseBody
	public ApiResult<?> recordList(@SuppressWarnings("rawtypes") @RequestBody Map map) {
		if(map.isEmpty()) {
			return ApiResult.error("");
		}
		UserInfo sessionUserInfo = (UserInfo) SessionUtil.getSession();
		String userId = sessionUserInfo.getId();
		List<RecordEntity> result = recordServiceImpl.recordList(userId, map.get("startDate").toString(), map.get("endDate").toString());
		return ApiResult.success(result);
	}
}
