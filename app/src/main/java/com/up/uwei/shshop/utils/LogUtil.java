package com.up.uwei.shshop.utils;

import android.util.Log;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.up.uwei.shshop.Configs;

/**
 * Created by Administrator on 2017/3/7.
 */

public class LogUtil {
    public static boolean isInitiated;

    public static void init() {
        if (!isInitiated) {
            Logger.init("Stormorai")
                    .hideThreadInfo()
                    .methodCount(1)
                    .methodOffset(1)
                    .logLevel(Configs.DEBUG_MODE ? LogLevel.FULL : LogLevel.NONE);
            isInitiated = true;
        }
    }

    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

    public static void w(String message, Object... args) {
        Logger.w(message, args);
    }

    public static void e(String message, Object... args) {
        //LogUploader.upload(Configs.LOG_ERROR_CODE, args == null ? message : String.format(message, args));
        Logger.e(message, args);
    }

    public static void wtf(String message, Object... args) {
        Logger.wtf(message, args);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }

    public static void object(String message, Object object) {
        Logger.t(message).d(object);
    }

    public static void object(Object object) {
        Logger.d(object);
    }

    public static void E(int code, String message, Object... args) {
        Logger.e(message, args);
        //LogUploader.upload(code, args == null ? message : String.format(message, args));
    }


}
