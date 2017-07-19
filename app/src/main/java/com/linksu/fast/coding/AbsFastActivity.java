package com.linksu.fast.coding;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.linksu.fast.coding.baselibrary.base.BaseActivity;
import com.linksu.fast.coding.baselibrary.base.LBaseActivity;
import com.linksu.fast.coding.baselibrary.dialog.SystemDialog;
import com.linksu.fast.coding.baselibrary.enetity.BaseEventBusBean;
import com.linksu.fast.coding.baselibrary.utils.LogUtils;
import com.linksu.fast.coding.baselibrary.utils.ToastUtils;

public class AbsFastActivity extends LBaseActivity {

    protected Dialog dialog;

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
}
