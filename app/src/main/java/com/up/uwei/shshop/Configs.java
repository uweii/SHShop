package com.up.uwei.shshop;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;

public class Configs {
    public static Context APP_CONTEXT;
    public static Context MAINACTIVITY_CONTEXT;
    public static Boolean DEBUG_MODE = true;
    public static RefWatcher REF_WATCHER;
    public static void setAppContext(Application appContext){
        APP_CONTEXT = appContext;
    }
    public static int STATUS_NORMAL = 0;
    public static int STATUS_LAST = 1;
    public static ArrayList<Integer> COLORS;

    public static void init(){
        COLORS = new ArrayList<>();
        COLORS.add(Color.parseColor("#FF9999"));
        COLORS.add(Color.parseColor("#0099CC"));
        COLORS.add(Color.parseColor("#FF6666"));
        COLORS.add(Color.parseColor("#99cc66"));
        COLORS.add(Color.parseColor("#ffcccc"));
        COLORS.add(Color.parseColor("#cc9999"));
        COLORS.add(Color.parseColor("#ccccff"));
        COLORS.add(Color.parseColor("#ffff99"));
        COLORS.add(Color.parseColor("#ff9966"));
        COLORS.add(Color.parseColor("#99cccc"));
    }


}
