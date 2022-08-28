package com.chengqiang.record.mapper;

import java.util.List;

public interface BaseMapper<T> {
	/**
	 * 插入一条数据
	 * @param entity
	 * @return
	 */
	int insert(T entity);
	
	/**
	 * 查询列表
	 * @return
	 */
	List<T> selectAll();
}
