package lib.prim.com.net.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Window;

import lib.prim.com.net.callback.custom.JsonCallback;
import okhttp3.Call;
import lib.prim.com.net.request.base.BaseRequest;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/10 0010
 * 描    述：对于json网络请求是否需要弹出加载对话框
 * 具体布局可自己修改实现
 * 修订历史：
 * ================================================
 */
public abstract class DialogCallback<T> extends JsonCallback<T> {
    private ProgressDialog dialog;

    private static final String TAG = "DialogCallback";

    private void initDialog(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    public DialogCallback(Activity activity) {
        super();
        initDialog(activity);
    }

    @Override
    public void onStart(BaseRequest<T, ? extends BaseRequest> request, int id) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        super.onError(call, e, id);
    }

    @Override
    public void onFinish(int id) {
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
