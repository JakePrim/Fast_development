package com.linksu.fast.coding;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.linksu.fast.coding.baselibrary.base.activity.LBaseActivity;
import com.linksu.fast.coding.baselibrary.enetity.BaseEventBusBean;
import com.linksu.fast.coding.baselibrary.utils.PrimLogger;
import com.linksu.fast.coding.bean.ServerModel;

import java.io.File;
import java.util.ArrayList;

import lib.prim.com.net.callback.DialogCallback;
import lib.prim.com.net.callback.UploadDialogCallback;
import lib.prim.com.net.model.LzyResponse;
import lib.prim.com.net.request.base.BaseRequest;
import lib.prim.com.net.PrimHttp;
import okhttp3.Call;

import static lib.prim.com.net.converter.FileConvert.DM_TARGET_FOLDER;

public class AbsFastActivity extends LBaseActivity {

    private TextView tv_test;

    @Override
    protected void operateArgs() {
        Log.e("linksu",
                "operateArgs(AbsFastActivity.java:20)");
    }

    @Override
    protected void initViewsAndEvent(Bundle savedInstanceState) {
        Log.e("linksu",
                "initViewsAndEvent(AbsFastActivity.java:26)");
        tv_test = findAviewById(R.id.tv_test);
        loadData();
    }

    @Override
    protected int getContentViewById() {
        return R.layout.activity_abs_fast;
    }

    @Override
    protected boolean openEventBus() {
        return false;
    }

    @Override
    protected void getEventBean(BaseEventBusBean event) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void loadData() {
        //https://api.douban.com/v2/book/1220562
        //httpUtil.get("/v2/movie/subject/1764796", 1, TestBean.class);
        //httpUtil.get("/v2/movie/in_theaters", 2, TestBean.class);

        //json 带自定义加载弹窗的 post请求 jsonObject
//        PrimHttp.getInstance()
//                .<LzyResponse<ServerModel>>post("http://server.jeasonlzy.com/OkHttpUtils/jsonObject")
//                .id(1)
//                .tag(TAG)
//                .params("id", "0")
//                .enqueue(new DialogCallback<LzyResponse<ServerModel>>(this) {
//                    @Override
//                    public void onSuccess(LzyResponse<ServerModel> response, int id) {
//                        super.onSuccess(response, id);
//                        PrimLogger.e(TAG, response.toString());
//                    }
//                });
//
//        //json 带自定义加载弹窗的 get请求 jsonObject
//        PrimHttp.getInstance()
//                .<LzyResponse<ServerModel>>get("http://server.jeasonlzy.com/OkHttpUtils/jsonObject")
//                .id(0)
//                .tag(TAG)
//                .params("id", "0")
//                .enqueue(new DialogCallback<LzyResponse<ServerModel>>(this) {
//                    @Override
//                    public void onSuccess(LzyResponse<ServerModel> response, int id) {
//                        super.onSuccess(response, id);
//                        PrimLogger.e(TAG, response.toString());
//                    }
//                });

        //json 不带加载弹窗的 get 请求 jsonObject
//        PrimHttp.getInstance()
//                .<LzyResponse<ServerModel>>get("http://server.jeasonlzy.com/OkHttpUtils/jsonObject")
//                .id(0)
//                .tag(TAG)
//                .params("id", "0")
//                .enqueue(new JsonCallback<LzyResponse<ServerModel>>() {
//                    @Override
//                    public void onSuccess(LzyResponse<ServerModel> response, int id) {
//                        super.onSuccess(response, id);
//                        PrimLogger.e(TAG, response.toString());
//                    }
//                });
//
//        //json 不带加载弹窗的 post 请求 jsonObject
//        PrimHttp.getInstance()
//                .<LzyResponse<ServerModel>>post("http://server.jeasonlzy.com/OkHttpUtils/jsonObject")
//                .id(0)
//                .tag(TAG)
//                .params("id", "0")
//                .enqueue(new JsonCallback<LzyResponse<ServerModel>>() {
//                    @Override
//                    public void onSuccess(LzyResponse<ServerModel> response, int id) {
//                        super.onSuccess(response, id);
//                    }
//                });

        //文件下载 get 请求
//        PrimHttp.getInstance()
//                .<File>get("http://server.jeasonlzy.com/OkHttpUtils/download")
//                .tag(this)
//                .params("p1", "p1")
//                .id(2)
//                .enqueue(new DownloadCallback("prim.apk") {
//                    @Override
//                    public void onStart(BaseRequest<File, ? extends BaseRequest> request, int id) {
//                        tv_test.setText("开始下载 --> " + id);
//                    }
//
//                    @Override
//                    public void downloadProgress(Progress progress) {
//                        PrimLogger.e(TAG, "下载进度 --> " + progress.fraction);
//                        tv_test.setText("下载进度:" + (progress.fraction * 100) + "%" + " 下载速度:" + (progress.speed / 1024) + "K/s");
//                    }
//
//                    @Override
//                    public void onSuccess(File response, int id) {
//                        tv_test.setText("下载成功 --> " + id);
//                    }
//                });

        //文件下载 post 请求
//        PrimHttp.getInstance()
//                .<File>post("http://server.jeasonlzy.com/OkHttpUtils/download")
//                .tag(this)
//                .params("p1", "p1")
//                .id(2)
//                .enqueue(new DownloadCallback("prim.apk") {
//                    @Override
//                    public void onStart(BaseRequest<File, ? extends BaseRequest> request, int id) {
//                        tv_test.setText("开始下载 --> " + id);
//                    }
//
//                    @Override
//                    public void downloadProgress(Progress progress) {
//                        PrimLogger.e(TAG, "下载进度 --> " + progress.fraction);
//                        tv_test.setText("下载进度:" + (progress.fraction * 100) + "%" + " 下载速度:" + (progress.speed / 1024) + "K/s");
//                    }
//
//                    @Override
//                    public void onSuccess(File response, int id) {
//                        tv_test.setText("下载成功 --> " + id);
//                    }
//                });

        //文件弹窗下载 post
//        PrimHttp.getInstance()
//                .<File>post("http://server.jeasonlzy.com/OkHttpUtils/download")
//                .tag(this)
//                .params("p1", "p1")
//                .id(2).enqueue(new DownloadDialogCallback(this, "prim.apk"));

        //文件弹窗下载 get
//        PrimHttp.getInstance()
//                .<File>get("http://server.jeasonlzy.com/OkHttpUtils/download")
//                .tag(this)
//                .params("p1", "p1")
//                .id(2).enqueue(new DownloadDialogCallback(this, "prim.apk"));


        final File file = new File(Environment.getExternalStorageDirectory() + DM_TARGET_FOLDER + "prim.apk");
        ArrayList<File> files = new ArrayList<>();
        files.add(file);
//        files.add(file);
//        files.add(file);
//        files.add(file);
        //只上传文件 post
//        PrimHttp.getInstance()
//                .<File>post("http://server.jeasonlzy.com/OkHttpUtils/upload")
//                .tag(this)
//                .id(3)
//                .onlyUpFile(file)
//                .enqueue(new UploadCallback(file) {
//                    @Override
//                    public void onSuccess(File response, int id) {
//                        super.onSuccess(response, id);
//                        PrimLogger.e("onSuccess" + response.getName());
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        super.onError(call, e, id);
//                        PrimLogger.e("onError");
//                    }
//
//                    @Override
//                    public void uploadProgress(Progress progress) {
//                        super.uploadProgress(progress);
//                        PrimLogger.e("uploadProgress");
//                        PrimLogger.e(progress.fraction + "%");
//                    }
//
//                    @Override
//                    public void onStart(BaseRequest<File, ? extends BaseRequest> request, int id) {
//                        super.onStart(request, id);
//                        PrimLogger.e("onStart");
//                    }
//                });

        //文件弹窗只上传一个文件 post
//        PrimHttp.getInstance()
//                .post("http://server.jeasonlzy.com/OkHttpUtils/upload")
//                .tag(this)
//                .id(3)
//                .onlyUpFile(file)
//                .enqueue(new UploadDialogCallback(this, file));

        //同一个key，上传多个文件
//        PrimHttp.getInstance().post("http://server.jeasonlzy.com/OkHttpUtils/upload")
//                .tag(this)
//                .id(4)
//                .params("file", files)// 这种方式为同一个key，上传多个文件
//                .enqueue(new UploadDialogCallback(this, files));

        //一个key，对应一个文件 上传文件
        PrimHttp.getInstance().<LzyResponse<ServerModel>>post("http://server.jeasonlzy.com/OkHttpUtils/upload")
                .tag(this)
                .id(4)
                .params("param2", "paramValue2")
                .params("file1", file)
                .enqueue(new UploadDialogCallback<LzyResponse<ServerModel>>(this));

        //上传普通数据
//        PrimHttp.getInstance().<LzyResponse<ServerModel>>post("http://server.jeasonlzy.com/OkHttpUtils/uploadString")
//                .tag(this)
//                .id(4)
//                .onlyUpString("这是要上传的文本数据")
//                .enqueue(new DialogCallback<LzyResponse<ServerModel>>(this) {
//                    @Override
//                    public void onStart(BaseRequest<LzyResponse<ServerModel>, ? extends BaseRequest> request, int id) {
//                        super.onStart(request, id);
//                        PrimLogger.e("开始请求");
//                    }
//
//                    @Override
//                    public void onSuccess(LzyResponse<ServerModel> response, int id) {
//                        super.onSuccess(response, id);
//                        PrimLogger.e("成功:" + response.toString());
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        super.onError(call, e, id);
//                        PrimLogger.e("失败:");
//                    }
//
//                    @Override
//                    public void onFinish(int id) {
//                        super.onFinish(id);
//                    }
//                });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void onRetryClick() {
        loadData();
    }
}
