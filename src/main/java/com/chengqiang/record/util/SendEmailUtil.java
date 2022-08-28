package com.chengqiang.record.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailUtil {
	public static String myEmailAccount = "cq18325420661@163.com";
    public static String myEmailPassword = "WNFHRRBWODFLTIXN";
    
    public static String myEmailSMTPHost = "smtp.163.com";
    
    //public static String receiveMailAccount = "1453001422@qq.com";
    
    public static Map<String, Object> send(String receiveMailAccount, String emailCode) throws Exception {
    	Map<String, Object> result = new HashMap<String, Object>();
    	 // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        
        // 开启 SSL 连接, 以及更详细的发送步骤请看上一篇: 基于 JavaMail 的 Java 邮件发送：简单邮件发送

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        //Session session = Session.getInstance(props);
        //session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log
        
        Session session = Session.getDefaultInstance(props);

        // 3. 创建一封邮件
        MimeMessage message = createTextMessage(session, myEmailAccount, receiveMailAccount, emailCode);

        // 也可以保持到本地查看
        // message.writeTo(file_out_put_stream);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器
        //    这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
        transport.connect(myEmailAccount, myEmailPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        
        System.out.println("Sent message successfully....");
        result.put("isSuccess", true);
        // 7. 关闭连接
        transport.close();
    	return result;
    }
    
    public static MimeMessage createTextMessage(Session session, String sendMail, String receiveMail, String emailCode) throws Exception {
        // 1. 创建邮件对象
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "我的测试邮件_发件人昵称", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.addRecipient(RecipientType.TO, new InternetAddress(receiveMail, "我的测试邮件_收件人昵称", "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject("TEST邮件主题（文本+图片+附件）", "UTF-8");

        // 5. 设置消息体
        message.setText("亲爱的宝贝，你的验证码是 " + emailCode);
        
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存上面的所有设置
        message.saveChanges();

        return message;
    }
}
