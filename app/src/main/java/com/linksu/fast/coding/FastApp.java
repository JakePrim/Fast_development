package com.linksu.fast.coding;

import android.content.Context;

import com.linksu.fast.coding.baselibrary.base.BaseApplication;

import weather.linksu.com.nethttplibrary.PrimHttpUtils;
import weather.linksu.com.nethttplibrary.httpclient.OkClient;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/6 0006
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class FastApp extends BaseApplication {

    private static FastApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        PrimHttpUtils.init(new OkClient());
        // 初始化参数依次为 this, AppId, AppKey
//        AVOSCloud.initialize(this, "44lwlOKiTbmYvGToxIggkHj3-gzGzoHsz", "Rk911hC66tLEadVHF2G42p8y");
//        AVOSCloud.setDebugLogEnabled(true);//开启调试日志
    }

    /**
     * 获取Application
     */
    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    @Override
    protected boolean isLogSwitch() {
        return true;
    }

    @Override
    protected boolean isFileSwitch() {
        return false;
    }

    @Override
    protected boolean isBorderSwitch() {
        return true;
    }

    @Override
    protected String getGlobalTag() {
        return "Linksu";
    }
}
