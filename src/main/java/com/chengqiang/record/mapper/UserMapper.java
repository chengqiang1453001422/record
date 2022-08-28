package com.chengqiang.record.mapper;

import com.chengqiang.record.entity.UserInfo;

public interface UserMapper extends BaseMapper<UserInfo>{
	public UserInfo getUserById(String userId);
	
	public UserInfo getUserByUsername(String userName);
	
	public UserInfo getUserBeanByToken(String token);
	
	public int insertUser(UserInfo userInfo);
	
	public int updatePhotoByUserId(String userId, String avatar);
	
	public int updatePasswordByEmailCode(String userName, String password, String email);
}
