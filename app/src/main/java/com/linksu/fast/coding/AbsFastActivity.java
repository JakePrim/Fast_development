package com.linksu.fast.coding;

import android.os.Bundle;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.linksu.fast.coding.baselibrary.BaseActivity;
import com.linksu.fast.coding.baselibrary.utils.LogUtils;
import com.linksu.fast.coding.baselibrary.utils.ToastUtils;

public class AbsFastActivity extends BaseActivity {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void initViewsAndEvent(Bundle savedInstanceState) {
//        setFullScreen();
        ToastUtils.showShort("开始应用了");
        LogUtils.json("ssssssssssssss");
    }

    @Override
    protected int getContentViewById() {
        return R.layout.activity_abs_fast;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_abs_fast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
