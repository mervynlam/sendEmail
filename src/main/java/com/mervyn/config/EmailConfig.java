package com.mervyn.config;

import com.mervyn.enums.ConfEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

/**
 * @author: mervynlam
 * @Title: Config
 * @Description:
 * @date: 2021/8/11 11:13
 */
@Slf4j
@Data
public class EmailConfig {

    private final Properties conf;

    private Properties emailProps;

    public EmailConfig(String path) {
        this.conf = loadProperties(path);
    }

    public Set<Object> getKeys() {
        return this.conf.keySet();
    }

    private String getValue(String key) {
        if (conf.containsKey(key)) {
            String value = this.conf.getProperty(key);
            return value;
        }
        return null;
    }

    public String getProperty(String key) throws NoSuchElementException {
        String value = this.getValue(key.trim());
        if (value == null) {
            log.error("key : {} 不存在", key);
            throw new NoSuchElementException();
        }
        return value.trim();
    }

    private Properties loadProperties(String path) {
        log.info("加载配置文件：{}", path);
        Properties properties = new Properties();
        try(InputStream is = new FileInputStream(path)) {
            properties.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("找不到文件：{}", path);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("加载配置文件：{}", path);
        }
        return properties;
    }

    /**
     * @author: mervynlam
     * @Title: initEmailProps
     * @Description: 初始化邮件属性
     * @date: 2021/8/11 17:46
     */
    public void initEmailProps() {
        if (emailProps == null) {
            emailProps = new Properties();
        }
        emailProps.put("mail.smtp.host", conf.getProperty(ConfEnum.SERVER.getKey())); // SMTP主机名
        emailProps.put("mail.smtp.port", conf.getProperty(ConfEnum.PORT.getKey())); // 主机端口号
        emailProps.put("mail.smtp.auth", "true"); // 是否需要用户认证
        emailProps.put("mail.smtp.starttls.enable", "true"); // 启用TLS加密
        emailProps.put("mail.smtp.ssl.enable", conf.getProperty(ConfEnum.ENABLE_SSL.getKey())); // ssl
    }

    public Properties getEmailProps() {
        if (emailProps == null) {
            initEmailProps();
        }
        return emailProps;
    }

}
