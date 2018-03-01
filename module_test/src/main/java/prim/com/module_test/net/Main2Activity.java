package prim.com.module_test.net;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import lib.linksu.fast.coding.baselibrary.utils.PrimLogger;

import lib.prim.com.net.PrimHttp;
import lib.prim.com.net.callback.DialogCallback;
import lib.prim.com.net.model.LzyResponse;
import lib.prim.com.net.request.base.BaseRequest;
import okhttp3.Call;
import okhttp3.Response;
import prim.com.module_test.R;

public class Main2Activity extends AppCompatActivity {

    private RadioGroup test_net_type_group, upType, requestCall;

    private EditText et_url;

    private int modthType;// 1 get 2 post

    private int requestTypes;// 1 json 2 下载 3 上传

    private int uploadTypes;


    private String hint;

    private int callType;// 1 加载回调 2 进度回调 3 not

    private static final String TAG = Main2Activity.class.getName();

    private TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        log = (TextView) findViewById(R.id.log);
        log.setMovementMethod(ScrollingMovementMethod.getInstance());
        initView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest(view);
            }
        });

        PrimHttp.getInstance()
                .<LzyResponse<ServerModel>>get("http://server.jeasonlzy.com/OkHttpUtils/jsonObject")
                .id(0)
                .tag(TAG)
                .params("id", "0")
                .enqueue(new DialogCallback<LzyResponse<ServerModel>>(this) {
                    @Override
                    public void onSuccess(LzyResponse<ServerModel> response, int id) {
                        super.onSuccess(response, id);
                        PrimLogger.e(TAG, response.toString());
                    }
                });
    }

    private void sendRequest(View view) {
        String message = "";
        if (modthType == 0) {
            message = "请选择请求方式!";
        } else if (requestTypes == 0) {
            message = "请选择请求类型";
        } else if (callType == 0) {
            message = "请选择请求回调类型";
        } else {
            if (requestTypes == 3) {
                if (uploadTypes == 0) {
                    message = "请选择上传类型";
                }
            }
        }
        if (!TextUtils.isEmpty(message)) {
            sandbar(view, message);
        } else {
            sandbar(view, "网络请求已发送");
            startRequest();
        }
    }

    private void startRequest() {
        final StringBuffer sf = new StringBuffer();
        if (modthType == 1) {//Get 方式
            if (requestTypes == 1) {//json请求
                et_url.setText(hint + "jsonObject");
                if (callType == 1) {//加载弹窗
                    PrimHttp.getInstance()
                            .<LzyResponse<ServerModel>>get(et_url.getText().toString())
                            .id(0)
                            .tag(TAG)
                            .params("id", "0")
                            .enqueue(new DialogCallback<LzyResponse<ServerModel>>(this) {
                                @Override
                                public void onStart(BaseRequest<LzyResponse<ServerModel>, ? extends BaseRequest> request, int id) {
                                    super.onStart(request, id);
                                    sf.append("开始请求:onStart() " +
                                            "\n 请求方式:GET " +
                                            "\n 请求回调类型:加载loading弹窗 " +
                                            "\n 请求URL:" + et_url.getText() +
                                            "\n 请求TAG:" + TAG +
                                            "\n 请求ID:" + id +
                                            "\n 请求参数:" + request.getParams().commonParams +
                                            "\n 代码:  PrimHttp.getInstance() " +
                                            "\n .<LzyResponse<ServerModel>>get(et_url.getText().toString())) " +
                                            "\n .id(0))" +
                                            "\n .tag(TAG))" +
                                            "\n .params(\"id\", \"0\") " +
                                            "\n .enqueue(new DialogCallback<LzyResponse<ServerModel>>(this)");
                                    log.append(sf.toString());
                                }

                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    super.onError(call, e, id);
                                    sf.append("\n 请求失败:onError()");
                                    log.append(sf.toString());
                                }

                                @Override
                                public void onSuccess(LzyResponse<ServerModel> response, int id) {
                                    super.onSuccess(response, id);
                                    sf.append("\n 请求成功:onSuccess()" +
                                            "\n 得到的数据:" + response.toString());
                                    log.setText(sf.toString());
                                }

                                @Override
                                public void onFinish(int id) {
                                    super.onFinish(id);
                                    sf.append("\n 请求完成:最终方法 onFinish() 所有的回调最后都会走此方法");
                                    log.setText(sf.toString());
                                }
                            });
                }
            } else if (requestTypes == 2) {//下载请求

            }
        }
    }

    private void sandbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void initView() {
        test_net_type_group = (RadioGroup) findViewById(R.id.test_net_type_group);
        test_net_type_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.get:
                        modthType = 1;
                        break;
                    case R.id.post:
                        modthType = 2;
                        break;
                }
            }
        });

        et_url = (EditText) findViewById(R.id.et_url);
        hint = et_url.getHint().toString();
        upType = (RadioGroup) findViewById(R.id.upType);

        final RadioGroup requestType = (RadioGroup) findViewById(R.id.requestType);
        requestType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.json:
                        requestTypes = 1;
                        upType.setVisibility(View.GONE);
                        break;
                    case R.id.download:
                        requestTypes = 2;
                        upType.setVisibility(View.GONE);
                        break;
                    case R.id.up:
                        requestTypes = 3;
                        upType.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        upType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.up_one:
                        uploadTypes = 1;
                        break;
                    case R.id.up_more:
                        uploadTypes = 2;
                        break;
                    case R.id.up_string:
                        uploadTypes = 3;
                        break;
                    case R.id.up_bytes:
                        uploadTypes = 4;
                        break;
                }
            }
        });

        requestCall = (RadioGroup) findViewById(R.id.requestCall);
        requestCall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.call_loading:
                        callType = 1;
                        break;
                    case R.id.call_dialog:
                        callType = 2;
                        break;
                    case R.id.call_not:
                        callType = 3;
                        break;
                }
            }
        });
    }

}
