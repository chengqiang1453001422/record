package com.chengqiang.record.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chengqiang.common.result.ApiResult;
import com.chengqiang.record.api.DepositServiceImpl;
import com.chengqiang.record.entity.DepositEntity;


@Controller
@RequestMapping(value="/deposit")
public class DepositController {
	@Resource
	private DepositServiceImpl depositServiceImpl;
	/**
	 * 存钱列表
	 * @return
	 */
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
	public ApiResult<?> list() {
		List<DepositEntity> list = depositServiceImpl.list();
		return ApiResult.success(list);
	}
	
	/**
	 * 存钱详情
	 * @param depositId
	 * @return
	 */
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	@ResponseBody
	public ApiResult<?> detail(@Param("depositId") String depositId) {
		DepositEntity result = depositServiceImpl.getDetailById(depositId);
		return ApiResult.success(result);
	}
	
	/**
	 * 新增存钱记录
	 * @return
	 */
	@RequestMapping(value="/saveDeposit", method=RequestMethod.POST)
	@ResponseBody
	public ApiResult<?> save(@RequestBody DepositEntity depositEntity) {
		String result = depositServiceImpl.saveDeposit(depositEntity);
		return ApiResult.success(result);
	}
}
