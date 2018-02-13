package lib.prim.com.net.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.view.Window;

import java.io.File;

import lib.prim.com.net.callback.custom.DownloadCallback;
import lib.prim.com.net.model.Progress;
import lib.prim.com.net.request.base.BaseRequest;
import okhttp3.Call;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/13 0013
 * 描    述：下载请求弹窗回调 显示下载进度和速度
 * 具体布局可自己定制实现 可保持全局统一
 * 修订历史：
 * ================================================
 */
public class DownloadDialogCallback extends DownloadCallback {
    private ProgressDialog dialog;

    private void initDialog(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("下载中...");
    }

    public DownloadDialogCallback(Activity activity) {
        super();
        initDialog(activity);
    }

    public DownloadDialogCallback(Activity activity, String fileName) {
        super(fileName);
        initDialog(activity);
    }

    public DownloadDialogCallback(Activity activity, String fileDir, String fileName) {
        super(fileDir, fileName);
        initDialog(activity);
    }

    @Override
    public void onStart(BaseRequest<File, ? extends BaseRequest> request, int id) {
        super.onStart(request, id);
        dialog.show();
    }

    @Override
    public void downloadProgress(Progress progress) {
        super.downloadProgress(progress);
        dialog.setMessage("下载中：" + (progress.fraction * 100) + "% \n" + (progress.speed / 1024) + " kb/s");
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        super.onError(call, e, id);
        dialog.setMessage("下载错误");
        dialog.dismiss();
    }

    @Override
    public void onSuccess(File response, int id) {
        super.onSuccess(response, id);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.setMessage("下载成功");
            }
        }, 1000);

    }

    @Override
    public void onFinish(int id) {
        super.onFinish(id);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
    }
}
