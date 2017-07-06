package com.linksu.fast.coding;

import com.linksu.fast.coding.baselibrary.BaseApplication;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/6 0006
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class fastApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
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
