package com.up.uwei.shshop;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.RefWatcher;

public class Configs {
    public static Context APP_CONTEXT;
    public static Context MAINACTIVITY_CONTEXT;
    public static Boolean DEBUG_MODE = true;
    public static RefWatcher REF_WATCHER;
    public static void setAppContext(Application appContext){
        APP_CONTEXT = appContext;
    }


}
