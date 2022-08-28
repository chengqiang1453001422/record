package com.chengqiang.record.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.chengqiang.record.api.UserServiceImpl;
import com.chengqiang.record.entity.UserInfo;
import com.chengqiang.record.mapper.RecordMapper;
import com.chengqiang.record.mapper.UserMapper;
import com.chengqiang.record.util.JjwtUtil;
import com.chengqiang.record.util.SessionUtil;

import io.jsonwebtoken.Claims;

import sun.misc.BASE64Encoder;

@Service
public class UserService implements UserServiceImpl {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Resource
	UserMapper userMapper;
	
	@Resource
	RecordMapper recordMapper;
	
	@Value( value = "${record.path.uploadFolder}")
    private String uploadFolder;

	public UserInfo getUserById(String userId) {
		UserInfo userInfo = userMapper.getUserById(userId);
		return userInfo;
	}
	
	public UserInfo getUserByUsername(String userName) {
		UserInfo userInfo = userMapper.getUserByUsername(userName);
		return userInfo;
	}
	
	public UserInfo getUserBeanByToken(String token) {
		Claims c = null;
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        if (StringUtils.isEmpty(token)) {
            return null;
        }
//        if (!token.startsWith("Bearer ")) {
//            throw new NotLoginException("token格式错误");
//        }
        token = token.replace("Bearer ", "");
        try {
            c = JjwtUtil.parseJWT(token);
        } catch (Exception e) {
            //log.error("解码异常:" + e);
            return null;
        }
        if (c == null) {
            //log.info("解码为空");
            return null;
        }
        String subject = c.getSubject();
        //log.debug("解析结果：" + subject);
        UserInfo tokenUserInfo = JSON.parseObject(subject, UserInfo.class);
		UserInfo saveUserInfo = new UserInfo();
		String tokenPassword = "";
        String savePassword = "";
        if (StringUtils.isNotEmpty(tokenUserInfo.getPassword())) {
        	saveUserInfo = getUserById(tokenUserInfo.getId());
            if (saveUserInfo == null) {
                return null;
            }
            tokenPassword = tokenUserInfo.getPassword();
            savePassword = tokenUserInfo.getPassword();
        }
        
        if (StringUtils.isEmpty(tokenPassword) || StringUtils.isEmpty(savePassword)) {
            return null;
        }
        if (tokenPassword.equals(savePassword)) {

            return saveUserInfo;
        } else {
            return null;
        }
	}
	
	public String registerUser(UserInfo userInfo) {
		String username = userInfo.getUsername();
		if(StringUtils.isNotBlank(username)) {
			UserInfo userInfoOld = userMapper.getUserByUsername(username);
			if(userInfoOld != null) {
				return "用户名已注册";
			}
		}
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
		userInfo.setCreateTime(dateTime);
		userInfo.setCreateBy("system");
		userInfo.setSex(1);
		userInfo.setDelFlag(0);
		int result = userMapper.insertUser(userInfo);
		String message = "";
		if(result==1) {
			message = "注册成功";
		}else {
			message = "注册失败";
		}
		return message;
	}
	
	public Map<String, Object >getRecordInfoByUserId(String userId){
		Map<String, Object> result = new HashMap<String, Object>();
		String recorddate = recordMapper.recordDateByUserId(userId);
		String recordNumber = recordMapper.recordNumberByUserId(userId);
		result.put("recorddate", recorddate);
		result.put("recordNumber", recordNumber);
		return result;
	}
	
	public Map<String, Object >uploadPhoto(MultipartFile file, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		if(file.isEmpty()) {
			String kk = "999";
		}
		String format = sdf.format(new Date());
		String fileName = file.getOriginalFilename();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		fileName = uuid + "_" + fileName;
		//String path = request.getSession().getServletContext().getRealPath("upload");
		String descfilePath = uploadFolder + File.separator + "uploadService" + File.separator + format;
//		try {
//			File folder = new File(descfilePath);
//			if(!folder.exists()) {
//				folder.mkdirs();
//			}
//			byte[] kkk = file.getBytes();
//			InputStream inputStream = file.getInputStream();
//			log.info("getBytes", kkk);
//			FileOutputStream fileOutputStream = new FileOutputStream(descfilePath + "/" + fileName);
//			fileOutputStream.write(kkk);
////			//创建一个缓冲区
////			byte[] buffer = new byte[1024 * 1024];
////			//判断读取是否完毕
////			int len = 0;
////			while ((len = inputStream.read(buffer)) > 0) {
////				fileOutputStream.write(buffer, 0, len);
////			}
//			//关闭流
//			fileOutputStream.close();
//			inputStream.close();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		//如果目录不存在则创建
		File folder = new File(descfilePath);
		if(!folder.isDirectory()) {
			if(!folder.mkdirs()) {
				result.put("error", "文件夹创建失败");
				return result;
			}
		}
		File targetFile = new File(descfilePath, fileName);
//		if(!targetFile.exists()) {
//			targetFile.mkdirs();
//		}
		InputStream in = null;
		try {
			file.transferTo(targetFile);
//			descFile = new File(descfilePath + File.separator + format + File.separator + fileName);
//			if(!descFile.getParentFile().exists()) {
//				descFile.getParentFile().mkdirs();
//				if(!descFile.isFile()) {
//					descFile.createNewFile();
//				}
//			}
			//转base64
			byte[] data = null;
			//读取图片的字节数据
//			String filename1 = targetFile.getAbsolutePath();
//			in = new FileInputStream(filename1);
//			data = new byte[in.available()];
//			in.read(data);
//			in.close();
			//对字节数据Base64编码
			//BASE64Encoder encoder = new BASE64Encoder();
			//String fileUrl = "data:image/jpeg;base64," + encoder.encode(data);
			String fileUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/files/" + "uploadService" + "/" + format + "/" + fileName; 
			UserInfo sessionUserInfo = (UserInfo) SessionUtil.getSession();
			String userId = sessionUserInfo.getId();
			int res = userMapper.updatePhotoByUserId(userId, fileUrl);
			result.put("fileUrl", fileUrl);
			result.put("fileName", fileName);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public String updatePasswordByEmailCode(String userName, String password, String email) {
		int result = userMapper.updatePasswordByEmailCode(userName, password, email);
		String message = "";
		if(result==1) {
			message = "注册成功";
		}else {
			message = "注册失败";
		}
		return message;
	}
}
