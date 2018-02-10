package weather.linksu.com.nethttplibrary.model;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/10 0010
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SimpleResponse implements Serializable {
    private static final long serialVersionUID = -1477609349345966116L;

    public int code;
    public String msg;

    public LzyResponse toLzyResponse() {
        LzyResponse lzyResponse = new LzyResponse();
        lzyResponse.code = code;
        lzyResponse.msg = msg;
        return lzyResponse;
    }
}
