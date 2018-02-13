package lib.prim.com.net.request.base;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import lib.prim.com.net.model.HttpParams;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import lib.prim.com.net.utils.Utils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/10 0010
 * 描    述：有RequestBody post 方式请求数据
 * 修订历史：
 * ================================================
 */
public abstract class HasBodyRequest<T, R extends HasBodyRequest> extends BaseRequest<T, R> {

    protected boolean isMultipart = false;  //是否强制使用 multipart/form-data 表单上传 键值对上传参数
    protected boolean isSpliceUrl = false;  //是否拼接url参数

    protected RequestBody requestBody;

    protected transient MediaType mediaType; //上传的类型
    protected File file;                     //上传的文件
    protected byte[] bytes;                  //上传字节

    protected String content;                //上传文本内容

    public HasBodyRequest(String url) {
        super(url);
    }

    @SuppressWarnings("unchecked")
    public R isMultipart(boolean isMultipart) {
        this.isMultipart = isMultipart;
        return (R) this;
    }

    /** 上传一个文件 一个key对应一个文件 */
    @SuppressWarnings("unchecked")
    public R params(String key, File file) {
        params.put(key, file);
        return (R) this;
    }

    /** 上传多个文件 一个key对应多个文件 */
    @SuppressWarnings("unchecked")
    public R params(String key, List<File> files) {
        params.putFiles(key, files);
        return (R) this;
    }

    /** 只上传一个文件没有其他参数 用此方法设置其他参数无效 header 参数有效 */
    @SuppressWarnings("unchecked")
    public R onlyUpFile(File file) {
        this.file = file;
        this.mediaType = Utils.guessMimeType(file.getName());
        return (R) this;
    }

    /** 只上传一个文件没有其他参数 用此方法设置其他参数无效 header 参数有效 */
    @SuppressWarnings("unchecked")
    public R onlyUpFile(File file, MediaType mediaType) {
        this.file = file;
        this.mediaType = mediaType;
        return (R) this;
    }

    /** 只上传字符串 用此方法设置其他参数无效 header 参数有效 */
    @SuppressWarnings("unchecked")
    public R onlyUpJson(String json) {
        this.content = json;
        mediaType = HttpParams.MEDIA_TYPE_JSON;
        return (R) this;
    }

    /** 只上传字符串 用此方法设置其他参数无效 header 参数有效 */
    @SuppressWarnings("unchecked")
    public R onlyUpJson(JSONArray json) {
        this.content = json.toString();
        mediaType = HttpParams.MEDIA_TYPE_JSON;
        return (R) this;
    }

    /** 只上传字符串 用此方法设置其他参数无效 header 参数有效 */
    @SuppressWarnings("unchecked")
    public R onlyUpJson(JSONObject json) {
        this.content = json.toString();
        mediaType = HttpParams.MEDIA_TYPE_JSON;
        return (R) this;
    }

    /** 只上传字符串 用此方法设置其他参数无效 header 参数有效 */
    @SuppressWarnings("unchecked")
    public R onlyUpString(String str) {
        this.content = str;
        mediaType = HttpParams.MEDIA_TYPE_PLAIN;
        return (R) this;
    }

    /** 只上传字节 用此方法设置其他参数无效 header 参数有效 */
    @SuppressWarnings("unchecked")
    public R onlyUpByte(byte[] bytes) {
        this.bytes = bytes;
        return (R) this;
    }


    @Override
    protected RequestBody generateRequestBody() {

        if (bytes != null && mediaType != null) return RequestBody.create(mediaType, bytes);
        if (content != null && mediaType != null) return RequestBody.create(mediaType, content);
        if (file != null && mediaType != null) return RequestBody.create(mediaType, file);

        return Utils.generateMultipartRequestBody(params, isMultipart);
    }

    protected okhttp3.Request.Builder generateRequestBuilder(RequestBody requestBody) {
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
        return Utils.appendHeaders(requestBuilder, headers);
    }
}
