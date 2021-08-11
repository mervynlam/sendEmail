package com.mervyn;

import com.mervyn.config.EmailConfig;
import com.mervyn.enums.ConfEnum;
import com.mervyn.utils.AttachmentUtils;
import com.mervyn.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * @author: mervynlam
 * @Title: SendEmail
 * @Description:
 * @date: 2021/8/11 11:04
 */
@Slf4j
public class SendEmail {

    private static EmailConfig conf;
    private static Properties emailProps;

    public static void main(String[] args) {
        initEmailProps();
        checkAttachment();
        MimeMessage mimeMessage;
        try {
            mimeMessage = initMessage();
        } catch (MessagingException e) {
            log.error("初始化邮件信息失败");
            e.printStackTrace();
            return;
        }
        File[] files = getAttachment();
        sendAttachment(mimeMessage, files);
    }

    /**
     * @author: mervynlam
     * @Title: initEmailProps
     * @Description: 初始化配置
     * @date: 2021/8/11 17:49
     */
    private static void initEmailProps() {
        conf = EmailUtils.initProps();
        emailProps = conf.getEmailProps();
    }

    /**
     * @author: mervynlam
     * @Title: checkAttachment
     * @Description: 检查附件
     * @date: 2021/8/11 17:48
     */
    private static void checkAttachment() {
        String attachmentPath = conf.getProperty(ConfEnum.ATTACHMENT_DIR.getKey());
        String extStr = conf.getProperty(ConfEnum.ATTACHMENT_EXTENSION.getKey());
        AttachmentUtils.checkAttachment(attachmentPath, extStr);
    }

    /**
     * @author: mervynlam
     * @Title: getAttachment
     * @Description: 获取附件
     * @date: 2021/8/11 17:48
     */
    private static File[] getAttachment() {
        String attachmentPath = conf.getProperty(ConfEnum.ATTACHMENT_DIR.getKey());
        String extStr = conf.getProperty(ConfEnum.ATTACHMENT_EXTENSION.getKey());
        return AttachmentUtils.getAttachment(attachmentPath, extStr);
    }

    /**
     * @author: mervynlam
     * @Title: initMessage
     * @Description: 初始化邮件对象
     * @date: 2021/8/11 17:48
     */
    private static MimeMessage initMessage() throws MessagingException {
        Session session = EmailUtils.getSession(conf, emailProps);
        String fromEmail = conf.getProperty(ConfEnum.FROM_EMAIL.getKey());
        String toEmail = conf.getProperty(ConfEnum.TO_EMAIL.getKey());
        String title = conf.getProperty(ConfEnum.TITLE.getKey());
        return EmailUtils.initMessage(session, fromEmail, toEmail, title);
    }

    /**
     * @author: mervynlam
     * @Title: sendAttachment
     * @Description: 发送附件
     * @date: 2021/8/11 17:48
     */
    private static void sendAttachment(MimeMessage message, File[] files) {
        for (File file : files) {
            message = EmailUtils.addAttachment(message, file);
            try {
                Transport.send(message);
            } catch (MessagingException e) {
                log.error("发送文件 {} 失败",file.getName());
                e.printStackTrace();
            }
        }
    }

}