package com.linksu.fast.coding.baselibrary.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/17 0006
 * 描    述：MVP 模式 BaseActivity 都继承此类
 * 修订历史：v1.0.0 first coding
 * ================================================
 */
public abstract class LMvpBaseActivity extends LBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
