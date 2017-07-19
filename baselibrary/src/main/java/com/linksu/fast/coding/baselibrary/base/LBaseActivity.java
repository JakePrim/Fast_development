package com.linksu.fast.coding.baselibrary.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.linksu.fast.coding.baselibrary.R;
import com.linksu.fast.coding.baselibrary.dialog.SystemDialog;
import com.linksu.fast.coding.baselibrary.manager.BaseActivityManager;
import com.linksu.fast.coding.baselibrary.utils.ToastUtils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/6 0006
 * 描    述：通用操作基类,若不是MVP MVVM 模式 都继承此类
 * 修订历史：v1.0.0 first coding
 * ================================================
 */
public abstract class LBaseActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 加载数据 具体在哪里调用交给子类去调用实现
     */
    protected abstract void loadData();

    /**
     * 自动转换id
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T findAviewById(int viewId) {
        if (viewId > 0) {
            return (T) findViewById(viewId);
        }
        return null;
    }

    /**
     * 设置全屏
     */
    protected void setFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 不传数据 不传返回值
     *
     * @param targetActivityClass
     */
    protected void startToActivity(Class<?> targetActivityClass, boolean isFinish) {
        startToActivity(targetActivityClass, null, isFinish);
    }

    /**
     * 传数据 不传返回值
     *
     * @param targetActivityClass
     * @param bundle
     */
    protected void startToActivity(Class<?> targetActivityClass, Bundle bundle, boolean isFinish) {
        startToActivity(targetActivityClass, bundle, -1, isFinish);
    }

    /**
     * 不传数据 传返回值 activity 不能finish
     *
     * @param targetActiviyClass
     * @param requestCode
     */
    protected void startToActivity(Class<?> targetActiviyClass, int requestCode) {
        startToActivity(targetActiviyClass, null, requestCode, false);
    }

    /**
     * 传数据 传返回值 Activity 不能finish
     *
     * @param targetActiviyClass
     * @param bundle
     * @param requestCode
     */
    protected void startToActivity(Class<?> targetActiviyClass, Bundle bundle, int requestCode, boolean isFinish) {
        Intent startIntent = new Intent(mContext, targetActiviyClass);
        startToActivity(startIntent, bundle, requestCode, isFinish);
    }

    /**
     * 控制Activity的跳转方法
     *
     * @param startIntent intent
     * @param bundle      bundle数据
     * @param requestCode 请求码
     */
    private void startToActivity(Intent startIntent, Bundle bundle, int requestCode, boolean isFinish) {
        if (startIntent != null) {
            if (bundle != null) {
                startIntent.putExtras(bundle);
            }
            if (requestCode != -1) {
                startActivity(startIntent);
                if (isFinish) {
                    finish();
                }
            } else {
                startActivityForResult(startIntent, requestCode);
            }
        }
    }

    /**
     * 弹出系统的dialog
     *
     * @param title
     * @param message
     * @param positive
     * @param negative
     * @param listener
     * @return Dialog 手动控制隐藏显示
     */
    protected void popupSysDialog(String title, String message, String positive, String negative, final SystemDialog.onSysDialogListener listener) {
         SystemDialog.getInstance(this).onCreateDialog(title, message, positive, negative, listener);
    }

    //通用弹出框调用


    //分享调用


    //登录状态判断


    //滚动到顶部

    protected long quitTime = 0;

    /**
     * 双击退出APP,监听onKeyDown事件
     */
    protected void doubleAppExit() {
        if (System.currentTimeMillis() - quitTime >= 2000) {
            ToastUtils.showShortSafe(getString(R.string.str_app_exit));
            quitTime = System.currentTimeMillis();
        } else {
            BaseActivityManager.getInstance().AppExit(this);
        }
    }

    /**
     * 直接退出APP
     */
    protected void appExit() {
        BaseActivityManager.getInstance().AppExit(this);
    }

}
