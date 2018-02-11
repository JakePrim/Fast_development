package lib.prim.com.net.converter;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import lib.prim.com.net.callback.HttpCallback;
import lib.prim.com.net.model.Progress;
import lib.prim.com.net.utils.IOUtils;
import lib.prim.com.net.utils.Utils;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/11 0011
 * 描    述：文件转换器
 * 修订历史：
 * ================================================
 */
public class FileConvert implements Converter<File> {

    public static final String DM_TARGET_FOLDER = File.separator + "download" + File.separator; //下载目标文件夹

    private String folder;                  //目标文件存储的文件夹路径
    private String fileName;                //目标文件存储的文件名
    private HttpCallback<File> callback;        //下载回调

    private static final String TAG = "FileConvert";

    public FileConvert() {
        this(null);
    }

    public FileConvert(String fileName) {
        this(Environment.getExternalStorageDirectory() + DM_TARGET_FOLDER, fileName);
    }

    public FileConvert(String folder, String fileName) {
        this.folder = folder;
        this.fileName = fileName;
    }

    public void setCallback(HttpCallback<File> callback) {
        this.callback = callback;
    }

    @Override
    public File convertResponse(Response response, int id) throws Throwable {
        String url = response.request().url().toString();
        if (TextUtils.isEmpty(folder))
            folder = Environment.getExternalStorageDirectory() + DM_TARGET_FOLDER;
        if (TextUtils.isEmpty(fileName)) fileName = Utils.getNetFileName(url);

        File dir = new File(folder);//创建一个文件夹
        IOUtils.createFolder(dir);//判断文件夹是否存在 若存在则删除在重新创建
        File file = new File(dir, fileName);//在文件夹中创建文件
        IOUtils.delFileOrFolder(file);//若下载的文件存在将其删除

        InputStream bodyStream = null;//输入流
        byte[] buffer = new byte[8192];
        FileOutputStream fileOutputStream = null;//输出流
        try {
            ResponseBody body = response.body();
            if (body == null) return null;
            bodyStream = body.byteStream();
            Progress progress = new Progress();
            progress.totalSize = body.contentLength();
            progress.fileName = fileName;
            progress.filePath = file.getAbsolutePath();
            progress.status = Progress.LOADING;
            progress.url = url;
            progress.tag = url;
            int len;
            fileOutputStream = new FileOutputStream(file);
            while ((len = bodyStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
                if (callback == null) continue;
                Progress.changeProgress(progress, len, new Progress.Action() {
                    @Override
                    public void call(Progress progress) {
                        onProgresss(progress);
                    }
                });
            }
            fileOutputStream.flush();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(bodyStream);
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    /** 注意转换类在子线程中 进度需要在UI */
    private void onProgresss(final Progress progress) {
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callback.downloadProgress(progress);
            }
        });
    }
}
