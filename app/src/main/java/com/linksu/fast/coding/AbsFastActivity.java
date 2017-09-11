package com.linksu.fast.coding;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.linksu.fast.coding.baselibrary.base.activity.LBaseActivity;
import com.linksu.fast.coding.baselibrary.dialog.SystemDialog;
import com.linksu.fast.coding.baselibrary.enetity.BaseEventBusBean;
import com.linksu.fast.coding.baselibrary.utils.LogUtils;
import com.linksu.fast.coding.baselibrary.utils.ToastUtils;

import okhttp3.Response;
import weather.linksu.com.nethttplibrary.HttpUtil;
import weather.linksu.com.nethttplibrary.okhttp.OkCallBack;
import weather.linksu.com.nethttplibrary.okhttp.OkClient;

public class AbsFastActivity extends LBaseActivity implements OkCallBack {

    protected Dialog dialog;

    @Override
    protected void onRetryClick() {

    }

    @Override
    protected void operateArgs() {

    }

    @Override
    protected void initViewsAndEvent(Bundle savedInstanceState) {
//        setFullScreen();
        ToastUtils.showShort("开始应用了");
        LogUtils.json("ssssssssssssss");
        findAviewById(R.id.fab).setOnClickListener(this);
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
//        AVObject testObject = new AVObject("TestProject");
//        testObject.put("words", "Hello World!");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    Log.d("saved", "success!");
//                }
//            }
//        });
//        setHttpClient(new OkClient());
        httpUtil.setCallBack(this);
        httpUtil.get("http://api.douban.com/v2/movie/subject/1764796", 1, TestBean.class);
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
    public void OnJsonParseError(Response response, Exception e) {
        ToastUtils.showShortSafe("json解析异常");
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
    }
}
