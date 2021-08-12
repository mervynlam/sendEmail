package com.mervyn.utils;

import java.io.File;

/**
 * @author: mervynlam
 * @Title: FileUtils
 * @Description:
 * @date: 2021/8/11 16:49
 */
public class FileUtils {
    /**
     * @author: mervynlam
     * @Title: getExt
     * @Description: 获取后缀名
     * @date: 2021/8/11 17:49
     */
    public static String getExt(String filename) {
        int index = filename.lastIndexOf(".");
        if (index < 0) {
            return "";
        }
        return filename.substring(index+1);
    }

    public static String getExt(File file) {
        return getExt(file.getName());
    }

    public static boolean isExist(File file) {
        return file.exists();
    }

    public static boolean isExist(String filename) {
        return isExist(new File(filename));
    }

    public static void mkdirs(File file) {
        file.mkdirs();
    }

    public static void mkdirs(String filename) {
        mkdirs(new File(filename));
    }

    /**
     * @author: mervynlam
     * @Title: checkFile
     * @Description: 检查文件是否是指定后缀
     * @date: 2021/8/12 11:26
     */
    public static boolean checkFileExt(String targetExt, String fileExt) {
        int index = targetExt.lastIndexOf(".");
        if (index >= 0) {
            targetExt = targetExt.substring(index+1);
        }
        return targetExt.trim().equalsIgnoreCase(fileExt);
    }

    /**
     * @author: mervynlam
     * @Title: isConvert
     * @Description: 判断是否为pdf,mobi,azw文件
     * @date: 2021/8/12 11:42
     */
    public static boolean isConvert(String fileName) {
        String ext = getExt(fileName);
        return !(checkFileExt("pdf", ext)
                ||checkFileExt("mobi", ext)
                ||checkFileExt("azw", ext));
    }

    public static boolean isConvert(File file) {
        String ext = getExt(file.getName());
        return !(checkFileExt("pdf", ext)
                ||checkFileExt("mobi", ext)
                ||checkFileExt("azw", ext));
    }
}
