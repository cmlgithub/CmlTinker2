package com.cml.myapplication.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.cml.myapplication.andfix.AndfixPatchManager;
import com.cml.myapplication.tinker.TinkerManager;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by chenmingliang on 2018/5/15.
 */

@DefaultLifeCycle(application = ".MyTinkerApplication", flags = ShareConstants.TINKER_ENABLE_ALL,
loadVerifyFlag = false)
public class MyApplication extends ApplicationLike {

    private Application application;

    public MyApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
        this.application = application;
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //使应用支持分包
        MultiDex.install(base);

        TinkerManager.installTinker(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAndfix();

    }

    private void initAndfix() {
        AndfixPatchManager.getInstance().initPatch(application);
    }
}
