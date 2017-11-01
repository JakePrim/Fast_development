package com.linksu.fast.coding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.linksu.fast.coding.baselibrary.base.activity.LBaseActivity;
import com.linksu.fast.coding.baselibrary.enetity.BaseEventBusBean;
import com.linksu.fast.coding.baselibrary.utils.ToastUtils;

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
//        httpUtil.get("/v2/movie/subject/1764796", 1, TestBean.class);
//        httpUtil.get("/v2/movie/in_theaters", 2, TestBean.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        }
    }

//    @Override
//    public void onLoadRequest(Object request) {
//        showLoadingView();
//    }
//
//    @Override
//    public void onFailure(int action, Object data, Exception e) {
//        showErrorView();
//        ToastUtils.showShortSafe(data.toString());
//    }
//
//    @Override
//    public void onResponse(int action, Object data) {
//        showContentView();
//        switch (action) {
//            case 1:
//                tv_test.setText("/v2/movie/subject/1764796");
//                break;
//            case 2:
//                tv_test.setText("/v2/movie/in_theaters");
//                break;
//        }
//    }

    @Override
    protected void onRetryClick() {
        loadData();
    }
}
