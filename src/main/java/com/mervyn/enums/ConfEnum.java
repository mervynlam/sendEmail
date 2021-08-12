package com.mervyn.enums;

/**
 * @author: mervynlam
 * @Title: ConfEnum
 * @Description:
 * @date: 2021/8/11 14:25
 */
public enum ConfEnum {
    TO_EMAIL("to_email"),
    PORT("port"),
    SERVER("server"),
    FROM_EMAIL("from_email"),
    AUTH_CODE("auth_code"),
    ENABLE_SSL("enable_ssl"),
    ATTACHMENT_DIR("attachment_dir"),
    ATTACHMENT_EXTENSION("attachment_extension"),
    AUTO_CONVERT("auto_convert"),
    ;

    ConfEnum(String key) {
        this.key = key;
    }

    private String key;

    public String getKey() {
        return key;
    }
}
