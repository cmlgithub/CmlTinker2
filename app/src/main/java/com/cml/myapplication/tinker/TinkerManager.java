package com.cml.myapplication.tinker;

import android.content.Context;

import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Created by chenmingliang on 2018/5/15.
 *
 * thinkerId is not set
 */

public class TinkerManager {

    private static boolean isInstalled = false;

    private static ApplicationLike mAppLike;

    /**
     * init Tinker
     * @param applicationLike
     */
    public static void installTinker(ApplicationLike applicationLike){
        mAppLike = applicationLike;
        if(isInstalled)
            return;

        TinkerInstaller.install(mAppLike);
        isInstalled = true;
    }

    /**
     * load patch file
     * @param path
     */
    public static void loadPatch(String path){
        if(isInstalled)
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),path);
    }

    /**
     * Get context by ApplicationLike
     * @return
     */
    private static Context getApplicationContext(){
        if(mAppLike != null)
            return mAppLike.getApplication().getApplicationContext();
        return null;
    }

}
