package com.mervyn.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.mail.Session;
import java.io.Serializable;

/**
 * @author: mervynlam
 * @Title: SessionObject
 * @Description:
 * @date: 2021/8/11 22:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionObject implements Serializable {
    private String userName;
    private Session session;
}
