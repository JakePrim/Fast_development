package lib.prim.com.net.callback.custom;

import java.io.File;

import lib.prim.com.net.callback.base.CallbackAdapter;
import lib.prim.com.net.request.base.BaseRequest;
import lib.prim.com.net.utils.Utils;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/13 0013
 * 描    述：上传文件的回调  注意 convertResponse 回调不做数据转换
 * 修订历史：
 * ================================================
 */
public class UploadCallback extends CallbackAdapter<File> {

    private File file;

    public UploadCallback(File file) {
        this.file = file;
    }

    @Override
    public void onStart(BaseRequest<File, ? extends BaseRequest> request, int id) {
        super.onStart(request, id);
    }

    @Override
    public File convertResponse(final Response response, final int id) throws Throwable {
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onSuccess(file, id);
                onFinish(id);
            }
        });
        return file;
    }
}
