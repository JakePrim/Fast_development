package lib.linksu.fast.coding.baselibrary.enetity;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/6 0006
 * 描    述：EventBus传递消息总体类
 * 修订历史：
 * ================================================
 */
public class BaseEventBusBean<T> {
    private int eventCode = -1;

    private T data;

    public BaseEventBusBean(int eventCode) {
        this.eventCode = eventCode;
    }

    public BaseEventBusBean(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    public int getEventCode() {
        return eventCode;
    }

    public T getData() {
        return data;
    }
}
