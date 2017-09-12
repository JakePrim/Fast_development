package com.linksu.fast.coding;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.linksu.fast.coding.baselibrary.base.activity.LBaseActivity;
import com.linksu.fast.coding.baselibrary.dialog.SystemDialog;
import com.linksu.fast.coding.baselibrary.enetity.BaseEventBusBean;
import com.linksu.fast.coding.baselibrary.utils.LogUtils;
import com.linksu.fast.coding.baselibrary.utils.ToastUtils;

import weather.linksu.com.nethttplibrary.BaseCallback;

public class AbsFastActivity extends LBaseActivity {

    private TextView tv_test;


    @Override
    protected void operateArgs() {

    }

    @Override
    protected void initViewsAndEvent(Bundle savedInstanceState) {
        findAviewById(R.id.fab).setOnClickListener(this);
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
        httpUtil.get("/v2/movie/subject/1764796", 1, TestBean.class);
        httpUtil.get("/v2/movie/in_theaters", 2, TestBean.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fab:
                popupSysDialog("测试", "测试系统弹出框", "确定", "取消", new SystemDialog.onSysDialogListener() {
                    @Override
                    public void onPositive(DialogInterface dialog) {
                        ToastUtils.showShort("确定");
                    }

                    @Override
                    public void onNegative(DialogInterface dialog) {
                        ToastUtils.showShort("取消");
                    }
                });
                break;
        }
    }

    @Override
    public void onLoadRequest(Object request) {
        showLoadingView();
    }

    @Override
    public void onFailure(int action, Object data, Exception e) {
        showErrorView();
        ToastUtils.showShortSafe(data.toString());
    }

    @Override
    public void onResponse(int action, Object data) {
        showContentView();
        switch (action) {
            case 1:
                tv_test.setText("/v2/movie/subject/1764796");
                break;
            case 2:
                tv_test.setText("/v2/movie/in_theaters");
                break;
        }
    }

    @Override
    protected void onRetryClick() {
        loadData();
    }
}
