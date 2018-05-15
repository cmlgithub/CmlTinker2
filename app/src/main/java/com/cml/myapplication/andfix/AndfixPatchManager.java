package com.cml.myapplication.andfix;

import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;
import com.cml.myapplication.util.Utils;

import java.io.IOException;

/**
 * Created by chenmingliang on 2018/5/15.
 *
 * Manager Andfix
 */

public class AndfixPatchManager {

    private static AndfixPatchManager andfixPatchManager;

    private static PatchManager patchManager ;

    private AndfixPatchManager(){}

    public static AndfixPatchManager getInstance(){
        if(andfixPatchManager == null){
            synchronized (AndfixPatchManager.class){
                if(andfixPatchManager == null){
                    andfixPatchManager = new AndfixPatchManager();
                }
            }
        }

        return andfixPatchManager;
    }


    public void initPatch(Context context){
        patchManager = new PatchManager(context);
        patchManager.init(Utils.getVersionName(context));
        patchManager.loadPatch();
    }

    public void addPatch(String path){
        if(patchManager != null) {
            try {
                patchManager.addPatch(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
