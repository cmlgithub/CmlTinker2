package com.cml.myapplication.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by chenmingliang on 2018/5/15.
 */

public class Utils {

    public static String getVersionName(Context context){
        String versionName = "1.0.0";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

}
