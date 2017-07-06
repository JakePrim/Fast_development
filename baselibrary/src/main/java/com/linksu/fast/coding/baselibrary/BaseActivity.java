package com.linksu.fast.coding.baselibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.linksu.fast.coding.baselibrary.manager.BaseActivityManager;
import com.linksu.fast.coding.baselibrary.utils.ToastUtils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/6 0006
 * 描    述：All is Activity extends BaseActivity
 * 封装常用的方法如:设置全屏、双击退出、Activity跳转等
 * 修订历史：v1.0.0 first coding
 * ================================================
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected String TAG = "";
    protected Context mContext;
    protected View mBaseLayout;
    protected FrameLayout mBaseContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivityManager.getInstance().addActivity(this);
        init(savedInstanceState);
    }

    /**
     * BaseActivity init
     *
     * @param savedInstanceState
     */
    protected void init(Bundle savedInstanceState) {
        mContext = this;
        TAG = getClass().getSimpleName();
        if (getLayoutView() != null) {
            setContentView(getLayoutView());
        }
        initViewsAndEvent(savedInstanceState);
    }

    protected View getLayoutView() {
        mBaseLayout = LayoutInflater.from(this).inflate(R.layout.lib_base_layout, null, false);
        mBaseContent = (FrameLayout) mBaseLayout.findViewById(R.id.lib_base_content);
        if (getContentViewById() != 0) {
            View contentView = LayoutInflater.from(this).inflate(getContentViewById(), null, false);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mBaseContent.addView(contentView, layoutParams);
        }
        return mBaseLayout;
    }

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
     * init views and events here
     */
    protected abstract void initViewsAndEvent(Bundle savedInstanceState);

    /**
     * bind layout resource file
     */
    @LayoutRes
    protected abstract int getContentViewById();

    /**
     * 设置全屏
     */
    protected void setFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected void startToActivity(Class<?> targetActivityClass) {
        startToActivity(targetActivityClass, null);
    }

    protected void startToActivity(Class<?> targetActivityClass, Bundle bundle) {
        startToActivity(targetActivityClass, bundle, -1);
    }

    protected void startToActivity(Class<?> targetActiviyClass, int requestCode) {
        startToActivity(targetActiviyClass, null, requestCode);
    }

    protected void startToActivity(Class<?> targetActiviyClass, Bundle bundle, int requestCode) {
        Intent startIntent = new Intent(mContext, targetActiviyClass);
        startToActivity(startIntent, bundle, requestCode);
    }

    /**
     * 控制Activity的跳转方法
     *
     * @param startIntent intent
     * @param bundle      bundle数据
     * @param requestCode 请求码
     */
    private void startToActivity(Intent startIntent, Bundle bundle, int requestCode) {
        if (startIntent != null) {
            if (bundle != null) {
                startIntent.putExtras(bundle);
            }
            if (requestCode != -1) {
                startActivity(startIntent);
            } else {
                startActivityForResult(startIntent, requestCode);
            }
        }
    }

    /**
     * all activity with click event
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    @Override
    public void finish() {
        super.finish();
        BaseActivityManager.getInstance().removeActivity(this);
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
