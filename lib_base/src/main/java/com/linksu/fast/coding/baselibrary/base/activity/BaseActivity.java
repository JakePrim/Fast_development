package com.linksu.fast.coding.baselibrary.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.linksu.fast.coding.baselibrary.R;
import com.linksu.fast.coding.baselibrary.widget.MultipleStatusView;

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

    protected String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    protected View mBaseLayout;
    public RelativeLayout content_view;
    public MultipleStatusView statusView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    /**
     * 返回布局View
     *
     * @return getLayoutView
     */
    protected View getLayoutView() {
        if (mBaseLayout == null) { // 防止每个Activity 都inflate view(Prevent each Activity from inflate view)
            mBaseLayout = LayoutInflater.from(this).inflate(R.layout.lib_base_layout, null, false);
            statusView = (MultipleStatusView) mBaseLayout.findViewById(R.id.main_multiplestatusview);
            content_view = (RelativeLayout) mBaseLayout.findViewById(R.id.content_view);
        }
        initListener();
        if (getContentViewById() != 0) {
            content_view.removeAllViews();
            View contentView = LayoutInflater.from(this).inflate(getContentViewById(), null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            content_view.addView(contentView, params);
        }
        return mBaseLayout;
    }

    /**
     * 初始化监听
     */
    protected void initListener() {
        statusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusView.showLoading();
                onRetryClick();
            }
        });
    }

    /**
     * 错误等界面的点击事件处理
     */
    protected abstract void onRetryClick();

    protected void showContentView() {
        statusView.showContent();
    }

    protected void showEmptyView() {
        statusView.showEmpty();
    }

    protected void showErrorView() {
        statusView.showError();
    }

    protected void showLoadingView() {
        statusView.showLoading();
    }

    protected void showNoNetworkView() {
        statusView.showNoNetwork();
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
     * all activity with click event
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }
}
