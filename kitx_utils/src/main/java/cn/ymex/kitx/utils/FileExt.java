package cn.ymex.kitx.utils;

import android.os.Environment;

import androidx.core.os.EnvironmentCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileExt {

    /**
     * 获取Android 外部存储
     *
     * @return 返回存储路径
     */
    public static File getExternalStorageDirectory() {
        File extFile = Environment.getExternalStorageDirectory();
        String storageState = EnvironmentCompat.getStorageState(extFile);
        if (!storageState.equals(Environment.MEDIA_MOUNTED)) {
            extFile = getDataDirectory();
        }
        return extFile;
    }
    /**
     * 获取Android data存储
     *
     * @return 返回存储路径
     */
    public static File getDataDirectory() {
        File extFile = Environment.getDataDirectory();
        if (!extFile.exists()) {
            extFile = null;
        }
        return extFile;
    }

    /**
     * 获取文件或文件夹文件大小
     *
     * @param file 文件
     * @return size
     */
    public static long getFileSize(File file) {
        long size = -1;
        if (file.exists()) {
            size = file.length();
            if (file.isFile()) {
                size = file.length();
            } else {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (f.isFile()) {
                        size += f.length();
                    } else if (f.isDirectory()) {
                        size += getFileSize(f); // 递归调用继续统计
                    }
                }
            }
        }
        return size;
    }


    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * @param text 写入的文本
     * @param file 文件
     * @throws IOException 文件不存在
     */
    public static void writeText(File file, String text) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(text);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * @param file 文件
     * @return
     * @throws IOException 文件不存在或读取异常
     */
    public static String readText(File file) throws IOException {
        if (!file.exists()) {
            return "";
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        return buffer.toString();
    }
}
