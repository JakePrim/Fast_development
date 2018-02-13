package lib.prim.com.net.callback.base;

import lib.prim.com.net.model.Progress;
import okhttp3.Call;
import lib.prim.com.net.converter.Converter;
import lib.prim.com.net.request.base.BaseRequest;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/11 0011
 * 描    述：基础网络请求回调
 * 以下为各种情况的回调顺序：
 * 请求成功: onStart --> convertResponse --> onSuccess --> onFinish
 * 请求失败: onStart --> onError --> onFinish
 * 解析失败: onStart --> convertResponse --> onError --> onFinish
 * 文件下载: onStart --> convertResponse --> downloadProgress --> onSuccess --> onFinish
 * 文件异常: onStart --> convertResponse --> onError --> onFinish
 * 修订历史：参考写okgo 大神的代码 从分析 okgo 给了我跟多思路和经验非常感谢
 * ================================================
 */
public interface Callback<T> extends Converter<T> {

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
    void uploadProgress(Progress progress);

    /** 下载过程中的进度回调，UI线程 */
    void downloadProgress(Progress progress);
}
