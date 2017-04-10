package com.xinyi_tech.commonlibs.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by studyjun on 2016/5/28.
 */
public class AndroidUtil {

    /**
     * 获取android版本
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageInfo info = null;
        PackageManager manager = context.getPackageManager();
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null==info?0:info.versionCode;

    }

    public static String getVersionName(Context context) {

        PackageInfo info = null;
        PackageManager manager = context.getPackageManager();
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null==info?"v1.0":info.versionName;

    }
}
