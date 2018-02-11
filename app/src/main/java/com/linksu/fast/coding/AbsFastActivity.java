package com.linksu.fast.coding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.linksu.fast.coding.baselibrary.base.activity.LBaseActivity;
import com.linksu.fast.coding.baselibrary.enetity.BaseEventBusBean;
import com.linksu.fast.coding.baselibrary.utils.PrimLogger;
import com.linksu.fast.coding.bean.ServerModel;

import lib.prim.com.net.model.LzyResponse;
import okhttp3.Call;
import lib.prim.com.net.PrimHttpUtils;
import lib.prim.com.net.callback.DialogCallback;
import okhttp3.Response;

public class AbsFastActivity extends LBaseActivity {

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
                .<LzyResponse<ServerModel>>post("http://server.jeasonlzy.com/OkHttpUtils/jsonObject")
                .id(1)
                .tag(TAG)
                .params("id", "0")
                .enqueue(new DialogCallback<LzyResponse<ServerModel>>(this) {
                    @Override
                    public void onSuccess(LzyResponse<ServerModel> response, int id) {
                        super.onSuccess(response, id);
                        PrimLogger.e(TAG, response.toString());
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
}
