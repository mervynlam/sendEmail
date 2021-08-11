package com.mervyn.utils;

import com.mervyn.config.EmailConfig;
import com.mervyn.consts.EmailConstants;
import com.mervyn.enums.ConfEnum;
import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
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
    public static MimeMessage initMessage(Session session, String fromEmail, String toEmail, String title) throws MessagingException {
        log.info("初始化邮件对象");
        MimeMessage message = new MimeMessage(session);
//        设置发送方地址:
        message.setFrom(new InternetAddress(fromEmail));
//        设置接收方地址:
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//        设置邮件主题:
        message.setSubject((title), EmailConstants.UTF8_CHARSET);
//        设置邮件正文:
//        message.setText("Hi Xiaoming...??", EmailConstants.UTF8_CHARSET);
//        Transport.send(message);
        return message;
    }

    /**
     * @author: mervynlam
     * @Title: addAttachment
     * @Description: 添加附件
     * @date: 2021/8/11 17:48
     */
    public static MimeMessage addAttachment(MimeMessage message, File file) throws MessagingException, UnsupportedEncodingException {
        Multipart multipart = new MimeMultipart();
        BodyPart bodyPart = new MimeBodyPart();
//        log.info(MimeUtility.encodeText(file.getName())+"===========");
        bodyPart.setFileName(file.getName());
        bodyPart.setDataHandler(new DataHandler(new FileDataSource(file)));
        multipart.addBodyPart(bodyPart);
        message.setContent(multipart);
        return message;
    }

}
