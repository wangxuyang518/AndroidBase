/*
 * Copyright (c) 2014 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.xinyi_tech.commonlibs.util;

import android.graphics.Bitmap;
import android.os.Environment;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtils {

    private static final String MOBILE_DIRECTORY_NAME = "QSCMobile";

    private static final SimpleDateFormat DATE_TIME_FORMAT =
            new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);

    public static String makeDateTimeFileName(String prefix, String suffix) {
        return String.format("%s_%s.%s", prefix, DATE_TIME_FORMAT.format(new Date()), suffix);
    }

    public static File makePictureDirectory() {
        return new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                MOBILE_DIRECTORY_NAME);
    }

    public static void ensureParentDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.isDirectory()) {
            parent.mkdirs();
        }
    }


    /**
     * 保存图片
     * @param bitmap
     * @param name
     */
    public static void saveInDisk(Bitmap bitmap,String name){
        if (bitmap!=null){
            String path =getRootSavePath();
            File file = new File(path);
            if (!file.exists()){
                file.mkdirs();
            }
            File bFile = new File(path,name);
            if (bFile.exists()){
                bFile.delete();
            }
            try {
                bFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileOutputStream fos = new FileOutputStream(bFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 保存图片
     * @param content
     * @param name
     */
    public static String saveInDisk(String content,String name){
        if (content!=null){
            String path =getRootSavePath();
            File file = new File(path);
            if (!file.exists()){
                file.mkdirs();
            }
            File bFile = new File(path,name);
            if (bFile.exists()){
                bFile.delete();
            }
            try {
                bFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileOutputStream fos = new FileOutputStream(bFile);
                fos.write(content.getBytes());
                fos.flush();
                fos.close();
                return bFile.getAbsolutePath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 获取更的存储节点
     * 默认获取sd卡xinyi，如果没有，则放在data下
     * @return
     */
    public static String getRootSavePath(){
        String path;
        if (Environment.isExternalStorageEmulated()){
            path=Environment.getExternalStorageDirectory()+"/xinyi";
        } else {
            path=Environment.getDataDirectory()+"'/xinyi";
        }
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        return path;
    }

    private FileUtils() {}
}
