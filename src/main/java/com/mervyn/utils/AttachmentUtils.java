package com.mervyn.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

/**
 * @author: mervynlam
 * @Title: AttachmentUtils
 * @Description:
 * @date: 2021/8/11 16:57
 */
@Slf4j
public class AttachmentUtils {

    /**
     * @author: mervynlam
     * @Title: checkAttachment
     * @Description: 检查附件是否存在
     * @date: 2021/8/11 17:46
     */
    public static boolean checkAttachment(String attachmentPath, String extStr) {
        File dir = new File(attachmentPath);
        if (!dir.exists()) {
            log.info("没有找到附件目录：{}", attachmentPath);
            return false;
        }
        File[] files = getAttachment(attachmentPath, extStr);
        if (files.length == 0) {
            log.info("附件目录中没有符合条件（{}）的附件", extStr);
            return false;
        }
        return true;
    }

    /**
     * @author: mervynlam
     * @Title: getAttachment
     * @Description: 获取符合条件的附件
     * @date: 2021/8/11 17:47
     */
    public static File[] getAttachment(String attachmentPath, String attachmentExt) {
        File attachmentDir = new File(attachmentPath);
        return attachmentDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (StringUtils.isBlank(attachmentExt)) {
                    return true;
                }
                List<String> exts = Arrays.asList(attachmentExt.split(","));
                String fileExt = FileUtils.getExt(name);
                long count = exts.stream().map(ext -> ext.contains(fileExt)).count();
                return count > 0;
            }
        });
    }
}
