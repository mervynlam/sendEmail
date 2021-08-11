package com.mervyn.utils;

import com.alibaba.fastjson.JSONObject;
import com.mervyn.config.EmailConfig;
import com.mervyn.consts.EmailConstants;
import com.mervyn.enums.ConfEnum;
import com.mervyn.objects.SessionObject;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.*;
import java.util.Properties;

/**
 * @author: mervynlam
 * @Title: SessionUtils
 * @Description:
 * @date: 2021/8/11 22:11
 */
@Slf4j
public class SessionUtils {
    
    public static void saveSession(Session session, EmailConfig conf) {
        log.info("保存session");
//        SessionObject sessionObject = new SessionObject(conf.getProperty(ConfEnum.FROM_EMAIL.getKey()), session);
//        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(EmailConstants.SESSION_FILE_NAME));) {
//            outputStream.writeObject(sessionObject);
//        } catch (FileNotFoundException e) {
//            log.error("{} 文件不存在", EmailConstants.SESSION_FILE_NAME);
//            e.printStackTrace();
//        } catch (IOException e) {
////            log.error("保存session失败");
//            e.printStackTrace();
//        }
        JSONObject json = new JSONObject();
        json.put(EmailConstants.SESSION_JSON_NAME_KEY, conf.getProperty(ConfEnum.FROM_EMAIL.getKey()));
        json.put(EmailConstants.SESSION_JSON_SESSION_KEY, session);

        try (FileWriter fileWriter = new FileWriter(new File(EmailConstants.SESSION_FILE_NAME))) {
            fileWriter.write(json.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            log.error("保存session失败");
            e.printStackTrace();
        }
    }
    
//    public static Session loadSession(EmailConfig conf) {
//        log.info("读取session");
//        try (ObjectInputStream inputStream = new ObjectInputStream (new FileInputStream(EmailConstants.SESSION_FILE_NAME))) {
//            SessionObject sessionObject = (SessionObject)inputStream.readObject();
//            String userName = sessionObject.getUserName();
//            if (userName.equals(conf.getProperty(ConfEnum.FROM_EMAIL.getKey()))) {
//                Session session = sessionObject.getSession();
//                return session;
//            }
//        } catch (FileNotFoundException e) {
//            log.error("{} 文件不存在", EmailConstants.SESSION_FILE_NAME);
//            e.printStackTrace();
//        } catch (IOException e) {
//            log.error("读取session失败");
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            log.error("读取sessionObject对象失败");
//            e.printStackTrace();
//        }
////        try (FileInputStream inputStream = new FileInputStream(new File(EmailConstants.SESSION_FILE_NAME))) {
////            byte[] bytes = new byte[inputStream.available()];
////            inputStream.read(bytes);
////            String jsonString = new String(bytes);
////            JSONObject json = JSONObject.parseObject(jsonString);
////            String userName = json.getString(EmailConstants.SESSION_JSON_NAME_KEY);
////            if (conf.getProperty(ConfEnum.FROM_EMAIL.getKey()).equals(userName)) {
////                Session session = (Session) json.get(EmailConstants.SESSION_JSON_SESSION_KEY);
////                return session;
////            }
////        } catch (FileNotFoundException e) {
////            log.info("session.json 文件不存在");
////            e.printStackTrace();
////        } catch (IOException e) {
////            log.info("读取session失败");
////            e.printStackTrace();
////        }
//        return null;
//    }

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
        saveSession(session, conf);
        return session;
    }
}
