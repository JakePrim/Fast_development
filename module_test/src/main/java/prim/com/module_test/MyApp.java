package prim.com.module_test;

import lib.linksu.fast.coding.baselibrary.base.BaseApplication;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/13 0013
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MyApp extends BaseApplication {
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
        return "prim";
    }
}
