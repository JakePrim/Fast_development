package com.linksu.fast.coding;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.linksu.fast.coding.baselibrary.base.BaseActivity;
import com.linksu.fast.coding.baselibrary.base.LBaseActivity;
import com.linksu.fast.coding.baselibrary.enetity.BaseEventBusBean;
import com.linksu.fast.coding.baselibrary.utils.LogUtils;
import com.linksu.fast.coding.baselibrary.utils.ToastUtils;

public class AbsFastActivity extends LBaseActivity {

    @Override
    protected void operateArgs() {

    }

    @Override
    protected void initViewsAndEvent(Bundle savedInstanceState) {
//        setFullScreen();
        ToastUtils.showShort("开始应用了");
        LogUtils.json("ssssssssssssss");
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
}
