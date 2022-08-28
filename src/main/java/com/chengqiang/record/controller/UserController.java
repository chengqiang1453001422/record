package com.chengqiang.record.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.chengqiang.common.result.ApiResult;
import com.chengqiang.record.api.UserServiceImpl;
import com.chengqiang.record.entity.UserInfo;
import com.chengqiang.record.util.JjwtUtil;
import com.chengqiang.record.util.SendEmailUtil;
import com.chengqiang.record.util.SessionUtil;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

@Slf4j
@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Resource
	private UserServiceImpl userService;
	
	@Value( value = "${record.path.uploadFolder}")
    private String uploadFolder;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	@ResponseBody
	public ApiResult<?> login(@Param("username") String username, @Param("password") String password, HttpServletRequest request) {
		UserInfo userInfo = userService.getUserByUsername(username);
		if(userInfo == null) {
			log.info("用户不存在：{}", userInfo);
			return ApiResult.error("用户不存在");
		}
		String jwt = "";
        try {
        	UserInfo sessionUserInfo = new UserInfo();
        	sessionUserInfo.setPassword(userInfo.getPassword());
        	sessionUserInfo.setId(userInfo.getId());
            jwt = JjwtUtil.createJWT("qiwenshare", "qiwen", JSON.toJSONString(sessionUserInfo));
        } catch (Exception e) {
            //log.info("登录失败：{}", e);
        	log.info("创建token失败：{}", e);
			return ApiResult.error("创建token失败");
        }
		String token = "";
		try {
			UserInfo sessionUserInfo = new UserInfo();
			sessionUserInfo.setPhone(userInfo.getPhone());
			sessionUserInfo.setPassword(userInfo.getPassword());
			//JSON.toJSONString(sessionUserInfo)
			token = UUID.randomUUID().toString().replace("-", "");
		} catch(Exception e) {
			e.printStackTrace();
			return ApiResult.error("token生成失败");
		}
		if(!password.equals(userInfo.getPassword())) {
			return ApiResult.error("密码输入错误");
		}
		HttpSession session = request.getSession();
		String tokenKey = "token" + userInfo.getId();
		session.setAttribute(tokenKey, token);
		session.setAttribute("user", userInfo);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("token", "Bearer " + jwt);
		result.put("userInfo", userInfo);
		return ApiResult.success(result);
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	@ResponseBody
	public ApiResult<?> register(@RequestBody UserInfo userInfo) {
		String result = userService.registerUser(userInfo);
		return ApiResult.success(result);
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	@ResponseBody
	public ApiResult<?> detail(@Param("userId") String userId) {
		UserInfo result = userService.getUserById(userId);
		return ApiResult.success(result);
	}
	
	@RequestMapping(value="/recordInfo", method=RequestMethod.GET)
	@ResponseBody
	public ApiResult<?> recordInfo(@Param("userId") String userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		result = userService.getRecordInfoByUserId(userId);
		return ApiResult.success(result);
	}
	
	@RequestMapping(value="/uploadPhoto", method=RequestMethod.POST)
	@ResponseBody
	public ApiResult<?> uploadPhoto(@RequestParam MultipartFile file, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result = userService.uploadPhoto(file, request);
		return ApiResult.success(result);
	}
	
	@RequestMapping(value="/getImage", method=RequestMethod.GET)
	@ResponseBody
	public void getImage(HttpServletResponse response) throws Exception {
		//读取文件
		String descfilePath = uploadFolder + File.separator + "uploadService" + File.separator + "2022-05-14";
		File file = new File(descfilePath + File.separator + "54e0fd756f14471791d3382d2eeb59b3_1652510901216.png");
		InputStream input = null;
		input = new FileInputStream(file);
		//放入字节数组中
		byte[] data = null;
		data = new byte[input.available()];
		input.read(data);
		input.close();
		response.reset();
		response.setContentType("image/png");
		response.setCharacterEncoding("utf-8");
		//方式一
		response.getOutputStream().write(data);
		//方式二
//		ServletOutputStream out = response.getOutputStream();
//		out.write(data);
//		out.flush();
//		out.close();
	}
	
	@RequestMapping(value="/getBaseImage", method=RequestMethod.GET)
	@ResponseBody
	public String getBaseImage(HttpServletResponse response) throws Exception {
		//读取文件
		String descfilePath = uploadFolder + File.separator + "uploadService" + File.separator + "2022-05-14";
		File file = new File(descfilePath + File.separator + "54e0fd756f14471791d3382d2eeb59b3_1652510901216.png");
		InputStream input = null;
		input = new FileInputStream(file);
		//放入字节数组中
		byte[] data = null;
		data = new byte[input.available()];
		input.read(data);
		input.close();
		response.reset();
		BASE64Encoder encoder = new BASE64Encoder();
		String fileUrl = "data:image/jpeg;base64," + encoder.encode(data);
		response.setContentType("image/png");
		response.setCharacterEncoding("utf-8");
		return fileUrl;
	}
	
	@RequestMapping(value="/downloadFileStream", method=RequestMethod.GET)
	@ResponseBody
	public void downloadFileStream(HttpServletResponse response,  String fileUrl) {
	  try {
          if(StringUtils.isNotBlank(fileUrl)) {
              File file = new File(fileUrl);
              String filename = file.getName();
              // 以流的形式下载文件。
              InputStream fis = new BufferedInputStream(new FileInputStream(fileUrl));
              byte[] buffer = new byte[fis.available()];
              fis.read(buffer);
              fis.close();
              // 清空response
              response.reset();
              response.setContentType("application/octet-stream;charset=UTF-8");
              String fileName = new String(filename.getBytes("gb2312"), "iso8859-1");
              response.setHeader("Content-disposition", "attachment;filename=" + fileName);
              OutputStream ouputStream = response.getOutputStream();
              ouputStream.write(buffer);
              ouputStream.flush();
              ouputStream.close();
          	}
       } catch (Exception e) {
           e.printStackTrace();
           log.error("文件下载出现异常", e);
       }
	}
	@RequestMapping(value="/sendEmailCode", method=RequestMethod.GET)
	@ResponseBody
	public ApiResult<?> sendEmailCode(HttpServletResponse response, String username, String email) {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = userService.getUserByUsername(username);
		if(userInfo == null) {
			log.info("用户不存在：{}", userInfo);
			return ApiResult.error("用户不存在");
		}
		try {
			String emailCode = "123456";
			result = SendEmailUtil.send(email, emailCode);
			SessionUtil.setLocalInfo(email, emailCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("isSuccess", false);
		}
		return ApiResult.success(result);
	}
	@RequestMapping(value="/forgetPassword", method=RequestMethod.POST)
	@ResponseBody
	public ApiResult<?> forgetPassword(@SuppressWarnings("rawtypes") @RequestBody Map map) {
		Map<String, Object> result = new HashMap<String, Object>();
		String emailCode = (String) map.get("emailCode");
		String email = (String) map.get("email");
		String username = (String) map.get("username");
		String password = (String) map.get("password");
		String userConfirmPassword = (String) map.get("userConfirmPassword");
		
		UserInfo userInfo = userService.getUserByUsername(username);
		if(userInfo == null) {
			log.info("用户不存在：{}", userInfo);
			return ApiResult.error("用户不存在");
		}
		String sessionEmailCode = (String) SessionUtil.getLocalInfo(email);
		if(password.equals(userConfirmPassword)) {
			return ApiResult.error("两次密码不一致");
		}
		if(emailCode.equals(sessionEmailCode)) {
			return ApiResult.error("验证码不正确");
		}
		
		String message = userService.updatePasswordByEmailCode(username, password, email);
		
		return ApiResult.success(message);
	}
}
