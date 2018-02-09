package com.linksu.fast.coding.baselibrary.utils;

import android.graphics.Typeface;
import android.widget.TextView;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/6 0006
 * 描    述：设置字体相关类
 * 修订历史：
 * ================================================
 */
public enum TypefaceUtils {
    TYPEFACE;

    private static Typeface typeface50;
    private static Typeface typeface55;

    public void setRegularTypeface(TextView textView) {
        if (typeface50 == null)
            typeface50 = Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/Roboto-Regular.otf");
        textView.setTypeface(typeface50);
    }

    public void setLightTypeface(TextView textView) {
        if (typeface55 == null)
            typeface55 = Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/Roboto-Light.otf");
        textView.setTypeface(typeface55);
    }
}
