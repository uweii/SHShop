package com.up.uwei.shshop;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configs {
    public static String BASE_URL = "http://100.64.25.253:8080/SHServer/";
    //public static String BASE_URL = "http://192.168.43.12:8080/SHServer/";
    public static String GET_GOODS = "getGoods";
    public static final int EMAIL_ALREADY_EXIST = 1;  //邮箱已存在
    public static final int EMAIL_NOT_EXIST = 2;      //邮箱不存在
    public static final int ERR_PASSWORD = 3;         //密码错误
    public static final int INSERT_ERR = 4;         //添加错误
    public static final String GOOD_PARCELABLE_KEY = "goods"; //序列化goods时用到的key

    public static final String  SPLIT_STRING = ",mysplit."; //分隔符

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
    //img字符分割成数组
    public static String[] getImgs(String string){
        if (string != null){
            return  string.split(SPLIT_STRING);
        }
        return null;
    }

    public static String getTag(String tag){
        if (tag != null){
            return tag.replace(SPLIT_STRING, "|");
        }
        return null;
    }
    public static List<String> getListTags(String tag){
        if (tag != null){
            return Arrays.asList(tag.split(SPLIT_STRING));
        }
        return null;
    }


   /* public static void main(String[] args) {
        String s = "篮球,mysplit.足球,mysplit.羽毛球";
        String[] ss = s.split(",mysplit.");
        System.out.println(Arrays.toString(ss));
    }*/
}
