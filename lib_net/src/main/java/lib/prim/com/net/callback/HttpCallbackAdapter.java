package lib.prim.com.net.callback;

import lib.prim.com.net.model.Progress;
import okhttp3.Call;
import okhttp3.Response;
import lib.prim.com.net.request.base.BaseRequest;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/10 0010
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class HttpCallbackAdapter<T> implements HttpCallback<T> {
    @Override
    public void onStart(BaseRequest<T, ? extends BaseRequest> request, int id) {

    }

    @Override
    public T convertResponse(Response response, int id) throws Throwable {
        return null;
    }

    @Override
    public void onSuccess(T response, int id) {

    }

    @Override
    public void onFinish(int id) {

    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void downloadProgress(Progress progress) {

    }
}
