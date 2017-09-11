package com.linksu.fast.coding.baselibrary.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.linksu.fast.coding.baselibrary.utils.LogUtils;
import com.linksu.fast.coding.baselibrary.utils.Utils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/6 0006
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class BaseApplication extends Application {

    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        initUtils();
    }

    /**
     * 获取Application
     */
    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    /**
     * 初始化工具类
     */
    private void initUtils() {
        Utils.init(this);
        new LogUtils.Builder()
                .setLogSwitch(isLogSwitch())// 设置log总开关，默认开
                .setGlobalTag(getGlobalTag())// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLog2FileSwitch(isFileSwitch())// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(isBorderSwitch())// 输出日志是否带边框开关，默认开
                .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose
    }

    /**
     * log 的总开关 交给子类去决定
     *
     * @return
     */
    protected abstract boolean isLogSwitch();

    /**
     * log 是否保存到文件交给子类去决定
     *
     * @return
     */
    protected abstract boolean isFileSwitch();

    /**
     * log 输出日志是否带边框开关交给子类去决定
     *
     * @return
     */
    protected abstract boolean isBorderSwitch();

    /**
     * log 设置log全局标签交给子类去决定
     *
     * @return
     */
    protected abstract String getGlobalTag();
}
