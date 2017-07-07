package com.linksu.fast.coding.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/7 0007
 * 描    述：简单的系统弹出框
 * 修订历史：
 * ================================================
 */
public class SystemDialog {
    protected Context context;
    protected static SystemDialog dialog;

    public static SystemDialog getInstance(Context context) {
        if (dialog == null) {
            synchronized (SystemDialog.class) {
                if (dialog == null) {
                    dialog = new SystemDialog(context);
                }
            }
        }
        return dialog;
    }

    public SystemDialog(Context context) {
        this.context = context;
    }

    /**
     * 创建一个dialog
     *
     * @param title
     * @param message
     * @param positive
     * @param negative
     * @param listener
     * @return
     */
    public void onCreateDialog(String title, String message, String positive, String negative, final onSysDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(title);
        builder.setTitle(message);
        if (!TextUtils.isEmpty(positive)) {
            builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    listener.onPositive(dialog);
                }
            });
        }
        if (!TextUtils.isEmpty(negative)) {
            builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    listener.onNegative(dialog);
                }
            });
        }
        builder.create().show();
    }

    /**
     * 系统弹窗 按钮点击监听
     */
    public interface onSysDialogListener {
        void onPositive(DialogInterface dialog);

        void onNegative(DialogInterface dialog);
    }
}
