package com.linksu.fast.coding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.linksu.fast.coding.baselibrary.base.activity.LBaseActivity;
import com.linksu.fast.coding.baselibrary.enetity.BaseEventBusBean;
import com.linksu.fast.coding.baselibrary.utils.PrimLogger;
import com.linksu.fast.coding.bean.ServerModel;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import lib.prim.com.net.callback.FileCallback;
import lib.prim.com.net.callback.JsonCallback;
import lib.prim.com.net.model.LzyResponse;
import lib.prim.com.net.model.Progress;
import lib.prim.com.net.request.base.BaseRequest;
import lib.prim.com.net.PrimHttp;
import lib.prim.com.net.callback.DialogCallback;
import lib.prim.com.net.request.base.ProgressRequestBody;

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

        //json 带自定义加载弹窗的 post请求
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
//        //json 带自定义加载弹窗的 get请求
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

        //json 不带加载弹窗的 get 请求
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
//        //json 不带加载弹窗的 post 请求
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
//                .enqueue(new FileCallback("prim.apk") {
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
//                .enqueue(new FileCallback("prim.apk") {
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

        //上传请求 post
        HashMap<String, String> params = new HashMap<>();
        params.put("key1", "value1");
        params.put("key2", "这里是需要提交的json格式数据");
        params.put("key3", "也可以使用三方工具将对象转成json字符串");
        params.put("key4", "其实你怎么高兴怎么写都行");
        JSONObject jsonObject = new JSONObject(params);
        PrimHttp.getInstance()
                .<LzyResponse<ServerModel>>post("http://server.jeasonlzy.com/OkHttpUtils/download")
                .tag(this)
                .id(3)
                .setUploadInterceptor(new ProgressRequestBody.UploadInterceptor() {
                    @Override
                    public void uploadProgress(Progress progress) {

                    }
                }).enqueue(new JsonCallback<LzyResponse<ServerModel>>() {
                    @Override
                    public void onSuccess(LzyResponse<ServerModel> response, int id) {
                        super.onSuccess(response, id);
                    }
                });
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
