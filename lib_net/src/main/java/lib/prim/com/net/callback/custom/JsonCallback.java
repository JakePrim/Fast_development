package lib.prim.com.net.callback.custom;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lib.prim.com.net.callback.base.CallbackAdapter;
import lib.prim.com.net.converter.JsonConvert;
import lib.prim.com.net.request.base.BaseRequest;
import lib.prim.com.net.utils.Utils;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/10 0010
 * 描    述：json 的回调 参考 okgo
 * 修订历史：
 * ================================================
 */
public class JsonCallback<T> extends CallbackAdapter<T> {
    private Type type;
    private Class<T> clazz;

    public JsonCallback() {
    }

    public JsonCallback(Type type) {
        this.type = type;
    }

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onStart(BaseRequest<T, ? extends BaseRequest> request, int id) {
        super.onStart(request, id);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     */
    @Override
    public T convertResponse(final Response response, final int id) throws Throwable {
        if (type == null) {
            if (clazz == null) {
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                JsonConvert<T> convert = new JsonConvert<>(clazz);
                return convert.convertResponse(response, id);
            }
        }
        final JsonConvert<T> convert = new JsonConvert<>(type);
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    onSuccess(convert.convertResponse(response, id), id);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                onFinish(id);
            }
        });
        return convert.convertResponse(response, id);

    }


}
