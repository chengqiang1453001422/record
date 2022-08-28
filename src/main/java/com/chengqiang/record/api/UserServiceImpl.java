package com.chengqiang.record.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.chengqiang.record.entity.UserInfo;

public interface UserServiceImpl {
	public UserInfo getUserById(String userId);

	public UserInfo getUserByUsername(String userName);
	
	public UserInfo getUserBeanByToken(String token);
	
	public String registerUser(UserInfo userInfo);
	
	public Map<String, Object >getRecordInfoByUserId(String userId);
	
	public Map<String, Object >uploadPhoto(MultipartFile file, HttpServletRequest request);
	
	public String updatePasswordByEmailCode(String userName, String password, String email);
}
