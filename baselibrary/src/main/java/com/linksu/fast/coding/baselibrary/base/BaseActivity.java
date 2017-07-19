package com.linksu.fast.coding.baselibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.linksu.fast.coding.baselibrary.R;
import com.linksu.fast.coding.baselibrary.enetity.BaseEventBusBean;
import com.linksu.fast.coding.baselibrary.manager.BaseActivityManager;
import com.linksu.fast.coding.baselibrary.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/6 0006
 * 描    述：All is Activity extends BaseActivity
 * 通用的布局基类
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
        initArgs();
        operateArgs();
        init(savedInstanceState);
    }

    /**
     * 在布局之前需要完成的操作
     */
    protected void initArgs() {
        BaseActivityManager.getInstance().addActivity(this);
        if (openEventBus()) {
            EventBus.getDefault().register(this);
        }
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            if (null != getIntent().getExtras()) {
                getBundleExtras(extras);
            }
        }
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

    /**
     * 返回布局View
     *
     * @return getLayoutView
     */
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
     * 获取LayoutInflater
     *
     * @return
     */
    public LayoutInflater getLayoutInflater() {
        LayoutInflater _layoutinflater = LayoutInflater.from(this);
        return _layoutinflater;
    }

    /**
     * 子类在加载布局之前的其他操作
     */
    protected abstract void operateArgs();

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
     * eventbus 开关
     *
     * @return true 注册eventbus false 不注册eventbus
     */
    protected abstract boolean openEventBus();

    /**
     * Bundle  传递数据
     *
     * @param extras 得到Activity传递过来的数据
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * eventbus在主线程接收方法
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaseEventBusBean event) {
        if (event != null) {
            getEventBean(event);
        }
    }

    /**
     * EventBus接收信息的方法，开启后才会调用
     *
     * @param event
     */
    protected abstract void getEventBean(BaseEventBusBean event);

    /**
     * all activity with click event
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        LogUtils.v("onClick");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaseLayout = null;
        BaseActivityManager.getInstance().removeActivity(this);
        if (openEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }
}
