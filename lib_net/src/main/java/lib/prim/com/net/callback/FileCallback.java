package lib.prim.com.net.callback;

import java.io.File;

import lib.prim.com.net.converter.FileConvert;
import lib.prim.com.net.utils.Utils;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/11 0011
 * 描    述：文件下载的调度 若需要弹出下载框 重写一个callback 集成 此类FileCallback
 * note : api >=23 创建文件夹和文件需要申请权限
 * 修订历史：
 * ================================================
 */
public class FileCallback extends CallbackAdapter<File> {

    private FileConvert fileConvert;

    public FileCallback() {
        this(null);
    }

    public FileCallback(String fileName) {
        this(null, fileName);
    }

    public FileCallback(String fileDir, String fileName) {
        fileConvert = new FileConvert(fileDir, fileName);
        fileConvert.setCallback(this);
    }

    @Override
    public File convertResponse(Response response, final int id) throws Throwable {
        final File file = fileConvert.convertResponse(response, id);
        response.close();
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
