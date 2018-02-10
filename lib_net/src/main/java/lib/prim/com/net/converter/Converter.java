package lib.prim.com.net.converter;

import okhttp3.Response;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/9 0009
 * 描    述：网络请求返回的数据的转换接口
 * 修订历史：
 * ================================================
 */
public interface Converter<T> {

    /**
     * 拿到响应后，将数据转换成需要的格式，子线程中执行，可以是耗时操作
     *
     * @param response
     *         需要转换的对象
     *
     * @return 返回转换的结果
     *
     * @throws Throwable
     */
    T convertResponse(Response response, int id) throws Throwable;
}
