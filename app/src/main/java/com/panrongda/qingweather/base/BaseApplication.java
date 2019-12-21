package com.panrongda.qingweather.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import interfaces.heweather.com.interfacesmodule.view.HeConfig;

/**
 * Create by Dada on 2019/12/10 15:28.
 */
public class BaseApplication extends Application {

    public static final String  KEY_NAME  = "dada2012";
    public static final String  USER_NAME = "HE1911301350411144";
    public static final String  KEY       = "fff38df400534787b30d14f878fed26d";
    private static      Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        initApp();
        initUtils();
        initAppKey();
    }

    public static Context getContext() {
        return context;
    }

    private void initApp() {
        context = getApplicationContext();
    }

    private void initAppKey() {
        HeConfig.init(USER_NAME, KEY);
        HeConfig.switchToFreeServerNode();  // 免费节点
    }

    private void initUtils() {
        LogUtils.getConfig().setLogSwitch(true);
        ToastUtils.setBgColor(Color.parseColor("#aa000000"));
    }
}
