package com.mervyn.utils;

import com.mervyn.config.EmailConfig;
import com.mervyn.consts.EmailConstants;
import com.mervyn.enums.ConfEnum;
import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
        EmailConfig conf = new EmailConfig(EmailConstants.CONFIG_FILE_NAME);
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
     * @Title: initMessage
     * @Description: 初始化邮件对象
     * @date: 2021/8/11 17:47
     */
    public static MimeMessage initMessage(Session session, String fromEmail, String toEmail) throws MessagingException {
        log.info("初始化邮件对象");
        MimeMessage message = new MimeMessage(session);
//        设置发送方地址:
        message.setFrom(new InternetAddress(fromEmail));
//        设置接收方地址:
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        return message;
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
//        session.setDebug(true);
        return session;
    }

    /**
     * @author: mervynlam
     * @Title: addAttachment
     * @Description: 添加附件
     * @date: 2021/8/11 17:48
     */
    public static MimeMessage addAttachment(MimeMessage message, File file, boolean autoConvert) throws MessagingException {
        //根据文件类型自动配置Convert标题
        if (autoConvert && FileUtils.isConvert(file)) {
            message.setSubject("Convert", EmailConstants.UTF8_CHARSET);
        } else {
            message.setSubject(file.getName(), EmailConstants.UTF8_CHARSET);
        }
        MimeMultipart multipart = new MimeMultipart();

        BodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText("发送附件："+file.getName()+"，请查收");

        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setFileName(file.getName());
        bodyPart.setDataHandler(new DataHandler(new FileDataSource(file)));
        multipart.addBodyPart(textBodyPart);
        multipart.addBodyPart(bodyPart);
        multipart.setSubType("mixed");
        message.setContent(multipart);
        return message;
    }

}
