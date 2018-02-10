package com.linksu.fast.coding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.linksu.fast.coding.baselibrary.base.activity.LBaseActivity;
import com.linksu.fast.coding.baselibrary.enetity.BaseEventBusBean;
import com.linksu.fast.coding.baselibrary.utils.PrimLogger;

import okhttp3.Call;
import okhttp3.Response;
import weather.linksu.com.nethttplibrary.PrimHttpUtils;
import weather.linksu.com.nethttplibrary.callback.DialogCallback;
import weather.linksu.com.nethttplibrary.callback.HttpCallback;
import weather.linksu.com.nethttplibrary.request.base.BaseRequest;

public class AbsFastActivity extends LBaseActivity implements HttpCallback {

    private TextView tv_test;

    @Override
    protected void operateArgs() {
        Log.e("linksu",
                "operateArgs(AbsFastActivity.java:20)");
    }

    @Override
    protected void initViewsAndEvent(Bundle savedInstanceState) {
        Log.e("linksu",
                "initViewsAndEvent(AbsFastActivity.java:26)");
        tv_test = findAviewById(R.id.tv_test);
        loadData();
    }

    @Override
    protected int getContentViewById() {
        return R.layout.activity_abs_fast;
    }

    @Override
    protected boolean openEventBus() {
        return false;
    }

    @Override
    protected void getEventBean(BaseEventBusBean event) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void loadData() {
        // 测试 SDK 是否正常工作的代码
        //https://api.douban.com/v2/book/1220562
//        httpUtil.get("/v2/movie/subject/1764796", 1, TestBean.class);
//        httpUtil.get("/v2/movie/in_theaters", 2, TestBean.class);
        PrimHttpUtils.getInstance()
                .get("https://api.douban.com/v2/movie/subject/1764796")
                .id(2)
                .tag(TAG)
                .enqueue(new DialogCallback<Object>(this) {
                    @Override
                    public void onFinish(int id) {
                        super.onFinish(id);
                        PrimLogger.e(TAG, "get 整个网络请求完毕 --> " + id);
                        PrimHttpUtils.getInstance()
                                .<String>post("https://api.douban.com/v2/movie/in_theaters")
                                .id(1)
                                .tag(TAG)
                                .enqueue(new DialogCallback<String>(AbsFastActivity.this) {
                                    @Override
                                    public void onFinish(int id) {
                                        super.onFinish(id);
                                        PrimLogger.e(TAG, "post 整个网络请求完毕 --> " + id);
                                    }
                                });
                    }

                    @Override
                    public void onSuccess(Object response, int id) {
                        super.onSuccess(response, id);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        }
    }

    @Override
    protected void onRetryClick() {
        loadData();
    }

    @Override
    public void onStart(BaseRequest request, int id) {
        showLoadingView();
        PrimLogger.e(TAG, "开始加载网络请求 --> " + id);
    }

    @Override
    public Object convertResponse(Response response, int id) {
        return null;
    }

    @Override
    public void onSuccess(Object response, int id) {
        PrimLogger.e(TAG, "得到转换成功的数据 --> " + response);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        showErrorView();
        PrimLogger.e(TAG, "请求网络失败或者解析失败 --> " + id);
    }

    @Override
    public void onFinish(int id) {
        showContentView();
        PrimLogger.e(TAG, "整个网络请求完毕 --> " + id);
    }
}
