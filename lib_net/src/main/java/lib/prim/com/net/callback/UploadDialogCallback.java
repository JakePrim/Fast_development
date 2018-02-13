package lib.prim.com.net.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import java.io.File;

import lib.prim.com.net.callback.custom.UploadCallback;
import lib.prim.com.net.model.Progress;
import lib.prim.com.net.request.base.BaseRequest;
import okhttp3.Call;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/13 0013
 * 描    述：请求上传文件的弹窗回调 显示上传进度等信息
 * 具体布局可自己修改实现
 * 修订历史：
 * ================================================
 */
public class UploadDialogCallback extends UploadCallback {
    private ProgressDialog dialog;

    public UploadDialogCallback(Activity activity, File file) {
        super(file);
        initDialog(activity);
    }

    private void initDialog(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("上传中...");
    }

    @Override
    public void onStart(BaseRequest<File, ? extends BaseRequest> request, int id) {
        super.onStart(request, id);
        dialog.show();
    }

    @Override
    public void uploadProgress(Progress progress) {
        super.uploadProgress(progress);
        dialog.setMessage((progress.fraction * 100) + "%" + (progress.speed / 1024) + "K/s");
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        super.onError(call, e, id);
        dialog.setMessage("上传错误");
        dialog.dismiss();
    }

    @Override
    public void onSuccess(File response, int id) {
        super.onSuccess(response, id);
        dialog.setMessage("上传成功");
    }

    @Override
    public void onFinish(int id) {
        super.onFinish(id);
        dialog.dismiss();
    }
}
