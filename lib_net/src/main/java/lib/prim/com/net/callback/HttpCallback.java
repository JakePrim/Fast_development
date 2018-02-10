package lib.prim.com.net.callback;

import okhttp3.Call;
import lib.prim.com.net.converter.Converter;
import lib.prim.com.net.request.base.BaseRequest;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/11 0011
 * 描    述：基础网络请求回调
 * 修订历史：
 * ================================================
 */
public interface HttpCallback<T> extends Converter<T> {

    /** 请求网络开始前，UI线程 */
    void onStart(BaseRequest<T, ? extends BaseRequest> request, int id);

    /** 对返回数据进行操作的回调， UI线程 */
    void onSuccess(T response, int id);

    /** 缓存成功的回调,UI线程 */
//    void onCacheSuccess(Response<T> response);

    /** 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程 */
    void onError(Call call, Exception e, int id);

    /** 请求网络结束后，UI线程 */
    void onFinish(int id);

    /** 上传过程中的进度回调，get请求不回调，UI线程 */
//    void uploadProgress(Progress progress);

    /** 下载过程中的进度回调，UI线程 */
//    void downloadProgress(Progress progress);

}
