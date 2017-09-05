package com.linksu.fast.coding.baselibrary.base.activity;

import android.view.KeyEvent;

import com.linksu.fast.coding.baselibrary.base.fragment.BaseFragment;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：8/11 0011
 * 描    述：Activity+多Fragment 集成此Activity
 * 修订历史：
 * ================================================
 */
public abstract class LFragmentBaseActivity extends LBaseActivity {

    // 布局中fragment的ID
    protected abstract int getFragmentContentId();

    // 添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    // 移除Fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
