package com.mervyn.utils;

import com.mervyn.config.EmailConfig;
import com.mervyn.enums.ConfEnum;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * @author: mervynlam
 * @Title: EmailUtils
 * @Description:
 * @date: 2021/8/11 16:58
 */
@Slf4j
public class EmailUtils {

    /**
     * @author: mervynlam
     * @Title: initProps
     * @Description: 初始化配置
     * @date: 2021/8/11 17:47
     */
    public static EmailConfig initProps() {
        //读配置
        EmailConfig conf = new EmailConfig("config");
        log.info("读取到配置：");
        for (Object oKey : conf.getKeys()) {
            String key = (String) oKey;
            String value = conf.getProperty(key);
            log.info("{} : {}", key, key.contains(ConfEnum.AUTH_CODE.getKey())?value.replaceAll(".", "*"):value);
        }
        return conf;
    }

    /**
     * @author: mervynlam
     * @Title: getSession
     * @Description: 获取session
     * @date: 2021/8/11 17:47
     */
    public static Session getSession(EmailConfig conf, Properties emailProps) {
        log.info("获取session实例");
        // 获取Session实例:
        Session session = Session.getInstance(emailProps, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(conf.getProperty(ConfEnum.FROM_EMAIL.getKey()),
                        conf.getProperty(ConfEnum.AUTH_CODE.getKey()));
            }
        });
        // 设置debug模式便于调试:
        session.setDebug(true);
        return session;
    }

    /**
     * @author: mervynlam
     * @Title: initMessage
     * @Description: 初始化邮件对象
     * @date: 2021/8/11 17:47
     */
    public static MimeMessage initMessage(Session session, String fromEmail, String toEmail, String title) throws MessagingException {

        MimeMessage message = new MimeMessage(session);
//        设置发送方地址:
        message.setFrom(new InternetAddress(fromEmail));
//        设置接收方地址:
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//        设置邮件主题:
        message.setSubject((title), "UTF-8");
////        设置邮件正文:
//        message.setText("Hi Xiaoming...??", "UTF-8");
        return message;
//        发送:
//        Transport.send(message);
    }

    /**
     * @author: mervynlam
     * @Title: addAttachment
     * @Description: 添加附件
     * @date: 2021/8/11 17:48
     */
    public static MimeMessage addAttachment(MimeMessage message, File file) {
        return message;
    }

}
